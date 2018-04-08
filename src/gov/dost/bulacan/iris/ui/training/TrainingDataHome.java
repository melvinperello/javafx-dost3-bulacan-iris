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
import gov.dost.bulacan.iris.models.TrainingDataModel;
import gov.dost.bulacan.iris.models.TrainingModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class TrainingDataHome extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableView<TrainingDataModel> tbl_training_data;

    @FXML
    private Label lbl_training_name;

    @FXML
    private Label lbl_venue;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_respondents;

    @FXML
    private JFXButton btn_reload;

    @FXML
    private TextArea txt_summary;

    public TrainingDataHome(TrainingModel trainingModel) {
        this.setDialogMessageTitle("Trainings and Seminar");
        this.tableData = FXCollections.observableArrayList();
        this.trainingModel = trainingModel;
    }

    //==========================================================================
    // C-02. Declaration of Class Variables
    //==========================================================================
    private final ObservableList<TrainingDataModel> tableData;

    private final TrainingModel trainingModel;

    @Override
    protected void setup() {
        this.setDialogMessageTitle("Trainings and Seminar");
        //======================================================================
        // S-01. Controller Initialization
        //======================================================================
        ProjectHeader.attach(this.hbox_header);
        //
        this.preloadData();
        this.btn_back_to_home.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingHome().load());
            value.consume();
        });

        this.btn_add.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingEncode(this.trainingModel, null).load());
            value.consume();
        });
    }

    private void preloadData() {
        this.lbl_training_name.setText(this.trainingModel.getTrainingTitle());
        this.lbl_venue.setText(this.trainingModel.getVenue());
        String dateFrom = "N/A";
        if (this.trainingModel.getDateStart() != null) {
            dateFrom = Context.app().getDateFormatNamed().format(this.trainingModel.getDateStart());
        }
        String dateEnd = "N/A";
        if (this.trainingModel.getDateEnd() != null) {
            dateEnd = Context.app().getDateFormatNamed().format(this.trainingModel.getDateEnd());
        }
        this.lbl_date.setText(dateFrom + " - " + dateEnd);

    }

}