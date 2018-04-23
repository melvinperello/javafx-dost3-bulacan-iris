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
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ScholarInformationModel;
import gov.dost.bulacan.iris.models.ScholarSubmissionModel;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 *
 * @author DOST-3
 */
public class ScholarSubmit extends IrisForm {

    @FXML
    private TextArea txt_documents;

    @FXML
    private TextArea txt_remarks;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    public ScholarSubmit(ScholarSubmissionModel submitModel, ScholarInformationModel scholarModel) {
        this.submitModel = submitModel;
        this.scholarModel = scholarModel;
        this.addingMode = (submitModel == null);
    }

    private final ScholarSubmissionModel submitModel;
    private final ScholarInformationModel scholarModel;
    private final boolean addingMode;

    @Override
    protected void setup() {

        if (this.addingMode) {
            // adding mode
        } else {
            // edit mode
            this.preloadData();
        }

        this.btn_save.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.insert()) {
                    this.getStage().close();
                }
            } else {
                if (this.update()) {
                    this.getStage().close();
                }
            }
            value.consume();
        });

        /**
         * Cancel Button.
         */
        this.btn_cancel.setOnMouseClicked(value -> {
            this.getStage().close();
            value.consume();
        });

    }

    //--------------------------------------------------------------------------
    private String frmDocuments;
    private String frmRemarks;

    private void submit() {
        this.frmDocuments = Context.filterInputControl(txt_documents);
        this.frmRemarks = Context.filterInputControl(txt_remarks);
    }

    private void preloadData() {
        this.txt_documents.setText(this.submitModel.getDocumentsSubmitted());
        this.txt_remarks.setText(this.submitModel.getRemarks());
    }

    private boolean insert() {
        this.submit();
        // Filter
        if (this.frmDocuments.isEmpty()) {
            this.showWarningMessage(null, "Please enter documents to submit.");
            return false;
        }
        //
        ScholarSubmissionModel model = new ScholarSubmissionModel();
        model.setSubmissionId(Context.createLocalKey());
        model.setDocumentsSubmitted(frmDocuments);
        model.setRemarks(frmRemarks);
        //
        boolean res = false;
        try {
            res = ScholarSubmissionModel.insert(model, scholarModel);
            if (res) {
                this.showWaitInformationMessage(null, "Documents added successfully.");
                /**
                 * Close stage for success.
                 */
                this.getStage().close();
            } else {
                this.showInformationMessage(null, "Cannot add documents.");
            }
        } catch (SQLException e) {
            this.showExceptionMessage(e, null, "Failed to add documents.");
        }
        return res;
    }

    private boolean update() {
        this.submit();
        // Filter
        if (this.frmDocuments.isEmpty()) {
            this.showWarningMessage(null, "Please enter documents to submit.");
            return false;
        }
        //
        ScholarSubmissionModel model = this.submitModel;
//        model.setSubmissionId(Context.createLocalKey());
        model.setDocumentsSubmitted(frmDocuments);
        model.setRemarks(frmRemarks);
        //
        boolean res = false;
        try {
            res = ScholarSubmissionModel.update(model);
            if (res) {
                this.showWaitInformationMessage(null, "Documents updated successfully.");
                /**
                 * Close stage for success.
                 */
                this.getStage().close();
            } else {
                this.showInformationMessage(null, "Cannot update documents.");
            }
        } catch (SQLException e) {
            this.showExceptionMessage(e, null, "Failed to update documents.");
        }
        return res;
    }

}
