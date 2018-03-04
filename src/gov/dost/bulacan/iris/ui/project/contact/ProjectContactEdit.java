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
package gov.dost.bulacan.iris.ui.project.contact;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Messageable;
import gov.dost.bulacan.iris.models.ProjectContactModel;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.afterschoolcreatives.polaris.java.util.StringTools;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectContactEdit extends PolarisFxController implements Messageable {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_position;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_landline;

    @FXML
    private TextField txt_email;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    /**
     * Constructor to pass model.
     *
     * @param model
     */
    public ProjectContactEdit(ProjectContactModel model) {
        this.contactModel = model;
        this.willAddNew = (model == null);
    }

    private final ProjectContactModel contactModel;
    private final boolean willAddNew;

    @Override
    protected void setup() {

    }

    //--------------------------------------------------------------------------
    // Message Boxes for this window.
    //--------------------------------------------------------------------------
    private final static String MESSAGE_TITLE = "Contact Person";

    @Override
    public void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle(MESSAGE_TITLE)
                .setHeaderText("Warning")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    @Override
    public void showInformationMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.INFORMATION)
                .setTitle(MESSAGE_TITLE)
                .setHeaderText("Information")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    @Override
    public void showErrorMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.ERROR)
                .setTitle(MESSAGE_TITLE)
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
                .setTitle(MESSAGE_TITLE)
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

    //--------------------------------------------------------------------------
    private String frmName;
    private String frmPosition;
    private String frmMobile;
    private String frmTel;
    private String frmMail;

    /**
     * Get data to form.
     */
    private void getFormDetails() {
        this.frmName = this.filterInput(this.txt_name);
        this.frmPosition = this.filterInput(this.txt_position);
        this.frmMobile = this.filterInput(this.txt_mobile);
        this.frmTel = this.filterInput(this.txt_landline);
        this.frmMail = this.filterInput(this.txt_email);
    }

    /**
     * Filter input from text field.
     *
     * @param textField
     * @return
     */
    private String filterInput(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }

}
