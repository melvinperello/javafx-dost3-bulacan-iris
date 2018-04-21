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
package gov.dost.bulacan.iris.ui.scholarship;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ScholarInformationModel;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author s500
 */
public class ScholarshipHome extends IrisForm {
    
    private static Logger logger = LoggerFactory.getLogger(ScholarshipHome.class);
    
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
    private JFXButton btn_edit;
    
    @FXML
    private JFXButton btn_remove;
    
    @FXML
    private TableView<ScholarInformationModel> tbl_scholars;
    
    public ScholarshipHome() {
        this.tableData = FXCollections.observableArrayList();
        this.setDialogMessageTitle("Scholarship");
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ScholarInformationModel> tableData;
    
    @Override
    protected void setup() {
        logger.debug("Controller Initialized");
        //======================================================================
        // S-01. Controller Initialization
        //======================================================================
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);
        //
        this.createTable();
        this.populateTable();
        //
        this.btn_add.setOnMouseClicked(value -> {
            this.changeRoot(new ScholarEdit(null).load());
            value.consume();
        });
        //
        this.btn_edit.setOnMouseClicked(value -> {
            ScholarInformationModel model = this.tbl_scholars.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please highlight an entry to edit.");
                return;
            }
            
            this.changeRoot(new ScholarEdit(model).load());
            value.consume();
        });
        
        this.btn_remove.setOnMouseClicked(value -> {
            ScholarInformationModel model = this.tbl_scholars.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please highlight an entry to remove.");
                return;
            }
            //------------------------------------------------------------------
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this entry? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = ScholarInformationModel.remove(model);
                    if (deleted) {
                        this.showInformationMessage(null, "Entry successfully deleted from the database.");
                        // refresh table
                        this.populateTable();
                    } else {
                        this.showInformationMessage(null, "Entry cannot be deleted at the moment. Please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showExceptionMessage(e, null, "Failed to delete entry.");
                }
            }

            //------------------------------------------------------------------
            value.consume();
        });
    }
    
    private void createTable() {
        TableColumn<ScholarInformationModel, String> studentNumber = new TableColumn<>("Student Number");
        studentNumber.setPrefWidth(120.0);
        studentNumber.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getStudentNumber()));
        //----------------------------------------------------------------------
        TableColumn<ScholarInformationModel, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setPrefWidth(300.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFullName().toUpperCase(Locale.ENGLISH)));
        //----------------------------------------------------------------------
        TableColumn<ScholarInformationModel, String> type = new TableColumn<>("Type");
        type.setPrefWidth(300.0);
        type.setCellValueFactory(value -> {
            SimpleStringProperty valThrow = new SimpleStringProperty("");
            ScholarInformationModel cell = (ScholarInformationModel) value.getValue();
            
            Integer meritType = cell.getMeritType();
            Integer scholarType = cell.getScholarType();
            String meritString = ScholarInformationModel.ScholarType.Merit.toObject(meritType).getName();
            String scholarString = ScholarInformationModel.ScholarType.toObject(scholarType).getName();
            valThrow.set("( " + meritString + " )" + "  " + scholarString);
            
            return valThrow;
        });
        //----------------------------------------------------------------------
        TableColumn<ScholarInformationModel, String> yearLevel = new TableColumn<>("Year Level");
        yearLevel.setPrefWidth(100.0);
        yearLevel.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getYear().toString()));
        //----------------------------------------------------------------------
        TableColumn<ScholarInformationModel, String> course = new TableColumn<>("Course");
        course.setPrefWidth(150.0);
        course.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCourse()));
        //----------------------------------------------------------------------
        TableColumn<ScholarInformationModel, String> mobile = new TableColumn<>("Mobile");
        mobile.setPrefWidth(150.0);
        mobile.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMobileNo()));
        
        this.tbl_scholars.getColumns().setAll(studentNumber, nameCol, type, yearLevel, course, mobile);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<ScholarInformationModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((ScholarInformationModel model) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (model.getStudentNumber().toLowerCase().equals(newValue)) {
                    return true;
                }

                /**
                 * Allow search of cooperator's name.
                 */
                if (model.getFullName().toLowerCase().contains(newValue)) {
                    return true;
                }
                
                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<ScholarInformationModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_scholars.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_scholars.setItems(sortedData);
    }

    /**
     * Populate table with contents. for refresh also of date.
     */
    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ScholarInformationModel> inquiries = new ArrayList<>();
        try {
            inquiries = ScholarInformationModel.listAllActive();
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load data.");
        }
        this.tableData.addAll(inquiries);
    }
    
}
