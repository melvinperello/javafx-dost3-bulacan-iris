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
@Table(TrainingModel.TABLE)
public class TrainingModel extends PolarisRecord {

    public final static String TABLE = "training";
    public final static String TRAINING_CODE = "training_code";
    public final static String TITLE_OF_TRAINING = "title_of_training";
    public final static String RESOURCE_SPEAKERS = "resource_speakers";
    public final static String VENUE = "venue";
    public final static String DATE_START = "date_start";
    public final static String DATE_END = "date_end";
    public final static String DELETED_AT = "deleted_at";

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

    @Column(DELETED_AT)
    private Date deletedAt;

    @Column(DATE_END)
    private Date dateEnd;

    public TrainingModel() {
        this.trainingTitle = "";
        this.resourceSpeakers = "";
        this.venue = "";
        this.dateStart = null;
        this.dateEnd = null;
        this.deletedAt = null;
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
            return model.insert(con);
        }
    }

    public static boolean update(TrainingModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
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

    public Date getDeletedAt() {
        return deletedAt;
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

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

}
