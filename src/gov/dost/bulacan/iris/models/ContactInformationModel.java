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
@Table(ContactInformationModel.TABLE)
public class ContactInformationModel extends PolarisRecord implements TableAuditor {

    public final static String TABLE = "contact_information";
    public final static String CONTACT_ID = "contact_id";
    public final static String ORGANIZATION = "organization";
    public final static String ORG_TYPE = "org_type"; // lgu academe
    public final static String OFFICE_NAME = "office_name"; // registrar
    public final static String CONTACT_PERSON = "contact_person";
    public final static String TEL_NO = "tel_no"; // +local
    public final static String FAX_NO = "fax_no";
    public final static String MOBILE_NO = "mobile_no";
    public final static String EMAIL = "email";

    /**
     * Organization Type.
     */
    public final static class Type {

        public final static String LGU = "LGU";
        public final static String ACADEME = "ACADEME";
        public final static String OTHERS = "OTHERS";

        public final static String[] LIST = new String[]{OTHERS, ACADEME, LGU};
    }

    @PrimaryKey
    @Column(CONTACT_ID)
    private String contactId;
    @Column(ORGANIZATION)
    private String organization;
    @Column(ORG_TYPE)
    private String orgType;
    @Column(OFFICE_NAME)
    private String officeName;
    @Column(CONTACT_PERSON)
    private String contactPerson;
    @Column(TEL_NO)
    private String telNo;
    @Column(FAX_NO)
    private String faxNo;
    @Column(MOBILE_NO)
    private String mobileNo;
    @Column(EMAIL)
    private String email;

    /**
     * Default Values.
     */
    public ContactInformationModel() {
//        this.contactId = ""; PK
        this.organization = "";
        orgType = "";
        officeName = "";
        contactPerson = "";
        telNo = "";
        faxNo = "";
        mobileNo = "";
        email = "";
    }

    /**
     * List all active entries from this model.
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
            return new ContactInformationModel().findMany(con, querySample);
        }
    }

    public static boolean insert(ContactInformationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditCreate();
            return model.insert(con);
        }
    }

    public static boolean update(ContactInformationModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.auditUpdate();
            return model.updateFull(con);
        }
    }

    public static boolean remove(ContactInformationModel model) throws SQLException {
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

    //--------------------------------------------------------------------------
    // GETTERS
    //--------------------------------------------------------------------------
    public String getContactId() {

        return ModelAccess.textAccess(sqlFrom);
    }

    public String getOrganization() {
        return ModelAccess.textAccess(organization);
    }

    public String getOrgType() {
        return ModelAccess.textAccess(orgType);
    }

    public String getOfficeName() {
        return ModelAccess.textAccess(officeName);
    }

    public String getContactPerson() {
        return ModelAccess.textAccess(contactPerson);
    }

    public String getTelNo() {
        return ModelAccess.textAccess(telNo);
    }

    public String getFaxNo() {
        return ModelAccess.textAccess(faxNo);
    }

    public String getMobileNo() {
        return ModelAccess.textAccess(mobileNo);
    }

    public String getEmail() {
        return ModelAccess.textAccess(email);
    }

    //--------------------------------------------------------------------------
    // SETTERS
    //--------------------------------------------------------------------------
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
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
