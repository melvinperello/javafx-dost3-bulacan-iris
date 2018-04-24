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
package gov.dost.bulacan.iris.ui.scholarship.transmit;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ScholarInformationModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.scholarship.ScholarshipHome;
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
 * @author DOST-3
 */
public class TransmittalHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_transmit;

    @FXML
    private TableView<TransmitData> tbl_transmit;

    public TransmittalHome() {
        this.setDialogMessageTitle("Transmittal");
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<TransmitData> tableData;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);

        /**
         * Back to home.
         */
        this.btn_back_to_home.setOnMouseClicked(value -> {
            this.changeRoot(new ScholarshipHome().load());
            value.consume();
        });

        /**
         * View transmitted documents.
         */
        this.btn_view.setOnMouseClicked(value -> {

            value.consume();
        });

        /**
         * Transmit documents.
         */
        this.btn_transmit.setOnMouseClicked(value -> {

            value.consume();
        });

        this.createTable();
    }

    private void createTable() {
        TableColumn<TransmitData, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(200.0);
        dateCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDate()));

        TableColumn<TransmitData, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(600.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCareOf()));

        TableColumn<TransmitData, String> entryCol = new TableColumn<>("No. of Entries");
        entryCol.setPrefWidth(300.0);
        entryCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCareOf()));

        this.tbl_transmit.getColumns().setAll(dateCol, nameCol, entryCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<TransmitData> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((TransmitData model) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (model.getDate().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TransmitData> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_transmit.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_transmit.setItems(sortedData);
    }

    public final static class TransmitData {

        private String date;
        private String careOf;
        private String numberOfDocuments;

        public String getDate() {
            return date;
        }

        public String getCareOf() {
            return careOf;
        }

        public String getNumberOfDocuments() {
            return numberOfDocuments;
        }

    }

}
