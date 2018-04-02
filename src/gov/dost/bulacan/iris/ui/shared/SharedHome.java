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
package gov.dost.bulacan.iris.ui.shared;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.RaidModel;
import gov.dost.bulacan.iris.models.SharedDocumentModel;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.raid.RaidDownload;
import gov.dost.bulacan.iris.ui.raid.RaidUpload;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisCustomListAdapter;

/**
 *
 * @author Jhon Melvin
 */
public class SharedHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXButton btn_refresh;

    @FXML
    private ListView<DocumentItem> list_files;

    public SharedHome() {
        this.setDialogMessageTitle("Shared Documents");
        this.observeableListItems = FXCollections.observableArrayList();
    }

    private final ObservableList<DocumentItem> observeableListItems;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);
        //

        this.populateList();
        this.constructCustomList();

        this.btn_refresh.setOnMouseClicked(value -> {
            this.txt_search.setText("");
            this.populateList();
            value.consume();
        });

        this.list_files.setOnMouseClicked(value -> {
            if (value.getClickCount() == 2 && value.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                // double click primary
                DocumentItem selectedItem = this.list_files.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    return;
                }
                SharedDocumentModel model = selectedItem.getDocumentModel();

                TextInputDialog dialog = new TextInputDialog(model.getDocName());
                dialog.initOwner(this.getStage());
                dialog.initModality(Modality.WINDOW_MODAL);

                dialog.getDialogPane().setPrefWidth(500.0);

                dialog.setTitle("Rename File");
                dialog.setHeaderText("Rename File");
                dialog.setContentText("New Name");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    if (name.isEmpty()) {
                        return;
                    }

                    if (name.equals(model.getDocName())) {
                        return;
                    }
                    try {

                        model.setDocName(name);
                        if (SharedDocumentModel.update(model)) {
                            // success
                            this.populateList();
                            this.showWaitInformationMessage(null, "Shared File Successfully Renamed !");
                            return;
                        }
                        // failed
                        this.showWaitWarningMessage(null, "Failed to renamed shared file.");
                    } catch (SQLException e) {
                        // error
                        this.showExceptionMessage(e, "Rename Failed", "Failed to renamed shared file.");
                    }
                });

            }
        });

        this.btn_view.setOnMouseClicked(value -> {
            DocumentItem selectedItem = this.list_files.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                this.showWarningMessage(null, "Please select file to view.");
                return;
            }

            SharedDocumentModel model = selectedItem.getDocumentModel();
            RaidModel raid = model.getLinkedModel();
            //------------------------------------------------------------------
            RaidDownload.callRaidUpload(raid).showAndWait();
        });

        this.btn_add.setOnMouseClicked(value -> {
            RaidUpload.callRaidUpload((raidModel) -> {
                return this.insert(raidModel);
            }).showAndWait();
            this.populateList();
        });

        this.btn_remove.setOnMouseClicked(value -> {
            DocumentItem selectedItem = this.list_files.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                this.showWarningMessage(null, "Please select file to remove.");
                return;
            }

            SharedDocumentModel model = selectedItem.getDocumentModel();
            //------------------------------------------------------------------
            // Remove Code
            //------------------------------------------------------------------
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this file? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = SharedDocumentModel.remove(model);
                    if (deleted) {
                        this.showInformationMessage(null, "File successfully removed.");
                        // refresh table
                        this.populateList();
                    } else {
                        this.showInformationMessage(null, "File cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showExceptionMessage(e, null, "Failed to delete file.");
                }
            }

        });
    }

    /**
     * Refresh List.
     */
    private void populateList() {

        //----------------------------------------------------------------------
        List<SharedDocumentModel> listItems = null;
        try {
            listItems = SharedDocumentModel.listActiveFilesWithRaid();
        } catch (SQLException e) {
            this.showExceptionMessage(e, "Cannot Retrieve Data !", "Cannot Retrieve Equipment Records !");
        }
        //----------------------------------------------------------------------

        // if error go back
        if (listItems == null) {
            return;
        }
        this.observeableListItems.clear();
        /**
         * Populate observable list.
         */
        for (SharedDocumentModel equip : listItems) {
            DocumentItem listItem = new DocumentItem();
            listItem.setDocumentModel(equip);
            listItem.load();
            this.observeableListItems.add(listItem);
        }

    }

    private void constructCustomList() {
        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<DocumentItem> filteredResult = new FilteredList<>(this.observeableListItems, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((DocumentItem listItem) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (listItem.getDocumentModel().getDocName().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        PolarisCustomListAdapter adapter = new PolarisCustomListAdapter(this.list_files, filteredResult);
        adapter.setCustomCellPrefHeight(50.0);
        adapter.customize();
        //----------------------------------------------------------------------
    }

    /**
     *
     * @param model
     * @return
     */
    private boolean insert(RaidModel model) {
        SharedDocumentModel docs = new SharedDocumentModel();
        docs.setDocId(Context.createLocalKey());
        docs.setLinkedModel(model);

        try {
            return SharedDocumentModel.insert(docs);
        } catch (SQLException e) {
            return false;
        }
    }

}
