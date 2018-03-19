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
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.afterschoolcreatives.polaris.java.util.StringTools;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author DOST-3
 */
public class ProjectDetailsEdit extends PolarisForm {
    
    @FXML
    private HBox hbox_header;
    
    @FXML
    private Label lbl_modify_header;
    
    @FXML
    private Label lbl_modify_time;
    
    @FXML
    private JFXButton btn_save_project;
    
    @FXML
    private JFXButton btn_cancel_edit;
    
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
    private ComboBox cmb_class_capital;
    
    @FXML
    private ComboBox cmb_class_employment;
    
    @FXML
    private ComboBox cmb_ownership;
    
    @FXML
    private ComboBox cmb_profitability;
    
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
    private ComboBox cmb_city;
    
    @FXML
    private TextArea txt_landmark;
    
    @FXML
    private TextField txt_website;
    
    @FXML
    private TableView<?> tbl_contact_person;
    
    @FXML
    private Label lbl_project_code;
    
    @FXML
    private TextField txt_spin_no;
    
    @FXML
    private ComboBox cmb_project_type;
    
    @FXML
    private ComboBox cmb_project_status;
    
    @FXML
    private TextArea txt_project_name;
    
    @FXML
    private DatePicker date_endorsed;
    
    @FXML
    private DatePicker date_approved;
    
    @FXML
    private TextField txt_approved_cost;
    
    @FXML
    private DatePicker date_duration_from;
    
    @FXML
    private DatePicker date_duration_to;
    
    @FXML
    private DatePicker date_moa;
    
    @FXML
    private TextField txt_actual_cost;

    /**
     * Recommended Constructor.
     *
     * @param controller
     * @param model
     */
    public ProjectDetailsEdit(PolarisFxController controller, ProjectModel model) {
        this.setDialogMessageTitle("Project Edit");
        this.parentController = controller;
        this.receiveModel = model;
        /**
         * Select an operation for this window EDIT and NEW.
         */
        this.willAddNew = this.receiveModel == null;
    }
    
    private final PolarisFxController parentController;
    private final ProjectModel receiveModel;
    private final boolean willAddNew;
    
    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        /**
         * Initialization of the combo boxes.
         */
        this.initializeComboBoxes();

        /**
         * If there is no model this is a create project.
         */
        if (this.willAddNew) {
            this.lbl_project_code.setText(this.generatedNewProjectKey());
            this.lbl_modify_header.setText("Create New Project");
            this.lbl_modify_time.setVisible(false);
        } else {
            this.preloadData();
        }

        /**
         * Cancel Modification or Creation.
         */
        this.btn_cancel_edit.setOnMouseClicked(value -> {
            if (this.willAddNew) {
                this.changeRoot(this.parentController.getRootPane());
                ProjectView projectViewer = (ProjectView) (this.parentController);
                projectViewer.populateTable();
            } else {
                if (ProjectDetailsView.loadMyData(this.receiveModel, this.getStage())) {
                    this.changeRoot(new ProjectDetailsView(this.receiveModel).load());
                }
            }
            value.consume();
        });
        /**
         * Save or Create New Project.
         */
        this.btn_save_project.setOnMouseClicked(value -> {
            if (this.willAddNew) {
                if (this.insertNewProject()) {
                    this.changeRoot(this.parentController.getRootPane());
                    ProjectView projectViewer = (ProjectView) (this.parentController);
                    projectViewer.populateTable();
                }
            } else {
                if (this.updateExistingProject()) {
                    if (ProjectDetailsView.loadMyData(this.receiveModel, this.getStage())) {
                        this.changeRoot(new ProjectDetailsView(this.receiveModel).load());
                    }
                    
                }
            }
            value.consume();
        });
    }

    /**
     * Create a newly created ID Key for a project.
     *
     * @return
     */
    private String generatedNewProjectKey() {
        return Context.app().generateTimestampKey();
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
        Context.comboBoxValueFactory(this.cmb_city, ProjectModel.TownValueModel.TOWN_LIST);
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
//    private String frmMapsLat;
//    private String frmMapsLong;
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
        ProjectModel.TownValueModel town = (ProjectModel.TownValueModel) this.cmb_city.getValue();
        this.frmCityZip = town.getZip();
        this.frmLandMark = filterInput(txt_landmark);
//        this.frmMapsLat = filterInput(txt_latitude);
//        this.frmMapsLong = filterInput(txt_longitude);
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

    /**
     * ADD NEW ENTRY TO THE DATABASE. Insert new project to the database.
     *
     * @return
     */
    private boolean insertNewProject() {
        /**
         * Get Project Values.
         */
        this.getProjectValues();

        // Create new Project
        ProjectModel project = new ProjectModel();
        project.setProjectCode(this.lbl_project_code.getText());
        //
        project.setSpinNo(frmSpinNo);
        project.setCompanyName(frmCooperator);
        project.setProjectName(frmProjectName);
        project.setProjectStatus(frmProjectStatus);
        project.setProjectType(frmProjectType);
        //
        project.setCompanyOwnership(frmOwnership);
        project.setCompanyOwner(frmOwner);
        project.setOwnerPosition(frmPosition);
        project.setOwnerAddress(frmOwnerAddress);
        project.setEndorsedDate(frmDateEndorsed);
        project.setEndorsedAttachment(null); // upon creation then add
        project.setApprovedDate(frmDateApproved);

        //----------------------------------------------------------------------
        // Filter Approved Cost
        Double approved_fund = null;
        try {
            if (this.frmApprovedCost.isEmpty()) {
                this.frmApprovedCost = "0.00";
            }
            approved_fund = Double.valueOf(this.frmApprovedCost);
            if (approved_fund < 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage(null, "You have entered an invalid approved cost.");
            return false;
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
            if (this.frmActualCost.isEmpty()) {
                this.frmActualCost = "0.00";
            }
            actual_cost = Double.valueOf(this.frmActualCost);
            if (actual_cost < 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage(null, "You have entered an invalid actual cost.");
            return false;
        }
        project.setActualCost(approved_fund);
        //----------------------------------------------------------------------
        project.setFactoryStreet(frmStreetAddress);
        project.setFactoryBrgy(frmBrgy);
        project.setFactoryCity(frmCityZip);
//        project.setFactoryLong(frmMapsLong);
//        project.setFactoryLat(frmMapsLat);
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
        boolean projectAdded = false;
        try {
            projectAdded = ProjectModel.insertNewProject(project);
            if (projectAdded) {
                this.showInformationMessage(null, "Project was successfully added to the database.");
            } else {
                this.showWarningMessage(null, "The project cannot be inserted at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to insert new project.");
        }
        return projectAdded;
    }
    
    private String filterInput(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }

    /**
     * FOR EDITTING, PRELOADING THE EXISTING DATA BEFORE EDITTING.
     */
    private void preloadData() {
        /**
         * Project Code.
         */
        this.lbl_project_code.setText(this.receiveModel.getProjectCode());
        //
        this.txt_cooperator.setText(this.receiveModel.getCompanyName());
        this.txt_owner.setText(this.receiveModel.getCompanyOwner());
        this.txt_owner_position.setText(this.receiveModel.getOwnerPosition());
        this.txt_owner_address.setText(this.receiveModel.getOwnerAddress());

        /**
         * Pre-load the selected combo box. SHIT! just been fooled by auto
         * boxing. been here for a long time.
         */
        Integer businessAcitivity = this.receiveModel.getBusinessActivity();
        for (Object item : this.cmb_sector.getItems()) {
            ProjectModel.BusinessActivity activity = (ProjectModel.BusinessActivity) item;
            if (Integer.valueOf(activity.getValue()).equals(businessAcitivity)) {
                this.cmb_sector.getSelectionModel().select(item);
                break;
            }
        }
        
        this.txt_year_established.setText(this.receiveModel.getYearEstablished());
        //----------------------------------------------------------------------
        // capital classification combo
        String selectedCapitalClass = this.receiveModel.getCapitalClassification();
        for (Object item : this.cmb_class_capital.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(selectedCapitalClass)) {
                this.cmb_class_capital.getSelectionModel().select(item);
                break;
            }
        }
        // employment
        String selectedEmploymentClass = this.receiveModel.getEmploymentClassification();
        for (Object item : this.cmb_class_employment.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(selectedEmploymentClass)) {
                this.cmb_class_employment.getSelectionModel().select(item);
                break;
            }
        }
        // ownership
        String ownership = this.receiveModel.getCompanyOwnership();
        for (Object item : this.cmb_ownership.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(ownership)) {
                this.cmb_ownership.getSelectionModel().select(item);
                break;
            }
        }
        // profitability
        String profitability = this.receiveModel.getProfitability();
        for (Object item : this.cmb_profitability.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(profitability)) {
                this.cmb_profitability.getSelectionModel().select(item);
                break;
            }
        }
        //----------------------------------------------------------------------
        this.txt_registration.setText(this.receiveModel.getRegistrationInformation());
        this.txt_products.setText(this.receiveModel.getMajorProducts());
        this.txt_market.setText(this.receiveModel.getExistingMarket());
        this.txt_street_address.setText(this.receiveModel.getFactoryStreet());
        this.txt_brgy.setText(this.receiveModel.getFactoryBrgy());
        //----------------------------------------------------------------------
        String cityZip = this.receiveModel.getFactoryCity();
        for (Object item : this.cmb_city.getItems()) {
            ProjectModel.TownValueModel activity = (ProjectModel.TownValueModel) item;
            if (activity.getZip().equalsIgnoreCase(cityZip)) {
                this.cmb_city.getSelectionModel().select(item);
                break;
            }
        }
        //----------------------------------------------------------------------
        this.txt_landmark.setText(this.receiveModel.getFactoryLandMark());
//        this.txt_latitude.setText(this.receiveModel.getFactoryLat());
//        this.txt_longitude.setText(this.receiveModel.getFactoryLong());
        this.txt_website.setText(this.receiveModel.getWebsite());
        //
        this.txt_spin_no.setText(this.receiveModel.getSpinNo());

        //----------------------------------------------------------------------
        String projectType = this.receiveModel.getProjectType();
        for (Object item : this.cmb_project_type.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(projectType)) {
                this.cmb_project_type.getSelectionModel().select(item);
                break;
            }
        }
        //
        Integer projectStatus = this.receiveModel.getProjectStatus();
        for (Object item : this.cmb_project_status.getItems()) {
            ProjectModel.ProjectStatus status = (ProjectModel.ProjectStatus) item;
            if (projectStatus.intValue() == status.getValue()) {
                this.cmb_project_status.getSelectionModel().select(item);
                break;
            }
        }
        //----------------------------------------------------------------------
        this.txt_project_name.setText(this.receiveModel.getProjectName());
        //----------------------------------------------------------------------
        /**
         * The following fields are double but null safe so there is no need for
         * proper formatting.
         */
        this.txt_approved_cost.setText(this.receiveModel.getApprovedFunding().toString());
        this.txt_actual_cost.setText(this.receiveModel.getActualCost().toString());
        //-------------
        // Dates
        //-------------
        this.setDateToPicker(this.date_endorsed, this.receiveModel.getEndorsedDate());
        this.setDateToPicker(this.date_approved, this.receiveModel.getApprovedDate());
        this.setDateToPicker(this.date_duration_from, this.receiveModel.getDurationFrom());
        this.setDateToPicker(this.date_duration_to, this.receiveModel.getDurationTo());
        this.setDateToPicker(this.date_moa, this.receiveModel.getMoaDate());
    }

    /**
     * SET VALUE FOR A DATEPICKER USING DATE.
     *
     * @param picker
     * @param dateEndorsed
     */
    private void setDateToPicker(DatePicker picker, Date dateEndorsed) {
        if (dateEndorsed != null) {
            SimpleDateFormat format = Context.app().getDateFormat();
            LocalDate setDate = LocalDate.parse(format.format(dateEndorsed), DateTimeFormatter.ofPattern(format.toPattern()));
            picker.setValue(setDate);
        }
    }
    
    private boolean updateExistingProject() {
        this.getProjectValues();

        //
        // Create new Project
        ProjectModel project = this.receiveModel;
        //project.setProjectCode(this.receiveModel.getProjectCode());
        //
        project.setSpinNo(frmSpinNo);
        project.setCompanyName(frmCooperator);
        project.setProjectName(frmProjectName);
        project.setProjectStatus(frmProjectStatus);
        project.setProjectType(frmProjectType);
        //
        project.setCompanyOwnership(frmOwnership);
        project.setCompanyOwner(frmOwner);
        project.setOwnerPosition(frmPosition);
        project.setOwnerAddress(frmOwnerAddress);
        project.setEndorsedDate(frmDateEndorsed);
        project.setEndorsedAttachment(null); // upon creation then add
        project.setApprovedDate(frmDateApproved);

        //----------------------------------------------------------------------
        // Filter Approved Cost
        Double approved_fund = null;
        try {
            if (this.frmActualCost.isEmpty()) {
                this.frmActualCost = "0.00";
            }
            approved_fund = Double.valueOf(this.frmApprovedCost);
            if (approved_fund < 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage(null, "You have entered an invalid approved cost.");
            return false;
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
            if (this.frmActualCost.isEmpty()) {
                this.frmActualCost = "0.00";
            }
            actual_cost = Double.valueOf(this.frmActualCost);
            if (actual_cost < 0.0d) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage(null, "You have entered an invalid actual cost.");
            return false;
        }
        project.setActualCost(actual_cost);
        //----------------------------------------------------------------------
        project.setFactoryStreet(frmStreetAddress);
        project.setFactoryBrgy(frmBrgy);
        project.setFactoryCity(frmCityZip);
//        project.setFactoryLong(frmMapsLong);
//        project.setFactoryLat(frmMapsLat);
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
        
        boolean projectUpdated = false;
        //----------------------------------------------------------------------
        try {
            projectUpdated = ProjectModel.updateExistingProject(project);
            if (projectUpdated) {
                this.showInformationMessage(null, "Project was successfully updated to the database.");
            } else {
                this.showWarningMessage(null, "The project cannot be updated at the moment please try again.");
            }
        } catch (SQLException ex) {
            this.showWaitExceptionMessage(ex, null, "Failed to update current project.");
        }
        //----------------------------------------------------------------------
        return projectUpdated;
    }
    
}
