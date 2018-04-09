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
 * Data Model that represents the SETUp Project Information.
 *
 * @author Jhon Melvin
 */
@Table(ProjectModel.TABLE)
public class ProjectModel extends PolarisRecord implements TableAuditor {

    /**
     * Initialize Default Values.
     */
    public ProjectModel() {
        this.projectCode = null;
        this.spinNo = "";
        this.companyName = "";
        this.projectName = "";
        this.projectStatus = null;
        this.projectType = "";
        this.companyOwner = "";
        this.history = "";
        this.ownerPosition = "";
        this.ownerAddress = "";
        //
        this.endorsedDate = null;
//        this.endorsedAttachment = null;
        this.approvedDate = null;
        this.approvedFunding = null;
//        this.approvedAttachment = null;
        //
        this.moaDate = null;
//        this.moaAttachment = null;
        this.durationFrom = null;
        this.durationTo = null;
        //
        this.actualCost = null;
        //
        this.factoryStreet = "";
        this.factoryBrgy = "";
        this.factoryCity = "";
//        this.factoryLong = "";
//        this.factoryLat = "";
        this.factoryLandMark = "";
        //
        this.yearEstablished = "";
        this.businessActivity = null;
        this.capitalClassification = "";
        this.employmentClassification = "";
        this.companyOwnership = "";
        this.profitability = "";
        this.registrationInformation = "";
        this.majorProducts = "";
        this.existingMarket = "";
        this.website = "";
    }
    //--------------------------------------------------------------------------
    // TABLE FIELDS
    //--------------------------------------------------------------------------

    public final static String TABLE = "setup_projects";
    // STC3000SET-02202018131137
    // STC3000GIA-02202018131137
    public final static String PROJECT_CODE = "project_code"; //-> Must be unique for distribution
    public final static String SPIN_NO = "spin_no";
    public final static String COMPANY_NAME = "company_name";
    // ADDED INFORMATION 02/22/2018
    public final static String COMPANY_OWNER = "company_owner";
    public final static String HISTORY = "history";
    public final static String OWNER_POSITION = "owner_position";
    public final static String OWNER_ADDRESS = "owner_address";
    //
    public final static String PROJECT_NAME = "project_name";
    public final static String PROJECT_STATUS = "project_status"; //-> Integer 0-2
    public final static String PROJECT_TYPE = "project_type";
    //ext
    public final static String ENDORSED_DATE = "endorsed_date";
//    public final static String ENDORSED_ATTACHMENT = "file_endorsed_attachment";
    //
    public final static String APPROVED_DATE = "approved_date";
    public final static String APPROVED_FUNDING = "approved_funding";
//    public final static String APPROVED_ATTACHMENT = "file_approved_attachment";
    //
//    public final static String RESTRUCTURED_FUNDING = "restructured_funding";
//    public final static String RESTRUCTURED_ATTACHMENT = "restructured_attachment";
    //
    public final static String MOA_DATE = "moa_date";
//    public final static String MOA_ATTACHMENT = "file_moa_attachment";

    public final static String ACTUAL_COST = "actual_cost";
    public final static String DURATION_FROM = "duration_from";
    public final static String DURATION_TO = "duration_to";
    //
    //- > Contact Person was Normalized to another table
    //
//    public final static String OFFICE_STREET = "office_street";
//    public final static String OFFICE_BRGY = "office_brgy";
//    public final static String OFFICE_CITY = "office_city";
//    public final static String OFFICE_LONG = "office_long";
//    public final static String OFFICE_LAT = "office_lat";
//    public final static String OFFICE_LANDMARK = "office_landmark";
    public final static String FACTORY_STREET = "factory_street";
    public final static String FACTORY_BRGY = "factory_brgy";
    public final static String FACTORY_CITY = "factory_city"; // insert zip from Object
//    public final static String FACTORY_LONG = "factory_long";
//    public final static String FACTORY_LAT = "factory_lat";
    public final static String FACTORY_LANDMARK = "factory_landmark";
    //public final static String FACTORY_COORDINATES = "factory_coordinates";
    //
    public final static String YEAR_ESTABLISHED = "year_established";
    public final static String BUSINESS_ACTIVITY = "business_activity"; // int
    //public final static String SPECIFIC_COMODITY = "specific_comodity";
    public final static String CAPITAL_CLASSIFICATION = "capital_classification";
    public final static String EMPLOYMENT_CLASSIFICATION = "employment_classification";
    public final static String COMPANY_OWNERSHIP = "company_ownership";
    public final static String PROFITABILITY = "profitability";
    public final static String REGISTRATION_INFO = "registration_info";
    public final static String MAJOR_PRODUCTS = "major_products";
    public final static String EXISTING_MARKET = "existing_market";
    //
    public final static String WEBSITE = "website";

    //--------------------------------------------------------------------------
    // DECLARATIONS
    //--------------------------------------------------------------------------
    @PrimaryKey
    @Column(PROJECT_CODE)
    private String projectCode; //--> PK
    @Column(SPIN_NO)
    private String spinNo;
    @Column(COMPANY_NAME)
    private String companyName;
    @Column(PROJECT_NAME)
    private String projectName;
    @Column(PROJECT_STATUS)
    private Integer projectStatus;
    @Column(PROJECT_TYPE)
    private String projectType;
    // Added info
    @Column(COMPANY_OWNER)
    private String companyOwner;
    @Column(HISTORY)
    private String history;
    @Column(OWNER_POSITION)
    private String ownerPosition;
    @Column(OWNER_ADDRESS)
    private String ownerAddress;
    //
    // endorsement
    @Column(ENDORSED_DATE)
    private Date endorsedDate;
//    @Column(ENDORSED_ATTACHMENT)
//    private Integer endorsedAttachment;
    // approval
    @Column(APPROVED_DATE)
    private Date approvedDate;
    @Column(APPROVED_FUNDING)
    private Double approvedFunding;
//    @Column(APPROVED_ATTACHMENT)
//    private Integer approvedAttachment;
    // restructuring
//    @Column(RESTRUCTURED_FUNDING)
//    private Double restructuredFunding;
//    @Column(RESTRUCTURED_ATTACHMENT)
//    private Integer restructuredAttachment;
    // moa
    @Column(MOA_DATE)
    private Date moaDate;
//    @Column(MOA_ATTACHMENT)
//    private Integer moaAttachment;
    @Column(DURATION_FROM)
    private Date durationFrom;
    @Column(DURATION_TO)
    private Date durationTo;

    @Column(ACTUAL_COST)
    private Double actualCost;

    //--------------------------------------------------------------------------
    // Contact Person / Proprietor / Proponent
    //--------------------------------------------------------------------------
    //--> Contact Person Was Normalized to another Table
    //--------------------------------------------------------------------------
    // Project Address
    //--------------------------------------------------------------------------
    //--> Office Address
//    @Column(OFFICE_STREET)
//    private String officeStreet;
//    @Column(OFFICE_BRGY)
//    private String officeBrgy;
//    @Column(OFFICE_CITY)
//    private String officeCity; //-> ZIP CODE
//    @Column(OFFICE_LONG)
//    private String officeLong; //--> https://www.google.com/maps/?q=-15.623037,18.388672
//    @Column(OFFICE_LAT)
//    private String officeLat;
//    @Column(OFFICE_LANDMARK)
//    private String officeLandMark;
    //--> Factory Address
    @Column(FACTORY_STREET)
    private String factoryStreet;
    @Column(FACTORY_BRGY)
    private String factoryBrgy;
    @Column(FACTORY_CITY)
    private String factoryCity; //-> ZIP CODE
//    @Column(FACTORY_LONG)
//    private String factoryLong; //--> https://www.google.com/maps/?q=-15.623037,18.388672
//    @Column(FACTORY_LAT)
//    private String factoryLat;
    @Column(FACTORY_LANDMARK)
    private String factoryLandMark;
//    @Column(FACTORY_COORDINATES)
//    private String factoryCoordinates;
    //--------------------------------------------------------------------------
    // Business Information.
    //--------------------------------------------------------------------------
    @Column(YEAR_ESTABLISHED)
    private String yearEstablished; // established
    @Column(BUSINESS_ACTIVITY)
    private Integer businessActivity;
//    @Column(SPECIFIC_COMODITY)
//    private String specificComodity;
    @Column(CAPITAL_CLASSIFICATION)
    private String capitalClassification;
    @Column(EMPLOYMENT_CLASSIFICATION)
    private String employmentClassification;
    @Column(COMPANY_OWNERSHIP)
    private String companyOwnership;
    @Column(PROFITABILITY)
    private String profitability;
    @Column(REGISTRATION_INFO)
    private String registrationInformation;
    @Column(MAJOR_PRODUCTS)
    private String majorProducts;
    @Column(EXISTING_MARKET)
    private String existingMarket;

    @Column(WEBSITE)
    private String website;

    //--------------------------------------------------------------------------
    // Static Classes.
    //--------------------------------------------------------------------------
    public static class ProjectStatus {

        private final String name;
        private final int value;

        public ProjectStatus(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public final static ProjectStatus PROPOSED = new ProjectStatus("PROPOSED", 0);
        public final static ProjectStatus ON_GOING = new ProjectStatus("ON GOING", 1);
        public final static ProjectStatus COMPLETED = new ProjectStatus("COMPLETED", 2);

        public final static ProjectStatus[] STATUS_LIST = new ProjectStatus[]{PROPOSED, ON_GOING, COMPLETED};

        public static String getStringValue(int value) throws UnknownModelValueException {
            for (ProjectStatus projectStatus : STATUS_LIST) {
                if (value == projectStatus.getValue()) {
                    return projectStatus.getName();
                }
            }
            throw new UnknownModelValueException();
        }

        @Override
        public String toString() {
            return this.name;
        }

    }

    /**
     * Classification According to Capital.
     */
    public static class CapitalClassification {

        /**
         * Less than 1.5M
         */
        public final static String MICRO = "MICRO";
        /**
         * 1.5M to 15M
         */
        public final static String SMALL = "SMALL";
        /**
         * 15M to 100M
         */
        public final static String MEDIUM = "MEDIUM";

        public final static String[] VALUE_LIST = new String[]{MICRO, SMALL, MEDIUM};
    }

    /**
     * Classification According to Employment.
     */
    public static class EmploymentClassification {

        /**
         * 1-9 Employees.
         */
        public final static String MICRO = "MICRO";
        /**
         * 10-99 Employees.
         */
        public final static String SMALL = "SMALL";
        /**
         * 100 - 199 Employees.
         */
        public final static String MEDIUM = "MEDIUM";

        public final static String[] VALUE_LIST = new String[]{MICRO, SMALL, MEDIUM};
    }

    /**
     * Type of ownership.
     */
    public static class Ownership {

        public final static String SINGLE_PROPRIETORSHIP = "SINGLE PROPRIETORSHIP";
        public final static String PARTNERSHIP = "PARTNERSHIP";
        public final static String COOPERATIVE = "COOPERATIVE";
        public final static String CORPORATION = "CORPORATION";
        public final static String LGU = "LGU";

        public final static String[] VALUE_LIST = new String[]{SINGLE_PROPRIETORSHIP,
            PARTNERSHIP, COOPERATIVE, CORPORATION, LGU
        };
    }

    /**
     * Classification According to Profitability.
     */
    public static class Profitability {

        public final static String PROFIT = "PROFIT";
        public final static String NON_PROFIT = "NON-PROFIT";

        public final static String[] VALUE_LIST = new String[]{PROFIT, NON_PROFIT};
    }

    /**
     * Town Value Model for BULACAN.
     */
    public static class TownValueModel {

        public final static TownValueModel ANGAT = new TownValueModel("ANGAT", "3012", "3");
        public final static TownValueModel BALAGTAS = new TownValueModel("BALAGTAS", "3016", "2");
        public final static TownValueModel BALIUAG = new TownValueModel("BALIUAG", "3006", "2");
        public final static TownValueModel BOCAUE = new TownValueModel("BOCAUE", "3018", "2");
        public final static TownValueModel BULACAN = new TownValueModel("BULACAN", "3017", "1");
        public final static TownValueModel BUSTOS = new TownValueModel("BUSTOS", "3007", "2");
        public final static TownValueModel CALUMPIT = new TownValueModel("CALUMPIT", "3003", "1");
        public final static TownValueModel DRT = new TownValueModel("DOÑA REMEDIOS TRINIDAD", "3009", "3");
        public final static TownValueModel GUIGUINTO = new TownValueModel("GUIGUINTO", "3015", "2");
        public final static TownValueModel HAGONOY = new TownValueModel("HAGONOY", "3002", "1");
        public final static TownValueModel MALOLOS = new TownValueModel("MALOLOS", "3000", "1");
        public final static TownValueModel MARILAO = new TownValueModel("MARILAO", "3019", "4");
        public final static TownValueModel MEYCAUYAN = new TownValueModel("MEYCAUYAN", "3020", "4");
        public final static TownValueModel NORZAGARAY = new TownValueModel("NORZAGARAY", "3013", "3");
        public final static TownValueModel OBANDO = new TownValueModel("OBANDO", "3021", "4");
        public final static TownValueModel PANDI = new TownValueModel("PANDI", "3014", "2");
        public final static TownValueModel PAOMBONG = new TownValueModel("PAOMBONG", "3001", "1");
        public final static TownValueModel PLARIDEL = new TownValueModel("PLARIDEL", "3004", "2");
        public final static TownValueModel PULILAN = new TownValueModel("PULILAN", "3005", "1");
        public final static TownValueModel SAN_ILDEFONSO = new TownValueModel("SAN ILDEFONSO", "3010", "3");
        public final static TownValueModel SJDM = new TownValueModel("SAN JOSE DEL MONTE", "3023", "0");
        public final static TownValueModel SAN_MIGUEL = new TownValueModel("SAN MIGUEL", "3011", "3");
        public final static TownValueModel SAN_RAFAEL = new TownValueModel("SAN RAFAEL", "3008", "3");
        public final static TownValueModel STA_MARIA = new TownValueModel("STA. MARIA", "3022", "4");

        /**
         * Town List.
         */
        public static final TownValueModel[] TOWN_LIST = new TownValueModel[]{
            ANGAT,
            BALAGTAS,
            BALIUAG,
            BOCAUE,
            BULACAN,
            BUSTOS,
            CALUMPIT,
            DRT,
            GUIGUINTO,
            HAGONOY,
            MALOLOS,
            MARILAO,
            MEYCAUYAN,
            NORZAGARAY,
            OBANDO,
            PANDI,
            PAOMBONG,
            PLARIDEL,
            PULILAN,
            SAN_ILDEFONSO,
            SJDM,
            SAN_MIGUEL,
            SAN_RAFAEL,
            STA_MARIA
        };

        public static TownValueModel getTown(String zip) {
            for (TownValueModel town : TOWN_LIST) {
                if (town.getZip().equalsIgnoreCase(zip)) {
                    return town;
                }
            }
            throw new UnknownModelValueException();
        }

        // non-static values
        private final String name;
        private final String zip;
        private final String district;

        private TownValueModel(String name, String zip, String district) {
            this.name = name;
            this.zip = zip;
            this.district = district;
        }

        public String getName() {
            return name;
        }

        public String getZip() {
            return zip;
        }

        public String getDistrict() {
            return district;
        }

        @Override
        public String toString() {
            return this.name + " /Dis-" + this.district + " | " + this.zip;
        }

    }

    /**
     * Business Activity Classification of the Company.
     */
    public static class BusinessActivity {

        private final String name;
        private final int value;

        private BusinessActivity(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public final static BusinessActivity FOOD_PROCESSING = new BusinessActivity("FOOD PROCESSING", 1);
        public final static BusinessActivity FURNITURES = new BusinessActivity("FURNITURES", 2);
        public final static BusinessActivity GIFTS_DECORS_HANDICRAFTS = new BusinessActivity("GIFTS, DECORS, HANDICRAFT", 3);
        public final static BusinessActivity METALS_AND_ENGINEERING = new BusinessActivity("METALS AND ENGINEERING", 4);
        public final static BusinessActivity AGRICULTURE_MARINE_AQUACULTURE = new BusinessActivity("AGRICULTURE/MARINE/AQUACULTURE", 5);
        public final static BusinessActivity HEALTH_PRODUCTS_AND_PHARMACEUTICALS = new BusinessActivity("HEALTH PRODUCTS AND PHARMACEUTICALS", 6);
        public final static BusinessActivity ICT_PRODUCTS = new BusinessActivity("INFORMATION AND COMMUNICATIONS TECHNOLOGY (ICT) PRODUCTS", 7);
        public final static BusinessActivity OTHERS = new BusinessActivity("OTHERS", 0);

        /**
         * Activity Type.
         */
        public final static BusinessActivity[] ACTIVITY_LIST = new BusinessActivity[]{
            FOOD_PROCESSING, FURNITURES, GIFTS_DECORS_HANDICRAFTS,
            METALS_AND_ENGINEERING, AGRICULTURE_MARINE_AQUACULTURE,
            HEALTH_PRODUCTS_AND_PHARMACEUTICALS, ICT_PRODUCTS,
            OTHERS
        };

        /**
         * Convert the value into String Equivalent.
         *
         * @param value Integer type business activity.
         * @return String Meaning.
         * @throws UnknownModelValueException when an invalid value was entered.
         */
        public static String getStringValue(int value) throws UnknownModelValueException {
            for (BusinessActivity businessActivity : ACTIVITY_LIST) {
                if (value == businessActivity.getValue()) {
                    return businessActivity.getName();
                }
            }
            throw new UnknownModelValueException();
        }
    } // end of business activity.

    /**
     * Project Type.
     */
    public static class ProjectType {

        public final static String GIA = "GIA";
        public final static String SETUP = "SETUP";

        /**
         * Project Types.
         */
        public final static String[] TYPE_LIST = new String[]{
            GIA, SETUP
        };

    }

    //--------------------------------------------------------------------------
    // CLASS COVERAGE
    //--------------------------------------------------------------------------
    //<@polaris:ignore>
    /**
     * Insert new Project Record in the database.
     *
     * @param project a project instance that contains the data to be inserted.
     * @return
     * @throws SQLException
     */
    public static boolean insert(ProjectModel project) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            project.auditCreate();
            return project.insert(con);
        }
    }

    /**
     * Fully updates including null values.
     *
     * @param project
     * @return
     * @throws SQLException
     */
    public static boolean update(ProjectModel project) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            project.auditUpdate();
            return project.updateFull(con);
        }
    }

    /**
     * This is the method that retrieves all the active data that will be shown
     * to the table in the project view.
     *
     * @param <T> List Data Type.
     * @return
     * @throws SQLException
     */
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
            return new ProjectModel().findMany(con, querySample);
        }
    }

    /**
     * Fetch a project instance using the project code.
     *
     * @param model
     * @param projectCode
     * @return
     * @throws SQLException
     */
    public static boolean fetchByCode(ProjectModel model, String projectCode) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.find(con, projectCode);
        }
    }

    /**
     * Deletes a project and its related contacts.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean remove(ProjectModel model) throws SQLException {
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
            boolean projectRecordUpdated = model.update(con);
            //------------------------------------------------------------------
            // if project record update failed rollback and return false.
            if (!projectRecordUpdated) {
                con.transactionRollBack();
                return false;
            }
            //------------------------------------------------------------------
            // Delete Related Contacts.
            //------------------------------------------------------------------
            // proceed to update related contacts deleted-at flag.
            SimpleQuery querySample = new SimpleQuery();
            querySample.addStatement("UPDATE")
                    .addStatement(ProjectContactModel.TABLE)
                    .addStatement("SET")
                    .addStatementWithParameter(ProjectContactModel.DELETED_AT + " = ?", Context.app().getServerDate())
                    .addStatement(",")
                    .addStatementWithParameter(ProjectContactModel.DELETED_BY + " = ?", Context.app().getAuditUser())
                    .addStatement("WHERE")
                    .addStatement(ProjectContactModel.DELETED_AT)
                    .addStatement("IS NULL")
                    .addStatement("AND")
                    .addStatementWithParameter(ProjectContactModel.SETUP_PROJECT_CODE + " = ?", model.getProjectCode());
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
    } // end delete project.

    //</@polaris:ignore>
    //--------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getProjectCode() {
        return projectCode;
    }

    public String getSpinNo() {
        return spinNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    public String getOwnerPosition() {
        return ownerPosition;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public Date getEndorsedDate() {
        return endorsedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public Double getApprovedFunding() {
        return approvedFunding;
    }

    public Date getMoaDate() {
        return moaDate;
    }

    public Date getDurationFrom() {
        return durationFrom;
    }

    public Date getDurationTo() {
        return durationTo;
    }

    public Double getActualCost() {
        return actualCost;
    }

    public String getFactoryStreet() {
        return factoryStreet;
    }

    public String getFactoryBrgy() {
        return factoryBrgy;
    }

    public String getFactoryCity() {
        return factoryCity;
    }

//    public String getFactoryLong() {
//        return factoryLong;
//    }
//
//    public String getFactoryLat() {
//        return factoryLat;
//    }
    public String getFactoryLandMark() {
        return factoryLandMark;
    }

    public String getYearEstablished() {
        return yearEstablished;
    }

    public Integer getBusinessActivity() {
        return businessActivity;
    }

    public String getCapitalClassification() {
        return capitalClassification;
    }

    public String getEmploymentClassification() {
        return employmentClassification;
    }

    public String getCompanyOwnership() {
        return companyOwnership;
    }

    public String getProfitability() {
        return profitability;
    }

    public String getRegistrationInformation() {
        return registrationInformation;
    }

    public String getMajorProducts() {
        return majorProducts;
    }

    public String getExistingMarket() {
        return existingMarket;
    }

    public String getWebsite() {
        return website;
    }
    //--------------------------------------------------------------------------

    /**
     * Setter for code including prefix.
     *
     * @param projectCode
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void setSpinNo(String spinNo) {
        this.spinNo = spinNo;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public void setOwnerPosition(String ownerPosition) {
        this.ownerPosition = ownerPosition;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public void setEndorsedDate(Date endorsedDate) {
        this.endorsedDate = endorsedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public void setApprovedFunding(Double approvedFunding) {
        this.approvedFunding = approvedFunding;
    }

    public void setMoaDate(Date moaDate) {
        this.moaDate = moaDate;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    public void setDurationTo(Date durationTo) {
        this.durationTo = durationTo;
    }

    public void setActualCost(Double actualCost) {
        this.actualCost = actualCost;
    }

    public void setFactoryStreet(String factoryStreet) {
        this.factoryStreet = factoryStreet;
    }

    public void setFactoryBrgy(String factoryBrgy) {
        this.factoryBrgy = factoryBrgy;
    }

    public void setFactoryCity(String factoryCity) {
        this.factoryCity = factoryCity;
    }

//    public void setFactoryLong(String factoryLong) {
//        this.factoryLong = factoryLong;
//    }
//
//    public void setFactoryLat(String factoryLat) {
//        this.factoryLat = factoryLat;
//    }
    public void setFactoryLandMark(String factoryLandMark) {
        this.factoryLandMark = factoryLandMark;
    }

    public void setYearEstablished(String yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public void setBusinessActivity(Integer businessActivity) {
        this.businessActivity = businessActivity;
    }

    public void setCapitalClassification(String capitalClassification) {
        this.capitalClassification = capitalClassification;
    }

    public void setEmploymentClassification(String employmentClassification) {
        this.employmentClassification = employmentClassification;
    }

    public void setCompanyOwnership(String companyOwnership) {
        this.companyOwnership = companyOwnership;
    }

    public void setProfitability(String profitability) {
        this.profitability = profitability;
    }

    public void setRegistrationInformation(String registrationInformation) {
        this.registrationInformation = registrationInformation;
    }

    public void setMajorProducts(String majorProducts) {
        this.majorProducts = majorProducts;
    }

    public void setExistingMarket(String existingMarket) {
        this.existingMarket = existingMarket;
    }

    public void setWebsite(String website) {
        this.website = website;
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
