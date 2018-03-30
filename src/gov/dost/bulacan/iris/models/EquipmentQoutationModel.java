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
@Table(EquipmentQoutationModel.TABLE)
public class EquipmentQoutationModel extends PolarisRecord {

    public final static String TABLE = "equipment_qoutation";
    public final static String QOUTE_CODE = "qoute_code";
    public final static String FK_SUPPLIER_CODE = "fk_supplier_code";
//    public final static String FK_PROJECT_CODE = "fk_project_code"; // if related to any project
    //
    public final static String EQUIPMENT_NAME = "equipment_name";
    public final static String QOUTATION_DATE = "qoutation_date";
    public final static String SPECIFICATIONS = "specification";
//    public final static String FEEDBACK = "feedback";
    public final static String REMARKS = "remarks";
    //
    public final static String STATUS = "status"; // purchased or canvassed
    public final static String QOUTATION_ATTACHMENT = "file_qoute_attachment";
    //
    public final static String KEYWORD = "search_keys"; // separated by comma(',').
    public final static String DELETED_AT = "deleted_at";

    /**
     * State of the equipment purchased.
     */
    public static class EquipmentStatus {

        public final static String CANVASSED = "CANVASSED";
        public final static String ACQUIRED = "ACQUIRED";

        public final static String[] LIST = new String[]{CANVASSED, ACQUIRED};

    }

    @PrimaryKey
    @Column(QOUTE_CODE)
    private String qouteCode;
    @Column(FK_SUPPLIER_CODE)
    private String supplierCode;
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
//    @Column(FK_PROJECT_CODE)
//    private String projectCode;
    //
    @Column(EQUIPMENT_NAME)
    private String equipmentName;
    @Column(QOUTATION_DATE)
    private Date qoutationDate;
    @Column(SPECIFICATIONS)
    private String specifications;
//    @Column(FEEDBACK)
//    private String feedBack;
    @Column(REMARKS)
    private String remarks;
    //
    @Column(STATUS)
    private String status;
    @Column(QOUTATION_ATTACHMENT)
    private Integer qoutationAttachment;
    //
    @Column(KEYWORD)
    private String keyword;
    @Column(DELETED_AT)
    private Date deletedAt;

    public EquipmentQoutationModel() {
//        this.qouteCode = ""; PK
        this.supplierCode = null;
//        this.projectCode = null;
        //
        this.equipmentName = "";
        this.specifications = "";
//        this.feedBack = "";
        this.remarks = "";
        this.status = "";
        this.qoutationAttachment = null;
        this.keyword = "";
        //
        this.qoutationDate = null;
        this.deletedAt = null;
    }

    //--------------------------------------------------------------------------
    // Code Coverage
    //--------------------------------------------------------------------------
    /**
     * Get All active equipment in the database. with the partial supplier
     * model.
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
        List<T> equipment = null;
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            equipment = new EquipmentQoutationModel().findMany(con, querySample);
            //------------------------------------------------------------------
            // If empty //
            if (equipment.isEmpty()) {
                return equipment;
            }
            //------------------------------------------------------------------
            // Where In Query to get the Partial Supplier Model
            SimpleQuery whereInQuery = new SimpleQuery();
            whereInQuery.addStatement("SELECT")
                    .addStatement(EquipmentSupplierModel.SUPPLIER_CODE)
                    .addStatement(",")
                    .addStatement(EquipmentSupplierModel.SUPPLIER_NAME)
                    .addStatement("FROM")
                    .addStatement(EquipmentSupplierModel.TABLE)
                    .addStatement("WHERE")
                    .addStatement(EquipmentSupplierModel.SUPPLIER_CODE)
                    .addStatement("IN (");
            // Query Preamble End.
            //------------------------------------------------------------------
            // Iterate to the equipment to get IN parmeters of SUPPLIER CODE of EQUIPMENT QOUTATUION
            for (int ctr = 0; ctr < equipment.size(); ctr++) {
                EquipmentQoutationModel model = (EquipmentQoutationModel) equipment.get(ctr);
                String code = model.getSupplierCode();
                whereInQuery.addStatementWithParameter("?", code);

                // attach comma if not last
                if (ctr < (equipment.size() - 1)) {
                    whereInQuery.addStatement(",");
                }

            }
            whereInQuery.addStatement(")"); // Close Query Preamble
            //------------------------------------------------------------------
            List<EquipmentSupplierModel> supplier = new EquipmentSupplierModel().findMany(con, whereInQuery);
            //------------------------------------------------------------------
            if (supplier.isEmpty()) {
                return equipment;
            }
            //------------------------------------------------------------------
            // Compare SUPPLIER CODE to attach Model to EQUIP QOUTATION MODEL.
            for (T equip : equipment) {
                // type case
                EquipmentQoutationModel equipModel = (EquipmentQoutationModel) equip;
                // if no assigned supplier code skip this.
                if (equipModel.getSupplierCode() == null) {
                    continue;
                }
                //--------------------------------------------------------------
                for (EquipmentSupplierModel supply : supplier) {
                    if (equipModel.getSupplierCode().equalsIgnoreCase(supply.getSupplierCode())) {
                        equipModel.setSupplierModel(supply);
                        break;
                    }
                }
            }
            //------------------------------------------------------------------

            //------------------------------------------------------------------
            return equipment;
        }
        //======================================================================
    }

    /**
     * Get this model's assigned supplier code.
     *
     * @return
     * @throws SQLException
     */
    public EquipmentSupplierModel fetchSupplierModel() throws SQLException {
        if (this.getSupplierCode() == null) {
            return null;
        }
        //----------------------------------------------------------------------
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            SimpleQuery querySample = new SimpleQuery();
            querySample.addStatement("SELECT")
                    .addStatement("*")
                    .addStatement("FROM")
                    .addStatement(EquipmentSupplierModel.TABLE)
                    .addStatement("WHERE")
                    .addStatement(EquipmentSupplierModel.SUPPLIER_CODE)
                    .addStatementWithParameter(" = ?", this.getSupplierCode());
            EquipmentSupplierModel supplier = new EquipmentSupplierModel();
            if (supplier.findQuery(con, querySample)) {
                return supplier;
            } else {
                return null;
            }
        }
    }

    /**
     * Insert new record of quotation.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean insert(EquipmentQoutationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    /**
     * Update this equipment.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean update(EquipmentQoutationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    /**
     * Remove this equipment.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean remove(EquipmentQoutationModel model) throws SQLException {
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

    //--------------------------------------------------------------------------
    private EquipmentSupplierModel supplierModel;

    public EquipmentSupplierModel getSupplierModel() {
        return supplierModel;
    }

    public void setSupplierModel(EquipmentSupplierModel supplierModel) {
        this.supplierModel = supplierModel;
    }
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getQouteCode() {
        return qouteCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

//    public String getProjectCode() {
//        return projectCode;
//    }
    public String getEquipmentName() {
        return equipmentName;
    }

    public Date getQoutationDate() {
        return qoutationDate;
    }

    public String getSpecifications() {
        return specifications;
    }

//    public String getFeedBack() {
//        return feedBack;
//    }
    public String getRemarks() {
        return remarks;
    }

    public String getStatus() {
        return status;
    }

    public Integer getQoutationAttachment() {
        return qoutationAttachment;
    }

    public String getKeyword() {
        return ModelAccess.stringValue(this.keyword);
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
    //--------------------------------------------------------------------------

    public void setQouteCode(String qouteCode) {
        this.qouteCode = qouteCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void setQoutationDate(Date qoutationDate) {
        this.qoutationDate = qoutationDate;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

//    public void setFeedBack(String feedBack) {
//        this.feedBack = feedBack;
//    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQoutationAttachment(Integer qoutationAttachment) {
        this.qoutationAttachment = qoutationAttachment;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
