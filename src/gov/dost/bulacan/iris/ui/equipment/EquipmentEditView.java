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
package gov.dost.bulacan.iris.ui.equipment;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.project.ProjectView;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.afterschoolcreatives.polaris.java.util.StringTools;

/**
 *
 * @author Jhon Melvin
 */
public class EquipmentEditView extends PolarisForm {
    
    @FXML
    private HBox hbox_header;
    
    @FXML
    private Label lbl_modify_header;
    
    @FXML
    private Label lbl_modify_time;
    
    @FXML
    private JFXButton btn_save_qoutation;
    
    @FXML
    private JFXButton btn_back;
    
    @FXML
    private TextField txt_equipment_name;
    
    @FXML
    private ComboBox<String> cmb_status;
    
    @FXML
    private TextArea txt_specs;
    
    @FXML
    private DatePicker date_qoutation;
    
    @FXML
    private TextArea txt_remarks;
    
    @FXML
    private TextArea txt_searchkeys;
    
    @FXML
    private Button btn_attachment;
    
    @FXML
    private Label lbl_code;
    
    public EquipmentEditView(EquipmentQoutationModel model) {
        this.setDialogMessageTitle("Equipment Qoutation");
        this.equipModel = model;
        if (model == null) {
            this.addingMode = true;
        } else {
            this.addingMode = false;
        }
    }
    
    private final EquipmentQoutationModel equipModel;
    private final boolean addingMode;
    
    private final static String BTN_EDIT_TEXT = "Edit";
    private final static String BTN_SAVE_TEXT = "Save";
    
    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        //----------------------------------------------------------------------
        this.cmb_status.getItems().setAll(Arrays.asList(EquipmentQoutationModel.EquipmentStatus.LIST));
        this.cmb_status.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        
        
        if (addingMode) {
            this.lbl_code.setText(Context.app().generateTimestampKey());
        } else {
            this.btn_save_qoutation.setText(BTN_EDIT_TEXT);
            this.lbl_code.setText(this.equipModel.getQouteCode());
            // preload data
            this.preloadData();
            // editable false
            this.txt_equipment_name.setEditable(false);
            this.txt_specs.setEditable(false);
            this.txt_remarks.setEditable(false);
            this.txt_searchkeys.setEditable(false);
            this.cmb_status.setDisable(true);
            this.date_qoutation.setDisable(true);
        }
        //----------------------------------------------------------------------
        this.btn_save_qoutation.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.addEquipment()) {
                    EquipmentView equipHome = new EquipmentView();
                    this.changeRoot(equipHome.load());
                }
            } else {
                if (this.btn_save_qoutation.getText().equalsIgnoreCase(BTN_EDIT_TEXT)) {
                    this.btn_save_qoutation.setText(BTN_SAVE_TEXT);
                    //
                    this.txt_equipment_name.setEditable(true);
                    this.txt_specs.setEditable(true);
                    this.txt_remarks.setEditable(true);
                    this.txt_searchkeys.setEditable(true);
                    this.cmb_status.setDisable(false);
                    this.date_qoutation.setDisable(false);
                    //
                } else if (this.btn_save_qoutation.getText().equalsIgnoreCase(BTN_SAVE_TEXT)) {
                    //----------------------------------------------------------
                    // Save Changes
                    //----------------------------------------------------------
                    if (this.updateEquipment()) {
                        this.btn_save_qoutation.setText(BTN_EDIT_TEXT);
                        //
                        this.txt_equipment_name.setEditable(false);
                        this.txt_specs.setEditable(false);
                        this.txt_remarks.setEditable(false);
                        this.txt_searchkeys.setEditable(false);
                        this.cmb_status.setDisable(true);
                        this.date_qoutation.setDisable(true);
                    }
                }
            }
            value.consume();
        });
        
        this.btn_back.setOnMouseClicked(value -> {
            EquipmentView equipmentView = new EquipmentView();
            this.changeRoot(equipmentView.load());
            value.consume();
        });
    }

    //--------------------------------------------------------------------------
    private void preloadData() {
        this.txt_equipment_name.setText(this.equipModel.getEquipmentName());
        //
        String projectType = this.equipModel.getStatus();
        for (String item : this.cmb_status.getItems()) {
            if (item.equalsIgnoreCase(projectType)) {
                this.cmb_status.getSelectionModel().select(item);
                break;
            }
        }
        //
        this.setDateToPicker(this.date_qoutation, this.equipModel.getQoutationDate());
        //
        this.txt_specs.setText(this.equipModel.getSpecifications());
        this.txt_remarks.setText(this.equipModel.getRemarks());
        this.txt_searchkeys.setText(this.equipModel.getKeyword());
    }
    
    private void setDateToPicker(DatePicker picker, Date dateEndorsed) {
        if (dateEndorsed != null) {
            SimpleDateFormat format = Context.app().getDateFormat();
            LocalDate setDate = LocalDate.parse(format.format(dateEndorsed), DateTimeFormatter.ofPattern(format.toPattern()));
            picker.setValue(setDate);
        }
    }
    //--------------------------------------------------------------------------
    private String frmEquipName;
    private String frmEquipStatus;
    private String frmEquipSpecs;
    private Date frmQouteDate;
    private String frmRemarks;
    private String frmSearchKeys;

    /**
     * Get Form Values.
     */
    private void getFormValues() {
        this.frmQouteDate = null;
        //
        this.frmEquipName = filterInput(this.txt_equipment_name);
        this.frmEquipStatus = this.cmb_status.getSelectionModel().getSelectedItem();
        this.frmEquipSpecs = filterInput(this.txt_specs);
        // Date Value
        if (this.date_qoutation.getValue() != null) {
            this.frmQouteDate = java.sql.Date.valueOf(this.date_qoutation.getValue());
        }
        //
        this.frmRemarks = filterInput(this.txt_remarks);
        this.frmSearchKeys = filterInput(this.txt_searchkeys);
    }
    
    private boolean updateEquipment() {
        this.getFormValues();
        if (frmEquipName.isEmpty()) {
            this.showWarningMessage("Euipment Name Required", "Please enter the equipment name.");
            return false;
        }
        
        EquipmentQoutationModel model = this.equipModel;
//        model.setQouteCode(this.lbl_code.getText());
        model.setEquipmentName(frmEquipName);
        model.setQoutationDate(frmQouteDate);
        model.setSpecifications(frmEquipSpecs);
        model.setRemarks(frmRemarks);
        model.setStatus(frmEquipStatus);
        model.setQoutationAttachment(null);
        model.setKeyword(frmSearchKeys);
        
        boolean updated = false;
        try {
            updated = EquipmentQoutationModel.updateEquip(model);
            if (updated) {
                this.showInformationMessage(null, "Equipment was successfully updated to the database.");
            } else {
                this.showWarningMessage(null, "The Equipment cannot be updated at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to update equipment.");
        }
        return updated;
    }
    
    private boolean addEquipment() {
        this.getFormValues();
        
        if (frmEquipName.isEmpty()) {
            this.showWarningMessage("Euipment Name Required", "Please enter the equipment name.");
            return false;
        }
        
        EquipmentQoutationModel model = new EquipmentQoutationModel();
        model.setQouteCode(this.lbl_code.getText());
        model.setEquipmentName(frmEquipName);
        model.setQoutationDate(frmQouteDate);
        model.setSpecifications(frmEquipSpecs);
        model.setRemarks(frmRemarks);
        model.setStatus(frmEquipStatus);
        model.setQoutationAttachment(null);
        model.setKeyword(frmSearchKeys);
        //----------------------------------------------------------------------
        boolean inserted = false;
        try {
            inserted = EquipmentQoutationModel.insertNew(model);
            if (inserted) {
                this.showInformationMessage(null, "Equipment was successfully added to the database.");
            } else {
                this.showWarningMessage(null, "The Equipment cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to insert New Equipment.");
        }
        return inserted;
    }
    
    private String filterInput(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }
    
}
