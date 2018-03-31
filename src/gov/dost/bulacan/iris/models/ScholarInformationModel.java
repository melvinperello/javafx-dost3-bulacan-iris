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
import static gov.dost.bulacan.iris.models.TrainingDataModel.DELETED_AT;
import static gov.dost.bulacan.iris.models.TrainingDataModel.TABLE;
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
@Table(ScholarInformationModel.TABLE)
public class ScholarInformationModel extends PolarisRecord {

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
    public final static String TABLE = "scholar_information";
    public final static String SCHOLAR_ID = "scholar_id";
    public final static String STUDENT_NUMBER = "student_number";
    public final static String LAST_NAME = "last_name";
    public final static String FIRST_NAME = "first_name";
    public final static String MIDDLE_NAME = "middle_name";
    public final static String EXT_NAME = "ext_name";
    public final static String GENDER = "gender";
    public final static String COURSE = "course";
    public final static String YEAR_SECTION = "year_section";
    public final static String UNIVERSITY = "university";
    public final static String MOBILE_NO = "mobile_no";
    public final static String TEL_NO = "tel_no";
    public final static String E_MAIL = "e_mail";
    // Time
    public final static String UPDATED_AT = "updated_at";
    public final static String DELETED_AT = "deleted_at";

    //==========================================================================
    // 02. Model Fields
    //==========================================================================
    @PrimaryKey
    @Column(SCHOLAR_ID)
    private String scholarId;

    @Column(STUDENT_NUMBER)
    private String studentNumber;

    @Column(LAST_NAME)
    private String lastName;

    @Column(FIRST_NAME)
    private String firstName;

    @Column(MIDDLE_NAME)
    private String middleName;

    @Column(EXT_NAME)
    private String extName;

    @Column(GENDER)
    private String gender;

    @Column(COURSE)
    private String course;

    @Column(YEAR_SECTION)
    private String yearSection;

    @Column(UNIVERSITY)
    private String university;

    @Column(MOBILE_NO)
    private String mobileNo;

    @Column(TEL_NO)
    private String telNo;

    @Column(E_MAIL)
    private String eMail;

    @Column(UPDATED_AT)
    private Date updatedAt;

    @Column(DELETED_AT)
    private Date deletedAt;

    //==========================================================================
    // 03. Constructor (Initialize Default Values)
    //==========================================================================
    public ScholarInformationModel() {
        this.studentNumber = "";
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.extName = "";
        this.gender = Gender.UNKNOWN;
        this.course = "";
        this.yearSection = "";
        this.university = "";
        this.mobileNo = "";
        this.telNo ="";
        this.eMail = "";
        this.updatedAt = null;
        this.deletedAt = null;
    }

    //==========================================================================
    // 04-A. Static Inner Classes
    //==========================================================================
    public final static class Gender {

        public final static String MALE = "MALE";
        public final static String FEMALE = "FEMALE";
        public final static String UNKNOWN = "UNKNOWN";

        public final static String[] LIST = new String[]{MALE, FEMALE};
    }

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
            return new ScholarInformationModel().findMany(con, querySample);
        }
    }

    public static boolean insert(ScholarInformationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    public static boolean update(ScholarInformationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    public static boolean remove(ScholarInformationModel model) throws SQLException {
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
    public String getFullName() {
        return this.getLastName() + ", "
                + this.getFirstName() + " "
                + this.getMiddleName() + " "
                + this.getExtName();
    }

    //==========================================================================
    // 05-A. Getters
    //==========================================================================
    public String getScholarId() {
        return scholarId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getExtName() {
        return extName;
    }

    public String getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }

    public String getYearSection() {
        return yearSection;
    }

    public String getUniversity() {
        return university;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public String geteMail() {
        return eMail;
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
    public void setScholarId(String scholarId) {
        this.scholarId = scholarId;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYearSection(String yearSection) {
        this.yearSection = yearSection;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
