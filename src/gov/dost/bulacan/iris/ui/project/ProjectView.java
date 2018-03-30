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
package gov.dost.bulacan.iris.ui.project;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ProjectModel;
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
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectView extends IrisForm {

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view_project;

    @FXML
    private JFXButton btn_new_project;

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_delete_project;

    @FXML
    private TableView<ProjectModel> tbl_project_table;

    public ProjectView() {
        this.tableData = FXCollections.observableArrayList();
        this.setDialogMessageTitle("Project View");
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ProjectModel> tableData;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);
        /**
         * Populate and create the table.
         */
        this.createTable();
        this.populateTable();

//        /**
//         * Add Action to table click.
//         */
//        this.tbl_project_table.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ProjectModel> observable, ProjectModel oldValue, ProjectModel newValue) -> {
//            ProjectModel selectedProject = newValue;
//            
//        });
        /**
         * New Project Action.
         */
        this.btn_new_project.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectDetailsEdit(this, null).load());
            value.consume();
        });

        this.btn_view_project.setOnMouseClicked(value -> {
            ProjectModel selectedProject = this.tbl_project_table.getSelectionModel().getSelectedItem();
            if (selectedProject == null) {
                this.showWarningMessage(null, "Please highlight a project to view.");
                return;
            }
            // open project view
            if (ProjectDetailsView.reloadModel(selectedProject)) {
                this.changeRoot(new ProjectDetailsView(selectedProject).load());
            }

            value.consume();
        });

        this.btn_delete_project.setOnMouseClicked(value -> {
            ProjectModel selectedProject = this.tbl_project_table.getSelectionModel().getSelectedItem();
            if (selectedProject == null) {
                this.showWarningMessage(null, "Please highlight a project to delete.");
                return;
            }
            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this project? This operation is ireversible.");
            if (res == 1) {
                try {
                    boolean deleted = ProjectModel.remove(selectedProject);
                    if (deleted) {
                        this.showInformationMessage(null, "Contact successfully deleted to this project.");
                        // refresh table
                        this.populateTable();
                    } else {
                        this.showInformationMessage(null, "Contact cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showExceptionMessage(e, null, "Failed to delete project.");
                }
            }
            value.consume();
        });
    }

    /**
     * Create the table including predicate.
     */
    private void createTable() {
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> yearCol = new TableColumn<>("Year");
        yearCol.setPrefWidth(75.0);
        yearCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            Date approved = project.getApprovedDate();
            String date = "";
            if (approved != null) {
                date = new SimpleDateFormat("yyyy").format(approved);
            }
            return new SimpleStringProperty(date);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> typeCol = new TableColumn<>("Type");
        typeCol.setPrefWidth(100.0);
        typeCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getProjectType()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(100.0);
        statusCol.setCellValueFactory(value -> new SimpleStringProperty(ProjectModel.ProjectStatus.getStringValue(value.getValue().getProjectStatus())));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> coopCol = new TableColumn<>("Cooperator");
        coopCol.setPrefWidth(245.0);
        coopCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCompanyName()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> districtCol = new TableColumn<>("District");
        districtCol.setPrefWidth(60.0);
        districtCol.setCellValueFactory(value -> new SimpleStringProperty(ProjectModel.TownValueModel.getTown(value.getValue().getFactoryCity()).getDistrict()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> endorseCol = new TableColumn<>("Endorsed");
        endorseCol.setPrefWidth(100.0);
        endorseCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            Date date = project.getEndorsedDate();
            String dateString = "";
            if (date != null) {
                dateString = new SimpleDateFormat("MM-dd-yy").format(date);
            }
            return new SimpleStringProperty(dateString);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> approvedCol = new TableColumn<>("Approved");
        approvedCol.setPrefWidth(100.0);
        approvedCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            Date date = project.getApprovedDate();
            String dateString = "";
            if (date != null) {
                dateString = new SimpleDateFormat("MM-dd-yy").format(date);
            }
            return new SimpleStringProperty(dateString);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> actualCostCol = new TableColumn<>("Actual Cost");
        actualCostCol.setPrefWidth(150.0);
        actualCostCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            String cost = "";
            if (project.getActualCost() != null) {
                cost = "P " + Context.app().getMoneyFormat().format(project.getActualCost());
            }
            return new SimpleStringProperty(cost);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> moaCol = new TableColumn<>("MOA Signed");
        moaCol.setPrefWidth(100.0);
        moaCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            Date date = project.getMoaDate();
            String dateString = "";
            if (date != null) {
                dateString = new SimpleDateFormat("MM-dd-yy").format(date);
            }
            return new SimpleStringProperty(dateString);
        });

        this.tbl_project_table.getColumns().setAll(yearCol, typeCol, statusCol, coopCol, districtCol, endorseCol,
                approvedCol, actualCostCol, moaCol
        );

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<ProjectModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((ProjectModel project) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (project.getCompanyName().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<ProjectModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_project_table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_project_table.setItems(sortedData);
    }

    /**
     * Populate table with contents. for refresh also of date.
     */
    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ProjectModel> inquiries = new ArrayList<>();
        try {
            inquiries = ProjectModel.listAllActive();
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load projects.");
        }
        this.tableData.addAll(inquiries);
    }

}
