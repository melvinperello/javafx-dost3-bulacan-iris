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
import gov.dost.bulacan.iris.Messageable;
import gov.dost.bulacan.iris.models.ProjectModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectView extends PolarisFxController implements Messageable {

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view_project;

    @FXML
    private JFXButton btn_new_project;

    @FXML
    private TableView<ProjectModel> tbl_project_table;

    public ProjectView() {
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ProjectModel> tableData;

    @Override
    protected void setup() {
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
//            this.changeRoot(new ProjectDetailsEdit().load());
//            value.consume();
        });

        this.btn_view_project.setOnMouseClicked(value -> {
            ProjectModel selectedProject = this.tbl_project_table.getSelectionModel().getSelectedItem();
            if (selectedProject == null) {
                this.showWarningMessage("Please highlight a project to view.");
                return;
            }
            // open project view
            this.changeRoot(new ProjectDetailsView(this.getRootPane(), selectedProject).load());

            value.consume();
        });
    }

    //--------------------------------------------------------------------------
    // Message Boxes for this window.
    //--------------------------------------------------------------------------
    @Override
    public void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle("SETUp/GIA Project")
                .setHeaderText("Warning")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    @Override
    public void showInformationMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.INFORMATION)
                .setTitle("SETUp/GIA Project")
                .setHeaderText("Information")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    @Override
    public void showErrorMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.ERROR)
                .setTitle("SETUp/GIA Project")
                .setHeaderText("Something Went Wrong !")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    @Override
    public int showConfirmation(String message) {
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("SETUp/GIA Project")
                .setHeaderText("Please Confirm")
                .setContentText(message)
                .setOwner(this.getStage())
                .setButtons(yesButton, cancelButton)
                .showAndWait();
        if (res.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            return 1;
        }
        return 0;
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
        districtCol.setCellValueFactory(value -> new SimpleStringProperty(ProjectModel.Town.getTown(value.getValue().getFactoryCity()).getDistrict()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> endorseCol = new TableColumn<>("Endorsed");
        endorseCol.setPrefWidth(100.0);
        endorseCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            String dateString = new SimpleDateFormat("MM-dd-yy").format(project.getEndorsedDate());
            return new SimpleStringProperty(dateString);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> approvedCol = new TableColumn<>("Approved");
        approvedCol.setPrefWidth(100.0);
        approvedCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            String dateString = new SimpleDateFormat("MM-dd-yy").format(project.getApprovedDate());
            return new SimpleStringProperty(dateString);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> actualCostCol = new TableColumn<>("Actual Cost");
        actualCostCol.setPrefWidth(150.0);
        actualCostCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            String cost = "";
            if (project.getActualCost() != null) {
                cost = project.getActualCost().toString();
            }
            return new SimpleStringProperty(cost);
        });
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> moaCol = new TableColumn<>("MOA Signed");
        moaCol.setPrefWidth(100.0);
        moaCol.setCellValueFactory((value) -> {
            ProjectModel project = (ProjectModel) value.getValue();
            String dateString = new SimpleDateFormat("MM-dd-yy").format(project.getMoaDate());
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
                // if contains in the name
//                if (project.getName().toLowerCase().contains(filterString)) {
//                    return true;
//                }

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
     * Populate table with contents.
     */
    private void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ProjectModel> inquiries = new ArrayList<>();
        try {
            inquiries = ProjectModel.getProjectTableData();
        } catch (SQLException ex) {
            PolarisDialog.exceptionDialog(ex);
        }
        this.tableData.addAll(inquiries);
    }

}
