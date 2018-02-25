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

import java.util.Date;
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
public class ProjectModel extends PolarisRecord {
    //--------------------------------------------------------------------------
    // TABLE FIELDS
    //--------------------------------------------------------------------------

    public final static String TABLE = "project";
    // STC3000SET-02202018131137
    // STC3000GIA-02202018131137
    public final static String PROJECT_CODE = "project_code"; //-> Must be unique for distribution
    public final static String SPIN_NO = "spin_no";
    public final static String COMPANY_NAME = "company_name";
    // ADDED INFORMATION 02/22/2018
    public final static String COMPANY_OWNER = "company_owner";
    public final static String OWNER_POSITION = "owner_position";
    public final static String OWNER_ADDRESS = "owner_address";
    //
    public final static String PROJECT_NAME = "project_name";
    public final static String PROJECT_STATUS = "project_status"; //-> Integer 0-2
    public final static String PROJECT_TYPE = "project_type";
    //ext
    public final static String ENDORSED_DATE = "endorsed_date";
    public final static String ENDORSED_ATTACHMENT = "endorsed_attachment";
    //
    public final static String APPROVED_DATE = "approved_date";
    public final static String APPROVED_FUNDING = "approved_funding";
    public final static String APPROVED_ATTACHMENT = "approved_attachment";
    //
//    public final static String RESTRUCTURED_FUNDING = "restructured_funding";
//    public final static String RESTRUCTURED_ATTACHMENT = "restructured_attachment";
    //
    public final static String MOA_DATE = "moa_date";
    public final static String MOA_ATTACHMENT = "moa_attachment";
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
    public final static String FACTORY_CITY = "factory_city";
    public final static String FACTORY_LONG = "factory_long";
    public final static String FACTORY_LAT = "factory_lat";
    public final static String FACTORY_LANDMARK = "factory_landmark";
    public final static String FACTORY_COORDINATES = "factory_coordinates";
    //
    public final static String YEAR_ESTABLISHED = "year_established";
    public final static String BUSINESS_ACTIVITY = "business_activity";
    public final static String SPECIFIC_COMODITY = "specific_comodity";
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
    private String projectStatus;
    @Column(PROJECT_TYPE)
    private String projectType;
    // Added info
    @Column(COMPANY_OWNERSHIP)
    private String companyOwner;
    @Column(OWNER_POSITION)
    private String ownerPosition;
    @Column(OWNER_ADDRESS)
    private String ownerAddress;
    //
    // endorsement
    @Column(ENDORSED_DATE)
    private Date endorsedDate;
    @Column(ENDORSED_ATTACHMENT)
    private Integer edorsedAttachment;
    // approval
    @Column(APPROVED_DATE)
    private Date approvedDate;
    @Column(APPROVED_FUNDING)
    private Double approvedFunding;
    @Column(APPROVED_ATTACHMENT)
    private Integer approvedAttachment;
    // restructuring
//    @Column(RESTRUCTURED_FUNDING)
//    private Double restructuredFunding;
//    @Column(RESTRUCTURED_ATTACHMENT)
//    private Integer restructuredAttachment;
    // moa
    @Column(MOA_DATE)
    private Date moaDate;
    @Column(MOA_ATTACHMENT)
    private Integer moaAttachment;

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
    @Column(FACTORY_LONG)
    private String factoryLong; //--> https://www.google.com/maps/?q=-15.623037,18.388672
    @Column(FACTORY_LAT)
    private String factoryLat;
    @Column(FACTORY_LANDMARK)
    private String factoryLandMark;
    @Column(FACTORY_COORDINATES)
    private String factoryCoordinates;
    //--------------------------------------------------------------------------
    // Business Information.
    //--------------------------------------------------------------------------
    @Column(YEAR_ESTABLISHED)
    private String yearEstablished; // established
    @Column(BUSINESS_ACTIVITY)
    private String businessActivity;
    @Column(SPECIFIC_COMODITY)
    private String specificComodity;
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
    // CLASS COVERAGE
    //--------------------------------------------------------------------------
    //<@polaris:ignore>
    // Do Your Code Logic Here
    //
    //</@polaris:ignore>
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

        final static ProjectStatus[] STATUS_LIST = new ProjectStatus[]{PROPOSED, ON_GOING, COMPLETED};

        public static String getStringValue(int value) throws UnknownValueException {
            for (ProjectStatus projectStatus : STATUS_LIST) {
                if (value == projectStatus.getValue()) {
                    return projectStatus.getName();
                }
            }
            throw new UnknownValueException();
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
    }

    /**
     * Contact Person's Sexuality for analytics purposes.
     */
    public static class Sexuality {

        public final static String MALE = "MALE";
        public final static String FEMALE = "FEMALE";
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
    }

    /**
     * Classification According to Profitability.
     */
    public static class Profitability {

        public final static String PROFIT = "PROFIT";
        public final static String NON_PROFIT = "NON-PROFIT";
    }

    public static class Town {

        public final static Town ANGAT = new Town("ANGAT", "3012", "3");
        public final static Town BALAGTAS = new Town("BALAGTAS", "3016", "2");
        public final static Town BALIUAG = new Town("BALIUAG", "3006", "2");
        public final static Town BOCAUE = new Town("BOCAUE", "3018", "2");
        public final static Town BULACAN = new Town("BULACAN", "3017", "1");
        public final static Town BUSTOS = new Town("BUSTOS", "3007", "2");
        public final static Town CALUMPIT = new Town("CALUMPIT", "3003", "1");
        public final static Town DRT = new Town("DOÑA REMEDIOS TRINIDAD", "3009", "3");
        public final static Town GUIGUINTO = new Town("GUIGUINTO", "3015", "2");
        public final static Town HAGONOY = new Town("HAGONOY", "3002", "1");
        public final static Town MALOLOS = new Town("MALOLOS", "3000", "1");
        public final static Town MARILAO = new Town("MARILAO", "3019", "4");
        public final static Town MEYCAUYAN = new Town("MEYCAUYAN", "3020", "4");
        public final static Town NORZAGARAY = new Town("NORZAGARAY", "3013", "3");
        public final static Town OBANDO = new Town("OBANDO", "3021", "4");
        public final static Town PANDI = new Town("PANDI", "3014", "2");
        public final static Town PAOMBONG = new Town("PAOMBONG", "3001", "1");
        public final static Town PLARIDEL = new Town("PLARIDEL", "3004", "2");
        public final static Town PULILAN = new Town("PULILAN", "3005", "1");
        public final static Town SAN_ILDEFONSO = new Town("SAN ILDEFONSO", "3010", "3");
        public final static Town SJDM = new Town("SAN JOSE DEL MONTE", "3023", "0");
        public final static Town SAN_MIGUEL = new Town("SAN MIGUEL", "3011", "3");
        public final static Town SAN_RAFAEL = new Town("SAN RAFAEL", "3008", "3");
        public final static Town STA_MARIA = new Town("STA. MARIA", "3022", "4");

        /**
         * Town List.
         */
        static final Town[] TOWN_LIST = new Town[]{
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

        /**
         * Town Name List.
         */
        static final String[] TOWN_NAME_LIST = new String[]{
            ANGAT.toString(),
            BALAGTAS.toString(),
            BALIUAG.toString(),
            BOCAUE.toString(),
            BULACAN.toString(),
            BUSTOS.toString(),
            CALUMPIT.toString(),
            DRT.toString(),
            GUIGUINTO.toString(),
            HAGONOY.toString(),
            MALOLOS.toString(),
            MARILAO.toString(),
            MEYCAUYAN.toString(),
            NORZAGARAY.toString(),
            OBANDO.toString(),
            PANDI.toString(),
            PAOMBONG.toString(),
            PLARIDEL.toString(),
            PULILAN.toString(),
            SAN_ILDEFONSO.toString(),
            SJDM.toString(),
            SAN_MIGUEL.toString(),
            SAN_RAFAEL.toString(),
            STA_MARIA.toString()
        };

        // non-static values
        private final String name;
        private final String zip;
        private final String district;

        private Town(String name, String zip, String district) {
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
            return this.name;
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
         * @throws UnknownValueException when an invalid value was entered.
         */
        public static String getStringValue(int value) throws UnknownValueException {
            for (BusinessActivity businessActivity : ACTIVITY_LIST) {
                if (value == businessActivity.getValue()) {
                    return businessActivity.getName();
                }
            }
            throw new UnknownValueException();
        }
    } // end of business activity.

    public static class UnknownValueException extends RuntimeException {

        static final long serialVersionUID = 1L;

        public UnknownValueException() {
            super("Invalid Integer Equivalent.");
        }

    }

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
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getSpinNo() {
        return spinNo;
    }

    public void setSpinNo(String spinNo) {
        this.spinNo = spinNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public String getOwnerPosition() {
        return ownerPosition;
    }

    public void setOwnerPosition(String ownerPosition) {
        this.ownerPosition = ownerPosition;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public Date getEndorsedDate() {
        return endorsedDate;
    }

    public void setEndorsedDate(Date endorsedDate) {
        this.endorsedDate = endorsedDate;
    }

    public Integer getEdorsedAttachment() {
        return edorsedAttachment;
    }

    public void setEdorsedAttachment(Integer edorsedAttachment) {
        this.edorsedAttachment = edorsedAttachment;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Double getApprovedFunding() {
        return approvedFunding;
    }

    public void setApprovedFunding(Double approvedFunding) {
        this.approvedFunding = approvedFunding;
    }

    public Integer getApprovedAttachment() {
        return approvedAttachment;
    }

    public void setApprovedAttachment(Integer approvedAttachment) {
        this.approvedAttachment = approvedAttachment;
    }

    public Date getMoaDate() {
        return moaDate;
    }

    public void setMoaDate(Date moaDate) {
        this.moaDate = moaDate;
    }

    public Integer getMoaAttachment() {
        return moaAttachment;
    }

    public void setMoaAttachment(Integer moaAttachment) {
        this.moaAttachment = moaAttachment;
    }

    public String getFactoryStreet() {
        return factoryStreet;
    }

    public void setFactoryStreet(String factoryStreet) {
        this.factoryStreet = factoryStreet;
    }

    public String getFactoryBrgy() {
        return factoryBrgy;
    }

    public void setFactoryBrgy(String factoryBrgy) {
        this.factoryBrgy = factoryBrgy;
    }

    public String getFactoryCity() {
        return factoryCity;
    }

    public void setFactoryCity(String factoryCity) {
        this.factoryCity = factoryCity;
    }

    public String getFactoryLong() {
        return factoryLong;
    }

    public void setFactoryLong(String factoryLong) {
        this.factoryLong = factoryLong;
    }

    public String getFactoryLat() {
        return factoryLat;
    }

    public void setFactoryLat(String factoryLat) {
        this.factoryLat = factoryLat;
    }

    public String getFactoryLandMark() {
        return factoryLandMark;
    }

    public void setFactoryLandMark(String factoryLandMark) {
        this.factoryLandMark = factoryLandMark;
    }

    public String getFactoryCoordinates() {
        return factoryCoordinates;
    }

    public void setFactoryCoordinates(String factoryCoordinates) {
        this.factoryCoordinates = factoryCoordinates;
    }

    public String getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(String yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getSpecificComodity() {
        return specificComodity;
    }

    public void setSpecificComodity(String specificComodity) {
        this.specificComodity = specificComodity;
    }

    public String getCapitalClassification() {
        return capitalClassification;
    }

    public void setCapitalClassification(String capitalClassification) {
        this.capitalClassification = capitalClassification;
    }

    public String getEmploymentClassification() {
        return employmentClassification;
    }

    public void setEmploymentClassification(String employmentClassification) {
        this.employmentClassification = employmentClassification;
    }

    public String getCompanyOwnership() {
        return companyOwnership;
    }

    public void setCompanyOwnership(String companyOwnership) {
        this.companyOwnership = companyOwnership;
    }

    public String getProfitability() {
        return profitability;
    }

    public void setProfitability(String profitability) {
        this.profitability = profitability;
    }

    public String getRegistrationInformation() {
        return registrationInformation;
    }

    public void setRegistrationInformation(String registrationInformation) {
        this.registrationInformation = registrationInformation;
    }

    public String getMajorProducts() {
        return majorProducts;
    }

    public void setMajorProducts(String majorProducts) {
        this.majorProducts = majorProducts;
    }

    public String getExistingMarket() {
        return existingMarket;
    }

    public void setExistingMarket(String existingMarket) {
        this.existingMarket = existingMarket;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
