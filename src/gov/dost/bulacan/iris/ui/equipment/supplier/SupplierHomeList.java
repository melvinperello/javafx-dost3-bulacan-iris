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

import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisCustomListAdapter.Listable;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel;
import gov.dost.bulacan.iris.models.EquipmentSupplierModel.Sector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author Jhon Melvin
 */
public class SupplierHomeList extends PolarisFxController implements Listable {

    @FXML
    private ImageView img_icon;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_sector;

    @FXML
    private Label lbl_date;

    //--------------------------------------------------------------------------
    public SupplierHomeList() {
        
    }
    private String sector;
    private EquipmentSupplierModel supplierModel;

    public EquipmentSupplierModel getSupplierModel() {
        return supplierModel;
    }

    public void setSupplierModel(EquipmentSupplierModel supplierModel) {
        this.sector = Sector.getStringValue(supplierModel.getSector());
        this.supplierModel = supplierModel;
    }

    public String getSector() {
        if (this.sector == null) {
            return "";
        }
        return this.sector;
    }

    @Override
    protected void setup() {
        this.lbl_name.setText(this.supplierModel.getSupplierName());
        this.lbl_sector.setText(sector);
    }

    @Override
    public Pane getCellGraphic() {
        return this.getRootPane();
    }

}
