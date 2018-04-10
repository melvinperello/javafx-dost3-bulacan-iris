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
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;

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

    private final RaidModel raidModel;

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

        this.btn_download.setOnMouseClicked(value -> {
            this.downloadFile();
            value.consume();
        });

        this.btn_open.setOnMouseClicked(value -> {
            System.out.println("open");
            value.consume();
        });

        //----------------------------------------------------------------------
        this.preloadData();
        //----------------------------------------------------------------------
        this.checkFileInRaid();
        //----------------------------------------------------------------------
    }

    private void preloadData() {
        this.lbl_raid_id.setText(this.raidModel.getId());
        this.lbl_filename.setText(this.raidModel.getDisplayName());
        Long fileSize = this.raidModel.getSize();
        String fileSizePretty = RaidContext.byteStringFormat().format(fileSize);
        String fileSzieShort = RaidContext.getStringFileSize(fileSize);
        this.lbl_file_size.setText(fileSzieShort + " ( " + fileSizePretty + " ) bytes");
        this.lbl_file_sig.setText(this.raidModel.getHash());
    }

    private void checkFileInRaid() {
        if (FileTool.checkFoldersQuietly("raid/bin")) {
            String fileName = this.raidModel.getName();
            File raidFile = new File("raid/bin/" + fileName);
            if (raidFile.exists()) {
                // copy to temp
                this.btn_download.setDisable(true);
                this.btn_open.setDisable(false);
                this.lbl_raid_id.setText(this.lbl_raid_id.getText() + " - Local Copy Verified (Ready)");
            } else {
                // download file
                this.btn_download.setDisable(false);
                this.btn_open.setDisable(true);
                this.lbl_raid_id.setText(this.lbl_raid_id.getText() + " - No Local Copy (Download)");
            }
        }
    }

    /**
     * Download file to RAID Array.
     */
    private void downloadFile() {
        DownloadThread dl = new DownloadThread();
        dl.setRaidModel(this.raidModel);
        dl.start();
    }

    /**
     * Download thread for RAID.
     */
    private static class DownloadThread extends Thread {

        private RaidModel raidModel; // raid model
        private ApacheFTPClientManager ftpConnection; // connection
        private OnUpdate onUpdate; // on update
        private Runnable onException; // when exception
        private volatile boolean runningFlag;

        /**
         * Set Raid Model.
         *
         * @param raidModel
         */
        public void setRaidModel(RaidModel raidModel) {
            this.raidModel = raidModel;
        }

        /**
         * Running when the upload is still in progress allows the viewing of
         * status.
         */
        @FunctionalInterface
        public interface OnUpdate {

            void onUpdate(String text, double percent);
        }

        @Override
        public void run() {
            //------------------------------------------------------------------
            // flag
            this.runningFlag = true;
            //------------------------------------------------------------------
            final String fileName = this.raidModel.getName();
            final double transSize = this.raidModel.getSize().doubleValue();
            try {
                this.ftpConnection = Context.app().ftp().createClientManager();
                //--------------------------------------------------------------
                this.ftpConnection.setOnTransferListener((totalBytesTransferred, bufferSize, streamSize) -> {
                    //----------------------------------------------------------
                    // double value of bytes transferred
                    final double trans = (double) totalBytesTransferred;
                    // percentage double expression of current acitivity
                    final double transPerc = trans / transSize;
                    // whole number of percentage
                    final int transWhole = (int) (transPerc * 100.0);
                    //----------------------------------------------------------
                    // progress text displayed below progress bar
                    final String progressText = transWhole + "% ( "
                            + RaidContext.byteStringFormat().format(totalBytesTransferred)
                            + " bytes / "
                            + RaidContext.byteStringFormat().format(transSize) + " bytes )";
                    //----------------------------------------------------------
                    if (this.onUpdate != null) {
                        // call only when on update was set
                        this.onUpdate.onUpdate(progressText, transPerc);
                    }
                }); // end listener
                //--------------------------------------------------------------
                this.ftpConnection.downloadStream("bin/" + fileName, "raid/bin/" + fileName);
            } catch (IOException e) {
                //--------------------------------------------------------------
                // call exception routine
                if (this.onException != null) {
                    this.onException.run();
                }
                //--------------------------------------------------------------
            } finally {
                //--------------------------------------------------------------
                // FTP Connection Clean Up.
                if (this.ftpConnection != null) {
                    try {
                        this.ftpConnection.closeQuietly();
                    } catch (Exception e) {
                        // ignore
                    }
                    this.ftpConnection = null;
                }
                //--------------------------------------------------------------
                // set running flag
                this.runningFlag = false;
                //--------------------------------------------------------------
            }
        }

        /**
         * Tries to abort current running operation.
         */
        public void cancel() {
            if (this.runningFlag) {
                try {
                    this.ftpConnection.abort();
                    this.ftpConnection.close();
                    this.ftpConnection = null;
                } catch (IOException e) {
                    // ignore
                } finally {
                    //----------------------------------------------------------
                    this.runningFlag = false;
                    //----------------------------------------------------------
                }
            }
        }

    }

}
