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
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.FetchOnly;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(ProjectContactModel.TABLE)
public class ProjectContactModel extends PolarisRecord {

    /**
     * Initialize default values.
     */
    public ProjectContactModel() {
        this.contactCode = ""; // primary key
        this.name = "";
        this.position = "";
        this.mobile = "";
        this.landline = "";
        this.email = "";
    }
    //--------------------------------------------------------------------------
    // TABLE FIELDS
    //--------------------------------------------------------------------------    
    public final static String TABLE = "setup_projects_contact";

    public final static String CONTACT_CODE = "contact_code";
    // FOREIGN KEY to ProjectModel
    public final static String SETUP_PROJECT_CODE = "fk_setup_project_code";
    public final static String NAME = "name";
    public final static String POSITION = "position";
    public final static String MOBILE = "mobile";
    public final static String LANDLINE = "landline";
    public final static String EMAIL = "email";
    public final static String DELETED_AT = "deleted_at";

    //--------------------------------------------------------------------------
    // DECLARATIONS
    //--------------------------------------------------------------------------
    @PrimaryKey
    @Column(CONTACT_CODE)
    private String contactCode;
    @Column(SETUP_PROJECT_CODE)
    private String setupProjectCode;
    @Column(NAME)
    private String name;
    @Column(POSITION)
    private String position;
    @Column(MOBILE)
    private String mobile;
    @Column(LANDLINE)
    private String landline;
    @Column(EMAIL)
    private String email;
    @Column(DELETED_AT)
    private Date deleted_at;

    //--------------------------------------------------------------------------
    // Static Methods
    //--------------------------------------------------------------------------
    /**
     * Fetch all contacts related to the host project model.
     *
     * @param <T>
     * @param projectCode
     * @return
     * @throws SQLException
     */
    public static <T> List<T> getAllContacts(String projectCode) throws SQLException {
        SimpleQuery querySample = new SimpleQuery();
        querySample.addStatement("SELECT")
                .addStatement("*")
                .addStatement("FROM")
                .addStatement(TABLE)
                .addStatement("WHERE")
                .addStatementWithParameter(SETUP_PROJECT_CODE + " = ?", projectCode)
                .addStatement(" AND ")
                .addStatement(DELETED_AT)
                .addStatement("IS NULL");
        //======================================================================
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return new ProjectContactModel().findMany(con, querySample);
        }
    }

    /**
     * Insert new contact.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean insertNewContact(ProjectContactModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.insert(con);
        }
    }

    /**
     * Update new contact.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean updateContact(ProjectContactModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            return model.updateFull(con);
        }
    }

    /**
     * Updates the deleted at value to trigger delete flag.
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public static boolean deleteContact(ProjectContactModel model) throws SQLException {
        try (ConnectionManager con = Context.app().db().createConnectionManager()) {
            model.setDeleted_at(Context.app().getServerDate());
            return model.update(con);
        }
    }

    //--------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getContactCode() {
        return contactCode;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    public String getSetupProjectCode() {
        return setupProjectCode;
    }

    public void setSetupProjectCode(String setupProjectCode) {
        this.setupProjectCode = setupProjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

}
