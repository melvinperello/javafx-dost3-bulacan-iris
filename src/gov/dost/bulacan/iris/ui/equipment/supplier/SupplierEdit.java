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
package gov.dost.bulacan.iris.ui.equipment.supplier;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.PolarisForm;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class SupplierEdit extends PolarisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private JFXButton btn_save_qoutation;

    @FXML
    private Label lbl_code;

    @FXML
    private TextField txt_name;

    @FXML
    private ComboBox<?> cmb_sector;

    @FXML
    private Label lbl_sector_selected;

    @FXML
    private RadioButton rdb_yes;

    @FXML
    private ToggleGroup rdo_group_accredited;

    @FXML
    private RadioButton rdb_no;

    @FXML
    private Label lbl_accredited_selected;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_fax;

    @FXML
    private TextField txt_email;

    @FXML
    private TextArea txt_address;

    @FXML
    private TextField txt_website;

    @Override
    protected void setup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
