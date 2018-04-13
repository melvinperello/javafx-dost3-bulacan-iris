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
import java.io.InputStream;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.util.Date;
import java.util.Locale;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;
import org.apache.commons.net.ftp.FTPFile;

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

    public static Stage call(RaidModel raidModel) {
        RaidDownload raid = new RaidDownload(raidModel);
        Stage raidStage = new Stage();
        raidStage.setScene(new Scene(raid.load()));
        raidStage.setWidth(600.0);
        raidStage.setHeight(400.0);
        raidStage.setResizable(false);
        raidStage.initModality(Modality.APPLICATION_MODAL);
        raidStage.setTitle(RaidContext.RAID_INFO);
        raidStage.getIcons()
                .add(new Image(Context
                        .getResourceStream("drawable/raid/icon_128.png")));
        raidStage.setOnCloseRequest(value -> {
            raid.cancelEvent();
            raidStage.close();
            value.consume();
        });
        return raidStage;
    }

    @Override
    protected void setup() {
        //----------------------------------------------------------------------
        this.defaultState();
        //----------------------------------------------------------------------

        this.btn_cancel.setOnMouseClicked(value -> {
            this.cancelEvent();
            value.consume();
        });

        this.btn_download.setOnMouseClicked(value -> {
            this.downloadFile();
            value.consume();
        });

        this.btn_open.setOnMouseClicked(value -> {
            this.checkFileIntegrity();
            value.consume();
        });

        //----------------------------------------------------------------------
        this.preloadData();
        //----------------------------------------------------------------------
        this.checkFileInRaid();
        //----------------------------------------------------------------------
    }

    private void cancelEvent() {
        if (this.downloadThread != null) {
            if (this.downloadThread.isRunning()) {
                this.downloadThread.cancel();
                this.showWaitWarningMessage("Download Cancelled", "File download was cancelled by the user.");
            }
        }
        this.getStage().close();
    }

    private void defaultState() {
        this.lbl_filename.setText("No File Selected");
        this.lbl_file_size.setText("0.00 MB ( 0 bytes )");
        this.lbl_file_sig.setText("");
        this.pb_progress.setProgress(0.00);
        this.lbl_progress.setText("0 % ( 0 bytes / 0 bytes )");
        this.btn_open.setDisable(true);
        this.btn_download.setDisable(true);
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
            final String fileName = this.raidModel.getName();
            File raidFile = new File("raid/bin/" + fileName);
            if (raidFile.exists()) {
                // copy to temp
                this.btn_download.setDisable(true);
                this.btn_open.setDisable(false);
                this.lbl_raid_id.setText(this.raidModel.getId() + " - Local Copy Verified (Ready)");
                this.pb_progress.setProgress(1.00);
                this.lbl_progress.setText("100% Download (COMPLETE)");
            } else {
                // download file
                this.btn_download.setDisable(false);
                this.btn_open.setDisable(true);
                this.lbl_raid_id.setText(this.raidModel.getId() + " - No Local Copy (Download)");
            }
        } else {
            // NO DIR EXCEPTION
            this.showWaitErrorMessage("Write Exception", "The system encountered an error while writing on the disk.");
            this.getStage().close();
        }
    }

    /**
     * Check file Integrity if this was success it will call openTempFile.
     *
     * @see RaidDownload#openTempFile(java.io.File)
     */
    private void checkFileIntegrity() {
        //----------------------------------------------------------------------
        // disable buttons when starting hash thread.
        this.btn_cancel.setDisable(true);
        this.btn_download.setDisable(true);
        this.btn_open.setDisable(true);
        //----------------------------------------------------------------------
        final String fileName = this.raidModel.getName();
        File file = new File("raid/bin/" + fileName);
        //----------------------------------------------------------------------
        HashThread hashThread = new HashThread();
        hashThread.setDaemon(true);
        hashThread.setLocalFile(file);
        Runnable onComplete = () -> {
            if (!hashThread.isSuccess()) {
                // If Failed to hash.
                Platform.runLater(() -> {
                    this.showWaitErrorMessage("Ooops! Something went wrong.", "File not verified please try again.");
                    this.getStage().close();
                });
            } else {
                final String localHash = hashThread.getFileHashValue();
                final String remoteHash = this.raidModel.getHash().toUpperCase(Locale.ENGLISH);

                if (localHash.equalsIgnoreCase(remoteHash)) {
                    // CREATE TEMP FILE BEFORE RUN
                    //----------------------------------------------------------
                    this.openTempFile(file);
                    //----------------------------------------------------------
                } else {
                    // hash fail delete file
                    try {
                        file.delete();
                    } catch (Exception e) {
                        // ignore
                    }
                    Platform.runLater(() -> {
                        this.showWaitErrorMessage("Integrity Error", "File may be corrupted or compromised by a virus.");
                        this.getStage().close();
                    });
                }

            }

        };
        hashThread.setOnCompletion(onComplete);
        hashThread.start();
        //------------------------------------------------------------
    }

    /**
     * WARNING: THIS METHOD IS RUNNING ON THE HASH THREAD.
     *
     * @param file
     */
    private void openTempFile(File file) {
        if (FileTool.checkFoldersQuietly(Context.DIR_TEMP)) {
            final String tempFile = this.raidModel.getDisplayName()
                    + "_" + Context.getDateFormatTimeStamp().format(new Date())
                    + "." + this.raidModel.getExtenstion();
            final File tempBinFile = new File(Context.DIR_TEMP + "/" + tempFile);
            boolean copied = false;
            try {
                copied = FileTool.copy(file, tempBinFile);
            } catch (IOException | IllegalArgumentException | NonReadableChannelException | NonWritableChannelException e) {
                // ignore
            }
            //------------------------------------------------------------------
            if (copied) {
                // 
                if (Context.desktopOpenQuietly(tempBinFile)) {
                    Platform.runLater(() -> {
                        this.showWaitInformationMessage("Openning File", "Please wait while the operating system loads the file.");
                        this.getStage().close();
                    });
                } else {
                    this.showWaitErrorMessage("Cannot Open", "The operating system was unable to open the file.");
                    this.getStage().close();
                }
            } else {
                Platform.runLater(() -> {
                    this.showWaitErrorMessage("Temp File Error", "The system has encountered an error while trying to create a temporary file.");
                    this.getStage().close();
                });
            }
            //------------------------------------------------------------------
        } else {
            // DIR EXCEPTION
            Platform.runLater(() -> {
                this.showWaitErrorMessage("Write Exception", "The system encountered an error while writing on the disk.");
                this.getStage().close();
            });

        }
    }

    private DownloadThread downloadThread;

    /**
     *
     * Download file to RAID Array.
     */
    private void downloadFile() {
        this.downloadThread = new DownloadThread();
        this.downloadThread.setRaidModel(this.raidModel);
        //----------------------------------------------------------------------
        /**
         * Update UI Frames.
         */
        this.downloadThread.setOnUpdate((text, percent) -> {
            Platform.runLater(() -> {
                this.lbl_progress.setText(String.valueOf(text));
                this.pb_progress.setProgress(percent);
            });
        });
        //----------------------------------------------------------------------
        /**
         * When encountered and error.
         */
        this.downloadThread.setOnException(() -> {
            Platform.runLater(() -> {
                if (this.downloadThread.getCaughtException() != null) {
                    if (this.downloadThread.getCaughtException().getMessage().equalsIgnoreCase("FILE_NOT_EXIST")) {
                        this.showWaitErrorMessage("Ooops! Missing File !", "The file that you are requesting is not existing in the server, this may be caused by hardware system failures. You may want to consider to delete this entry permanently.");
                        this.getStage().close();
                        return;
                    }
                }
                this.showWaitErrorMessage("Ooops! Something went wrong.", "File download has encountered a problem. Please try again later.");
                this.getStage().close();
            });
        });
        //----------------------------------------------------------------------
        this.downloadThread.setOnComplete(() -> {
            Platform.runLater(() -> {
                this.btn_download.setDisable(true);
                this.btn_open.setDisable(false);
                this.lbl_raid_id.setText(this.raidModel.getId() + " - Local Copy Detected (Ready)");
                int c = this.showConfirmationMessage("Downloaded Successfully.", "The file is now ready to be open, Open it now ?");
                if (c == 1) {
                    this.checkFileIntegrity();
                }
            });
        });
        //----------------------------------------------------------------------
        this.btn_download.setDisable(true);
        this.downloadThread.start();
    }

    /**
     * Download thread for RAID.
     */
    private static class DownloadThread extends Thread {

        private RaidModel raidModel; // raid model
        private ApacheFTPClientManager ftpConnection; // connection
        private OnUpdate onUpdate; // on update
        private Runnable onException; // when exception
        private Runnable onComplete;
        private volatile boolean runningFlag;
        private volatile boolean cancelFlag; //no-op
        private Exception caughtException;

        public Exception getCaughtException() {
            return caughtException;
        }

        /**
         * Check if running.
         *
         * @return
         */
        public boolean isRunning() {
            return runningFlag;
        }

        /**
         * Set Raid Model.
         *
         * @param raidModel
         */
        public void setRaidModel(RaidModel raidModel) {
            this.raidModel = raidModel;
        }

        /**
         * Updates.
         *
         * @param onUpdate
         */
        public void setOnUpdate(OnUpdate onUpdate) {
            this.onUpdate = onUpdate;
        }

        /**
         * Failed to complete and with exceptions.
         *
         * @param onException
         */
        public void setOnException(Runnable onException) {
            this.onException = onException;
        }

        /**
         * When download thread is completed with success.
         *
         * @param onComplete
         */
        public void setOnComplete(Runnable onComplete) {
            this.onComplete = onComplete;
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
            this.cancelFlag = false;
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
                //
                FTPFile[] listFile = this.ftpConnection.getFtpClient().listFiles("bin/" + fileName);
                if (listFile.length == 0) {
                    throw new IOException("FILE_NOT_EXIST");
                }
                //
                boolean downloaded = this.ftpConnection.downloadStream("bin/" + fileName, "raid/bin/" + fileName);

                if (this.cancelFlag) {
                    return; // cancel
                }

                if (downloaded) {
                    if (this.onComplete != null) {
                        this.onComplete.run();
                    }
                } else {
                    if (this.onException != null) {
                        this.onException.run();
                    }
                }
            } catch (IOException e) {
                //--------------------------------------------------------------
                if (this.cancelFlag) {
                    return; // cancel
                }
                this.caughtException = e;
                //--------------------------------------------------------------
                // call exception routine
                if (this.onException != null) {
                    this.onException.run();
                }
                //--------------------------------------------------------------
            } catch (NullPointerException e) {
                // IGNORE
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
                    if (this.ftpConnection != null) {
                        this.ftpConnection.abort();
                        this.ftpConnection.close();
                    }
                    this.ftpConnection = null;
                } catch (IOException e) {
                    // ignore
                } finally {
                    //----------------------------------------------------------
                    this.runningFlag = false;
                    //----------------------------------------------------------
                }
            }
            //------------------------------------------------------------------
            this.cancelFlag = true;
            //------------------------------------------------------------------
        }

    }

}
