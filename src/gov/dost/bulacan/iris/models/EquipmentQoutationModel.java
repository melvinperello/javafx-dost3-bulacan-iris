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
    public final static String FK_PROJECT_CODE = "fk_project_code"; // if related to any project
    //
    public final static String EQUIPMENT_NAME = "equipment_name";
    public final static String SPECIFICATIONS = "specification";
    public final static String FEEDBACK = "feedback";
    public final static String REMARKS = "remarks";
    //
    public final static String STATUS = "status"; // purchased or canvassed
    public final static String QOUTATION_ATTACHMENT = "file_qoute_attachment";
    //
    public final static String KEYWORD = "search_keys"; // separated by comma(',').

    /**
     * State of the equipment purchased.
     */
    public static class EquipmentStatus {

        public final static String CANVASSED = "CANVASSED";
        public final static String PURCHASED = "PURCHASED";

        public final static String[] LIST = new String[]{CANVASSED, PURCHASED};

    }

    @PrimaryKey
    @Column(QOUTE_CODE)
    private String qouteCode;
    @Column(FK_SUPPLIER_CODE)
    private String supplierCode;
    @Column(FK_PROJECT_CODE)
    private String projectCode;
    //
    @Column(EQUIPMENT_NAME)
    private String equipmentName;
    @Column(SPECIFICATIONS)
    private String specifications;
    @Column(FEEDBACK)
    private String feedBack;
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

    public EquipmentQoutationModel() {
//        this.qouteCode = ""; PK
        this.supplierCode = null;
        this.projectCode = null;
        //
        this.equipmentName = "";
        this.specifications = "";
        this.feedBack = "";
        this.remarks = "";
        this.status = "";
        this.qoutationAttachment = null;
        this.keyword = "";
    }

    //--------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getQouteCode() {
        return qouteCode;
    }

    public void setQouteCode(String qouteCode) {
        this.qouteCode = qouteCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQoutationAttachment() {
        return qoutationAttachment;
    }

    public void setQoutationAttachment(Integer qoutationAttachment) {
        this.qoutationAttachment = qoutationAttachment;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
