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
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ScholarInformationModel;
import gov.dost.bulacan.iris.models.ScholarSubmissionModel;
import gov.dost.bulacan.iris.models.ScholarTransmittalModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

/**
 *
 * @author DOST-3
 */
public class TransmitCandidate extends IrisForm {
    
    @FXML
    private HBox hbox_header;
    
    @FXML
    private JFXButton btn_record;
    
    @FXML
    private JFXButton btn_export;
    @FXML
    private JFXButton btn_back_to_home;
    
    @FXML
    private TextField txt_search;
    
    @FXML
    private Label lbl_entry_count;
    
    @FXML
    private TableView<ReportData> tbl_transmittal;
    
    public TransmitCandidate(List<ScholarSubmissionModel> list, boolean allowTransmit) {
        this.setDialogMessageTitle("Transmittal");
        
        this.list = list;
        this.allowTransmit = allowTransmit;
        this.tableContents = new ArrayList<>();

        /**
         * Convert List to table contents.
         */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        for (int ctr = 0; ctr < list.size(); ctr++) {
            ScholarSubmissionModel submissionModel = list.get(ctr);
            ScholarInformationModel scholarModel = submissionModel.getScholarInformationModel();
            ReportData data = new ReportData();
            //
            data.setNo(String.valueOf(ctr + 1));
            //
            Date created = submissionModel.getCreatedAt();
            Date updated = submissionModel.getUpdatedAt();
            Date displayDate = updated == null ? created : updated;
            String dateString = dateFormat.format(displayDate);
            //
            data.setDateReceived(dateString);
            //
            String fullName = scholarModel.getFullName().toUpperCase(Locale.ENGLISH);
            //
            data.setFullName(fullName);
            //
            data.setMobile(scholarModel.getMobileNo());
            data.setEmail(scholarModel.getMail());
            data.setTel(scholarModel.getTelNo());
            data.setDocsSubmitted(submissionModel.getDocumentsSubmitted().toUpperCase(Locale.ENGLISH));
            data.setRemarks(submissionModel.getRemarks().toUpperCase(Locale.ENGLISH));

            /**
             * Add Entry.
             */
            this.tableContents.add(data);
        }
        
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ReportData> tableData;
    
    private final List<ScholarSubmissionModel> list;
    private final boolean allowTransmit;
    private final List<ReportData> tableContents;
    
    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        //
        this.tbl_transmittal.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        this.createTable();
        this.populateTable();
        //
        if (this.allowTransmit) {
            this.btn_record.setDisable(false);
            this.btn_export.setDisable(true);
        } else {
            this.btn_record.setDisable(true);
            this.btn_export.setDisable(false);
        }

        /**
         * Display number of entries.
         */
        this.lbl_entry_count.setText(String.valueOf(list.size()) + " entries not transmitted");
        
        this.btn_back_to_home.setOnMouseClicked(value -> {
            this.changeRoot(new TransmittalHome().load());
            value.consume();
        });
        
        this.btn_record.setOnMouseClicked((event) -> {
            this.record();
            event.consume();
        });
    }

    /**
     * marks the un transmitted documents as transmitted.
     */
    private void record() {
        //----------------------------------------------------------------------
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(this.getStage());
        dialog.initModality(Modality.WINDOW_MODAL);
        
        dialog.getDialogPane().setPrefWidth(500.0);
        
        dialog.setTitle("Transmit Documents");
        dialog.setHeaderText("Enter the name ");
        dialog.setContentText("New Name");
        
        Optional<String> result = dialog.showAndWait();
        
        if (!result.isPresent()) {
            // no value
            return;
        }
        if (result.get().isEmpty()) {
            this.showWaitWarningMessage(null, "Name is required !");
            return;
        }

        //----------------------------------------------------------------------
        List<ScholarSubmissionModel> listModel = this.list;
        ScholarTransmittalModel model = new ScholarTransmittalModel();
        model.setTransId(Context.createLocalKey());
        model.setTransBy(result.get());
        try {
            model.setTransDate(Context.app().getServerDate());
            boolean success = ScholarTransmittalModel.transmitSubmittedDocuments(model, listModel);
            if (success) {
                //
                this.showWaitInformationMessage(null, "Documents successfully transmitted.");

                //--------------------------------------------------------------
                // BACK TO TRANSMITTALS
                this.changeRoot(new TransmittalHome().load());
                //--------------------------------------------------------------
            } else {
                //
                this.showWaitWarningMessage(null, "Failed to create trasmittal.");
            }
        } catch (SQLException e) {
            this.showExceptionMessage(e, "Transmit Failed", "An error occured while recording transmittals.");
        }
    }

    /**
     * Populate table with contents. for refresh also of date.
     */
    public void populateTable() {
        this.tableData.clear();
        this.tableData.addAll(this.tableContents);
    }
    
    private void createTable() {
        TableColumn<ReportData, String> noCol = new TableColumn<>("No.");
        noCol.setPrefWidth(50.0);
        noCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNo()));
        
        TableColumn<ReportData, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(150.0);
        dateCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDateReceived()));
        
        TableColumn<ReportData, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setPrefWidth(250.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFullName()));
        
        TableColumn<ReportData, String> mobileCol = new TableColumn<>("Mobile");
        mobileCol.setPrefWidth(150.0);
        mobileCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMobile()));
        
        TableColumn<ReportData, String> mailCol = new TableColumn<>("E-Mail");
        mailCol.setPrefWidth(150.0);
        mailCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));
        
        TableColumn<ReportData, String> telCol = new TableColumn<>("Tel.");
        telCol.setPrefWidth(150.0);
        telCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTel()));
        
        TableColumn<ReportData, String> docCol = new TableColumn<>("Documents");
        docCol.setPrefWidth(200.0);
        docCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDocsSubmitted()));
        
        TableColumn<ReportData, String> remarkCol = new TableColumn<>("Remarks");
        remarkCol.setPrefWidth(200.0);
        remarkCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getRemarks()));
        
        this.tbl_transmittal.getColumns().setAll(noCol, dateCol, nameCol, mobileCol, mailCol, telCol, docCol, remarkCol);

        //----------------------------------------------------------------------
        // Add Search Predicate
        //----------------------------------------------------------------------
        // 01. wrap the observeable list inside the filter list.
        FilteredList<ReportData> filteredResult = new FilteredList<>(this.tableData, predicate -> true);

        // 02. bind the filter to a text source and add filters
        this.txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredResult.setPredicate((ReportData model) -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String filterString = newValue.toLowerCase();

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
        SortedList<ReportData> sortedData = new SortedList<>(filteredResult);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tbl_transmittal.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        this.tbl_transmittal.setItems(sortedData);
    }
    
    public final static class ReportData {
        
        private String no;
        private String dateReceived;
        private String fullName;
        private String mobile;
        private String email;
        private String tel;
        private String docsSubmitted;
        private String remarks;
        
        public ReportData() {
            this.no = "";
            this.dateReceived = "";
            this.fullName = "";
            this.mobile = "";
            this.email = "";
            this.tel = "";
            this.docsSubmitted = "";
            this.remarks = "";
        }
        
        public String getNo() {
            return no;
        }
        
        public String getDateReceived() {
            return dateReceived;
        }
        
        public String getFullName() {
            return fullName;
        }
        
        public String getMobile() {
            return mobile;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getTel() {
            return tel;
        }
        
        public String getDocsSubmitted() {
            return docsSubmitted;
        }
        
        public String getRemarks() {
            return remarks;
        }

        //----------------------------------------------------------------------
        public void setNo(String no) {
            this.no = no;
        }
        
        public void setDateReceived(String dateReceived) {
            this.dateReceived = dateReceived;
        }
        
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public void setTel(String tel) {
            this.tel = tel;
        }
        
        public void setDocsSubmitted(String docsSubmitted) {
            this.docsSubmitted = docsSubmitted;
        }
        
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
        
    }
    
}
