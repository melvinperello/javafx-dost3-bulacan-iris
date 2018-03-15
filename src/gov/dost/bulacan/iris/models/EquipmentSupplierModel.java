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

import gov.dost.bulacan.iris.models.ext.UnknownModelValueException;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(EquipmentSupplierModel.TABLE)
public class EquipmentSupplierModel extends PolarisRecord {

    public final static String TABLE = "equipment_supplier";
    public final static String SUPPLIER_CODE = "supplier_code";
    public final static String SUPPLIER_NAME = "supplier_name";
    //
    public final static String MOBILE = "mobile_no";
    public final static String TEL = "telephone_no";
    public final static String FAX = "fax_no";
    public final static String WEBSITE = "website_address";
    //
    public final static String SECTOR = "sector";
    public final static String DOST_ACCREDITED = "dost_accredited";
    //--------------------------------------------------------------------------
    // Address Fields for Analytics
    public final static String REGION = "supplier_region";
    public final static String PROVINCE = "supplier_province";
    public final static String CITY = "supplier_city";
    public final static String BRGY = "supplier_brgy";
    public final static String STREET_ADDRESS = "supplier_street";

    //--------------------------------------------------------------------------
    @PrimaryKey
    @Column(SUPPLIER_CODE)
    private String supplierCode;
    @Column(SUPPLIER_NAME)
    private String supplierName;
    @Column(MOBILE)
    private String mobileNo;
    @Column(TEL)
    private String telNo;
    @Column(FAX)
    private String faxNo;
    @Column(WEBSITE)
    private String websiteAddress;
    @Column(SECTOR)
    private Integer sector;
    @Column(DOST_ACCREDITED)
    private String dostAccredited;
    @Column(REGION)
    private String supplierRegion;
    @Column(PROVINCE)
    private String supplierProvince;
    @Column(CITY)
    private String supplierCity;
    @Column(BRGY)
    private String supplierBrgy;
    @Column(STREET_ADDRESS)
    private String supplierStreet;

    public EquipmentSupplierModel() {
//        this.supplierCode = ""; PRIMARY
        this.supplierName = "";
        this.mobileNo = "";
        this.telNo = "";
        this.faxNo = "";
        this.websiteAddress = "";
        this.sector = null;
        this.dostAccredited = "";
        this.supplierRegion = "";
        this.supplierProvince = "";
        this.supplierCity = "";
        this.supplierBrgy = "";
        this.supplierStreet = "";
    }

    //--------------------------------------------------------------------------
    public static class DostAccredited {

        public final static String YES = "YES";
        public final static String NO = "NO";

        public final static String[] LIST = new String[]{YES, NO};
    }

    /**
     * Supplier Sector.
     */
    public static class Sector {

        private int value;
        private String name;

        public Sector(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
        //----------------------------------------------------------------------
        public final static Sector OTHERS = new Sector(0, "Others");
        public final static Sector FOOD_PROCESSING = new Sector(1, "Food Processing");
        public final static Sector FURNITURE = new Sector(2, "Furniture");
        public final static Sector GIFTS_HOUSEWARE_DECOR = new Sector(3, "Gifts, Housewares and Decors");
        public final static Sector HORTICULTURE_AGRIGCULTURE = new Sector(4, "Horticulture and Agriculture");
        public final static Sector METALS_ENGINEERING = new Sector(5, "Metals and Engineering");
        public final static Sector ICT_ELECTRONICS = new Sector(6, "Information and Communication Technology / Electronics");
        public final static Sector HEALTH_PHARMACY = new Sector(7, "Health Products and Pharmaceuticals");
        public final static Sector MARINE_AQUA_RESOURCE = new Sector(8, "Marine and Aquatic Resources");
        public final static Sector PROMOTION = new Sector(9, "Promotion");
        public final static Sector HUMAN_RES_DEV = new Sector(10, "Human Resource Development");
        public final static Sector EDUCATION = new Sector(11, "Education");
        public final static Sector ENVIRONMENT = new Sector(12, "Environment");

        /**
         * Sector List.
         */
        public final static Sector[] LIST = new Sector[]{
            FOOD_PROCESSING, FURNITURE, GIFTS_HOUSEWARE_DECOR,
            HORTICULTURE_AGRIGCULTURE, METALS_ENGINEERING,
            ICT_ELECTRONICS, HEALTH_PHARMACY, MARINE_AQUA_RESOURCE,
            PROMOTION, HUMAN_RES_DEV, EDUCATION, ENVIRONMENT, OTHERS
        };

        /**
         * Converts Sector Integer value to String.
         *
         * @param value
         * @return
         * @throws UnknownModelValueException
         */
        public static String getStringValue(int value) throws UnknownModelValueException {
            for (Sector sector : LIST) {
                if (value == sector.getValue()) {
                    return sector.getName();
                }
            }
            throw new UnknownModelValueException();
        }
    }

    //--------------------------------------------------------------------------
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    public String getDostAccredited() {
        return dostAccredited;
    }

    public void setDostAccredited(String dostAccredited) {
        this.dostAccredited = dostAccredited;
    }

    public String getSupplierRegion() {
        return supplierRegion;
    }

    public void setSupplierRegion(String supplierRegion) {
        this.supplierRegion = supplierRegion;
    }

    public String getSupplierProvince() {
        return supplierProvince;
    }

    public void setSupplierProvince(String supplierProvince) {
        this.supplierProvince = supplierProvince;
    }

    public String getSupplierCity() {
        return supplierCity;
    }

    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }

    public String getSupplierBrgy() {
        return supplierBrgy;
    }

    public void setSupplierBrgy(String supplierBrgy) {
        this.supplierBrgy = supplierBrgy;
    }

    public String getSupplierStreet() {
        return supplierStreet;
    }

    public void setSupplierStreet(String supplierStreet) {
        this.supplierStreet = supplierStreet;
    }

}