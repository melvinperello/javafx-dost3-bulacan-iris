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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Locale;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;

/**
 *
 * @author Jhon Melvin
 */
public class RaidUpload extends IrisForm {

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
        this.setDialogMessageTitle("Upload File");
    }

    //--------------------------------------------------------------------------
    public static Stage callRaidUpload(OnRaidRecorded raidRecorded) {
        RaidUpload raid = new RaidUpload();
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
        raid.setOnRaidRecorded(raidRecorded);
        raidStage.setOnCloseRequest(value -> {
            raid.cancelEvent();
            raidStage.close();
            value.consume();
        });
        return raidStage;
    }

    //--------------------------------------------------------------------------
    public interface OnRaidRecorded {

        boolean onRaidRecorded(RaidModel raidModel);
    }

    private OnRaidRecorded onRaidRecorded;

    /**
     * when calling this method the file is already uploaded in the FTP server
     * the only thing missing is the reference flag.
     *
     * @param onRaidRecorded
     */
    public void setOnRaidRecorded(OnRaidRecorded onRaidRecorded) {
        this.onRaidRecorded = onRaidRecorded;
    }

    @Override
    protected void setup() {
        this.defaultState();

        this.btn_select.setOnMouseClicked(value -> {
            this.prepareFile();
            value.consume();
        });

        this.btn_upload.setOnMouseClicked(value -> {
            this.uploadFile();
            value.consume();
        });

        this.btn_cancel.setOnMouseClicked(value -> {
            this.cancelEvent();
            this.getStage().close();
            value.consume();
        });

    }

    public void cancelEvent() {
        if (this.uploadThread != null) {
            if (this.uploadThread.isStarted()) {
                this.uploadThread.cancel();
                this.showWaitWarningMessage("Upload Cancelled", "File upload was cancelled by the user.");
            }
        }
    }

    /**
     * Default State. use when hash error or initialization.
     */
    private void defaultState() {
        this.lbl_filename.setText("No File Selected");
        this.lbl_file_size.setText("0.00 MB ( 0 bytes )");
        this.lbl_file_sig.setText("");
        this.pb_progress.setProgress(0.00);
        this.lbl_progress.setText("0 % ( 0 bytes / 0 bytes )");
        this.btn_upload.setDisable(true);
        this.btn_select.setDisable(false);
        this.btn_cancel.setDisable(false);
    }

    /**
     * File Meta Data For Upload.
     */
    private Meta fileMeta;

    /**
     * Prepares the file for upload and gathering of information.
     */
    private void prepareFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Redundant Application Information Distribution");
        File file = fileChooser.showOpenDialog(this.getStage());
        if (file == null) {
            return;
        }
        //----------------------------------------------------------------------
        // Get File Name
        String fileName = RaidContext.getFileName(file.getAbsolutePath());
        //----------------------------------------------------------------------
        // Get File Size
        long fileSize = 0L;
        boolean sizingError = false;
        try {
            fileSize = RaidContext.getFileSize(file);
        } catch (IOException ex) {
            // cannot retrieve file size
            sizingError = true;
        }
        if (sizingError) {
            this.showWaitErrorMessage("Indeterminate File Size", "The system cannot determine the file size.");
            return;
        }

        if (fileSize > RaidContext.MAX_SIZE) {
            this.showWaitWarningMessage("File Too Large !", "File size limit is 52,428,800 bytes (Approx. 50 MB). Due to RAID Optimization the system can only accept a limited file size");
            return;
        }

        //----------------------------------------------------------------------
        // prettysize with comma
        String prettySize = RaidContext.byteStringFormat().format(fileSize);
        // normmal size label text
        String fileSizeString = RaidContext.getStringFileSize(fileSize) + " ( "
                + prettySize + " bytes )";
        //----------------------------------------------------------------------
        HashThread hashThread = new HashThread();
        hashThread.setDaemon(true);
        hashThread.setLocalFile(file);
        final long finalizeSize = fileSize;
        Runnable onComplete = () -> {

            Platform.runLater(() -> {
                if (!hashThread.isResult()) {
                    // If Failed to hash.
                    this.fileMeta = null;
                    this.defaultState();
                } else {
                    // is hash success
                    this.btn_upload.setDisable(false);
                    String progressLabel = "0 % ( 0 bytes / " + prettySize + " bytes )";
                    this.lbl_filename.setText(fileName);
                    this.lbl_file_size.setText(fileSizeString);
                    this.lbl_file_sig.setText(hashThread.getHashResult() + " ( SHA1 )");
                    this.lbl_progress.setText(progressLabel);
                    this.btn_cancel.setDisable(false);
                    this.btn_select.setDisable(false);
                    // set common file meta.
                    Meta meta = new Meta();
                    meta.setFile(file);
                    meta.setFileHash(hashThread.getHashResult());
                    meta.setFileSize(finalizeSize);
                    meta.setFileName(fileName);
                    this.fileMeta = meta;
                }

            });
        };
        hashThread.setOnCompletion(onComplete);
        //----------------------------------------------------------------------
        // disable buttons when starting hash thread.
        this.btn_cancel.setDisable(true);
        this.btn_select.setDisable(true);
        this.btn_upload.setDisable(true);
        //----------------------------------------------------------------------
        hashThread.start();
        //----------------------------------------------------------------------
    }

    /**
     * Upload Thread.
     */
    private UploadThread uploadThread;

    /**
     * Upload File.
     */
    private void uploadFile() {
        if (this.fileMeta == null) {
            return;
        }

        //----------------------------------------------------------------------
        final String raidKey = RaidContext.createRaidKey();
        final String raidName = raidKey + "_" + this.fileMeta.getFileHash();
        this.fileMeta.setRaidName(raidName);
        //----------------------------------------------------------------------
        final RaidModel model = new RaidModel();
        model.setId(raidKey);
        model.setName(raidName);
        model.setDisplayName(this.fileMeta.getFileName());
        // set file meta dir
        this.fileMeta.setRaidPath(RaidContext.BIN_DIR);
        model.setPath(this.fileMeta.getRaidPath());
        model.setExtenstion(RaidContext.getFileExtension(this.fileMeta.getFile().getAbsolutePath()));
        model.setSize(this.fileMeta.getFileSize());
        model.setHash(this.fileMeta.getFileHash());
        //----------------------------------------------------------------------
        boolean preUploadSuccess = false;
        try {
            preUploadSuccess = RaidModel.insert(model);
        } catch (SQLException e) {
            preUploadSuccess = false;
        }

        if (!preUploadSuccess) {
            this.showWaitErrorMessage("Ooops! Something went wrong.", "File upload was unable to start because the database server has encountered a problem.");
            this.getStage().close();
            return;
        }
        //----------------------------------------------------------------------

        //----------------------------------------------------------------------
        uploadThread = new UploadThread();
        uploadThread.setFileMeta(this.fileMeta);
        uploadThread.setOnUpdate((text, value) -> {
            Platform.runLater(() -> {
                this.lbl_progress.setText(String.valueOf(text));
                this.pb_progress.setProgress(value);
            });
        });
        uploadThread.setOnComplete(() -> {
            //------------------------------------------------------------------
            boolean onPostRaid = false;
            if (this.onRaidRecorded != null) {
                onPostRaid = this.onRaidRecorded.onRaidRecorded(model);
            }
            //------------------------------------------------------------------
            if (onPostRaid) {
                Platform.runLater(() -> {
                    this.showWaitInformationMessage("Upload Success !", "File successfully uploaded to server.");
                    this.getStage().close();
                });
            } else {
                Platform.runLater(() -> {
                    this.showWaitInformationMessage("Upload Failed !", "There was a problem while trying to complete the upload.");
                    this.getStage().close();
                });
            }

        });

        uploadThread.setOnException(() -> {
            Platform.runLater(() -> {
                this.showWaitErrorMessage("Ooops! Something went wrong.", "File upload has encountered a problem. Please try again later.");
                this.getStage().close();
            });
        });
        this.btn_upload.setDisable(true);
        this.btn_select.setDisable(true);
        uploadThread.start();
    }

    /**
     * Storing File Meta.
     */
    public static class Meta {

        private String fileName;
        private String raidName;
        private Long fileSize;
        private File file;
        private String fileHash;
        private String raidPath;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getFileHash() {
            return fileHash;
        }

        public void setFileHash(String fileHash) {
            this.fileHash = fileHash;
        }

        public String getRaidName() {
            return raidName;
        }

        public void setRaidName(String raidName) {
            this.raidName = raidName;
        }

        public String getRaidPath() {
            return raidPath;
        }

        public void setRaidPath(String raidPath) {
            this.raidPath = raidPath;
        }

    }

    /**
     * Upload Worker Thread.
     */
    private static class UploadThread extends Thread {

        private Meta fileMeta; // file meta
        private OnUpdate onUpdate; // on update
        private ApacheFTPClientManager ftpConnection; // connection
        private boolean cancelFlag; // when the upload was cancelled
        private Runnable onComplete; // when complete and not cancelled
        private boolean started; // when started
        //----------------------------------------------------------------------

        public void setFileMeta(Meta fileMeta) {
            this.fileMeta = fileMeta;
        }

        public void setOnUpdate(OnUpdate onUpdate) {
            this.onUpdate = onUpdate;
        }

        public void setFtpConnection(ApacheFTPClientManager ftpConnection) {
            this.ftpConnection = ftpConnection;
        }

        /**
         * Upload success and no exceptions.
         *
         * @param onComplete
         */
        public void setOnComplete(Runnable onComplete) {
            this.onComplete = onComplete;
        }

        //----------------------------------------------------------------------
        private Runnable onException;

        /**
         * IO Exception or upload fail result.
         *
         * @param onException
         */
        public void setOnException(Runnable onException) {
            this.onException = onException;
        }

        //----------------------------------------------------------------------
        /**
         * Running when the upload is still in progress allows the viewing of
         * status.
         */
        @FunctionalInterface
        public interface OnUpdate {

            void onUpdate(String text, double percent);
        }
        //----------------------------------------------------------------------

        @Override
        public void run() {
            //------------------------------------------------------------------
            // Set Flags
            this.cancelFlag = false;
            this.started = true;
            //------------------------------------------------------------------
            // total size in bytes
            final double transSize = this.fileMeta.getFileSize().doubleValue();
            this.ftpConnection = null;
            //------------------------------------------------------------------
            try {
                // open ftp connection
                this.ftpConnection = Context.app().ftp().createClientManager();
                // set listener
                ftpConnection.setOnTransferListener((totalBytesTransferred, bufferSize, streamSize) -> {
                    if (this.cancelFlag) {
                        // do not listen when cancelled flag was raised
                        return;
                    }
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
                }); // END LISTENER
                //--------------------------------------------------------------
                // Start Upload
                boolean uploaded = ftpConnection
                        .uploadStream(this.fileMeta.getFile().getAbsolutePath(),
                                "/" + this.fileMeta.getRaidPath()
                                + "/" + this.fileMeta.getRaidName());
                //--------------------------------------------------------------
                // When no error
                if (this.onComplete != null) {
                    if (!this.cancelFlag) {
                        // when completed and not cancelled
                        // call on completion routine.
                        if (uploaded) {
                            // when upload success
                            this.onComplete.run();
                        } else {
                            // when upload failed called on exception instead
                            if (this.onException != null) {
                                this.onException.run();
                            }
                        }

                    }
                }
                //--------------------------------------------------------------
            } catch (IOException e) {
                if (this.cancelFlag) {
                    return;
                }
                //--------------------------------------------------------------
                // CALL EXCEPTION HANDLING WHEN NOT CANCELLED
                //
                if (this.onException != null) {
                    this.onException.run();
                }
            } finally {
                //--------------------------------------------------------------
                // Connection Clean Up.
                if (ftpConnection != null) {
                    try {
                        ftpConnection.closeQuietly();
                    } catch (Exception e) {
                        // ignore
                    }
                    this.ftpConnection = null;
                }
                //--------------------------------------------------------------
                this.started = false; // fall down start flag
            }
        }

        /**
         * Cancel Current Upload.
         */
        public void cancel() {
            if (this.started) {
                this.cancelFlag = true;
                try {
                    this.ftpConnection.abort();
                } catch (IOException e) {
                    // ignore
                }
                this.ftpConnection.closeQuietly();
                this.ftpConnection = null;
                //--------------------------------------------------------------
                this.started = false;
                //--------------------------------------------------------------
            }

        }

        /**
         * If the user invoke the cancel method.
         *
         * @return
         */
        public boolean isCanceled() {
            return cancelFlag;
        }

        /**
         * If the thread was started and not cancelled or still running.
         *
         * @return
         */
        public boolean isStarted() {
            return started;
        }

    } // end of upload thread

    /**
     * Worker thread for hashing the file to avoid freezing.
     */
    public static class HashThread extends Thread {

        //----------------------------------------------------------------------
        /**
         * Local File to hash.
         */
        private File localFile;
        /**
         * When Completed.
         */
        private Runnable onCompletion;

        public void setLocalFile(File localFile) {
            this.localFile = localFile;
        }

        public void setOnCompletion(Runnable onCompletion) {
            this.onCompletion = onCompletion;
        }

        //----------------------------------------------------------------------
        /**
         * Hash Result.
         */
        private String hashResult;
        /**
         * Is This thread successfully run.
         */
        private boolean result;

        public String getHashResult() {
            return hashResult;
        }

        public boolean isResult() {
            return result;
        }

        @Override
        public void run() {
            this.result = false;
            this.hashResult = "";
            try {
                this.hashResult = RaidContext.getFileHash(this.localFile);
                this.hashResult = this.hashResult.toUpperCase(Locale.ENGLISH);
                this.result = true;
            } catch (IOException | NoSuchAlgorithmException ex) {
                this.hashResult = null;
            }
            if (onCompletion != null) {
                this.onCompletion.run();
            }
        }

    }

}
