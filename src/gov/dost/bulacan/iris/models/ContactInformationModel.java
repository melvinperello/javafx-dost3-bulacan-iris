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
import static gov.dost.bulacan.iris.models.ProjectModel.DELETED_AT;
import static gov.dost.bulacan.iris.models.ProjectModel.TABLE;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;

/**
 *
 * @author Jhon Melvin
 */
public class ContactInformationModel extends PolarisRecord {

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
    //
    public final static String DELETED_AT = "deleted_at";

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
    @Column(DELETED_AT)
    private Date deletedAt;

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
        deletedAt = null;
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

    //--------------------------------------------------------------------------
    // GETTERS
    //--------------------------------------------------------------------------
    public String getContactId() {
        return contactId;
    }

    public String getOrganization() {
        return organization;
    }

    public String getOrgType() {
        return orgType;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getTelNo() {
        return telNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public Date getDeletedAt() {
        return new Date(deletedAt.getTime());
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

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = new Date(deletedAt.getTime());
    }

}
