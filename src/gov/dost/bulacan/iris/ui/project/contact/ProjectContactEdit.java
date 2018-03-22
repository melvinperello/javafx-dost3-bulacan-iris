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
package gov.dost.bulacan.iris.ui.project.contact;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.models.ProjectContactModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.afterschoolcreatives.polaris.java.util.StringTools;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectContactEdit extends PolarisForm {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_position;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_landline;

    @FXML
    private TextField txt_email;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    /**
     * Constructor to pass model.
     *
     * @param model current model for updating if applicable.
     * @param project reference project for foreign key.
     */
    public ProjectContactEdit(ProjectContactModel model, ProjectModel project) {
        this.contactModel = model;
        this.willAddNew = (model == null);
        this.projectModel = project;
        this.setDialogMessageTitle("Contact Person");
    }

    private final ProjectContactModel contactModel;
    private final boolean willAddNew;
    private final ProjectModel projectModel;

    @Override
    protected void setup() {
        /**
         * If will not add new pre load data.
         */
        if (!this.willAddNew) {
            this.preloadContactData();
        }

        /**
         * Save Button.
         */
        this.btn_save.setOnMouseClicked(value -> {
            if (this.willAddNew) {
                this.addNewContact();
            } else {
                this.updateContact();
            }
            value.consume();
        });

        /**
         * Cancel Button.
         */
        this.btn_cancel.setOnMouseClicked(value -> {
            this.getStage().close();
        });
    }

    //--------------------------------------------------------------------------
    private String frmName;
    private String frmPosition;
    private String frmMobile;
    private String frmTel;
    private String frmMail;

    /**
     * Get data to form.
     */
    private void getFormDetails() {
        this.frmName = this.filterInput(this.txt_name);
        this.frmPosition = this.filterInput(this.txt_position);
        this.frmMobile = this.filterInput(this.txt_mobile);
        this.frmTel = this.filterInput(this.txt_landline);
        this.frmMail = this.filterInput(this.txt_email);
    }

    /**
     * Filter input from text field.
     *
     * @param textField
     * @return
     */
    private String filterInput(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }

    //--------------------------------------------------------------------------
    /**
     * Add New Contact.
     */
    private void addNewContact() {
        this.getFormDetails();
        // Filter
        if (this.frmName.isEmpty()) {
            this.showWarningMessage(null, "Please enter a contact name.");
            return;
        }
        //
        ProjectContactModel model = new ProjectContactModel();
        model.setContactCode(Context.app().generateTimestampKey());
        model.setEmail(frmMail);
        model.setLandline(frmTel);
        model.setMobile(frmMobile);
        model.setName(frmName);
        model.setPosition(frmPosition);
        /**
         * Foreign Key.
         */
        model.setSetupProjectCode(this.projectModel.getProjectCode());
        //
        try {
            boolean res = ProjectContactModel.insertNewContact(model);
            if (res) {
                this.showWaitInformationMessage(null, "Contact successfully added to this project.");
                /**
                 * Close stage for success.
                 */
                this.getStage().close();
            } else {
                this.showInformationMessage(null, "Contact cannot be added at the moment please try again later.");
            }
        } catch (SQLException e) {
            this.showWaitExceptionMessage(e, null, "Failed to add new contact.");
        }
    }

    /**
     * Update existing contact.
     */
    private void updateContact() {
        this.getFormDetails();
        // Filter
        if (this.frmName.isEmpty()) {
            this.showWarningMessage(null, "Please enter a contact name.");
            return;
        }
        //
        ProjectContactModel model = this.contactModel;
        model.setEmail(frmMail);
        model.setLandline(frmTel);
        model.setMobile(frmMobile);
        model.setName(frmName);
        model.setPosition(frmPosition);
        /**
         * Foreign Key.
         */
        model.setSetupProjectCode(this.projectModel.getProjectCode());
        //
        try {
            boolean res = ProjectContactModel.updateContact(model);
            if (res) {
                this.showWaitInformationMessage(null, "Contact successfully updated to this project.");
                /**
                 * Close stage for success.
                 */
                this.getStage().close();
            } else {
                this.showInformationMessage(null, "Contact cannot be updated at the moment please try again later.");
            }
        } catch (SQLException e) {
            //
            this.showWaitExceptionMessage(e, null, "Failed to update existing contact.");
        }
    }

    /**
     * pre load contact data for editing.
     */
    private void preloadContactData() {
        this.txt_name.setText(this.contactModel.getName());
        this.txt_position.setText(this.contactModel.getPosition());
        this.txt_mobile.setText(this.contactModel.getMobile());
        this.txt_landline.setText(this.contactModel.getLandline());
        this.txt_email.setText(this.contactModel.getEmail());
    }

}
