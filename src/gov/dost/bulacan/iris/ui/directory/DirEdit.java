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
package gov.dost.bulacan.iris.ui.directory;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ContactInformationModel;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.equipment.EquipmentView;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class DirEdit extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private Label lbl_modify_time;

    @FXML
    private JFXButton btn_save_qoutation;

    @FXML
    private Label lbll_dir_id;

    @FXML
    private TextField txt_org;

    @FXML
    private ComboBox<String> cmb_org_type;

    @FXML
    private TextField txt_department;

    @FXML
    private TextField txt_contact;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_fax;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_email;

    public DirEdit(ContactInformationModel model) {
        this.model = model;
        this.addingMode = (model == null);

    }
    private final ContactInformationModel model;
    private final boolean addingMode;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        //----------------------------------------------------------------------
        this.cmb_org_type.getItems().setAll(Arrays.asList(ContactInformationModel.Type.LIST));
        this.cmb_org_type.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        if (addingMode) {
            this.lbll_dir_id.setText(Context.createLocalKey());
        } else {
            this.preloadData();
        }

        this.btn_back.setOnMouseClicked(value -> {
            DirHome fx = new DirHome();
            this.changeRoot(fx.load());
            value.consume();
        });

        this.btn_save_qoutation.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.insert()) {
                    this.changeRoot(new DirHome().load());
                }
            } else {
                if (this.update()) {
                    this.changeRoot(new DirHome().load());
                }
            }
            value.consume();
        });
    }

    private void preloadData() {
        this.lbll_dir_id.setText(this.model.getContactId());
        this.txt_org.setText(this.model.getOrganization());
        //
        String projectType = this.model.getOrgType();
        for (String item : this.cmb_org_type.getItems()) {
            if (item.equalsIgnoreCase(projectType)) {
                this.cmb_org_type.getSelectionModel().select(item);
                break;
            }
        }
        //
        this.txt_department.setText(this.model.getOfficeName());
        this.txt_contact.setText(this.model.getContactPerson());
        this.txt_tel.setText(this.model.getTelNo());
        this.txt_fax.setText(this.model.getFaxNo());
        this.txt_mobile.setText(this.model.getMobileNo());
        this.txt_email.setText(this.model.getEmail());
    }

    //--------------------------------------------------------------------------
    private String frmOrg;
    private String frmType;
    private String frmDept;
    private String frmContact;
    private String frmTel;
    private String frmFax;
    private String frmMobile;
    private String frmEmail;

    private void submit() {
        this.frmOrg = Context.filterInputControl(this.txt_org);
        this.frmType = this.cmb_org_type.getSelectionModel().getSelectedItem();
        this.frmDept = Context.filterInputControl(this.txt_department);
        this.frmContact = Context.filterInputControl(this.txt_contact);
        this.frmTel = Context.filterInputControl(this.txt_tel);
        this.frmFax = Context.filterInputControl(this.txt_fax);
        this.frmMobile = Context.filterInputControl(this.txt_mobile);
        this.frmEmail = Context.filterInputControl(this.txt_email);
    }

    private boolean insert() {
        this.submit();

        if (this.frmOrg.isEmpty()) {
            this.showWarningMessage("Euipment Name Required", "Please enter the equipment name.");
            return false;
        }

        ContactInformationModel model = new ContactInformationModel();
        model.setContactId(this.lbll_dir_id.getText());
        model.setOrganization(frmOrg);
        model.setOrgType(frmType);
        model.setOfficeName(frmDept);
        model.setContactPerson(frmContact);
        model.setTelNo(frmTel);
        model.setFaxNo(frmFax);
        model.setMobileNo(frmMobile);
        model.setEmail(frmEmail);

        boolean inserted = false;
        try {
            inserted = ContactInformationModel.insert(model);
            if (inserted) {
                this.showInformationMessage(null, "Contact Information was successfully added to the directory.");
            } else {
                this.showWarningMessage(null, "The Contact Information cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to insert New Contact Information.");
        }
        return inserted;

    }

    private boolean update() {
        this.submit();

        if (this.frmOrg.isEmpty()) {
            this.showWarningMessage("Euipment Name Required", "Please enter the equipment name.");
            return false;
        }

        ContactInformationModel model = this.model;
//        model.setContactId(this.lbll_dir_id.getText());
        model.setOrganization(frmOrg);
        model.setOrgType(frmType);
        model.setOfficeName(frmDept);
        model.setContactPerson(frmContact);
        model.setTelNo(frmTel);
        model.setFaxNo(frmFax);
        model.setMobileNo(frmMobile);
        model.setEmail(frmEmail);

        boolean updated = false;
        try {
            updated = ContactInformationModel.update(model);
            if (updated) {
                this.showInformationMessage(null, "Contact Information was successfully updated.");
            } else {
                this.showWarningMessage(null, "The Contact Information cannot be updated at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to update Contact Information.");
        }
        return updated;
    }

}
