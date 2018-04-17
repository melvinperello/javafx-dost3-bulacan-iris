/**
 * Information Retrieval Integrated System ( I.R.I.S. )
 * Republic of The Philippines, DOST Regional Office No. III
 * Provincial Science Technology Center, City of Malolos, Bulacan
 *
 * Afterschool Creatives "Captivating Creativity"
 *
 * Copyright 2018 Jhon Melvin Perello
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package gov.dost.bulacan.iris.models;

import gov.dost.bulacan.iris.Context;
import static gov.dost.bulacan.iris.models.ScholarInformationModel.DELETED_AT;
import static gov.dost.bulacan.iris.models.ScholarInformationModel.TABLE;
import gov.dost.bulacan.iris.models.ext.TableAuditor;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(RaidModel.TABLE)
public class RaidModel extends PolarisRecord implements TableAuditor {

    //==========================================================================
    // Afterschool Creatives Polaris Record Content Standardization
    //==========================================================================
    // Sections
    // 01. Table Columns
    // 02. Model Fields
    // 03. Constructor (Initialize Default Values)
    // 04-A. Static Inner Classes
    // 04-B. Static Methods (Mostly Database Queries)
    // 04-C. Custom Methods (Non-Static Methods)
    // 05-A. Getters
    // 05-B. Setters
    //
    // To standardized the creation of PolarisRecord classes. a complete child
    // class must contain all of the following sections mentioned above.
    //
    // All Child classes must contain this standardization notice for reference.
    //
    // Standardization Code: 001 - 03/31/2018
    //==========================================================================
    // 01. Table Columns
    //==========================================================================
    public final static String TABLE = "raid_table";
    public final static String ID = "id"; // primary key
    public final static String FILE_DISPLAY_NAME = "file_display_name";
    public final static String FILE_PATH = "file_path"; // folder location
    public final static String FILE_NAME = "file_name"; // file name
    public final static String FILE_EXT = "file_ext"; // file extension
    public final static String FILE_SIZE = "file_size";
    public final static String FILE_HASH = "file_hash";
    public final static String REF_STATE = "reference_state";
    public final static String REF_DESCRIPTION = "reference_description";

    //==========================================================================
    // 02. Model Fields
    //==========================================================================
    @PrimaryKey
    @Column(ID)
    private String id;

    @Column(FILE_DISPLAY_NAME)
    private String displayName;

    @Column(FILE_PATH)
    private String path;

    @Column(FILE_NAME)
    private String name;

    @Column(FILE_EXT)
    private String extenstion;

    @Column(FILE_SIZE)
    private Long size;

    @Column(FILE_HASH)
    private String hash;

    @Column(REF_STATE)
    private Integer referenceState;

    @Column(REF_DESCRIPTION)
    private String referenceDescription;

    //==========================================================================
    // 03. Constructor (Initialize Default Values)
    //==========================================================================
    public RaidModel() {
        this.displayName = "";
        this.path = "";
        this.name = "";
        this.extenstion = "";
        this.size = 0L;
        this.hash = "";
        this.referenceState = ReferenceState.BROKEN;
        this.referenceDescription = "BROKEN";
    }

    //==========================================================================
    // 04-A. Static Inner Classes
    //==========================================================================
    public static class ReferenceState {

        /**
         * If the file was missing.
         */
        public final static int MISSING = -2;

        /**
         * If the file was no longer reference and deleted.
         */
        public final static int DELETED = -1;
        /**
         * The file was uploaded to the server but unreachable due to no
         * reference.
         */
        public final static int BROKEN = 0;
        /**
         * File was successfully uploaded to the server and reachable thru a
         * reference.
         */
        public final static int LINKED = 1;

    }

    //==========================================================================
    // 04-B. Static Class Methods
    //==========================================================================
    /**
     * List all active files in the RAID Array.
     *
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> List<T> listActiveRaidArray() throws SQLException {
        // Build Query
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE)
                .addStatement("WHERE")
                //
                .addStatement(DELETED_AT)
                .addStatement("IS NULL")
                //
                .addStatement("AND")
                .addStatement(CREATED_AT)
                .addStatement("IS NOT NULL")
                //
                .addStatement("AND")
                .addStatementWithParameter(REF_STATE + " = ?", ReferenceState.LINKED);

        // Execute Query
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return new RaidModel().findMany(con, querySample);
        }
    }

    //--------------------------------------------------------------------------
    public static boolean insert(RaidModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    public static boolean update(RaidModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    public static boolean locate(RaidModel model, String raidId) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.find(con, raidId);
        }
    }

    public static boolean remove(RaidModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            model.setReferenceState(RaidModel.ReferenceState.DELETED);
            model.auditDelete();
            //------------------------------------------------------------------
            // execute query.
            return model.updateFull(con);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static boolean markAsMissing(RaidModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            model.setReferenceState(RaidModel.ReferenceState.MISSING);
            model.auditDelete();
            //------------------------------------------------------------------
            // execute query.
            return model.updateFull(con);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    //==========================================================================
    // 04-C. Custom Methods
    //==========================================================================
    // N/A
    //==========================================================================
    // 05-A. Getters
    //==========================================================================
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getExtenstion() {
        return extenstion;
    }

    public Long getSize() {
        return size;
    }

    public String getHash() {
        return hash;
    }

    public Integer getReferenceState() {
        return referenceState;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    //==========================================================================
    // 05-B. Setters
    //==========================================================================
    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtenstion(String extenstion) {
        this.extenstion = extenstion;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setReferenceState(Integer referenceState) {
        this.referenceState = referenceState;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    //==========================================================================
    // ANNEX-A. Table Audit
    //==========================================================================
    // Table Columns
    //--------------------------------------------------------------------------
    public final static String CREATED_BY = "created_by";
    public final static String CREATED_AT = "created_at";
    public final static String DELETED_BY = "deleted_by";
    public final static String DELETED_AT = "deleted_at";
    //--------------------------------------------------------------------------
    // Fields
    //--------------------------------------------------------------------------
    @Column(CREATED_BY)
    private String createdBy;

    @Column(CREATED_AT)
    private java.util.Date createdAt;

    @Column(DELETED_BY)
    private String deletedBy;

    @Column(DELETED_AT)
    private java.util.Date deletedAt;

    //--------------------------------------------------------------------------
    // Getters
    //--------------------------------------------------------------------------
    @Override
    public String getCreatedBy() {
        return (this.createdBy == null) ? "" : this.createdBy;
    }

    @Override
    public java.util.Date getCreatedAt() {
        return (this.createdAt == null) ? null : new Date(this.createdAt.getTime());
    }

    @Override
    public String getUpdatedBy() {
//        return (this.updatedBy == null) ? "" : this.updatedBy;
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public java.util.Date getUpdatedAt() {
//        return (this.updatedAt == null) ? null : new Date(this.updatedAt.getTime());
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public String getDeletedBy() {
        return (this.deletedBy == null) ? "" : this.deletedBy;
    }

    @Override
    public java.util.Date getDeletedAt() {
        return (this.deletedAt == null) ? null : new Date(this.deletedAt.getTime());
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = (createdBy == null) ? "" : createdBy;
    }

    @Override
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = (createdAt == null) ? null : new Date(createdAt.getTime());
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
//        this.updatedBy = (updatedBy == null) ? "" : updatedBy;
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public void setUpdatedAt(java.util.Date updatedAt) {
//        this.updatedAt = (updatedAt == null) ? null : new Date(updatedAt.getTime());
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = (deletedBy == null) ? "" : deletedBy;
    }

    @Override
    public void setDeletedAt(java.util.Date deletedAt) {
        this.deletedAt = (deletedAt == null) ? null : new Date(deletedAt.getTime());
    }
    // </ANNEX-A. Table Audit> 

    @Override
    public String auditToString() {
        if (this.getCreatedAt() != null) {
            return "Entry Created by [ " + this.getCreatedBy() + " ] "
                    + " at " + Context.getDateFormat12().format(this.getCreatedAt());
        } else {
            return "No Audit History";
        }
    }

    @Override
    public void auditUpdate() {
        throw new UnsupportedOperationException("NO UPDATE");
    }

}
