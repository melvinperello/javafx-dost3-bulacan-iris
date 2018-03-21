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
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import gov.dost.bulacan.iris.ui.equipment.EquipmentEditView;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jhon Melvin
 */
public class SupplierHome extends PolarisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_save_qoutation;

    @FXML
    private ListView<?> lst_supplier;

    @FXML
    private Label lbl_code;

    @FXML
    private ComboBox<EquipmentSupplierModel.Sector> cmb_sector;

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

    public SupplierHome(EquipmentQoutationModel equipModel) {
        this.equipModel = equipModel;
    }

    private final EquipmentQoutationModel equipModel;

    @Override
    protected void setup() {
        ProjectHeader.attach(hbox_header);
        this.cmb_sector.getItems().setAll(Arrays.asList(EquipmentSupplierModel.Sector.LIST));
        this.cmb_sector.getSelectionModel().selectFirst();
        this.rdb_no.setSelected(true);

        this.rdo_group_accredited.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
//            if (this.rdb_yes.isSelected()) {
//                this.lbl_accredited_selected.setText("This supplier is DOST Accedited");
//            } else {
//                this.lbl_accredited_selected.setText("This supplier is NOT Accedited");
//            }
        });

        this.btn_back.setOnMouseClicked(value -> {
            EquipmentEditView editEquip = new EquipmentEditView(equipModel);
            editEquip.load();
            this.changeRoot(editEquip.getRootPane());
        });

        /**
         * Add New.
         */
        this.btn_add.setOnMouseClicked(value -> {

        });

        /**
         * Clear form.
         */
        this.btn_clear.setOnMouseClicked(value -> {

        });

        /**
         * Save changes.
         */
        this.btn_save_qoutation.setOnMouseClicked(value -> {

        });
    }

}
