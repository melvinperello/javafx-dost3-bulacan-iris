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
@Table(ScholarSubmissionModel.TABLE)
public class ScholarSubmissionModel extends PolarisRecord {

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
    public final static String TABLE = "scholar_submission";
    public final static String SUBMISSION_ID = "submission_id";
    public final static String FK_SCHOLAR_ID = "fk_scholar_id";
    public final static String FK_TRANSMITTAL_ID = "fk_transmittal_id";
    public final static String DOCUMENTS_SUBMITTED = "documents_submitted";
    public final static String REMARKS = "remarks";
    // Time
    public final static String UPDATED_AT = "updated_at";
    public final static String DELETED_AT = "deleted_at";

    //==========================================================================
    // 02. Model Fields
    //==========================================================================
    @PrimaryKey
    @Column(SUBMISSION_ID)
    private String submissionId;

    @Column(FK_SCHOLAR_ID)
    private String fkScholarId;

    @Column(FK_TRANSMITTAL_ID)
    private String fkTransmittalId;

    @Column(DOCUMENTS_SUBMITTED)
    private String documentsSubmitted;

    @Column(REMARKS)
    private String remarks;

    @Column(UPDATED_AT)
    private Date updatedAt;

    @Column(DELETED_AT)
    private Date deletedAt;

    //==========================================================================
    // 03. Constructor (Initialize Default Values)
    //==========================================================================
    public ScholarSubmissionModel() {
        this.fkScholarId = null;
        this.fkTransmittalId = null;
        this.documentsSubmitted = "";
        this.remarks = "";
        this.updatedAt = null;
        this.deletedAt = null;
    }

    //==========================================================================
    // 04-A. Static Inner Classes
    //==========================================================================
    // N/A
    //==========================================================================
    // 04-B. Static Class Methods
    //==========================================================================
    public static <T> List<T> listAllActive() throws SQLException {
        // Build Query
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE)
                .addStatement("WHERE")
                .addStatement(DELETED_AT)
                .addStatement("IS NULL");
        // Execute Query
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return new ScholarSubmissionModel().findMany(con, querySample);
        }
    }

    public static boolean insert(ScholarSubmissionModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    public static boolean update(ScholarSubmissionModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    public static boolean remove(ScholarSubmissionModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            // get server date.
            Date serverDate = Context.app().getServerDate();
            // update value
            model.setDeletedAt(serverDate);
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
    public String getSubmissionId() {
        return submissionId;
    }

    public String getFkScholarId() {
        return fkScholarId;
    }

    public String getFkTransmittalId() {
        return fkTransmittalId;
    }

    public String getDocumentsSubmitted() {
        return documentsSubmitted;
    }

    public String getRemarks() {
        return remarks;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
    //==========================================================================
    // 05-B. Setters
    //==========================================================================

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public void setFkScholarId(String fkScholarId) {
        this.fkScholarId = fkScholarId;
    }

    public void setFkTransmittalId(String fkTransmittalId) {
        this.fkTransmittalId = fkTransmittalId;
    }

    public void setDocumentsSubmitted(String documentsSubmitted) {
        this.documentsSubmitted = documentsSubmitted;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
