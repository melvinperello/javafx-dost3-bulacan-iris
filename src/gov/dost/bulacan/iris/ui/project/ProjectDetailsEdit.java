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

import gov.dost.bulacan.iris.models.ProjectModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author DOST-3
 */
public class ProjectDetailsEdit extends PolarisFxController {

    @FXML
    private TextField txt_cooperator;

    @FXML
    private TextField txt_owner;

    @FXML
    private TextField txt_owner_position;

    @FXML
    private TextArea txt_owner_address;

    @FXML
    private ComboBox<?> cmb_sector;

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

    @Override
    protected void setup() {

    }

    private void initializeComboBoxes() {
        for (int i : ProjectModel.BusinessActivity.ACTIVITY_LIST) {
            String stringValue = ProjectModel.BusinessActivity.getStringValue(i);

        }
    }

}
