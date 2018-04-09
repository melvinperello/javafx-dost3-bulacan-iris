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
@Table(TrainingModel.TABLE)
public class TrainingModel extends PolarisRecord implements TableAuditor {

    public final static String TABLE = "training";
    public final static String TRAINING_CODE = "training_code";
    public final static String TITLE_OF_TRAINING = "title_of_training";
    public final static String RESOURCE_SPEAKERS = "resource_speakers";
    public final static String VENUE = "venue";
    public final static String DATE_START = "date_start";
    public final static String DATE_END = "date_end";

    @PrimaryKey
    @Column(TRAINING_CODE)
    private String trainingCode;

    @Column(TITLE_OF_TRAINING)
    private String trainingTitle;

    @Column(RESOURCE_SPEAKERS)
    private String resourceSpeakers;

    @Column(VENUE)
    private String venue;

    @Column(DATE_START)
    private Date dateStart;

    @Column(DATE_END)
    private Date dateEnd;

    public TrainingModel() {
        this.trainingTitle = "";
        this.resourceSpeakers = "";
        this.venue = "";
        this.dateStart = null;
        this.dateEnd = null;
    }

    //==========================================================================
    // Methods
    //==========================================================================
    public static <T> List<T> listAllActive() throws SQLException {
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE)
                .addStatement("WHERE")
                .addStatement(DELETED_AT)
                .addStatement("IS NULL");
        //======================================================================
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return new TrainingModel().findMany(con, querySample);
        }
    }

    public static boolean insert(TrainingModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditCreate();
            return model.insert(con);
        }
    }

    public static boolean update(TrainingModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditUpdate();
            return model.updateFull(con);
        }
    }

    public static boolean remove(TrainingModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            // start transaction
            con.transactionStart();
            //------------------------------------------------------------------
            model.auditDelete();
            // execute query.
            boolean parentUpdated = model.update(con);
            //------------------------------------------------------------------
            // if project record update failed rollback and return false.
            if (!parentUpdated) {
                con.transactionRollBack();
                return false;
            }
            //------------------------------------------------------------------
            // Delete Related Contacts.
            //------------------------------------------------------------------
            // proceed to update related contacts deleted-at flag.
            SimpleQuery querySample = new SimpleQuery();
            querySample.addStatement("UPDATE")
                    .addStatement(TrainingDataModel.TABLE)
                    .addStatement("SET")
                    .addStatementWithParameter(TrainingDataModel.DELETED_AT + " = ?", Context.app().getServerDate())
                    .addStatement(",")
                    .addStatementWithParameter(TrainingDataModel.DELETED_BY + " = ?", Context.app().getAuditUser())
                    .addStatement("WHERE")
                    .addStatement(TrainingDataModel.DELETED_AT)
                    .addStatement("IS NULL")
                    .addStatement("AND")
                    .addStatementWithParameter(TrainingDataModel.TRAINING_CODE + " = ?", model.getTrainingCode());
            //------------------------------------------------------------------
            try {
                // execute update to contacts.
                con.update(querySample);
            } catch (SQLException e) {
                // if something happened wrong
                con.transactionRollBack();
                return false;
            }
            //------------------------------------------------------------------
            //  End Delete Contact Coverage
            //------------------------------------------------------------------
            // if nothing happened wrong
            con.transactionCommit();
            return true;
        } finally {
            if (con != null) {
                con.close(); // auto roll back
            }
        }
    }

    //==========================================================================
    // Getters
    //==========================================================================
    public String getTrainingCode() {
        return trainingCode;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public String getResourceSpeakers() {
        return resourceSpeakers;
    }

    public String getVenue() {
        return venue;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    //==========================================================================
    // Setters
    //==========================================================================
    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public void setResourceSpeakers(String resourceSpeakers) {
        this.resourceSpeakers = resourceSpeakers;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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
