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
    public static <T> List<T> getAllActiveEquipment() throws SQLException {
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE);
        //======================================================================
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return new EquipmentQoutationModel().findMany(con, querySample);
        }
    }

    public static boolean insertNew(EquipmentQoutationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    public static boolean updateEquip(EquipmentQoutationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

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

//    public void setProjectCode(String projectCode) {
//        this.projectCode = projectCode;
//    }
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
