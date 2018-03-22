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
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel.Sector;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.equipment.EquipmentEditView;
import gov.dost.bulacan.iris.ui.equipment.EquipmentView;
import gov.dost.bulacan.iris.ui.equipment.EquipmentViewListItem;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
    private JFXButton btn_clear;

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

    public SupplierHome(EquipmentQoutationModel equipModel) {
        this.equipModel = equipModel;
        this.setDialogMessageTitle("Supplier");
        this.observeableListItems = FXCollections.observableArrayList();
    }

    private final ObservableList<SupplierHomeList> observeableListItems;

    private final EquipmentQoutationModel equipModel;
    private final static String STR_NOT_ACCREDITED = "This supplier is NOT Accedited";
    private final static String STR_ACCREDITED = "This supplier is DOST Accedited";

    @Override
    protected void setup() {
        ProjectHeader.attach(hbox_header);
        this.cmb_sector.getItems().setAll(Arrays.asList(EquipmentSupplierModel.Sector.LIST));
        this.cmb_sector.getSelectionModel().selectFirst();
        this.rdb_no.setSelected(true);

        //
        this.populateList();
        this.constructCustomList();

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
            EquipmentEditView editEquip = new EquipmentEditView(equipModel);
            editEquip.load();
            this.changeRoot(editEquip.getRootPane());
        });

        /**
         * Add New.
         */
        this.btn_add.setOnMouseClicked(value -> {
            if (this.addSupplier()) {
                this.clear();
            }
        });

        /**
         * Clear form.
         */
        this.btn_clear.setOnMouseClicked(value -> {
            this.clear();
        });

        /**
         * Save changes.
         */
        this.btn_save_qoutation.setOnMouseClicked(value -> {

        });
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

                return false; // no match.
            });
        });

        /**
         * Add filtering
         */
        this.lst_supplier.setItems(filteredResult);

        //----------------------------------------------------------------------
        /**
         * customize list view output.
         */
        this.lst_supplier.setCellFactory(new Callback<ListView<SupplierHomeList>, ListCell<SupplierHomeList>>() {
            @Override
            public ListCell<SupplierHomeList> call(ListView<SupplierHomeList> param) {
                return new ListCell() {
                    {
                        this.setPrefHeight(70.0);
                    }

                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            /**
                             * Must implement Polaris list item. must be loaded.
                             */
                            SupplierHomeList listItem = (SupplierHomeList) item;
                            // bind pref width.
                            listItem.getRootPane().prefWidthProperty().bind(this.prefWidthProperty());
                            // load to cell.
                            setGraphic(listItem.getCustomListCellGraphic());
                        } else {
                            /**
                             * Redraws the cell.
                             */
                            setGraphic(null);
                        }

                    }
                };
            }
        });
        //----------------------------------------------------------------------
    }

    /**
     * Clear Text Fields.
     */
    private void clear() {
        this.lbl_code.setText("N/A");
        this.cmb_sector.getSelectionModel().selectFirst();
        this.lbl_sector_selected.setText(this.cmb_sector.getSelectionModel().getSelectedItem().toString());
        this.rdb_no.setSelected(true);
        this.lbl_accredited_selected.setText(STR_NOT_ACCREDITED);
        this.txt_mobile.setText("");
        this.txt_tel.setText("");
        this.txt_fax.setText("");
        this.txt_email.setText("");
        this.txt_address.setText("");
        this.txt_name.setText("");
        this.txt_website.setText("");
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
        Sector selectedSector = this.cmb_sector.getSelectionModel().getSelectedItem();
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
            inserted = EquipmentSupplierModel.addNewSupplier(supplier);
            if (inserted) {
                this.showInformationMessage(null, "Supplier was successfully added to the database.");
            } else {
                this.showWarningMessage(null, "The Supplier cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to insert New Supplier.");
        }
        return inserted;
    }

}
