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
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.util.StringTools;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author DOST-3
 */
public class ProjectDetailsEdit extends PolarisFxController implements Messageable {

    @FXML
    private TextField txt_cooperator;

    @FXML
    private TextField txt_owner;

    @FXML
    private TextField txt_owner_position;

    @FXML
    private TextArea txt_owner_address;

    @FXML
    private ComboBox cmb_sector;

    @FXML
    private TextField txt_year_established;

    @FXML
    private ComboBox<?> cmb_class_capital;

    @FXML
    private ComboBox<?> cmb_class_employment;

    @FXML
    private ComboBox<?> cmb_ownership;

    @FXML
    private ComboBox<?> cmb_profitability;

    @FXML
    private TextArea txt_registration;

    @FXML
    private TextArea txt_products;

    @FXML
    private TextArea txt_market;

    @FXML
    private TextArea txt_street_address;

    @FXML
    private TextField txt_brgy;

    @FXML
    private ComboBox<?> cmb_city;

    @FXML
    private TextArea txt_landmark;

    @FXML
    private HBox lbl_coordinates;

    @FXML
    private TextField txt_latitude;

    @FXML
    private TextField txt_longitude;

    @FXML
    private TextField txt_website;

    @FXML
    private TableView<?> tbl_contact_person;

    @FXML
    private Label lbl_project_code;

    @FXML
    private TextField txt_spin_no;

    @FXML
    private ComboBox<?> cmb_project_type;

    @FXML
    private ComboBox<?> cmb_project_status;

    @FXML
    private TextArea txt_project_name;

    @FXML
    private DatePicker date_endorsed;

    @FXML
    private Label lbl_click_endorsed;

    @FXML
    private DatePicker date_approved;

    @FXML
    private Label lbl_click_approved;

    @FXML
    private TextField txt_approved_cost;

    @FXML
    private Label lbl_project_duration;

    @FXML
    private DatePicker date_moa;

    @FXML
    private Label lbl_click_moa_attachment;

    @FXML
    private TextField txt_actual_cost;

    @FXML
    private JFXButton btn_save_project;

    @FXML
    private JFXButton btn_cancel_edit;

    @FXML
    private DatePicker date_duration_from;

    @FXML
    private DatePicker date_duration_to;

    public ProjectDetailsEdit(Pane pane, ProjectModel model) {

    }

    @Override
    protected void setup() {
        /**
         * Generate Key.
         */
        Calendar dateKey = Calendar.getInstance();
        String generatedKey = Context.PROJECT_CODE_PREFIX
                + String.valueOf(dateKey.get(Calendar.YEAR))
                + "-"
                + new SimpleDateFormat("MMddHHmmss").format(dateKey.getTime());

        this.lbl_project_code.setText(generatedKey);

        /**
         * Initialization of the combo boxes.
         */
        this.initializeComboBoxes();

        /**
         * Cancel Modification or Creation.
         */
        this.btn_cancel_edit.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectView().load());
            value.consume();
        });
        /**
         * Save or Create New Project.
         */
        this.btn_save_project.setOnMouseClicked(value -> {
            this.insertNewProject();
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
     * Initialize all the contents of the combo box in this window.
     */
    private void initializeComboBoxes() {
        //----------------------------------------------------------------------
        // Business Sector.
        //----------------------------------------------------------------------
        Context.comboBoxValueFactory(this.cmb_sector, ProjectModel.BusinessActivity.ACTIVITY_LIST);
        this.cmb_sector.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        // Classification.
        //----------------------------------------------------------------------
        Context.comboBoxValueFactory(this.cmb_class_capital, ProjectModel.CapitalClassification.VALUE_LIST);
        this.cmb_class_capital.getSelectionModel().selectFirst();
        Context.comboBoxValueFactory(this.cmb_class_employment, ProjectModel.EmploymentClassification.VALUE_LIST);
        this.cmb_class_employment.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        // Ownership.
        //----------------------------------------------------------------------
        Context.comboBoxValueFactory(this.cmb_ownership, ProjectModel.Ownership.VALUE_LIST);
        this.cmb_ownership.getSelectionModel().selectFirst();
        Context.comboBoxValueFactory(this.cmb_profitability, ProjectModel.Profitability.VALUE_LIST);
        this.cmb_profitability.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        // City.
        //----------------------------------------------------------------------
        Context.comboBoxValueFactory(this.cmb_city, ProjectModel.Town.TOWN_LIST);
        this.cmb_city.getSelectionModel().selectFirst();
        //----------------------------------------------------------------------
        // Type.
        //----------------------------------------------------------------------
        Context.comboBoxValueFactory(this.cmb_project_type, ProjectModel.ProjectType.TYPE_LIST);
        this.cmb_project_type.getSelectionModel().selectFirst();
        Context.comboBoxValueFactory(this.cmb_project_status, ProjectModel.ProjectStatus.STATUS_LIST);
        this.cmb_project_status.getSelectionModel().selectFirst();
    }

    //--------------------------------------------------------------------------
    // Get Form Values.
    //--------------------------------------------------------------------------
    private String frmCooperator;
    private String frmOwner;
    private String frmPosition;
    private String frmOwnerAddress;
    private Integer frmBusinessSector;
    private String frmYearEstablished;
    private String frmCapitalClass;
    private String frmEmploymentClass;
    private String frmOwnership;
    private String frmProfitability;
    private String frmRegistrationDetails;
    private String frmProducts;
    private String frmMarket;
    private String frmStreetAddress;
    private String frmBrgy;
    private String frmCityZip;
    private String frmLandMark;
    private String frmMapsLat;
    private String frmMapsLong;
    private String frmWebsite;
    private String frmSpinNo;
    private String frmProjectType;
    private Integer frmProjectStatus;
    private String frmProjectName;
    private Date frmDateEndorsed;
    private Date frmDateApproved;
    private String frmApprovedCost;
    private Date frmDurationFrom;
    private Date frmDurationTo;
    private Date frmMoaSigned;
    private String frmActualCost;

    /**
     * Get Values in the Java FX Form.
     */
    private void getProjectValues() {

        this.frmDateApproved = null;
        this.frmDateEndorsed = null;
        this.frmDurationFrom = null;
        this.frmDurationTo = null;
        this.frmMoaSigned = null;

        this.frmCooperator = filterInput(txt_cooperator);
        this.frmOwner = filterInput(txt_owner);
        this.frmPosition = filterInput(txt_owner_position);
        this.frmOwnerAddress = filterInput(txt_owner_address);
        // save int value for business activity
        ProjectModel.BusinessActivity selectedActivity = (ProjectModel.BusinessActivity) this.cmb_sector.getValue();
        this.frmBusinessSector = selectedActivity.getValue();
        this.frmYearEstablished = filterInput(txt_year_established);
        this.frmCapitalClass = (String) this.cmb_class_capital.getValue();
        this.frmEmploymentClass = (String) this.cmb_class_employment.getValue();
        this.frmOwnership = (String) this.cmb_ownership.getValue();
        this.frmProfitability = this.cmb_profitability.getValue().toString();
        this.frmRegistrationDetails = filterInput(txt_registration);
        this.frmProducts = filterInput(txt_products);
        this.frmMarket = filterInput(txt_market);
        this.frmStreetAddress = filterInput(txt_street_address);
        this.frmBrgy = filterInput(txt_brgy);
        // save zip code for town
        ProjectModel.Town town = (ProjectModel.Town) this.cmb_city.getValue();
        this.frmCityZip = town.getZip();
        this.frmLandMark = filterInput(txt_landmark);
        this.frmMapsLat = filterInput(txt_latitude);
        this.frmMapsLong = filterInput(txt_longitude);
        this.frmWebsite = filterInput(txt_website);
        this.frmSpinNo = filterInput(txt_spin_no);
        this.frmProjectType = this.cmb_project_type.getValue().toString();
        ProjectModel.ProjectStatus status = (ProjectModel.ProjectStatus) this.cmb_project_status.getValue();
        this.frmProjectStatus = status.getValue();
        this.frmProjectName = filterInput(txt_project_name);

        //----------------------------------------------------------------------
        // Date Value
        if (this.date_endorsed.getValue() != null) {
            this.frmDateEndorsed = java.sql.Date.valueOf(this.date_endorsed.getValue());
        }

        if (this.date_approved.getValue() != null) {
            this.frmDateApproved = java.sql.Date.valueOf(this.date_approved.getValue());
        }

        //----------------------------------------------------------------------
        this.frmApprovedCost = filterInput(txt_approved_cost);

        //----------------------------------------------------------------------
        // Date Value
        if (this.date_duration_from.getValue() != null) {
            this.frmDurationFrom = java.sql.Date.valueOf(this.date_duration_from.getValue());
        }

        if (this.date_duration_to.getValue() != null) {
            this.frmDurationTo = java.sql.Date.valueOf(this.date_duration_to.getValue());
        }
        //----------------------------------------------------------------------
        if (this.date_moa.getValue() != null) {
            this.frmMoaSigned = java.sql.Date.valueOf(this.date_moa.getValue());
        }

        this.frmActualCost = filterInput(txt_actual_cost);
    }

    private void insertNewProject() {
        /**
         * Get Project Values.
         */
        this.getProjectValues();

        // Create new Project
        ProjectModel project = new ProjectModel();
        project.setProjectCode(new Date().toString());
        //
        project.setSpinNo(frmSpinNo);
        project.setCompanyName(frmCooperator);
        project.setProjectName(frmProjectName);
        project.setProjectStatus(frmProjectStatus);
        project.setProjectType(frmProjectType);
        //
        project.setCompanyOwnership(frmOwnership);
        project.setOwnerPosition(frmPosition);
        project.setOwnerAddress(frmOwnerAddress);
        project.setEndorsedDate(frmDateEndorsed);
        project.setEndorsedAttachment(null); // upon creation then add
        project.setApprovedDate(frmDateApproved);

        //----------------------------------------------------------------------
        // Filter Approved Cost
        Double approved_fund = null;
        try {
            approved_fund = Double.valueOf(this.frmApprovedCost);
            if (approved_fund <= 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage("You have entered an invalid approved cost.");
            return;
        }
        project.setApprovedFunding(approved_fund);
        //----------------------------------------------------------------------
        project.setApprovedAttachment(null);
        //
        project.setMoaDate(frmMoaSigned);
        project.setMoaAttachment(null);
        project.setDurationFrom(frmDurationFrom);
        project.setDurationTo(frmDurationTo);

        //----------------------------------------------------------------------
        // Filter Actual Cost
        Double actual_cost = null;
        try {
            actual_cost = Double.valueOf(this.frmActualCost);
            if (actual_cost <= 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage("You have entered an invalid actual cost.");
            return;
        }
        project.setActualCost(approved_fund);
        //----------------------------------------------------------------------
        project.setFactoryStreet(frmStreetAddress);
        project.setFactoryBrgy(frmBrgy);
        project.setFactoryCity(frmCityZip);
        project.setFactoryLong(frmMapsLong);
        project.setFactoryLat(frmMapsLat);
        project.setFactoryLandMark(frmLandMark);
        //
        project.setYearEstablished(frmYearEstablished);
        project.setBusinessActivity(frmBusinessSector);
        project.setCapitalClassification(frmCapitalClass);
        project.setEmploymentClassification(frmEmploymentClass);
        project.setCompanyOwnership(frmOwnership);
        project.setProfitability(frmProfitability);
        project.setRegistrationInformation(frmRegistrationDetails);
        project.setMajorProducts(frmProducts);
        project.setExistingMarket(frmMarket);
        //
        project.setWebsite(frmWebsite);
        try {
            boolean projectAdded = ProjectModel.insertNewProject(project);
            if (projectAdded) {
                this.showInformationMessage("Project was successfully added to the database.");
            } else {
                this.showWarningMessage("The project cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            PolarisDialog.exceptionDialog(ex);
        }
    }

    private String filterInput(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }

}
