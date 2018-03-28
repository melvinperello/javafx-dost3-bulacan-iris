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
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisCustomListAdapter;
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel.Sector;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.equipment.EquipmentEditView;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class SupplierHome extends PolarisForm {

    @FXML
    private TextField txt_search;

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_save_qoutation;

    @FXML
    private ListView<SupplierHomeList> lst_supplier;

    @FXML
    private Label lbl_code;

    @FXML
    private ComboBox<EquipmentSupplierModel.Sector> cmb_sector;

    @FXML
    private Label lbl_sector_selected;

    @FXML
    private RadioButton rdb_yes;

    @FXML
    private ToggleGroup rdo_group_accredited;

    @FXML
    private RadioButton rdb_no;

    @FXML
    private Label lbl_accredited_selected;

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
    private TextField txt_name;

    @FXML
    private TextField txt_website;

    @FXML
    private JFXButton btn_select;

    @FXML
    private Label lbl_equipment_name;

    @FXML
    private JFXButton btn_delete;

    /**
     * Initialize this object.
     *
     * @param equipModel
     */
    public SupplierHome(EquipmentQoutationModel equipModel, EquipmentSupplierModel currentSupplier) {
        this.selectedEquipmentModel = equipModel;
        this.setDialogMessageTitle("Supplier");
        this.observeableListItems = FXCollections.observableArrayList();
        this.selectedSupplierModel = currentSupplier;
    }

    private void preSelectedSupplier() {
        if (this.selectedSupplierModel != null) {

            for (SupplierHomeList item : lst_supplier.getItems()) {
                if (item.getSupplierModel().getSupplierCode()
                        .equalsIgnoreCase(this.selectedSupplierModel.getSupplierCode())) {
                    this.lst_supplier.getSelectionModel().select(item);
                    break;
                }
            }

        }
    }
    //--------------------------------------------------------------------------
    private final ObservableList<SupplierHomeList> observeableListItems;
    //--------------------------------------------------------------------------
    private final EquipmentQoutationModel selectedEquipmentModel;
    //--------------------------------------------------------------------------
    private final EquipmentSupplierModel selectedSupplierModel;
    //--------------------------------------------------------------------------
    // String List
    //--------------------------------------------------------------------------
    private final static String STR_NOT_ACCREDITED = "This supplier is NOT Accedited";
    private final static String STR_ACCREDITED = "This supplier is DOST Accedited";
    private final static String STR_NA = "N/A";
    //--------------------------------------------------------------------------

    /**
     * Initialize View.
     */
    @Override
    protected void setup() {
        //----------------------------------------------------------------------
        // build components
        ProjectHeader.attach(hbox_header);
        this.lbl_code.setText(STR_NA);
        this.cmb_sector.getItems().setAll(Arrays.asList(EquipmentSupplierModel.Sector.LIST));
        this.cmb_sector.getSelectionModel().selectFirst();
        this.rdb_no.setSelected(true);
        //----------------------------------------------------------------------
        // build list
        this.populateList();
        this.constructCustomList();
        //----------------------------------------------------------------------
        this.enableEdit(false);
        //----------------------------------------------------------------------
        this.lbl_equipment_name.setText(this.selectedEquipmentModel.getEquipmentName());
        //----------------------------------------------------------------------
        // Actions
        this.rdo_group_accredited.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (this.rdb_yes.isSelected()) {
                this.lbl_accredited_selected.setText(STR_ACCREDITED);
            } else {
                this.lbl_accredited_selected.setText(STR_NOT_ACCREDITED);
            }
        });

        this.cmb_sector.valueProperty().addListener((ObservableValue<? extends EquipmentSupplierModel.Sector> observable, EquipmentSupplierModel.Sector oldValue, EquipmentSupplierModel.Sector newValue) -> {
            this.lbl_sector_selected.setText(newValue.toString());
        });

        this.btn_back.setOnMouseClicked(value -> {
            EquipmentEditView editEquip = new EquipmentEditView(this.selectedEquipmentModel);
            editEquip.load();
            this.changeRoot(editEquip.getRootPane());
        });

        /**
         * Add New.
         */
        this.btn_add.setOnMouseClicked(value -> {
            //------------------------------------------------------------------
            SupplierEdit supplyEdit = new SupplierEdit(null, this.selectedEquipmentModel);
            supplyEdit.load();
            this.changeRoot(supplyEdit.getRootPane());
        });

        //----------------------------------------------------------------------
        // Save or Edit Entries.
        this.btn_save_qoutation.setOnMouseClicked(value -> {
            SupplierHomeList list = this.lst_supplier.getSelectionModel().getSelectedItem();
            // if no item was selected in the list prompt user
            if (list == null) {
                this.showWaitWarningMessage(null, "Please select a supplier to update");
                return;
            }

            //------------------------------------------------------------------
            SupplierEdit supplyEdit = new SupplierEdit(list.getSupplierModel(), this.selectedEquipmentModel);
            supplyEdit.load();
            this.changeRoot(supplyEdit.getRootPane());
        });

        this.btn_select.setOnMouseClicked(value -> {
            SupplierHomeList list = this.lst_supplier.getSelectionModel().getSelectedItem();
            // if no item was selected in the list prompt user
            if (list == null) {
                this.showWaitWarningMessage(null, "Please select a supplier for this equipment.");
                return;
            }
            //------------------------------------------------------------------
            EquipmentQoutationModel qoutation = this.selectedEquipmentModel;
            this.selectedEquipmentModel.setSupplierCode(list.getSupplierModel().getSupplierCode());

            if (this.changeSupplier(qoutation)) {
                //
                EquipmentEditView editView = new EquipmentEditView(qoutation);
                editView.load();
                this.changeRoot(editView.getRootPane());
            }

            value.consume();
        });

        this.btn_delete.setOnMouseClicked(value -> {
            SupplierHomeList list = this.lst_supplier.getSelectionModel().getSelectedItem();
            // if no item was selected in the list prompt user
            if (list == null) {
                this.showWaitWarningMessage(null, "Please select a supplier to delete.");
                return;
            }
            //------------------------------------------------------------------
            // Remove Code
            //------------------------------------------------------------------
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this supplier? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = EquipmentSupplierModel.removeSupplier(list.getSupplierModel());
                    if (deleted) {
                        this.showInformationMessage(null, "Supplier successfully deleted.");
                        // refresh table
                        this.populateList();
                        //
                    } else {
                        this.showInformationMessage(null, "Supplier cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showWaitExceptionMessage(e, null, "Failed to delete supplier.");
                }
            }

            value.consume();
        });

        //----------------------------------------------------------------------
        this.lst_supplier.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SupplierHomeList> observable, SupplierHomeList oldValue, SupplierHomeList newValue) -> {
            this.listChangeListener(newValue, oldValue);
        });

        //----------------------------------------------------------------------
        this.preSelectedSupplier();
        //----------------------------------------------------------------------
    } // END SETUP

    private boolean changeSupplier(EquipmentQoutationModel qoutation) {
        boolean updated = false;
        try {
            updated = EquipmentQoutationModel.updateEquip(qoutation);
            if (updated) {
                this.showWaitInformationMessage(null, "Supplier was successfully added to the database.");
            } else {
                this.showWaitWarningMessage(null, "The Supplier cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to insert New Supplier.");
        }
        return updated;
    }

    private void deleteSupplier() {

    }

    /**
     * Executes when the selection of the list view changes.
     *
     * @param newValue
     */
    private void listChangeListener(SupplierHomeList newValue, SupplierHomeList old) {
        //----------------------------------------------------------------------
        if (newValue == null) {
            return;
        }
        //----------------------------------------------------------------------
        EquipmentSupplierModel model = newValue.getSupplierModel();
        if (model == null) {
            return;
        }

        //
        this.lbl_code.setText(model.getSupplierCode());
        this.txt_name.setText(model.getSupplierName());

        Sector sector = Sector.getObject(model.getSector());
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

    /**
     * Checks for components editable status.
     *
     * 0* @param editable
     */
    private void enableEdit(boolean editable) {
        this.txt_name.setEditable(editable);
        this.cmb_sector.setDisable(!editable);
        this.rdb_no.setDisable(!editable);
        this.rdb_yes.setDisable(!editable);
        this.txt_mobile.setEditable(editable);
        this.txt_tel.setEditable(editable);
        this.txt_fax.setEditable(editable);
        this.txt_email.setEditable(editable);
        this.txt_address.setEditable(editable);
        this.txt_website.setEditable(editable);
    }

    /**
     * Refresh List.
     */
    private void populateList() {
        List<EquipmentSupplierModel> equipments = null;
        try {
            equipments = EquipmentSupplierModel.getAllActiveSupplier();
        } catch (SQLException e) {
            this.showWaitExceptionMessage(e, "Cannot Retrieve Data !", "Cannot Retrieve Supplier Records !");
        }

        // if error go back
        if (equipments == null) {
            return;
        }
        this.observeableListItems.clear();
        /**
         * Populate observable list.
         */
        for (EquipmentSupplierModel supplier : equipments) {
            SupplierHomeList listItem = new SupplierHomeList();
            listItem.setSupplierModel(supplier);
            listItem.load();
            this.observeableListItems.add(listItem);
        }

    }

    /**
     * Construct Custom List.
     */
    private void constructCustomList() {
        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<SupplierHomeList> filteredResult = new FilteredList<>(this.observeableListItems, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((SupplierHomeList supplier) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (supplier.getSupplierModel().getSupplierName().toLowerCase().contains(newValue)) {
                    return true;
                }

                if (supplier.getSector().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        //----------------------------------------------------------------------
        /**
         * customize list view output.
         */
        PolarisCustomListAdapter adapter = new PolarisCustomListAdapter(this.lst_supplier, filteredResult);
        adapter.setCustomCellPrefHeight(70.0);
        adapter.customize();
        //----------------------------------------------------------------------
    }

}
