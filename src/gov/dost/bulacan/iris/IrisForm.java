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
package gov.dost.bulacan.iris;

import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisForm;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 * Polaris FX Controller Extension for this project. added messaging feature.
 *
 * @author Jhon Melvin
 */
public abstract class IrisForm extends PolarisForm {

    /**
     * Show warning message with header text.
     *
     * @param header the tile of the message.
     * @param message the message.
     */
    public void showWarningMessage(String header, String message) {
        if (header == null) {
            header = "Warning";
        }
        this.messageWarning(header, message).show();
    }

    public void showWaitWarningMessage(String header, String message) {
        if (header == null) {
            header = "Warning";
        }
        this.messageWarning(header, message).showAndWait();
    }

    public void showInformationMessage(String header, String message) {
        if (header == null) {
            header = "Information";
        }
        this.messageInfo(header, message).show();
    }

    /**
     * Show and wait information message.
     *
     * @param header
     * @param message
     */
    public void showWaitInformationMessage(String header, String message) {
        if (header == null) {
            header = "Information";
        }
        this.messageInfo(header, message).showAndWait();
    }

    public void showErrorMessage(String header, String message) {
        if (header == null) {
            header = "Error";
        }
        this.messageError(header, message).show();
    }

    public void showWaitErrorMessage(String header, String message) {
        if (header == null) {
            header = "Error";
        }
        this.messageError(header, message).showAndWait();
    }

    /**
     * Show confirmation message with title.
     *
     * @param header
     * @param message
     * @return
     */
    public int showConfirmationMessage(String header, String message) {
        if (header == null) {
            header = "Please Confirm";
        }
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> res = this.messageConfirm(header, message, yesButton, cancelButton);
        if (res.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            return 1;
        }
        return 0;
    }

    //--------------------------------------------------------------------------
    public void showExceptionMessage(Exception e, String header, String message) {
        if (header == null) {
            header = "System Error Encountered !";
        }
        this.messageException(e, header, message).showAndWait();
        //----------------------------------------------------------------------
        IRIS.telemetry(e, this.getStage());
        //----------------------------------------------------------------------
    }

    //--------------------------------------------------------------------------
}
