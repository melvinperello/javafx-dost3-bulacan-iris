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
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ScholarSubmissionModel;
import gov.dost.bulacan.iris.models.ScholarTransmittalModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 *
 * @author DOST-3
 */
public class TransmitInfo extends IrisForm {
    
    @FXML
    private TextArea txt_documents;
    
    @FXML
    private TextArea txt_remarks;
    
    @FXML
    private TextArea txt_info;
    
    @FXML
    private JFXButton btn_ok;
    
    public TransmitInfo(ScholarSubmissionModel submitModel) {
        this.submitModel = submitModel;
        if (this.submitModel.getFkTransmittalId() == null) {
            this.transmitModel = null;
        } else {
            ScholarTransmittalModel transmitModel;
            try {
                transmitModel = ScholarTransmittalModel.findById(this.submitModel.getFkTransmittalId());
            } catch (SQLException e) {
                transmitModel = null;
            }
            this.transmitModel = transmitModel;
        }
    }
    
    private final ScholarSubmissionModel submitModel;
    private final ScholarTransmittalModel transmitModel;
    
    @Override
    protected void setup() {
        this.btn_ok.setOnMouseClicked(value -> {
            this.getStage().close();
            value.consume();
        });
        
        if (this.transmitModel == null) {
            this.txt_info.setText("Not Transmitted");
        } else {
            StringBuilder builder = new StringBuilder("");
            builder.append("Transmittal ID: ");
            builder.append(this.transmitModel.getTransId());
            builder.append("\n");
            builder.append("Transmitted by: ");
            builder.append(this.transmitModel.getTransBy());
            builder.append(" @ ");
            SimpleDateFormat df = new SimpleDateFormat("MMMMMMMMMMMMMMMM dd, yyyy");
            builder.append(df.format(this.transmitModel.getTransDate()));
        }
        
        this.txt_documents.setText(this.submitModel.getDocumentsSubmitted());
        this.txt_remarks.setText(this.submitModel.getRemarks());
        
        this.txt_documents.setEditable(false);
        this.txt_info.setEditable(false);
        this.txt_remarks.setEditable(false);
    }
    
}
