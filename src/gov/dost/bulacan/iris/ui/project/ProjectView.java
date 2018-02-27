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
import gov.dost.bulacan.iris.models.ProjectModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectView extends PolarisFxController {

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_new_project;

    @FXML
    private TableView<ProjectModel> tbl_project_table;

    public ProjectView() {
        this.tableData = null;
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ProjectModel> tableData;

    @Override
    protected void setup() {
        /**
         * New Project Action.
         */
        this.btn_new_project.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectDetailsView().load());
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
        typeCol.setPrefWidth(130.0);
        typeCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getProjectType()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(200.0);
        statusCol.setCellValueFactory(value -> new SimpleStringProperty(ProjectModel.ProjectStatus.getStringValue(value.getValue().getProjectStatus())));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> coopCol = new TableColumn<>("Cooperator");
        coopCol.setPrefWidth(200.0);
        coopCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCompanyName()));
        //----------------------------------------------------------------------
        TableColumn<ProjectModel, String> districtCol = new TableColumn<>("District");
        districtCol.setPrefWidth(220.0);
        districtCol.setCellValueFactory(value -> new SimpleStringProperty(ProjectModel.Town.getTown(value.getValue().getFactoryCity()).getDistrict()));

        TableColumn<ProjectModel, String> concernCol = new TableColumn<>("Concern");
        concernCol.setPrefWidth(150.0);
        concernCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getConcern()));

        this.tbl_project_table.getColumns().addAll(numberCol, dateCol, nameCol, repCol, addressCol, concernCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<InquiryModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((InquiryModel company) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();
                // if contains in the name
                if (company.getName().toLowerCase().contains(filterString)) {
                    return true;
                }

                // if contains in the name
                if (company.getCreatedDate().toLowerCase().contains(filterString)) {
                    return true;
                }

                // same id
                if (company.getId().toString().equalsIgnoreCase(filterString)) {
                    return true;
                }

                if (company.getRepresentative().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getAddress().toLowerCase().contains(filterString)) {
                    return true;
                }

                if (company.getConcern().toLowerCase().contains(filterString)) {
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
     * Populate table with contents.
     */
    private void populateTable() {
        this.tableData.clear(); // clear
        ArrayList<ProjectModel> inquiries = ProjectModel.getAllRecords();
        this.tableData.addAll(inquiries);
    }

}
