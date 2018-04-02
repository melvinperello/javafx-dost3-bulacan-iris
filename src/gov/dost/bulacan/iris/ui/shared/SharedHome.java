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
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.RaidModel;
import gov.dost.bulacan.iris.models.SharedDocumentModel;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.equipment.EquipmentViewListItem;
import gov.dost.bulacan.iris.ui.raid.RaidUpload;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        
        this.btn_add.setOnMouseClicked(value -> {
            RaidUpload.callRaidUpload((raidModel) -> {
                return this.insert(raidModel);
            }).showAndWait();
            this.populateList();
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
