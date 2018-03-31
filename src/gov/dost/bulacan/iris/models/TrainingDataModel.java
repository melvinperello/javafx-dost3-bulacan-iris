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
@Table(TrainingDataModel.TABLE)
public class TrainingDataModel extends PolarisRecord {

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
    public final static String TABLE = "training_data";
    /* #01 */
    public final static String DATA_CODE = "data_code";
    /* #02 */
    public final static String TRAINING_CODE = "fk_training_code";
    /* #03 */
    public final static String ENTRY_NO = "entry_no";
    /* #04 */
    public final static String A_GROUP = "a_group";
    /* #05 */
    public final static String B_GROUP = "b_group";
    /* #06 */
    public final static String C_GROUP = "c_group";
    /* #07 */
    public final static String OVERALL_RATING = "overall_rating";
    /* #08 */
    public final static String COMMENT = "comment";
    /* #09 */
    public final static String NAME = "name";
    /* #10 */
    public final static String DELETED_AT = "deleted_at";

    //==========================================================================
    // 02. Model Fields
    //==========================================================================
    @PrimaryKey
    @Column(DATA_CODE)
    private String dataCode;

    @Column(TRAINING_CODE)
    private String trainingCode;

    @Column(ENTRY_NO)
    private String entryNo;

    @Column(A_GROUP)
    private String aGroup;

    @Column(B_GROUP)
    private String bGroup;

    @Column(C_GROUP)
    private String cGroup;

    @Column(OVERALL_RATING)
    private String overallRating;

    @Column(COMMENT)
    private String comment;

    @Column(NAME)
    private String name;

    @Column(DELETED_AT)
    private Date deletedAt;

    //==========================================================================
    // 03. Constructor (Initialize Default Values)
    //==========================================================================
    public TrainingDataModel() {
        this.entryNo = "";
        this.aGroup = "";
        this.bGroup = "";
        this.cGroup = "";
        this.overallRating = "";
        this.comment = "";
        this.name = "";
        this.deletedAt = null;
    }

    //==========================================================================
    // 04-A. Static Inner Classes
    //==========================================================================
    // 
    // /* Static Classes Here */
    //
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
            return new TrainingDataModel().findMany(con, querySample);
        }
    }

    public static boolean insert(TrainingDataModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    public static boolean update(TrainingDataModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    public static boolean remove(TrainingDataModel model) throws SQLException {
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
    // 
    // /* Custom Methods Here */
    //
    //==========================================================================
    // 05-A. Getters
    //==========================================================================
    public String getDataCode() {
        return dataCode;
    }

    public String getTrainingCode() {
        return trainingCode;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public String getaGroup() {
        return aGroup;
    }

    public String getbGroup() {
        return bGroup;
    }

    public String getcGroup() {
        return cGroup;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    //==========================================================================
    // 05-B. Setters
    //==========================================================================
    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public void setaGroup(String aGroup) {
        this.aGroup = aGroup;
    }

    public void setbGroup(String bGroup) {
        this.bGroup = bGroup;
    }

    public void setcGroup(String cGroup) {
        this.cGroup = cGroup;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
