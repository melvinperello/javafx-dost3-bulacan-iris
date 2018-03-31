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
package gov.dost.bulacan.iris.ui.training;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ContactInformationModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.models.TrainingModel;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class TrainingHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableView<TrainingModel> tbl_trainings;

    //==========================================================================
    // C-01. Initialization of Class Variables
    //==========================================================================
    public TrainingHome() {
        this.setDialogMessageTitle("Trainings and Seminar");
        this.tableData = FXCollections.observableArrayList();
    }

    //==========================================================================
    // C-02. Declaration of Class Variables
    //==========================================================================
    private final ObservableList<TrainingModel> tableData;

    @Override
    protected void setup() {
        //======================================================================
        // S-01. Controller Initialization
        //======================================================================
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);
        //======================================================================
        // S-02. Table Construction
        //======================================================================
        this.createTable();
        this.populateTable();
        //======================================================================
        // S-03. Event Section
        //======================================================================
    }

    //==========================================================================
    // C-03. Methods
    //==========================================================================
    private void createTable() {
        //----------------------------------------------------------------------
        TableColumn<TrainingModel, String> titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(600.0);
        titleCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTrainingTitle()));
        //----------------------------------------------------------------------
        TableColumn<TrainingModel, String> venueCol = new TableColumn<>("Venue");
        venueCol.setPrefWidth(400.0);
        venueCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getVenue()));
        //----------------------------------------------------------------------
        TableColumn<TrainingModel, String> dateStart = new TableColumn<>("Date");
        dateStart.setPrefWidth(120.0);
        dateStart.setCellValueFactory(value -> {
            TrainingModel model = (TrainingModel) value.getValue();
            Date date = model.getDateStart();
            String dateString = "";
            if (date != null) {
                dateString = Context.app().getDateFormatNamed().format(date);
            }
            return new SimpleStringProperty(dateString);
        });
        //----------------------------------------------------------------------

        this.tbl_trainings.getColumns().setAll(dateStart,titleCol,venueCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<TrainingModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((TrainingModel model) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (model.getTrainingTitle().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TrainingModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_trainings.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_trainings.setItems(sortedData);
    }

    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<TrainingModel> list = new ArrayList<>();
        try {
            list = TrainingModel.listAllActive();
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load projects.");
        }
        this.tableData.addAll(list);
    }

}
