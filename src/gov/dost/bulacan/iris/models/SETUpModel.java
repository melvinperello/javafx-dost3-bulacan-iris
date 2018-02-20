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
import java.util.Objects;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 * Data Model that represents the SETUp Project Information.
 *
 * @author Jhon Melvin
 */
@Table(SETUpModel.TABLE)
public class SETUpModel extends PolarisRecord {
    //--------------------------------------------------------------------------
    // TABLE FIELDS
    //--------------------------------------------------------------------------

    public final static String TABLE = "project";
    // STC3000SET-02202018131137
    public final static String PROJECT_CODE = "project_code"; //-> Must be unique for distribution
    public final static String COMPANY_NAME = "company_name";
    public final static String PROJECT_NAME = "project_name";
    public final static String PROJECT_STATUS = "project_status"; //-> Integer 0-2
    //ext
    public final static String ENDORSED_DATE = "endorsed_date";
    public final static String ENDORSED_ATTACHMENT = "endorsed_attachment";
    //
    public final static String APPROVED_DATE = "approved_date";
    public final static String APPROVED_FUNDING = "approved_funding";
    public final static String APPROVED_ATTACHMENT = "approved_attachment";
    //
    public final static String RESTRUCTURED_FUNDING = "restructured_funding";
    public final static String RESTRUCTURED_ATTACHMENT = "restructured_attachment";
    //
    public final static String MOA_DATE = "moa_date";
    public final static String MOA_ATTACHMENT = "moa_attachment";
    //
    //- > Contact Person was Normalized to another table
    //
    public final static String OFFICE_STREET = "office_street";
    public final static String OFFICE_BRGY = "office_brgy";
    public final static String OFFICE_CITY = "office_city";
    public final static String OFFICE_LONG = "office_long";
    public final static String OFFICE_LAT = "office_lat";
    public final static String OFFICE_LANDMARK = "office_landmark";
    public final static String FACTORY_STREET = "factory_street";
    public final static String FACTORY_BRGY = "factory_brgy";
    public final static String FACTORY_CITY = "factory_city";
    public final static String FACTORY_LONG = "factory_long";
    public final static String FACTORY_LAT = "factory_lat";
    public final static String FACTORY_LANDMARK = "factory_landmark";
    //
    public final static String YEAR_ESTABLISHED = "year_established";
    public final static String BUSINESS_ACTIVITY = "business_activity";
    public final static String CAPITAL_CLASSIFICATION = "capital_classification";
    public final static String EMPLOYMENT_CLASSIFICATION = "employment_classification";
    public final static String COMPANY_OWNERSHIP = "company_ownership";
    public final static String PROFITABILITY = "profitability";
    public final static String REGISTRATION_INFO = "registration_info";
    public final static String MAJOR_PRODUCTS = "major_products";
    public final static String EXISTING_MARKET = "existing_market";

    //--------------------------------------------------------------------------
    // DECLARATIONS
    //--------------------------------------------------------------------------
    @PrimaryKey
    @Column(PROJECT_CODE)
    private String projectCode; //--> PK
    @Column(COMPANY_NAME)
    private String companyName;
    @Column(PROJECT_NAME)
    private String projectName;
    @Column(PROJECT_STATUS)
    private String projectStatus;
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
    @Column(RESTRUCTURED_FUNDING)
    private Double restructuredFunding;
    @Column(RESTRUCTURED_ATTACHMENT)
    private Integer restructuredAttachment;
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
    @Column(OFFICE_STREET)
    private String officeStreet;
    @Column(OFFICE_BRGY)
    private String officeBrgy;
    @Column(OFFICE_CITY)
    private String officeCity; //-> ZIP CODE
    @Column(OFFICE_LONG)
    private String officeLong; //--> https://www.google.com/maps/?q=-15.623037,18.388672
    @Column(OFFICE_LAT)
    private String officeLat;
    @Column(OFFICE_LANDMARK)
    private String officeLandMark;
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
    //--------------------------------------------------------------------------
    // Business Information.
    //--------------------------------------------------------------------------
    @Column(YEAR_ESTABLISHED)
    private String yearEstablished; // established
    @Column(BUSINESS_ACTIVITY)
    private String businessActivity; //--> 0@FoodProcessing;1@Ewan;
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

    //--------------------------------------------------------------------------
    // CLASS COVERAGE
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    // Static Classes.
    //--------------------------------------------------------------------------
    public static class ProjectStatus {

        public final static int PROPOSED = 0;
        public final static int ON_GOING = 1;
        public final static int COMPLETED = 2;

        public static String getStringValue(int value) throws UnknownValueException {
            switch (value) {
                case 0:
                    return "PROPOSED";
                case 1:
                    return "ON GOING";
                case 2:
                    return "COMPLETED";
                default:
                    throw new UnknownValueException();
            }
        }

    }

    /**
     * Classification According to Capital.
     */
    public static class CapitalClassification {

        /**
         * Less than 1.5M
         */
        public static String MICRO = "MICRO";
        /**
         * 1.5M to 15M
         */
        public static String SMALL = "SMALL";
        /**
         * 15M to 100M
         */
        public static String MEDIUM = "MEDIUM";
    }

    /**
     * Classification According to Employment.
     */
    public static class EmploymentClassification {

        /**
         * 1-9 Employees.
         */
        public static String MICRO = "MICRO";
        /**
         * 10-99 Employees.
         */
        public static String SMALL = "SMALL";
        /**
         * 100 - 199 Employees.
         */
        public static String MEDIUM = "MEDIUM";
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

    public static class Municipalities {

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
        public static final Town[] TOWN_LIST = new Town[]{
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
        public final static String[] TOWN_NAME_LIST = new String[]{
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

        public static class Town {

            private String name;
            private String zip;
            private String district;

            public Town(String name, String zip, String district) {
                this.name = name;
                this.zip = zip;
                this.district = district;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            @Override
            public String toString() {
                return this.getName();
            }

        }
    }

    /**
     * Business Activity Classification of the Company.
     */
    public static class BusinessActivity {

        public final static int FOOD_PROCESSING = 1;
        public final static int FURNITURES = 2;
        public final static int GIFTS_DECORS_HANDICRAFTS = 3;
        public final static int METALS_AND_ENGINEERING = 4;
        public final static int AGRICULTURE_MARINE_AQUACULTURE = 5;
        public final static int HEALTH_PRODUCTS_AND_PHARMACEUTICALS = 6;
        public final static int ICT_PRODUCTS = 7;
        public final static int OTHERS = 0;

        /**
         * Convert the value into String Equivalent.
         *
         * @param value Integer type business activity.
         * @return String Meaning.
         * @throws UnknownValueException when an invalid value was entered.
         */
        public static String getStringValue(int value) throws UnknownValueException {
            switch (value) {
                case 0:
                    return "OTHERS";
                case 1:
                    return "FOOD PROCESSING";
                case 2:
                    return "FURNITURES";
                case 3:
                    return "GIFTS, DECORS, HANDICRAFT";
                case 4:
                    return "METALS AND ENGINEERING";
                case 5:
                    return "AGRICULTURE/MARINE/AQUACULTURE";
                case 6:
                    return "HEALTH PRODUCTS AND PHARMACEUTICALS";
                case 7:
                    return "INFORMATION AND COMMUNICATIONS TECHNOLOGY (ICT) PRODUCTS";
                default:
                    throw new UnknownValueException();
            }
        }
    } // end of business activity.

    public static class UnknownValueException extends RuntimeException {

        static final long serialVersionUID = 1L;

        public UnknownValueException() {
            super("Invalid Integer Equivalent.");
        }

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

    public Double getRestructuredFunding() {
        return restructuredFunding;
    }

    public void setRestructuredFunding(Double restructuredFunding) {
        this.restructuredFunding = restructuredFunding;
    }

    public Integer getRestructuredAttachment() {
        return restructuredAttachment;
    }

    public void setRestructuredAttachment(Integer restructuredAttachment) {
        this.restructuredAttachment = restructuredAttachment;
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

    public String getOfficeStreet() {
        return officeStreet;
    }

    public void setOfficeStreet(String officeStreet) {
        this.officeStreet = officeStreet;
    }

    public String getOfficeBrgy() {
        return officeBrgy;
    }

    public void setOfficeBrgy(String officeBrgy) {
        this.officeBrgy = officeBrgy;
    }

    public String getOfficeCity() {
        return officeCity;
    }

    public void setOfficeCity(String officeCity) {
        this.officeCity = officeCity;
    }

    public String getOfficeLong() {
        return officeLong;
    }

    public void setOfficeLong(String officeLong) {
        this.officeLong = officeLong;
    }

    public String getOfficeLat() {
        return officeLat;
    }

    public void setOfficeLat(String officeLat) {
        this.officeLat = officeLat;
    }

    public String getOfficeLandMark() {
        return officeLandMark;
    }

    public void setOfficeLandMark(String officeLandMark) {
        this.officeLandMark = officeLandMark;
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

}
