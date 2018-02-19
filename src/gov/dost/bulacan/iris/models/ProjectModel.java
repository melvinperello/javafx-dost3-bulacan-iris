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

import gov.dost.bulacan.iris.UnknownBusinessActivityException;

/**
 * Data Model that represents the SETUp Project Information.
 *
 * @author Jhon Melvin
 */
public class ProjectModel {

    //--------------------------------------------------------------------------
    // DECLARATIONS
    //--------------------------------------------------------------------------
    private String projectCode; //--> PK
    private String companyName;
    private String projectName;
    //--> Address Block.
    //--> Must include auto detect of district based on city.
    private String streetAddress;
    private String brgyAddress;
    private String cityAddress; //-> ZIP CODE
    //--> for googlemaps 
    private String longitudeAddress; //--> https://www.google.com/maps/?q=-15.623037,18.388672
    private String latitudeAddress;
    private String landMarkAddress;
    //--> Contact Person
    private String contactPerson;
    private String contactPersonSexuality; // for analytics purposes
    private String contactPersonPosition;
    private String contactPersonMobile;
    private String contactPersonLandline;
    private String contactPersonEmail;
    //--> Enterprise Profile
    private String yearEstablished;
    /**
     * Must allow multiple selection.
     */
    private String businessActivity; //--> 0@FoodProcessing;1@Ewan;
    private String capitalClassification;
    private String employmentClassification;
    private String companyOwnership;
    private String profitability;
    private String registrationInformation;
    private String majorProductLines;
    private String existingMarket;

    //--------------------------------------------------------------------------
    // Static Classes.
    //--------------------------------------------------------------------------
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
         * @throws UnknownBusinessActivityException when an invalid value was
         * entered.
         */
        public static String getStringValue(int value) throws UnknownBusinessActivityException {
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
                    throw new UnknownBusinessActivityException("Invalid Integer Equivalent.");
            }
        }
    } // end of business activity.

    //--------------------------------------------------------------------------
    // Getter
    //--------------------------------------------------------------------------
    public String getProjectCode() {
        return projectCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getBrgyAddress() {
        return brgyAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public String getLongitudeAddress() {
        return longitudeAddress;
    }

    public String getLatitudeAddress() {
        return latitudeAddress;
    }

    public String getLandMarkAddress() {
        return landMarkAddress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactPersonSexuality() {
        return contactPersonSexuality;
    }

    public String getContactPersonPosition() {
        return contactPersonPosition;
    }

    public String getContactPersonMobile() {
        return contactPersonMobile;
    }

    public String getContactPersonLandline() {
        return contactPersonLandline;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public String getYearEstablished() {
        return yearEstablished;
    }

    public String getBusinessActivity() {
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

    public String getMajorProductLines() {
        return majorProductLines;
    }

    public String getExistingMarket() {
        return existingMarket;
    }

    //--------------------------------------------------------------------------
    // Setter
    //--------------------------------------------------------------------------
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setBrgyAddress(String brgyAddress) {
        this.brgyAddress = brgyAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public void setLongitudeAddress(String longitudeAddress) {
        this.longitudeAddress = longitudeAddress;
    }

    public void setLatitudeAddress(String latitudeAddress) {
        this.latitudeAddress = latitudeAddress;
    }

    public void setLandMarkAddress(String landMarkAddress) {
        this.landMarkAddress = landMarkAddress;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactPersonSexuality(String contactPersonSexuality) {
        this.contactPersonSexuality = contactPersonSexuality;
    }

    public void setContactPersonPosition(String contactPersonPosition) {
        this.contactPersonPosition = contactPersonPosition;
    }

    public void setContactPersonMobile(String contactPersonMobile) {
        this.contactPersonMobile = contactPersonMobile;
    }

    public void setContactPersonLandline(String contactPersonLandline) {
        this.contactPersonLandline = contactPersonLandline;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public void setYearEstablished(String yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public void setBusinessActivity(String businessActivity) {
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

    public void setMajorProductLines(String majorProductLines) {
        this.majorProductLines = majorProductLines;
    }

    public void setExistingMarket(String existingMarket) {
        this.existingMarket = existingMarket;
    }

}
