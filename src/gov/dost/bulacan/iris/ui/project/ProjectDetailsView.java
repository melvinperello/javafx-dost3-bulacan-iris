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

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.Messageable;
import gov.dost.bulacan.iris.models.ProjectContactModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.ui.project.contact.ProjectContactEdit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.util.StringTools;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectDetailsView extends PolarisFxController implements Messageable {

    @FXML
    private Label lbl_cooperator_header;

    @FXML
    private Label lbl_header_last_edit;

    @FXML
    private JFXButton btn_print;

    @FXML
    private JFXButton btn_edit_project;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_cooperator;

    @FXML
    private Label lbl_factory_address;

    @FXML
    private Label lbl_owner_name;

    @FXML
    private Label lbl_owner_position;

    @FXML
    private Label lbl_owner_address;

    @FXML
    private Label lbl_business_sector;

    @FXML
    private Label lbl_year_established;

    @FXML
    private Label lbl_capital_class;

    @FXML
    private Label lbl_employment_class;

    @FXML
    private Label lbl_ownership;

    @FXML
    private Label lbl_products;

    @FXML
    private Label lbl_market;

    @FXML
    private Label lbl_registration;

    @FXML
    private Label lbl_landmark;

//    @FXML
//    private Label lbl_coordinates;
//
//    @FXML
//    private Label lbl_click_maps;

    @FXML
    private Label lbl_click_website;

    @FXML
    private TableView<ProjectContactModel> tbl_contact_person;

    @FXML
    private Label lbl_project_code;

    @FXML
    private Label lbl_spin_no;

    @FXML
    private Label lbl_project_type;

    @FXML
    private Label lbl_project_name;

    @FXML
    private Label lbl_district;

    @FXML
    private Label lbl_date_endorsed;

    @FXML
    private Label lbl_click_endorse;

    @FXML
    private Label lbl_date_approve;

    @FXML
    private Label lbl_click_approved;

    @FXML
    private Label lbl_approved_cost;

    @FXML
    private Label lbl_date_moa;

    @FXML
    private Label lbl_click_moa;

    @FXML
    private Label lbl_project_duration;

    @FXML
    private Label lbl_actual_cost;

    @FXML
    private JFXButton btn_add_contact;

    @FXML
    private JFXButton btn_edit_contact;

    @FXML
    private JFXButton btn_delete_contact;

    /**
     * Recommended Constructor for viewing the details.
     *
     * @param model
     */
    public ProjectDetailsView(ProjectModel model) {
        this.projectModel = model;
        this.tableData = FXCollections.observableArrayList();
    }

    /**
     * Contains the data of the table.
     */
    private final ObservableList<ProjectContactModel> tableData;

    /**
     * contains the model to display.
     */
    private final ProjectModel projectModel;

    /**
     * checks for data disparity. upon loading the data.
     *
     * @param projectModel
     * @param stage
     * @return
     */
    public static boolean loadMyData(ProjectModel projectModel, Stage stage) {
        try {
            if (!ProjectModel.getProjectViaProjectCode(projectModel, projectModel.getProjectCode())) {
                // not loaded
                throw new SQLException();
            }
        } catch (SQLException e) {
            // error
            PolarisDialog.create(PolarisDialog.Type.WARNING)
                    .setTitle("SETUp/GIA Project")
                    .setHeaderText("Warning")
                    .setContentText("There was a problem loading the data")
                    .setOwner(stage)
                    .showAndWait();
            return false;
        }
        return true;
    }

    @Override
    protected void setup() {

        //----------------------------------------------------------------------
        this.preloadData();
        this.createContactListTable();
        this.populateContactTable();
        //----------------------------------------------------------------------
        // Buttons.
        //----------------------------------------------------------------------
        this.btn_back.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectView().load());
            value.consume();
        });

        this.btn_edit_project.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectDetailsEdit(this, this.projectModel).load());
            value.consume();
        });

        this.btn_print.setOnMouseClicked(value -> {
            this.printProjectInfoReport();
            value.consume();
        });
        //--------------------------
        // Contact
        //--------------------------
        // Add Contact
        this.btn_add_contact.setOnMouseClicked(value -> {
            this.showEditContacts(null);
            this.populateContactTable();
            value.consume();
        });
        // Edit Contact
        this.btn_edit_contact.setOnMouseClicked(value -> {
            ProjectContactModel contact = this.tbl_contact_person.getSelectionModel().getSelectedItem();
            if (contact == null) {
                this.showWarningMessage("Please highlight a contact to edit.");
                return;
            }
            this.showEditContacts(contact);
            this.populateContactTable();
            value.consume();
        });
        /**
         * Delete Contact.
         */
        this.btn_delete_contact.setOnMouseClicked(value -> {
            ProjectContactModel contact = this.tbl_contact_person.getSelectionModel().getSelectedItem();
            if (contact == null) {
                this.showWarningMessage("Please highlight a contact to delete.");
                return;
            }

            if (this.showConfirmation("Are you sure you want to delete this contact information.") == 1) {
                try {
                    boolean deleted = ProjectContactModel.deleteContact(contact);
                    if (deleted) {
                        this.showInformationMessage("Contact successfully deleted to this project.");
                        // refresh table
                        this.populateContactTable();
                    } else {
                        this.showInformationMessage("Contact cannot be deleted at the moment please try again later.");
                    }
                } catch (SQLException ex) {
                    //
                    PolarisDialog.exceptionDialog(ex)
                            .setContentText("Failed to delete contact.")
                            .show();
                }
            }

            value.consume();
        });
        //----------------------------------------------------------------------
        // Clickables.
        //----------------------------------------------------------------------
//        this.lbl_click_maps.setOnMouseClicked(value -> {
//            if (this.lbl_coordinates.getText().trim().isEmpty()) {
//                this.showWarningMessage("No map data is available.");
//                return;
//            }
//            this.showInformationMessage("Google Maps is not yet supported");
//
//            value.consume();
//        });
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

    private void showEditContacts(ProjectContactModel model) {
        Stage contactStage = new Stage();
        contactStage.initOwner(this.getStage());
        contactStage.setMinHeight(280.0);
        contactStage.setMinWidth(450.0);
        contactStage.setResizable(false);
        contactStage.setTitle("Project Contacts");
        ProjectContactEdit contact = new ProjectContactEdit(model, this.projectModel);
        contactStage.setScene(new Scene(contact.load()));
        contactStage.showAndWait();
    }

    private void printProjectInfoReport() {
        PrintDetails printable = new PrintDetails();
        //
        String city = this.projectModel.getFactoryCity();
        ProjectModel.Town town = ProjectModel.Town.getTown(city);
        /**
         * Factory Address.
         */
        String factoryAddress = this.projectModel.getFactoryStreet()
                + " "
                + this.projectModel.getFactoryBrgy()
                + " "
                + town.getName()
                + " "
                + town.getZip();
        //
        printable.setCooperator(this.projectModel.getCompanyName());
        printable.setLocation(factoryAddress);
        //
        printable.setName(this.projectModel.getCompanyOwner());
        printable.setPosition(this.projectModel.getOwnerPosition());
        printable.setAddress(this.projectModel.getOwnerAddress());
        //
        String businessActivity = ProjectModel.BusinessActivity.getStringValue(this.projectModel.getBusinessActivity());
        printable.setSector(businessActivity);
        printable.setYearEstablished(this.projectModel.getYearEstablished());
        String classified = "Capital - " + this.projectModel.getCapitalClassification()
                + " / "
                + "Employment - " + this.projectModel.getEmploymentClassification();
        printable.setClassification(classified);
        printable.setOwnership(this.projectModel.getCompanyOwnership());
        printable.setProducts(this.projectModel.getMajorProducts());
        printable.setMarket(this.projectModel.getExistingMarket());
        printable.setRegistrationDetails(this.projectModel.getRegistrationInformation());
        printable.setLandmark(this.projectModel.getFactoryLandMark());
        printable.setWebsite(this.projectModel.getWebsite());
        //
        printable.setContactInformation("");
        try {
            StringBuilder temp_contact = new StringBuilder("");
            List<ProjectContactModel> contacts = ProjectContactModel.getAllContacts(this.projectModel.getProjectCode());
            for (ProjectContactModel contact : contacts) {
                String temp = contact.getName() + " ( " + contact.getPosition()
                        + " ) Mobile:"
                        + contact.getMobile()
                        + " / Tel: "
                        + contact.getLandline()
                        + " / Mail: "
                        + contact.getEmail() + "\n";
                temp_contact.append(temp);
            }
            printable.setContactInformation(temp_contact.toString());
        } catch (SQLException e) {
            printable.setContactInformation("No Contact Information");
        }

        //
        printable.setProjectCode(this.projectModel.getProjectCode());
        printable.setSpinNo(this.projectModel.getSpinNo());
        String type = this.projectModel.getProjectType() + " - " + this.projectModel.getProjectStatus();
        printable.setProjectType(type);
        printable.setDistrict(Context.app().intToRoman(town.getDistrict()));
        //
        if (this.projectModel.getEndorsedDate() != null) {
            printable.setDateEndorsed(Context.app().getDateFormatNamed().format(this.projectModel.getEndorsedDate()));
        }
        if (this.projectModel.getApprovedDate() != null) {
            printable.setDateEndorsed(Context.app().getDateFormatNamed().format(this.projectModel.getApprovedDate()));
        }

        printable.setApprovedCost("P " + Context.app().getMoneyFormat().format(this.projectModel.getApprovedFunding()));

        if (this.projectModel.getMoaDate() != null) {
            printable.setDateEndorsed(Context.app().getDateFormatNamed().format(this.projectModel.getMoaDate()));
        }
        String durFrom = "No Data";
        String durTo = "No Data";
        if (this.projectModel.getDurationFrom() == null) {
            durFrom = Context.app().getDateFormatNamed().format(this.projectModel.getDurationFrom());
        }

        if (this.projectModel.getDurationTo() == null) {
            durTo = Context.app().getDateFormatNamed().format(this.projectModel.getDurationTo());
        }

        String durDate = durFrom + " - " + durTo;
        printable.setDuration(durDate);

        printable.setPrintInfo("Date Printed: "
                + Context.app().getDateFormat12().format(new Date())
                + " / PSTC-Bulacan");

        printable.setActualCost("Actual Cost:  P " + Context.app().getMoneyFormat().format(this.projectModel.getActualCost()));

        try {
            printable.printDetails();
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load Data for viewing.
     */
    public void preloadData() {
        String city = this.projectModel.getFactoryCity();
        ProjectModel.Town town = ProjectModel.Town.getTown(city);

        this.lbl_cooperator_header.setText(this.projectModel.getCompanyName());
        this.lbl_cooperator.setText(this.projectModel.getCompanyName());
        /**
         * Factory Address.
         */
        String factoryAddress = this.projectModel.getFactoryStreet()
                + " "
                + this.projectModel.getFactoryBrgy()
                + " "
                + town.getName()
                + " "
                + town.getZip();

        factoryAddress = StringTools.clearExtraSpaces(factoryAddress);
        this.lbl_factory_address.setText(factoryAddress);
        //----------------------------------------------------------------------
        //
        this.lbl_owner_name.setText(this.projectModel.getCompanyOwner());
        this.lbl_owner_position.setText(this.projectModel.getOwnerPosition());
        this.lbl_owner_address.setText(this.projectModel.getOwnerAddress());
        //
        String businessActivity = ProjectModel.BusinessActivity.getStringValue(this.projectModel.getBusinessActivity());
        this.lbl_business_sector.setText(businessActivity);
        this.lbl_year_established.setText(this.projectModel.getYearEstablished());
        this.lbl_capital_class.setText(this.projectModel.getCapitalClassification());
        this.lbl_employment_class.setText(this.projectModel.getEmploymentClassification());
        //
        /**
         * Ownership display.
         */
        String ownership = this.projectModel.getCompanyOwnership();
        if (true/*ownership.equalsIgnoreCase(ProjectModel.Ownership.CORPORATION)*/) {
            ownership += (" / " + this.projectModel.getProfitability());
        }
        this.lbl_ownership.setText(ownership);
        //----------------------------------------------------------------------

        this.lbl_products.setText(this.projectModel.getMajorProducts());
        this.lbl_market.setText(this.projectModel.getExistingMarket());
        //
        this.lbl_registration.setText(this.projectModel.getRegistrationInformation());
        this.lbl_landmark.setText(this.projectModel.getFactoryLandMark());
        /**
         * Coordinates.
         */
//        String coordinates = this.projectModel.getFactoryLat() + " " + this.projectModel.getFactoryLong();
//        this.lbl_coordinates.setText(coordinates);
        //
        this.lbl_click_website.setText(this.projectModel.getWebsite());
        //

        //
        this.lbl_project_code.setText(this.projectModel.getProjectCode());
        this.lbl_spin_no.setText(this.projectModel.getSpinNo());
        /**
         * Project Type.
         */
        String projectType = this.projectModel.getProjectType();
        String porjectStatus = ProjectModel.ProjectStatus.getStringValue(this.projectModel.getProjectStatus());
        projectType += (" - " + porjectStatus);
        //----------------------------------------------------------------------
        this.lbl_project_type.setText(projectType);
        this.lbl_project_name.setText(this.projectModel.getProjectName());
        this.lbl_district.setText(town.getDistrict());
        //
        /**
         * Endorsed Date.
         */
        Date endorsedDate = this.projectModel.getEndorsedDate();
        if (endorsedDate == null) {
            this.lbl_date_endorsed.setText("");
        } else {
            this.lbl_date_endorsed.setText(Context.app().getDateFormat().format(endorsedDate));
        }
        //----------------------------------------------------------------------
        /**
         * Approved Date.
         */
        Date approvedDate = this.projectModel.getApprovedDate();
        if (approvedDate == null) {
            this.lbl_date_approve.setText("");
        } else {
            this.lbl_date_approve.setText(Context.app().getDateFormat().format(approvedDate));
        }
        //----------------------------------------------------------------------
        /**
         * approved funding.
         */
        String approvedCost = "P ";
        approvedCost += (Context.app().getMoneyFormat().format(this.projectModel.getApprovedFunding()));
        this.lbl_approved_cost.setText(approvedCost);
        /**
         * Moa Date.
         */
        Date moaDate = this.projectModel.getMoaDate();
        if (moaDate == null) {
            this.lbl_date_moa.setText("");
        } else {
            this.lbl_date_moa.setText(Context.app().getDateFormat().format(moaDate));
        }
        //----------------------------------------------------------------------
        /**
         * Duration.
         */
        String from = "N.D.";
        if (this.projectModel.getDurationFrom() != null) {
            from = Context.app().getDateFormat().format(this.projectModel.getDurationFrom());
        }
        String to = "N.D.";
        if (this.projectModel.getDurationTo() != null) {
            to = Context.app().getDateFormat().format(this.projectModel.getDurationTo());
        }
        String duration = from + " - " + to;
        this.lbl_project_duration.setText(duration);
        /**
         * actual funding.
         */
        String actualCost = "P ";
        actualCost += (Context.app().getMoneyFormat().format(this.projectModel.getActualCost()));
        this.lbl_actual_cost.setText(actualCost);

    }

    /**
     *
     */
    private void createContactListTable() {
        TableColumn<ProjectContactModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(100.0);
        nameCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));
        //
        TableColumn<ProjectContactModel, String> posCol = new TableColumn<>("Position");
        posCol.setPrefWidth(100.0);
        posCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getPosition()));
        //
        TableColumn<ProjectContactModel, String> mobileCol = new TableColumn<>("Mobile");
        mobileCol.setPrefWidth(100.0);
        mobileCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMobile()));
        //
        TableColumn<ProjectContactModel, String> landlineCol = new TableColumn<>("Tel");
        landlineCol.setPrefWidth(100.0);
        landlineCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getLandline()));
        //
        TableColumn<ProjectContactModel, String> emailCol = new TableColumn<>("E-Mail");
        emailCol.setPrefWidth(100.0);
        emailCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));

        /**
         * Add columns to the table.
         */
        this.tbl_contact_person.getColumns().setAll(nameCol, posCol, mobileCol, landlineCol, emailCol);
        /**
         * Load Data.
         */
        this.tbl_contact_person.setItems(this.tableData);
    }

    /**
     * Populate table with contents. for refresh also of date.
     */
    public void populateContactTable() {
        this.tableData.clear();
        //----------------------------------------------------------------------
        List<ProjectContactModel> inquiries = new ArrayList<>();
        try {
            inquiries = ProjectContactModel.getAllContacts(this.projectModel.getProjectCode());
        } catch (SQLException ex) {
            PolarisDialog.exceptionDialog(ex)
                    .setContentText("Failed to load contact list.")
                    .show();
        }
        this.tableData.setAll(inquiries);
    }

    /**
     * Folder that contains any setup printable materials.
     */
    public final static String DIR_SETUP_PRINTS = Context.DIR_TEMP
            + File.separator
            + "setup_prints";

    /**
     * Static Inner Class For Printing.
     */
    public static class PrintDetails {

        private String cooperator;
        private String location;
        //
        private String name;
        private String position;
        private String address;
        //
        private String sector;
        private String yearEstablished;
        private String classification;
        private String ownership;
        private String products; // TEXT AREA
        private String market; // TEXT AREA
        private String registrationDetails; // TEXT AREA
        private String landmark;
        private String website;
        private String contactInformation; // TEXT AREA
        //
        private String projectCode;
        private String spinNo;
        private String projectType;
        private String district; // Roman Numerals
        private String dateEndorsed; // WORD DATE
        private String dateApproved; // WORD DATE
        private String approvedCost;
        private String moaSigned; // WORD DATE
        private String duration; // Word Date
        private String actualCost; // BOLD
        private String printInfo;

        private PrintDetails() {
            cooperator = "";
            location = "";
            //
            name = "";
            position = "";
            address = "";
            //
            sector = "";
            yearEstablished = "";
            classification = "";
            ownership = "";
            products = ""; // TEXT AREA
            market = ""; // TEXT AREA
            registrationDetails = "";// TEXT AREA
            landmark = "";
            website = "";
            contactInformation = ""; // TEXT AREA
            //
            projectCode = "";
            spinNo = "";
            projectType = "";
            district = ""; // Roman Numerals
            dateEndorsed = ""; // WORD DATE
            dateApproved = ""; // WORD DATE
            approvedCost = "";
            moaSigned = ""; // WORD DATE
            duration = ""; // Word Date
            actualCost = ""; // BOLD
            printInfo = "";
        }

        public void setCooperator(String cooperator) {
            this.cooperator = cooperator;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public void setYearEstablished(String yearEstablished) {
            this.yearEstablished = yearEstablished;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public void setOwnership(String ownership) {
            this.ownership = ownership;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public void setRegistrationDetails(String registrationDetails) {
            this.registrationDetails = registrationDetails;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public void setContactInformation(String contactInformation) {
            this.contactInformation = contactInformation;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public void setSpinNo(String spinNo) {
            this.spinNo = spinNo;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setDateEndorsed(String dateEndorsed) {
            this.dateEndorsed = dateEndorsed;
        }

        public void setDateApproved(String dateApproved) {
            this.dateApproved = dateApproved;
        }

        public void setApprovedCost(String approvedCost) {
            this.approvedCost = approvedCost;
        }

        public void setMoaSigned(String moaSigned) {
            this.moaSigned = moaSigned;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setActualCost(String actualCost) {
            this.actualCost = actualCost;
        }

        public void setPrintInfo(String printInfo) {
            this.printInfo = printInfo;
        }

        /**
         * Prints the given details into a PDF Document.
         *
         * @return
         * @throws IOException
         * @throws DocumentException
         */
        public boolean printDetails() throws IOException, DocumentException {
            final String fillSpace = "     ";
            //
            String cooperatorInfo = "";
            cooperatorInfo += ("Cooperator:" + fillSpace + this.cooperator);
            cooperatorInfo += ("\n");
            cooperatorInfo += ("Location:  " + fillSpace + this.location);
            //
            String ownerInfo = "";
            ownerInfo += ("Name:    " + fillSpace + this.name);
            ownerInfo += ("\n");
            ownerInfo += ("Position:" + fillSpace + this.position);
            ownerInfo += ("\n");
            ownerInfo += ("Address: " + fillSpace + this.address);
            //
            String businessInfo = "";
            businessInfo += ("Sector:          " + fillSpace + this.sector);
            businessInfo += ("\n");
            businessInfo += ("Year Established:" + fillSpace + this.yearEstablished);
            businessInfo += ("\n");
            businessInfo += ("Classification:  " + fillSpace + this.classification);
            businessInfo += ("\n");
            businessInfo += ("Ownership:       " + fillSpace + this.ownership);
            businessInfo += ("\n");
            businessInfo += ("Products:        " + fillSpace + this.products);
            businessInfo += ("\n");
            businessInfo += ("\n");
            businessInfo += ("Market:          " + fillSpace + this.market);
            businessInfo += ("\n");
            businessInfo += ("Registration Details:\n" + this.registrationDetails);
            businessInfo += ("\n");
            businessInfo += ("Landmark:        " + fillSpace + this.landmark);
            businessInfo += ("\n");
            businessInfo += ("Website:         " + fillSpace + this.website);
            businessInfo += ("\n");
            businessInfo += ("Contact Information:\n" + this.contactInformation);
            //
            String projectInfo = "";
            projectInfo += ("Project Code:     " + fillSpace + this.projectCode);
            projectInfo += ("\n");
            projectInfo += ("SPIN No:          " + fillSpace + this.spinNo);
            projectInfo += ("\n");
            projectInfo += ("Type:             " + fillSpace + this.projectType);
            projectInfo += ("\n");
            projectInfo += ("District:         " + fillSpace + this.district);
            projectInfo += ("\n");
            projectInfo += ("Date Endorsed:    " + fillSpace + this.dateEndorsed);
            projectInfo += ("\n");
            projectInfo += ("Date Approved:    " + fillSpace + this.dateApproved);
            projectInfo += ("\n");
            projectInfo += ("Approved Cost:    " + fillSpace + this.approvedCost);
            projectInfo += ("\n");
            projectInfo += ("MOA Signed:       " + fillSpace + this.moaSigned);
            projectInfo += ("\n");
            projectInfo += ("Duration:         " + fillSpace + this.duration);

            /**
             * Attempt to check the template directory.
             */
            if (!FileTool.checkFoldersQuietly(Context.DIR_TEMPLATE)) {
                throw new FileNotFoundException("Template directory cannot be created.");
            }
            /**
             * Attempt to check the certificates directory.
             */
            if (!FileTool.checkFoldersQuietly(ProjectDetailsView.DIR_SETUP_PRINTS)) {
                throw new FileNotFoundException("Temp directory cannot be created.");
            }
            /**
             * Template File Path.
             */
            String templatePath = Context.DIR_TEMPLATE + File.separator + "setup_print_blank.pdf";
            File templateFile = new File(templatePath);

            /**
             * Check if the template file is not existing.
             */
            if (!templateFile.exists()) {
                throw new FileNotFoundException("Certificate Template not existing.");
            }
            /**
             * Initialize Stream.
             */
            PdfStamper stamper = null;
            PdfReader reader = null;
            try {
                /**
                 * Read the template.
                 */
                reader = new PdfReader(templateFile.getAbsolutePath());

                String infoNamePdf = "temp_info_"
                        + Context.app().getDateFormatTimeStamp().format(
                                Context.app().getLocalDate()
                        )
                        + ".pdf";
                /**
                 * Output file.
                 */
                File stampedCertificatePdf = new File(
                        ProjectDetailsView.DIR_SETUP_PRINTS
                        + File.separator
                        + infoNamePdf);

                stamper = new PdfStamper(reader, new FileOutputStream(stampedCertificatePdf));
                AcroFields form = stamper.getAcroFields();
                form.setField("txt_cooperator", cooperatorInfo);
                form.setField("txt_owner", ownerInfo);
                form.setField("txt_business_info", businessInfo);
                form.setField("txt_project_info", projectInfo);
                form.setField("txt_total_cost", this.actualCost);
                form.setField("txt_print_info", this.printInfo);
                /**
                 * Closing Methods.
                 */
                stamper.setFormFlattening(true);
                stamper.close();
                reader.close();
                // Attempt to open the file.
                Context.app().desktopOpenQuietly(stampedCertificatePdf);
            } finally {
                try {
                    if (stamper != null) {
                        stamper.close();
                    }
                } catch (DocumentException | IOException e) {
                    // ignore close
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    // ignore close
                }
            }

            /**
             * Return true if not exceptions were encountered. and to know that
             * the file was created and stamped.
             */
            return true;
        }

    } // end of print details class
}
