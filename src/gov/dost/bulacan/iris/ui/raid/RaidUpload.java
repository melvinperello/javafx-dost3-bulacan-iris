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
import gov.dost.bulacan.iris.RaidTool;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author Jhon Melvin
 */
public class RaidUpload extends PolarisFxController {

    @FXML
    private JFXButton btn_select;

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
    private JFXButton btn_upload;

    @FXML
    private JFXButton btn_cancel;

    public RaidUpload() {

    }

    private File fileUpload;

    @Override
    protected void setup() {
        this.lbl_filename.setText("No File Selected");
        this.lbl_file_size.setText("0.00 MB ( 0 bytes )");
        this.lbl_file_sig.setText("");

        this.pb_progress.setProgress(0.00);
        this.lbl_progress.setText("0 % ( 0 bytes / 0 bytes )");

        this.btn_upload.setDisable(true);

        this.btn_select.setOnMouseClicked(value -> {
            this.prepareFile();
        });

        this.btn_upload.setOnMouseClicked(value -> {
            this.uploadFile();
        });
    }

    private void prepareFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Redundant Application Information Distribution");
        File file = fileChooser.showOpenDialog(this.getStage());
        if (file == null) {
            return;
        }

        String fileName = RaidTool.getFileName(file.getAbsolutePath());
        long fileSize = 0L;
        try {
            fileSize = RaidTool.getFileSize(file);
        } catch (IOException ex) {
            // cannot retrieve file size
        }

        String prettySize = Context.app().getIntegerPrettyFormat().format(fileSize);
        String fileSizeString = RaidTool.getStringFileSize(fileSize) + " ( "
                + prettySize + " bytes )";

        //----------------------------------------------------------------------
        HashThread hashThread = new HashThread();
        hashThread.setDaemon(true);
        hashThread.localFile = file;
        hashThread.onCompletion = () -> {
            Platform.runLater(() -> {
                if (!hashThread.isResult()) {
                    this.btn_upload.setDisable(true);
                    this.fileUpload = null;
                } else {
                    this.btn_upload.setDisable(false);
                    String progressLabel = "0 % ( 0 bytes / " + prettySize + " bytes )";
                    this.lbl_filename.setText(fileName);
                    this.lbl_file_size.setText(fileSizeString);
                    this.lbl_file_sig.setText(hashThread.getHashResult());
                    this.lbl_progress.setText(progressLabel);
                    this.btn_cancel.setDisable(false);
                    this.btn_select.setDisable(false);
                    this.fileUpload = file;
                }

            });
        };
        this.btn_cancel.setDisable(true);
        this.btn_select.setDisable(true);
        this.btn_upload.setDisable(true);
        hashThread.start();
        //----------------------------------------------------------------------

    }

    private void uploadFile() {
        if (this.fileUpload == null) {
            return;
        }
        ApacheFTPClientManager ftp = null;
        try {
            ftp = Context.app().ftp().createClientManager();
            ftp.setOnTransferListener((totalBytesTransferred, bufferSize, streamSize) -> {
                System.out.println(totalBytesTransferred);
            });
            ftp.uploadStream(this.fileUpload.getAbsolutePath(), "Fires");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp != null) {
                ftp.closeQuietly();
            }
        }
    }

    public static class HashThread extends Thread {

        private File localFile;
        private String hashResult;
        private boolean result;

        private Runnable onCompletion;

        @Override
        public void run() {
            this.result = false;
            this.hashResult = "";
            try {
                this.hashResult = RaidTool.getFileHash(this.localFile);
                this.hashResult = this.hashResult.toUpperCase(Locale.ENGLISH) + " ( " + RaidTool.HASH_ALGORITHM + " )";
                this.result = true;
            } catch (IOException | NoSuchAlgorithmException ex) {
                this.hashResult = null;
            }
            if (onCompletion != null) {
                this.onCompletion.run();
            }
        }

        public String getHashResult() {
            return hashResult;
        }

        public boolean isResult() {
            return result;
        }

    }

}
