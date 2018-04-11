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
import gov.dost.bulacan.iris.models.TrainingDataModel;
import gov.dost.bulacan.iris.models.TrainingModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.json.JSONObject;

/**
 *
 * @author Jhon Melvin
 */
public class TrainingDataHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableView<TrainingDataModel> tbl_training_data;

    @FXML
    private Label lbl_training_name;

    @FXML
    private Label lbl_venue;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_respondents;

    @FXML
    private JFXButton btn_reload;

    @FXML
    private TextArea txt_summary;

    public TrainingDataHome(TrainingModel trainingModel) {
        this.setDialogMessageTitle("Trainings and Seminar");
        this.tableData = FXCollections.observableArrayList();
        this.trainingModel = trainingModel;
    }

    //==========================================================================
    // C-02. Declaration of Class Variables
    //==========================================================================
    private final ObservableList<TrainingDataModel> tableData;

    private final TrainingModel trainingModel;

    @Override
    protected void setup() {
        //======================================================================
        // S-01. Controller Initialization
        //======================================================================
        ProjectHeader.attach(this.hbox_header);
        //
        this.createTable();
        this.populateTable();
        //
        this.preloadData();
        //
        this.btn_back_to_home.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingHome().load());
            value.consume();
        });

        this.btn_edit.setOnMouseClicked(value -> {
            TrainingDataModel selectedModel = this.tbl_training_data.getSelectionModel().getSelectedItem();
            if (selectedModel == null) {
                this.showWarningMessage(null, "Please select an item to edit.");
                return;
            }
            // edit mode
            this.changeRoot(new TrainingEncode(this.trainingModel, selectedModel).load());

            value.consume();
        });

        this.btn_add.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingEncode(this.trainingModel, null).load());
            value.consume();
        });

        this.btn_remove.setOnMouseClicked(value -> {
            TrainingDataModel selectedModel = this.tbl_training_data.getSelectionModel().getSelectedItem();
            if (selectedModel == null) {
                this.showWarningMessage(null, "Please select an item to delete.");
                return;
            }

            int res = this.showConfirmationMessage(null, "Are you sure you want to remove this evaluation data?");
            if (res == 1) {
                try {
                    boolean deleted = TrainingDataModel.remove(selectedModel);
                    if (deleted) {
                        this.showInformationMessage(null, "Successfully removed from the database.");
                        // refresh table
                        this.populateTable();
                    } else {
                        this.showInformationMessage(null, "Cannot be removed at the moment please try again later.");
                    }
                } catch (SQLException e) {
                    //
                    this.showExceptionMessage(e, null, "Failed to remove from the database.");
                }
            }
        });
        //
        //----------------------------------------------------------------------
        this.btn_reload.setOnMouseClicked(value -> {
            this.summarize();
            value.consume();
        });
        //----------------------------------------------------------------------
    }

    private void preloadData() {
        this.lbl_training_name.setText(this.trainingModel.getTrainingTitle());
        this.lbl_venue.setText(this.trainingModel.getVenue());
        String dateFrom = "N/A";
        if (this.trainingModel.getDateStart() != null) {
            dateFrom = Context.getDateFormatNamed().format(this.trainingModel.getDateStart());
        }
        String dateEnd = "N/A";
        if (this.trainingModel.getDateEnd() != null) {
            dateEnd = Context.getDateFormatNamed().format(this.trainingModel.getDateEnd());
        }
        this.lbl_date.setText(dateFrom + " - " + dateEnd);

    }

    private void createTable() {
        //----------------------------------------------------------------------
        TableColumn<TrainingDataModel, String> titleNo = new TableColumn<>("Entry");
        titleNo.setPrefWidth(100.0);
        titleNo.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEntryNo()));
        //----------------------------------------------------------------------
        TableColumn<TrainingDataModel, String> colName = new TableColumn<>("Name");
        colName.setPrefWidth(200.0);
        colName.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));
        //----------------------------------------------------------------------
        TableColumn<TrainingDataModel, String> colComment = new TableColumn<>("Comment");
        colComment.setPrefWidth(200.0);
        colComment.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getComment()));
        //----------------------------------------------------------------------

        this.tbl_training_data.getColumns().setAll(titleNo, colName, colComment);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<TrainingDataModel> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((TrainingDataModel model) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterString = newValue.toLowerCase();

                /**
                 * Allow search of cooperator's name.
                 */
                if (model.getEntryNo().toLowerCase().contains(newValue)) {
                    return true;
                }

                if (model.getName().toLowerCase().contains(newValue)) {
                    return true;
                }

                if (model.getComment().toLowerCase().contains(newValue)) {
                    return true;
                }

                return false; // no match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TrainingDataModel> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_training_data.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_training_data.setItems(sortedData);
    }

    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<TrainingDataModel> list = new ArrayList<>();
        try {
            list = TrainingDataModel.listAllActive(this.trainingModel);
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load projects.");
        }
        this.tableData.addAll(list);

        this.lbl_respondents.setText(String.valueOf(this.tableData.size()));
        this.txt_summary.setEditable(true);
        this.txt_summary.setText("");
        this.txt_summary.setEditable(false);
    }

    private void summarize() {
        StringBuilder textSummary = new StringBuilder("");
        //
        String training = this.trainingModel.getTrainingTitle();
        String speaker = this.trainingModel.getResourceSpeakers();
        String venue = this.trainingModel.getVenue();
        String respondents = this.lbl_respondents.getText();
        String dateFrom = "";
        if (this.trainingModel.getDateStart() != null) {
            dateFrom = Context.getDateFormatNamed().format(this.trainingModel.getDateStart());
        }

        String dateEnd = "";
        if (this.trainingModel.getDateEnd() != null) {
            dateEnd = Context.getDateFormatNamed().format(this.trainingModel.getDateEnd());
        }

        String date = "";
        if (dateEnd.isEmpty()) {
            date = dateFrom;
        } else {
            date = dateFrom + " - " + dateEnd;
        }

        textSummary.append("Training Name:  \n");
        textSummary.append(training);
        textSummary.append("\n\nResource Persons:  \n");
        textSummary.append(speaker);
        textSummary.append("\n\nVenue:  \n");
        textSummary.append(venue);
        textSummary.append("\n\nDate:  \n");
        textSummary.append(date);
        textSummary.append("\n\nNumber of Respondents: ");
        textSummary.append(respondents);
        Calculator compute = new Calculator();
        //
        StringBuilder comments = new StringBuilder("\n\nComments\n");
        for (TrainingDataModel trainingDataModel : tableData) {
            JSONObject json = new JSONObject(trainingDataModel.getRating());

            for (int x = 1; x <= 15; x++) {
                if (x == 6 || x == 11) {
                    continue;
                }
                String key = String.valueOf(x);
                String value = json.getString(key);
                compute.feed(key, value);
            }

            comments.append(trainingDataModel.getComment());
            comments.append("\n");
        }

        textSummary.append(compute.view());
        textSummary.append(comments.toString());

        this.txt_summary.setEditable(true);
        this.txt_summary.setText(textSummary.toString());
        this.txt_summary.setEditable(false);

    }

    /**
     * Static class to compute summary.
     */
    public final static class Calculator {

        private final HashMap<String, ArrayList<String>> contents;

        public Calculator() {
            this.contents = new HashMap<>();
        }

        /**
         * Enter Values.
         *
         * @param key
         * @param value
         */
        public void feed(String key, String value) {
            ArrayList<String> valList = this.contents.getOrDefault(key, null);
            if (valList == null) {
                this.contents.put(key, new ArrayList());
                valList = this.contents.getOrDefault(key, null);
            }

            if (valList == null) {
                return;
            }

            valList.add(value);

        }

        /**
         * View Result.
         *
         * @return
         */
        public String view() {
            StringBuilder result = new StringBuilder("");
            result.append("\n\nTECHNOLOGY/ INFORMATION PRESENTED\n");
            for (int x = 1; x <= 15; x++) {
                if (x == 6) {
                    result.append("\n\nRESOURCE SPEAKER\n");
                    continue;
                }

                if (x == 11) {
                    result.append("\n\nLOGISTIC SUPPORT\n");
                    continue;
                }

                if (x == 15) {
                    result.append("\n\nOVERALL RATING\n");
                }

                ArrayList<String> val = this.contents.get(String.valueOf(x));
                result.append(calculate(val));
                result.append(("\n"));
            }

            return result.toString();

        }

        public String calculate(ArrayList<String> list) {
            int _1 = 0;
            int _2 = 0;
            int _3 = 0;
            int _4 = 0;
            int _5 = 0;
            int na = 0;
            for (String val : list) {
                if (val.equalsIgnoreCase("1")) {
                    _1++;
                } else if (val.equalsIgnoreCase("2")) {
                    _2++;
                } else if (val.equalsIgnoreCase("3")) {
                    _3++;
                } else if (val.equalsIgnoreCase("4")) {
                    _4++;
                } else if (val.equalsIgnoreCase("5")) {
                    _5++;
                } else if (val.isEmpty()) {
                    na++;
                } else {
                    // nothing
                }
            }

            int computableTotal = _1 + _2 + _3 + _4 + _5;
            int totalResponse = list.size();
            int totalSum = (_1 * 1) + (_2 * 2) + (_3 * 3) + (_4 * 4) + (_5 * 5);
            double totalP = ((double) totalSum) / ((double) computableTotal);

            String r1 = _1 + "\t" + percentMe(_1, totalResponse) + "%";
            String r2 = _2 + "\t" + percentMe(_2, totalResponse) + "%";
            String r3 = _3 + "\t" + percentMe(_3, totalResponse) + "%";
            String r4 = _4 + "\t" + percentMe(_4, totalResponse) + "%";
            String r5 = _5 + "\t" + percentMe(_5, totalResponse) + "%";
            String rn = na + "\t" + percentMe(na, totalResponse) + "%";

            String row = r5 + "\t" + r4 + "\t" + r3 + "\t" + r2 + "\t" + r1 + "\t" + rn + "\t" + totalResponse + "\t" + Context.getDecimal2Format().format(totalP);

            return row;

        }

        public int percentMe(int x, int y) {
            double a = (double) x;
            double b = (double) y;
            double q = a / b;

            q = q * 100;

            return (int) Math.round(q);
        }

    }

}
