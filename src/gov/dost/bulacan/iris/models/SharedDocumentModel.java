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
@Table(SharedDocumentModel.TABLE)
public class SharedDocumentModel extends PolarisRecord implements TableAuditor {

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
    public final static String TABLE = "shared_documents";
    public final static String DOC_ID = "doc_id";
    public final static String FK_RAID_ID = "fk_raid_id";
    public final static String DOC_NAME = "doc_name";

    //==========================================================================
    // 02. Model Fields
    //==========================================================================
    @PrimaryKey
    @Column(DOC_ID)
    private String docId;

    @Column(FK_RAID_ID)
    private String raidId;

    @Column(DOC_NAME)
    private String docName;

    //==========================================================================
    // 03. Constructor (Initialize Default Values)
    //==========================================================================
    public SharedDocumentModel() {
        this.docId = null;
        this.raidId = null;
        this.docName = null;
    }

    //==========================================================================
    // 04-A. Static Inner Classes
    //==========================================================================
    // N/A
    //==========================================================================
    // 04-B. Static Class Methods
    //==========================================================================
    public static <T> List<T> listActiveFilesWithRaid() throws SQLException {
        // Build Query
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE)
                .addStatement("WHERE")
                .addStatement(DELETED_AT)
                .addStatement("IS NULL")
                .addStatement("ORDER BY")
                .addStatement(CREATED_AT)
                .addStatement("DESC");
        // Execute Query
        List<T> files = null;
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            files = new SharedDocumentModel().findMany(con, querySample);
            //------------------------------------------------------------------
            // If empty //
            if (files.isEmpty()) {
                return files;
            }
            //------------------------------------------------------------------
            //------------------------------------------------------------------
            // Where In Query to get the Partial Supplier Model
            SimpleQuery whereInQuery = new SimpleQuery();
            whereInQuery.addStatement("SELECT")
                    .addStatement("*")
                    .addStatement("FROM")
                    .addStatement(RaidModel.TABLE)
                    .addStatement("WHERE")
                    .addStatement(RaidModel.ID)
                    .addStatement("IN (");
            // Query Preamble End.
            //------------------------------------------------------------------
            // Iterate to the equipment to get IN parmeters of SUPPLIER CODE of EQUIPMENT QOUTATUION
            for (int ctr = 0; ctr < files.size(); ctr++) {
                SharedDocumentModel model = (SharedDocumentModel) files.get(ctr);
                String code = model.getRaidId();
                whereInQuery.addStatementWithParameter("?", code);

                // attach comma if not last
                if (ctr < (files.size() - 1)) {
                    whereInQuery.addStatement(",");
                }

            }
            whereInQuery.addStatement(")"); // Close Query Preamble
            //------------------------------------------------------------------
            List<RaidModel> raidList = new RaidModel().findMany(con, whereInQuery);
            //------------------------------------------------------------------
            if (raidList.isEmpty()) {
                return files;
            }
            //------------------------------------------------------------------
            //------------------------------------------------------------------
            // Compare SUPPLIER CODE to attach Model to EQUIP QOUTATION MODEL.
            for (T file : files) {
                // type case
                SharedDocumentModel shareModel = (SharedDocumentModel) file;
                // if no assigned supplier code skip this.
                if (shareModel.getRaidId() == null) {
                    continue;
                }
                //--------------------------------------------------------------
                for (RaidModel raid : raidList) {
                    if (shareModel.getRaidId().equalsIgnoreCase(raid.getId())) {
                        shareModel.setLinkedModel(raid);
                        break;
                    }
                }
            }
            //------------------------------------------------------------------
            //==================================================================
            return files;
        } // end try
    }

    public static boolean insert(SharedDocumentModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // establish raid reference
            RaidModel raid = model.getLinkedModel();
            raid.setReferenceState(RaidModel.ReferenceState.LINKED);
            raid.setReferenceDescription("SHARED DOCUMENTS");
            raid.auditCreate();
            //------------------------------------------------------------------
            // link document to raid
            model.setRaidId(raid.getId());
            model.setDocName(raid.getDisplayName());
            model.auditCreate();
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            // begin transaction
            con.transactionStart();

            if (raid.updateFull(con)) {
                if (model.insert(con)) {
                    con.transactionCommit();
                    return true;
                }
            }

            con.transactionRollBack();
            return false;
        } finally {
            if (con != null) {
                con.close(); // auto rollback
            }
        }
    }

    public static boolean update(SharedDocumentModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditUpdate();
            return model.updateFull(con);
        }
    }

    public static boolean remove(SharedDocumentModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            model.auditDelete();
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
    private RaidModel linkedModel;

    public RaidModel getLinkedModel() {
        return linkedModel;
    }

    public void setLinkedModel(RaidModel linkedModel) {
        this.linkedModel = linkedModel;
    }

    //==========================================================================
    // 05-A. Getters
    //==========================================================================
    public String getDocId() {
        return docId;
    }

    public String getRaidId() {
        return raidId;
    }

    public String getDocName() {
        return docName;
    }

    //==========================================================================
    // 05-B. Setters
    //==========================================================================
    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setRaidId(String raidId) {
        this.raidId = raidId;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    //==========================================================================
    // ANNEX-A. Table Audit
    //==========================================================================
    // Table Columns
    //--------------------------------------------------------------------------
    public final static String CREATED_BY = "created_by";
    public final static String CREATED_AT = "created_at";
    public final static String UPDATED_BY = "updated_by";
    public final static String UPDATED_AT = "updated_at";
    public final static String DELETED_BY = "deleted_by";
    public final static String DELETED_AT = "deleted_at";
    //--------------------------------------------------------------------------
    // Fields
    //--------------------------------------------------------------------------
    @Column(CREATED_BY)
    private String createdBy;

    @Column(CREATED_AT)
    private java.util.Date createdAt;

    @Column(UPDATED_BY)
    private String updatedBy;

    @Column(UPDATED_AT)
    private java.util.Date updatedAt;

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
        return (this.updatedBy == null) ? "" : this.updatedBy;
    }

    @Override
    public java.util.Date getUpdatedAt() {
        return (this.updatedAt == null) ? null : new Date(this.updatedAt.getTime());
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
        this.updatedBy = (updatedBy == null) ? "" : updatedBy;
    }

    @Override
    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = (updatedAt == null) ? null : new Date(updatedAt.getTime());
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

}
