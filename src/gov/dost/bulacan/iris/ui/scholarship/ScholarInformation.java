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
import gov.dost.bulacan.iris.models.ScholarSubmissionModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jhon Melvin
 */
public class ScholarInformation extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private Label lbl_scholar_id;

    @FXML
    private TextField txt_student_number;

    @FXML
    private TextField txt_last_name;

    @FXML
    private TextField txt_ext_name;

    @FXML
    private TextField txt_first_name;

    @FXML
    private TextField txt_middle_name;

    @FXML
    private Label lbl_gender;

    @FXML
    private Label lbl_scholarship;

    @FXML
    private Label lbl_scholar_type;

    @FXML
    private TextField txt_course;

    @FXML
    private Label lbl_year;

    @FXML
    private TextField txt_section;

    @FXML
    private TextField txt_university;

    @FXML
    private TextField txt_mobile_no;

    @FXML
    private TextField txt_tel_no;

    @FXML
    private TextField txt_email;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableView<ScholarSubmissionModel> tbl_documents;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_city;

    @FXML
    private TextField txt_province;

    public ScholarInformation(ScholarInformationModel scholarModel) {
        this.setDialogMessageTitle("Scholarship Documents");
        this.tableData = FXCollections.observableArrayList();
        this.scholarModel = scholarModel;
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ScholarSubmissionModel> tableData;

    private final ScholarInformationModel scholarModel;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);

        this.btn_back.setOnMouseClicked(value -> {
            this.changeRoot(new ScholarshipHome().load());
            value.consume();
        });

        this.btn_add.setOnMouseClicked(value -> {
            this.showDocumentForm(null);
            this.populateTable();
            value.consume();
        });

        this.btn_edit.setOnMouseClicked(value -> {
            ScholarSubmissionModel model = this.tbl_documents.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please select a document to edit.");
                return;
            }

            // transmitted already
            if (model.getFkTransmittalId() != null) {
                this.showWarningMessage(null, "Entry already transmitted, modifying not allowed.");
                return;
            }

            this.showDocumentForm(model);
            this.populateTable();
            value.consume();
        });

        this.btn_remove.setOnMouseClicked(value -> {
            ScholarSubmissionModel model = this.tbl_documents.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please select a document to edit.");
                return;
            }

            // transmitted already
            if (model.getFkTransmittalId() != null) {
                this.showWarningMessage(null, "Entry already transmitted, removing not allowed.");
                return;
            }

            if (this.showConfirmationMessage(null, "Are you sure you want to remove this documents ?") == 1) {
                try {
                    boolean deleted = ScholarSubmissionModel.remove(model);
                    if (deleted) {
                        this.showInformationMessage(null, "Documents successfully removed.");
                        // refresh table
                        this.populateTable();
                    } else {
                        this.showInformationMessage(null, "Documents cannot be removed at the moment, please try again later.");
                    }
                } catch (SQLException ex) {
                    //
                    this.showExceptionMessage(ex, null, "Failed to remove documents.");
                }
            }

            value.consume();
        });

        /**
         * View Information.
         */
        this.btn_view.setOnMouseClicked(value -> {
            ScholarSubmissionModel model = this.tbl_documents.getSelectionModel().getSelectedItem();
            if (model == null) {
                this.showWarningMessage(null, "Please select a document to view.");
                return;
            }

            this.showTransmitInfo(model);

        });

        this.preloadData();
        //
        this.createTable();
        this.populateTable();
    }

    private void preloadData() {
        this.lbl_scholar_id.setText(this.scholarModel.getScholarId());
        this.txt_student_number.setText(this.scholarModel.getStudentNumber());
        this.txt_last_name.setText(this.scholarModel.getLastName());
        this.txt_ext_name.setText(this.scholarModel.getExtName());
        this.txt_first_name.setText(this.scholarModel.getFirstName());
        this.txt_middle_name.setText(this.scholarModel.getMiddleName());
        this.lbl_gender.setText(this.scholarModel.getGender());
        //
        Integer intScholar = this.scholarModel.getScholarType();
        ScholarInformationModel.ScholarType objScholar = ScholarInformationModel.ScholarType.toObject(intScholar);
        this.lbl_scholarship.setText(objScholar.getName());
        //
        Integer intScholarType = this.scholarModel.getMeritType();
        ScholarInformationModel.ScholarType.Merit objMerit = ScholarInformationModel.ScholarType.Merit.toObject(intScholarType);
        this.lbl_scholar_type.setText(objMerit.getName());
        //
        this.txt_course.setText(this.scholarModel.getCourse());
        this.lbl_year.setText(ScholarInformationModel.YearLevel.toWord(this.scholarModel.getYear()));
        this.txt_section.setText(this.scholarModel.getSection());
        this.txt_university.setText(this.scholarModel.getUniversity());
        this.txt_mobile_no.setText(this.scholarModel.getMobileNo());
        this.txt_tel_no.setText(this.scholarModel.getTelNo());
        this.txt_email.setText(this.scholarModel.getMail());
        //
        this.txt_address.setText(this.scholarModel.getStudentAddress());
        this.txt_city.setText(this.scholarModel.getStudentCityMunicipality());
        this.txt_province.setText(this.scholarModel.getStudentProvince());

        //
        this.txt_student_number.setEditable(false);
        this.txt_last_name.setEditable(false);
        this.txt_ext_name.setEditable(false);
        this.txt_first_name.setEditable(false);
        this.txt_middle_name.setEditable(false);
        txt_course.setEditable(false);
        this.txt_section.setEditable(false);
        this.txt_university.setEditable(false);
        this.txt_mobile_no.setEditable(false);
        this.txt_tel_no.setEditable(false);
        this.txt_email.setEditable(false);

        this.txt_address.setEditable(false);
        this.txt_city.setEditable(false);
        this.txt_province.setEditable(false);
    }

    private void showDocumentForm(ScholarSubmissionModel submitModel) {
        Stage contactStage = new Stage();
        contactStage.setMinHeight(280.0);
        contactStage.setMinWidth(450.0);
        contactStage.setResizable(false);
        contactStage.setTitle("Submit Documents");
        ScholarSubmit submit = new ScholarSubmit(submitModel, this.scholarModel);
        contactStage.setScene(new Scene(submit.load()));
        contactStage.initOwner(this.getStage());
        contactStage.initModality(Modality.WINDOW_MODAL);
        contactStage.getIcons().setAll(this.getStage().getIcons());
        contactStage.showAndWait();
    }

    private void showTransmitInfo(ScholarSubmissionModel submitModel) {
        Stage contactStage = new Stage();
        contactStage.setMinHeight(280.0);
        contactStage.setMinWidth(450.0);
        contactStage.setResizable(false);
        contactStage.setTitle("Transmittal Info");
        TransmitInfo fx = new TransmitInfo(submitModel);
        contactStage.setScene(new Scene(fx.load()));
        contactStage.initOwner(this.getStage());
        contactStage.initModality(Modality.WINDOW_MODAL);
        contactStage.getIcons().setAll(this.getStage().getIcons());
        contactStage.showAndWait();
    }

    private void createTable() {
        TableColumn<ScholarSubmissionModel, String> docDate = new TableColumn<>("Date");
        docDate.setPrefWidth(140.0);
        docDate.setCellValueFactory(value -> {
            ScholarSubmissionModel model = value.getValue();
            Date updatedDate = model.getUpdatedAt();
            Date createdDate = model.getCreatedAt();
            Date displayDate = (updatedDate == null) ? createdDate : updatedDate;
            String dateString = new SimpleDateFormat("MM-dd-yyyy hh:mm a").format(displayDate);
            return new SimpleStringProperty(dateString);
        });
        //
        TableColumn<ScholarSubmissionModel, String> docSub = new TableColumn<>("Documents");
        docSub.setPrefWidth(230.0);
        docSub.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDocumentsSubmitted()));
        //
        TableColumn<ScholarSubmissionModel, String> docRemark = new TableColumn<>("Remarks");
        docRemark.setPrefWidth(170.0);
        docRemark.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getRemarks()));
        //
        TableColumn<ScholarSubmissionModel, String> docStat = new TableColumn<>("Status");
        docStat.setPrefWidth(100.0);
        docStat.setCellValueFactory(value -> {
            ScholarSubmissionModel model = value.getValue();
            String transID = model.getFkTransmittalId();
            String status = "OK";
            if (transID == null) {
                status = "";
            }
            return new SimpleStringProperty(status);
        });

        this.tbl_documents.getColumns().setAll(docDate, docSub, docRemark, docStat);
        this.tbl_documents.setItems(this.tableData);
    }

    /**
     * Populate table with contents. for refresh also of date.
     */
    public void populateTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ScholarSubmissionModel> inquiries = new ArrayList<>();
        try {
            inquiries = ScholarSubmissionModel.listAllActive(this.scholarModel);
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to load data.");
        }
        this.tableData.addAll(inquiries);
    }

}
