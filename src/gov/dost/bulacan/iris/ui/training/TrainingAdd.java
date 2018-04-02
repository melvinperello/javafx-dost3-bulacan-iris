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
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.models.TrainingModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class TrainingAdd extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private Label lbl_modify_time;

    @FXML
    private JFXButton btn_save;

    @FXML
    private Label lbl_code;

    @FXML
    private TextField txt_title;

    @FXML
    private TextField txt_speaker;

    @FXML
    private TextField txt_venue;

    @FXML
    private DatePicker dp_start;

    @FXML
    private DatePicker dp_end;

    public TrainingAdd(TrainingModel model) {
        this.dataModel = model;
        this.addingMode = model == null;
    }

    private final TrainingModel dataModel;
    private final boolean addingMode;

    @Override
    protected void setup() {
        ProjectHeader.attach(hbox_header);

        if (this.addingMode) {
            this.lbl_modify_time.setVisible(false);
            this.lbl_code.setText(Context.createLocalKey());
            this.lbl_modify_header.setText("Add Training");
        } else {
            this.lbl_modify_header.setText("Edit Training");
            this.lbl_code.setText(this.dataModel.getTrainingCode());
            this.lbl_modify_time.setVisible(true);
            this.lbl_modify_time.setText(this.dataModel.auditToString());
        }

        this.btn_back.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingHome().load());
            value.consume();
        });

        this.btn_save.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.insert()) {
                    this.changeRoot(new TrainingHome().load());
                }
            } else {
                if (this.update()) {
                    this.changeRoot(new TrainingHome().load());
                }
            }
            value.consume();
        });

    }

    //--------------------------------------------------------------------------
    // Form Values
    //--------------------------------------------------------------------------
    private String frmTitle;
    private String frmSpeaker;
    private String frmVenue;
    private Date frmDateStart;
    private Date frmDateEnd;

    private void submit() {
        this.frmTitle = Context.filterInputControl(txt_title);
        this.frmSpeaker = Context.filterInputControl(txt_speaker);
        this.frmVenue = Context.filterInputControl(txt_venue);

        if (this.dp_start.getValue() != null) {
            this.frmDateStart = java.sql.Date.valueOf(this.dp_start.getValue());
        }

        if (this.dp_end.getValue() != null) {
            this.frmDateEnd = java.sql.Date.valueOf(this.dp_end.getValue());
        }
    }

    private boolean insert() {
        this.submit();

        if (this.frmTitle.isEmpty()) {
            this.showWarningMessage(null, "Please enter the equipment name.");
            return false;
        }

        TrainingModel model = new TrainingModel();
        model.setTrainingCode(lbl_code.getText());
        model.setTrainingTitle(frmTitle);
        model.setResourceSpeakers(frmSpeaker);
        model.setVenue(frmVenue);
        model.setDateStart(frmDateStart);
        model.setDateEnd(frmDateEnd);

        boolean inserted = false;
        try {
            inserted = TrainingModel.insert(model);
            if (inserted) {
                this.showInformationMessage(null, "Successfully added to the database.");
            } else {
                this.showWarningMessage(null, "Cannot be added to the database at the moment. Please try again later.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to insert to the database");
        }
        return inserted;
    }

    private boolean update() {
        this.submit();

        if (this.frmTitle.isEmpty()) {
            this.showWarningMessage(null, "Please enter the equipment name.");
            return false;
        }

        TrainingModel model = this.dataModel;
        //model.setTrainingCode(lbl_code.getText());
        model.setTrainingTitle(frmTitle);
        model.setResourceSpeakers(frmSpeaker);
        model.setVenue(frmVenue);
        model.setDateStart(frmDateStart);
        model.setDateEnd(frmDateEnd);

        boolean updated = false;
        try {
            updated = TrainingModel.update(model);
            if (updated) {
                this.showInformationMessage(null, "Successfully updated the database.");
            } else {
                this.showWarningMessage(null, "Cannot be updated at the moment. Please try again later.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to update the database");
        }
        return updated;
    }

}
