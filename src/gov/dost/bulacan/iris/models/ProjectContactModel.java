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
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(ProjectContactModel.TABLE)
public class ProjectContactModel extends PolarisRecord {
    //--------------------------------------------------------------------------
    // TABLE FIELDS
    //--------------------------------------------------------------------------

    public final static String TABLE = "project_contact_model";
    public final static String LAST_NAME = "last_name";
    public final static String FIRST_NAME = "first_name";
    public final static String MIDDLE_NAME = "middle_name";
    public final static String EXTENSION_NAME = "extension_name";
    public final static String SEX = "sex";
    public final static String POSITION = "position";
    public final static String MOBILE = "mobile";
    public final static String LANDLINE = "landline";
    public final static String EMAIL = "email";

    //--------------------------------------------------------------------------
    // DECLARATIONS
    //--------------------------------------------------------------------------
    @Column(LAST_NAME)
    private String last_name;
    @Column(FIRST_NAME)
    private String first_name;
    @Column(MIDDLE_NAME)
    private String middle_name;
    @Column(EXTENSION_NAME)
    private String extension_name; // JR. SR. III.
    @Column(SEX)
    private String sex; // for analytics purposes
    @Column(POSITION)
    private String position;
    @Column(MOBILE)
    private String mobile;
    @Column(LANDLINE)
    private String landline;
    @Column(EMAIL)
    private String email;

    //--------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getExtension_name() {
        return extension_name;
    }

    public void setExtension_name(String extension_name) {
        this.extension_name = extension_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

}
