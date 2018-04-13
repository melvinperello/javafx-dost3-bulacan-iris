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
@Table(EquipmentSupplierModel.TABLE)
public class EquipmentSupplierModel extends PolarisRecord implements TableAuditor {

    public final static String TABLE = "equipment_supplier";
    public final static String SUPPLIER_CODE = "supplier_code";
    public final static String SUPPLIER_NAME = "supplier_name";
    //
    public final static String MOBILE = "mobile_no";
    public final static String TEL = "telephone_no";
    public final static String FAX = "fax_no";
    public final static String WEBSITE = "website_address";
    public final static String EMAIL = "email";
    //
    public final static String SECTOR = "sector";
    public final static String DOST_ACCREDITED = "dost_accredited";
    //--------------------------------------------------------------------------
    // Address Fields for Analytics
    public final static String SUPPLIER_ADDRESS = "supplier_address";
//    public final static String REGION = "supplier_region";
//    public final static String PROVINCE = "supplier_province";
//    public final static String CITY = "supplier_city";
//    public final static String BRGY = "supplier_brgy";
//    public final static String STREET_ADDRESS = "supplier_street";

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
    @Column(SUPPLIER_ADDRESS)
    private String supplierAddress;
    @Column(EMAIL)
    private String supplierEmail;
//    @Column(REGION)
//    private String supplierRegion;
//    @Column(PROVINCE)
//    private String supplierProvince;
//    @Column(CITY)
//    private String supplierCity;
//    @Column(BRGY)
//    private String supplierBrgy;
//    @Column(STREET_ADDRESS)
//    private String supplierStreet;

    public EquipmentSupplierModel() {
//        this.supplierCode = ""; PRIMARY
        this.supplierName = "";
        this.mobileNo = "";
        this.telNo = "";
        this.faxNo = "";
        this.websiteAddress = "";
        this.sector = null;
        this.dostAccredited = "";
        this.supplierAddress = "";
//        this.supplierRegion = "";
//        this.supplierProvince = "";
//        this.supplierCity = "";
//        this.supplierBrgy = "";
//        this.supplierStreet = "";
        this.supplierEmail = "";
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

        public static Sector getObject(int value) throws UnknownModelValueException {
            for (Sector sector : LIST) {
                if (value == sector.getValue()) {
                    return sector;
                }
            }
            throw new UnknownModelValueException();
        }
    }

    //--------------------------------------------------------------------------
    // Class Coverage
    //--------------------------------------------------------------------------
    /**
     * Add New Supplier.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean insert(EquipmentSupplierModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditCreate();
            return model.insert(con);
        }
    }

    /**
     * Updating this current supplier model instance.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean update(EquipmentSupplierModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditUpdate();
            return model.updateFull(con);
        }
    }

    public static boolean remove(EquipmentSupplierModel model) throws SQLException {
        ConnectionManager con = null;
        try {
            //------------------------------------------------------------------
            // open connection
            con = Context.app().db().createConnectionManager();
            //------------------------------------------------------------------
            // REMOVED DEPENDENCIES
            SimpleQuery searchAssignment = new SimpleQuery();
            searchAssignment.addStatement("UPDATE")
                    .addStatement(EquipmentQoutationModel.TABLE)
                    .addStatement("SET")
                    .addStatement(EquipmentQoutationModel.FK_SUPPLIER_CODE + " = NULL")
                    .addStatement("WHERE")
                    .addStatement(EquipmentQoutationModel.FK_SUPPLIER_CODE + " = ?")
                    .addParameter(model.getSupplierCode());

            boolean removedDependency = false;
            try {
                con.update(searchAssignment);
                removedDependency = true;
            } catch (SQLException e) {
                con.transactionRollBack();
                return false;
            }
            //------------------------------------------------------------------
            if (removedDependency) {
                model.auditDelete();
                // execute query.
                boolean updated = model.updateFull(con);
                if (updated) {
                    con.transactionCommit();
                    return true;
                }
            }

            con.transactionRollBack();
            return false;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    /**
     * Get All active supplier model in the database.
     *
     * @param <T>
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
            return new EquipmentSupplierModel().findMany(con, querySample);
        }
    }

    //--------------------------------------------------------------------------
    // Getter
    //--------------------------------------------------------------------------
    public String getSupplierCode() {
        return ModelAccess.textAccess(supplierCode);
    }

    public String getSupplierName() {
        return ModelAccess.textAccess(supplierName);
    }

    public String getMobileNo() {
        return ModelAccess.textAccess(mobileNo);
    }

    public String getTelNo() {
        return ModelAccess.textAccess(telNo);
    }

    public String getFaxNo() {
        return ModelAccess.textAccess(faxNo);
    }

    public String getWebsiteAddress() {
        return ModelAccess.textAccess(websiteAddress);
    }

    public Integer getSector() {
        return sector;
    }

    public String getDostAccredited() {
        return ModelAccess.textAccess(dostAccredited);
    }

    public String getSupplierAddress() {
        return ModelAccess.textAccess(supplierAddress);
    }

    public String getSupplierEmail() {
        return ModelAccess.textAccess(supplierEmail);
    }

    //--------------------------------------------------------------------------
    // Setter
    //--------------------------------------------------------------------------
    /**
     *
     * @param supplierCode
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    public void setDostAccredited(String dostAccredited) {
        this.dostAccredited = dostAccredited;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
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
