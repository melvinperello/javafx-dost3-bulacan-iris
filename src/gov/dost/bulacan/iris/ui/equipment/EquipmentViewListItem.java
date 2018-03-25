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
package gov.dost.bulacan.iris.ui.equipment;

import gov.dost.bulacan.iris.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisCustomListAdapter.Listable;
import gov.dost.bulacan.iris.models.EquipmentQoutationModel;

/**
 *
 * @author Jhon Melvin
 */
public class EquipmentViewListItem extends PolarisFxController implements Listable {

    @FXML
    private ImageView img_icon;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_supplier;
    
    @FXML
    private Label lbl_keys;

    public EquipmentViewListItem() {
        this.qouteModel = null;
    }

    private EquipmentQoutationModel qouteModel;

    public void setQouteModel(EquipmentQoutationModel qouteModel) {
        this.qouteModel = qouteModel;
    }

    public EquipmentQoutationModel getQouteModel() {
        return qouteModel;
    }

    @Override
    protected void setup() {
        if (qouteModel != null) {
            //
            this.lbl_name.setText(qouteModel.getEquipmentName());
            //
            String date = "No Specified Date";
            if (qouteModel.getQoutationDate() != null) {
                date = Context.app().getDateFormatNamed().format(qouteModel.getQoutationDate());
            }
            //
            this.lbl_date.setText(date);
            //
            this.lbl_keys.setText("No Related Keywords");
            if (!qouteModel.getKeyword().isEmpty()) {
                this.lbl_keys.setText(qouteModel.getKeyword());
            }
            //
            lbl_supplier.setText("Unknown Supplier");

        }
    }

    @Override
    public Pane getCellGraphic() {
        return this.getRootPane();
    }

}
