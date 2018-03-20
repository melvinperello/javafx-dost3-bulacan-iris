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
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author Jhon Melvin
 */
public class EquipmentView extends PolarisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private ListView<EquipmentViewListItem> list_equipment;

    public EquipmentView() {
        this.observeableListItems = FXCollections.observableArrayList();
    }
    private final ObservableList<EquipmentViewListItem> observeableListItems;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);

        this.populateList();
        this.constructCustomList();

        this.btn_view.setOnMouseClicked(value -> {
            EquipmentViewListItem selectedProject = this.list_equipment.getSelectionModel().getSelectedItem();
            if (selectedProject == null) {
                this.showWarningMessage(null, "Please select equipment to view.");
                return;
            }

            EquipmentQoutationModel model = selectedProject.getQouteModel();
            //
            EquipmentEditView equipEdit = new EquipmentEditView(model);
            this.changeRoot(equipEdit.load());

            value.consume();
        });
        this.btn_add.setOnMouseClicked(value -> {
            EquipmentEditView equipEdit = new EquipmentEditView(null);
            this.changeRoot(equipEdit.load());
            value.consume();
        });
        this.btn_remove.setOnMouseClicked(value -> {
            EquipmentViewListItem selectedProject = this.list_equipment.getSelectionModel().getSelectedItem();
            if (selectedProject == null) {
                this.showWarningMessage(null, "Please select equipment to delete.");
                return;
            }

            EquipmentQoutationModel model = selectedProject.getQouteModel();
            //------------------------------------------------------------------
            // Remove Code
            //------------------------------------------------------------------
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this equipment? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = EquipmentQoutationModel.removeEquip(model);
                    if (deleted) {
                        this.showInformationMessage(null, "Equipment successfully deleted.");
                        // refresh table
                        this.populateList();
                    } else {
                        this.showInformationMessage(null, "Equipment cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showWaitExceptionMessage(e, null, "Failed to delete equipment.");
                }
            }
            value.consume();
        });
    }

    private void constructCustomList() {
        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<EquipmentViewListItem> filteredResult = new FilteredList<>(this.observeableListItems, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((EquipmentViewListItem equipment) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (equipment.getQouteModel().getEquipmentName().toLowerCase().contains(newValue)) {
                    return true;
                }

                if (equipment.getQouteModel().getKeyword().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        /**
         * Add filtering
         */
        this.list_equipment.setItems(filteredResult);

        //----------------------------------------------------------------------
        /**
         * customize list view output.
         */
        this.list_equipment.setCellFactory(new Callback<ListView<EquipmentViewListItem>, ListCell<EquipmentViewListItem>>() {
            @Override
            public ListCell<EquipmentViewListItem> call(ListView<EquipmentViewListItem> param) {
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
                            EquipmentViewListItem listItem = (EquipmentViewListItem) item;
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

    private void populateList() {
        List<EquipmentQoutationModel> equipments = null;
        try {
            equipments = EquipmentQoutationModel.getAllActiveEquipment();
        } catch (SQLException e) {
            this.showWaitExceptionMessage(e, "Cannot Retrieve Data !", "Cannot Retrieve Equipment Records !");
        }

        // if error go back
        if (equipments == null) {
            return;
        }
        this.observeableListItems.clear();
        /**
         * Populate observable list.
         */
        for (EquipmentQoutationModel equip : equipments) {
            EquipmentViewListItem listItem = new EquipmentViewListItem();
            listItem.setQouteModel(equip);
            listItem.load();
            this.observeableListItems.add(listItem);
        }

    }

}
