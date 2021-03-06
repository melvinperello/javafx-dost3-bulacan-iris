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
import gov.dost.bulacan.iris.models.ext.ModelAccess;
import gov.dost.bulacan.iris.models.ext.TableAuditor;
import gov.dost.bulacan.iris.models.ext.UnknownModelValueException;
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
public class ScholarInformationModel extends PolarisRecord implements TableAuditor {

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
    public final static String SCHOLAR_TYPE = "scholar_type";
    public final static String MERIT_TYPE = "merit_type";
    public final static String COURSE = "course";
    public final static String YEAR = "year_level";
    public final static String SECTION = "section";
    public final static String UNIVERSITY = "university";
    public final static String MOBILE_NO = "mobile_no";
    public final static String TEL_NO = "tel_no";
    public final static String E_MAIL = "e_mail";
    public final static String STUDENT_ADDRESS = "student_address";
    public final static String STUDENT_CITY_MUNICIPALITY = "student_city_municipality";
    public final static String STUDENT_PROVINCE = "student_province";

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

    @Column(SCHOLAR_TYPE)
    private Integer scholarType;

    @Column(MERIT_TYPE)
    private Integer meritType;

    @Column(COURSE)
    private String course;

    @Column(YEAR)
    private Integer year;

    @Column(SECTION)
    private String section;

    @Column(UNIVERSITY)
    private String university;

    @Column(MOBILE_NO)
    private String mobileNo;

    @Column(TEL_NO)
    private String telNo;

    @Column(E_MAIL)
    private String mail;

    @Column(STUDENT_ADDRESS)
    private String studentAddress;

    @Column(STUDENT_CITY_MUNICIPALITY)
    private String studentCityMunicipality;

    @Column(STUDENT_PROVINCE)
    private String studentProvince;

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
        this.year = YearLevel.DEFAULT;
        this.section = "";
        this.university = "";
        this.mobileNo = "";
        this.telNo = "";
        this.mail = "";
        this.studentAddress = "";
        this.studentCityMunicipality = "";
        this.studentProvince = "";
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

    public final static class YearLevel {

        public final static int DEFAULT = 0;
        public final static int _1 = 1;
        public final static int _2 = 2;
        public final static int _3 = 3;
        public final static int _4 = 4;
        public final static int _5 = 5;

        public final static Integer[] LIST = new Integer[]{_1, _2, _3, _4, _5};

        public final static String toWord(int year) {
            switch (year) {
                case _1:
                    return "FIRST YEAR";
                case _2:
                    return "SECOND YEAR";
                case _3:
                    return "THIRD YEAR";
                case _4:
                    return "FOURTH YEAR";
                case _5:
                    return "FIFTH YEAR";
                default:
                    throw new UnknownModelValueException();
            }
        }

    }

    /**
     * Scholarship Types.
     */
    public final static class ScholarType {

        private final String name;
        private final Integer value;

        public ScholarType(String name, Integer code) {
            this.name = name;
            this.value = code;
        }

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name;
        }

        //----------------------------------------------------------------------
        public final static ScholarType JLSS = new ScholarType("RA 10612 Junior Level Science Scholarship", 2);
        public final static ScholarType UNDERGRAD = new ScholarType("RA 7687 Undergraduate Scholarship", 1);
        //----------------------------------------------------------------------

        public final static ScholarType[] LIST = new ScholarType[]{UNDERGRAD, JLSS};

        public static ScholarType toObject(Integer val) {
            for (ScholarType scholarType : LIST) {
                if (scholarType.getValue().intValue() == val.intValue()) {
                    return scholarType;
                }
            }
            throw new UnknownModelValueException();
        }

        /**
         * 3rd Degree Inner class from parent.
         */
        public final static class Merit {

            private final String name;
            private final Integer value;

            public String getName() {
                return name;
            }

            public Integer getValue() {
                return value;
            }

            public Merit(String name, Integer value) {
                this.name = name;
                this.value = value;
            }

            @Override
            public String toString() {
                return this.name;
            }

            //------------------------------------------------------------------
            public final static Merit MERIT = new Merit("MERIT", 2);
            public final static Merit NON_MERIT = new Merit("NON-MERIT", 1);
            //------------------------------------------------------------------

            public final static Merit[] LIST = new Merit[]{NON_MERIT, MERIT};

            public static Merit toObject(Integer val) {
                for (Merit meritType : LIST) {
                    if (meritType.getValue().intValue() == val.intValue()) {
                        return meritType;
                    }
                }
                throw new UnknownModelValueException();
            }
        }
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
            model.auditCreate();
            return model.insert(con);
        }
    }

    public static boolean update(ScholarInformationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditUpdate();
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
        return ModelAccess.textAccess(studentNumber);
    }

    public String getLastName() {
        return ModelAccess.textAccess(lastName);
    }

    public String getFirstName() {
        return ModelAccess.textAccess(firstName);
    }

    public String getMiddleName() {
        return ModelAccess.textAccess(middleName);
    }

    public String getExtName() {
        return ModelAccess.textAccess(extName);
    }

    public String getGender() {
        return ModelAccess.textAccess(gender);
    }

    public Integer getScholarType() {
        return scholarType;
    }

    public Integer getMeritType() {
        return meritType;
    }

    public String getCourse() {
        return ModelAccess.textAccess(course);
    }

    public Integer getYear() {
        return year;
    }

    public String getSection() {
        return ModelAccess.textAccess(section);
    }

    public String getUniversity() {
        return ModelAccess.textAccess(university);
    }

    public String getMobileNo() {
        return ModelAccess.textAccess(mobileNo);
    }

    public String getTelNo() {
        return ModelAccess.textAccess(telNo);
    }

    public String getMail() {
        return ModelAccess.textAccess(mail);
    }

    public String getStudentAddress() {
        return ModelAccess.textAccess(studentAddress);
    }

    public String getStudentCityMunicipality() {
        return ModelAccess.textAccess(studentCityMunicipality);
    }

    public String getStudentProvince() {
        return ModelAccess.textAccess(studentProvince);
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

    public void setScholarType(Integer scholarType) {
        this.scholarType = scholarType;
    }

    public void setMeritType(Integer meritType) {
        this.meritType = meritType;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setSection(String section) {
        this.section = section;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public void setStudentCityMunicipality(String studentCityMunicipality) {
        this.studentCityMunicipality = studentCityMunicipality;
    }

    public void setStudentProvince(String studentProvince) {
        this.studentProvince = studentProvince;
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
