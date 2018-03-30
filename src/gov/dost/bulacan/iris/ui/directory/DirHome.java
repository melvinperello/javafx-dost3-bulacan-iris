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
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ContactInformationModel;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class DirHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_add_contacts;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private TableView<ContactInformationModel> tbl_contacts;

    public DirHome() {
        this.setDialogMessageTitle("Local Directory");
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ContactInformationModel> tableData;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);

        this.createTable();
        this.populateTable();

        this.btn_add_contacts.setOnMouseClicked(value -> {
            this.changeRoot(new DirEdit(null).load());
            value.consume();
        });

        this.btn_edit.setOnMouseClicked(value -> {
            ContactInformationModel model = this.tbl_contacts.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please highlight a contact to view.");
                return;
            }

            this.changeRoot(new DirEdit(model).load());

            value.consume();
        });

        this.btn_delete.setOnMouseClicked(value -> {
            ContactInformationModel model = this.tbl_contacts.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please highlight a contact to remove.");
                return;
            }

            //------------------------------------------------------------------
            // Remove Code
            //------------------------------------------------------------------
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this contact? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = ContactInformationModel.remove(model);
                    if (deleted) {
                        this.showInformationMessage(null, "Contact Information successfully deleted.");
                        // refresh table
                        this.populateTable();
                    } else {
                        this.showInformationMessage(null, "Contact Information cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showExceptionMessage(e, null, "Failed to delete Contact Information.");
                }
            }

            value.consume();
        });

    }

    /**
     * Create Table.
     */
    private void createTable() {
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> orgCol = new TableColumn<>("Organization");
        orgCol.setPrefWidth(100.0);
        orgCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getOrganization()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> orgTypeCol = new TableColumn<>("Type");
        orgTypeCol.setPrefWidth(100.0);
        orgTypeCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getOrgType()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> officeNameCol = new TableColumn<>("Office");
        officeNameCol.setPrefWidth(100.0);
        officeNameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getOfficeName()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> contactPerCol = new TableColumn<>("Contact");
        contactPerCol.setPrefWidth(100.0);
        contactPerCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getContactPerson()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> telCol = new TableColumn<>("Tel");
        telCol.setPrefWidth(100.0);
        telCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTelNo()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> mobCol = new TableColumn<>("Mobile");
        mobCol.setPrefWidth(100.0);
        mobCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMobileNo()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> faxCol = new TableColumn<>("Fax");
        faxCol.setPrefWidth(100.0);
        faxCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFaxNo()));
        //----------------------------------------------------------------------
        TableColumn<ContactInformationModel, String> mailCol = new TableColumn<>("E-Mail");
        mailCol.setPrefWidth(100.0);
        mailCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));

        this.tbl_contacts.getColumns().setAll(orgCol, orgTypeCol, officeNameCol,
                contactPerCol, telCol, mobCol, faxCol, mailCol
        );

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<ContactInformationModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((ContactInformationModel contacts) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (contacts.getOrganization().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<ContactInformationModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_contacts.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_contacts.setItems(sortedData);
    }

    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ContactInformationModel> list = new ArrayList<>();
        try {
            list = ContactInformationModel.listAllActive();
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load projects.");
        }
        this.tableData.addAll(list);
    }

}
