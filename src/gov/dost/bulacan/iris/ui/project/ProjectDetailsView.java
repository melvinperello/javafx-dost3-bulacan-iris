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
import gov.dost.bulacan.iris.models.ProjectContactModel;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.ui.project.contact.ProjectContactEdit;
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

    @FXML
    private Label lbl_coordinates;

    @FXML
    private Label lbl_click_maps;

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
            this.showInformationMessage("Printing is not yet supported. Please wait for further releases.");
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
        this.lbl_click_maps.setOnMouseClicked(value -> {
            if (this.lbl_coordinates.getText().trim().isEmpty()) {
                this.showWarningMessage("No map data is available.");
                return;
            }
            this.showInformationMessage("Google Maps is not yet supported");

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
        String coordinates = this.projectModel.getFactoryLat() + " " + this.projectModel.getFactoryLong();
        this.lbl_coordinates.setText(coordinates);
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
        actualCost += (Context.app().getMoneyFormat().format(this.projectModel.getApprovedFunding()));
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
     * Static Inner Class For Printing.
     */
    public static class PrintDetails {

    }
}
