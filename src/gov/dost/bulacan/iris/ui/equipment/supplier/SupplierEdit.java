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
package gov.dost.bulacan.iris.ui.equipment.supplier;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class SupplierEdit extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private JFXButton btn_save;

    @FXML
    private Label lbl_code;

    @FXML
    private TextField txt_name;

    @FXML
    private ComboBox<EquipmentSupplierModel.Sector> cmb_sector;

    @FXML
    private RadioButton rdb_yes;

    @FXML
    private ToggleGroup rdo_group_accredited;

    @FXML
    private RadioButton rdb_no;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_fax;

    @FXML
    private TextField txt_email;

    @FXML
    private TextArea txt_address;

    @FXML
    private TextField txt_website;

    public SupplierEdit(EquipmentSupplierModel supplierModel, EquipmentQoutationModel qoutationModel) {
        this.supplierModel = supplierModel;
        this.qoutationModel = qoutationModel;
        if (supplierModel == null) {
            this.addingMode = true;
        } else {
            this.addingMode = false;
        }
    }

    private final EquipmentSupplierModel supplierModel;
    private final EquipmentQoutationModel qoutationModel;
    private final boolean addingMode;

    @Override
    protected void setup() {
        //----------------------------------------------------------------------
        ProjectHeader.attach(hbox_header);
        this.cmb_sector.getItems().setAll(Arrays.asList(EquipmentSupplierModel.Sector.LIST));
        this.cmb_sector.getSelectionModel().selectFirst();
        this.rdb_no.setSelected(true);
        //----------------------------------------------------------------------
        this.btn_back.setOnMouseClicked(value -> {
            this.backToSupplyHome(null);
            value.consume();
        });

        this.btn_save.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.addSupplier()) {
                    // go back after insert
                    this.backToSupplyHome(null);
                }
            } else {
                if (this.updateSupplier()) {
                    // go back after update
                    this.backToSupplyHome(null);
                }
            }

            value.consume();
        });

        //----------------------------------------------------------------------
        if (this.addingMode) {
            //
            this.lbl_code.setText(Context.app().generateTimestampKey());
        } else {
            this.preloadData();
        }
        //----------------------------------------------------------------------

    }

    /**
     * Go back to supplier home.
     *
     * @param selected
     */
    private void backToSupplyHome(EquipmentSupplierModel selected) {
        SupplierHome supplyHome = new SupplierHome(this.qoutationModel, selected);
        this.changeRoot(supplyHome.load());
    }

    private void preloadData() {
        EquipmentSupplierModel model = this.supplierModel;
        this.lbl_code.setText(model.getSupplierCode());
        this.txt_name.setText(model.getSupplierName());

        EquipmentSupplierModel.Sector sector = EquipmentSupplierModel.Sector.getObject(model.getSector());
        this.cmb_sector.getSelectionModel().select(sector);

        if (model.getDostAccredited().equalsIgnoreCase(EquipmentSupplierModel.DostAccredited.YES)) {
            this.rdb_yes.setSelected(true);
        } else {
            this.rdb_no.setSelected(true);
        }

        this.txt_mobile.setText(model.getMobileNo());
        this.txt_tel.setText(model.getTelNo());
        this.txt_fax.setText(model.getFaxNo());
        this.txt_email.setText(model.getSupplierEmail());
        this.txt_address.setText(model.getSupplierAddress());
        this.txt_website.setText(model.getWebsiteAddress());
    }

    private String frmSupplierCodel;
    private String frmSupplierName;
    private Integer frmSector;
    private String frmAccredited;
    private String frmMobile;
    private String frmlTel;
    private String frmFax;
    private String frmEmail;
    private String frmAddress;
    private String frmWebsite;

    private void getFormValues() {
        this.frmSupplierCodel = this.lbl_code.getText();
        EquipmentSupplierModel.Sector selectedSector = this.cmb_sector.getSelectionModel().getSelectedItem();
        this.frmSector = selectedSector.getValue();
        this.frmAccredited = this.rdb_no.isSelected()
                ? EquipmentSupplierModel.DostAccredited.NO
                : EquipmentSupplierModel.DostAccredited.YES;

        this.frmMobile = Context.app().filterInputControl(this.txt_mobile);
        this.frmlTel = Context.app().filterInputControl(this.txt_tel);
        this.frmEmail = Context.app().filterInputControl(this.txt_email);
        this.frmAddress = Context.app().filterInputControl(this.txt_address);
        this.frmSupplierName = Context.app().filterInputControl(this.txt_name);
        this.frmWebsite = Context.app().filterInputControl(this.txt_website);
    }

    private boolean addSupplier() {
        //----------------------------------------------------------------------
        this.getFormValues();
        //----------------------------------------------------------------------
        if (this.frmSupplierName.isEmpty()) {
            this.showWarningMessage(null, "Please enter the supplier name.");
            return false;
        }

        EquipmentSupplierModel supplier = new EquipmentSupplierModel();
        supplier.setSupplierCode(Context.app().generateTimestampKey());
        supplier.setSupplierName(frmSupplierName);
        supplier.setSector(frmSector);
        supplier.setDostAccredited(frmAccredited);
        supplier.setMobileNo(frmMobile);
        supplier.setTelNo(frmlTel);
        supplier.setFaxNo(frmFax);
        supplier.setWebsiteAddress(frmWebsite);
        supplier.setSupplierEmail(frmEmail);

        boolean inserted = false;
        try {
            inserted = EquipmentSupplierModel.insert(supplier);
            if (inserted) {
                this.showWaitInformationMessage(null, "Supplier was successfully added to the database.");
            } else {
                this.showWaitWarningMessage(null, "The Supplier cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to insert New Supplier.");
        }
        return inserted;
    }

    /**
     * Update Supplier information.
     *
     * @return
     */
    private boolean updateSupplier() {
        //----------------------------------------------------------------------
        this.getFormValues();
        //----------------------------------------------------------------------
        if (this.frmSupplierName.isEmpty()) {
            this.showWarningMessage(null, "Please enter the supplier name.");
            return false;
        }

        EquipmentSupplierModel supplier = this.supplierModel;
        //supplier.setSupplierCode(Context.app().generateTimestampKey());
        supplier.setSupplierName(frmSupplierName);
        supplier.setSector(frmSector);
        supplier.setDostAccredited(frmAccredited);
        supplier.setMobileNo(frmMobile);
        supplier.setTelNo(frmlTel);
        supplier.setFaxNo(frmFax);
        supplier.setWebsiteAddress(frmWebsite);
        supplier.setSupplierEmail(frmEmail);

        boolean updated = false;
        try {
            updated = EquipmentSupplierModel.update(supplier);
            if (updated) {
                this.showWaitInformationMessage(null, "Supplier was successfully added to the database.");
            } else {
                this.showWaitWarningMessage(null, "The Supplier cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to insert New Supplier.");
        }
        return updated;
    }

}
