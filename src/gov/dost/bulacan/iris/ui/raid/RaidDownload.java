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
package gov.dost.bulacan.iris.ui.raid;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.RaidContext;
import gov.dost.bulacan.iris.models.RaidModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jhon Melvin
 */
public class RaidDownload extends IrisForm {

    @FXML
    private Label lbl_raid_id;

    @FXML
    private Label lbl_filename;

    @FXML
    private Label lbl_file_size;

    @FXML
    private Label lbl_file_sig;

    @FXML
    private ProgressBar pb_progress;

    @FXML
    private Label lbl_progress;

    @FXML
    private JFXButton btn_open;

    @FXML
    private JFXButton btn_download;

    @FXML
    private JFXButton btn_cancel;

    public RaidDownload(RaidModel raidModel) {
        this.raidModel = raidModel;
    }

    private RaidModel raidModel;

    public static Stage callRaidUpload(RaidModel raidModel) {
        RaidDownload raid = new RaidDownload(raidModel);
        Stage raidStage = new Stage();
        raidStage.setScene(new Scene(raid.load()));
        raidStage.setWidth(600.0);
        raidStage.setHeight(400.0);
        raidStage.setResizable(false);
        raidStage.initModality(Modality.APPLICATION_MODAL);
        raidStage.setTitle(RaidContext.RAID_INFO);
        raidStage.getIcons()
                .add(new Image(Context.app()
                        .getResourceStream("drawable/raid/icon_128.png")));
        return raidStage;
    }

    @Override
    protected void setup() {
        this.btn_open.setDisable(true);
        this.btn_download.setDisable(true);
        
        this.btn_cancel.setOnMouseClicked(value -> {
            this.getStage().close();
            value.consume();
        });
    }

}
