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

import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.MariaDB;
import gov.dost.bulacan.iris.RaidContext;
import gov.dost.bulacan.iris.models.RaidModel;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author s500
 */
public class Raid extends IrisForm {

    @FXML
    private ProgressBar pb_current;

    @FXML
    private Label lbl_current;

    private Runnable onCompletion;

    public void setOnCompletion(Runnable onCompletion) {
        this.onCompletion = onCompletion;
    }

    @Override
    protected void setup() {
        RaidManagerThread rmThread = new RaidManagerThread();
        rmThread.setLbl_current(lbl_current);
        rmThread.setPb_current(pb_current);
        rmThread.setOnCompletion(this.onCompletion);
        rmThread.start();
    }

    //--------------------------------------------------------------------------
    // 1. Get Files From Server
    // 2. Get All Files from Local raid/bin
    // 3. Compare then delete local files which are not listed
    // 4. Check Local Files with Remote Files
    // 5. If Not Existing Download The File.
    // 6. If Existing Check File Signature
    public final static class RaidManagerThread extends Thread {

        @FXML
        private ProgressBar pb_current;

        @FXML
        private Label lbl_current;

        public void setPb_current(ProgressBar pb_current) {
            this.pb_current = pb_current;
        }

        public void setLbl_current(Label lbl_current) {
            this.lbl_current = lbl_current;
        }

        private static void log(Object log) {
            System.out.println(String.valueOf(log));
        }

        public RaidManagerThread() {
            this.remoteActiveFiles = null;
            this.localFiles = null;
            this.localFileFolders = null;
        }

        private List<RaidModel> remoteActiveFiles;
        private List<File> localFiles;
        private List<File> localFileFolders;
        //--------------------------------------------------------------------------
        private Runnable onCompletion;

        public void setOnCompletion(Runnable onCompletion) {
            this.onCompletion = onCompletion;
        }

        private void showError(String message, Exception ex) {
        }

        @Override
        public final void run() {
            Platform.runLater(() -> {
                this.lbl_current.setText("Automatic Backup Started . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            log("Raid Manager: Started");
            this.runRaidManager();
            log("Raid Manager: Finished");

            Platform.runLater(() -> {
                this.lbl_current.setText("Automatic Backup Finished");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if (this.onCompletion != null) {
                this.onCompletion.run();
            }

        }

        private void runRaidManager() {
            //----------------------------------------------------------------------
            //
            Platform.runLater(() -> {
                this.lbl_current.setText("Fetching Remote Files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
            //
            try {
                this.fetchRemoteFileArray();
            } catch (SQLException e) {
                this.showError("Unable to fetch remote files", e);
                return;
            }
            //----------------------------------------------------------------------
            log("Remote Files: " + this.remoteActiveFiles.size());
            log("Remote Files: Successfully Fetched");
            //----------------------------------------------------------------------
            //
            Platform.runLater(() -> {
                this.lbl_current.setText("Fetching Local Files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
            //

            log("Local Files: Fetching");
            try {
                this.fetchLocalFileArray();
            } catch (SecurityException e) {
                this.showError("Failed to fetch local files", e);
                return;
            }
            //----------------------------------------------------------------------
            log("Local Files: " + " File( " + this.localFiles.size() + " ) / Folders ( " + this.localFileFolders.size() + " )");
            log("Local Files: Successfully Fetched");
            try {
                //----------------------------------------------------------------------
                if (FileTool.checkFolders("raid/bin")) {
                    this.executeCheck();
                } else {
                    this.showError("Failed to create local directory.", null);
                    log("Local Files: Failed to create local directory.");
                    return;
                }
            } catch (IOException ex) {
                this.showError("Failed to create local directory.", ex);
                return;
            }
            //------------------------------------------------------------------
            // Clean-up here
            //
            // SQL Backup here.
            try {
                boolean res = MariaDB.backup("127.0.0.1",
                        "iris_db",
                        "1234567", "iris_bulacan_dost3", "D:/asd.sql");

                System.out.println("SQL RESULT: " + res);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private void fetchRemoteFileArray() throws SQLException {
            this.remoteActiveFiles = RaidModel.listActiveRaidArray();

            if (this.remoteActiveFiles == null) {
                this.remoteActiveFiles = new ArrayList<>(0);
            }

        }

        private void fetchLocalFileArray() throws SecurityException {
            this.localFiles = new ArrayList<>();
            this.localFileFolders = new ArrayList<>();
            //
            final File folder = new File("raid/bin");
            final File[] listOfFiles = folder.listFiles();

            if (listOfFiles == null) {
                return;
            }

            if (listOfFiles.length == 0) {
                return;
            }

            //------------------------------------------------------------------
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    this.localFiles.add(file);
                } else if (file.isDirectory()) {
                    this.localFileFolders.add(file);
                }
            }
            //------------------------------------------------------------------
        }

        private void executeCheck() {
            for (RaidModel remoteFile : this.remoteActiveFiles) {
                log("Processing Remote File: " + remoteFile.getId());
                //--------------------------------------------------------------
                // Clone the list to a temporary list
                // in this case the deletion of entry will be allowed during iteration.
                List<File> tempLocFileList = new ArrayList<>(this.localFiles);
                //--------------------------------------------------------------
                // If the file is already existing in the local raid array FLAG
                boolean localCopyExisting = false;
                //--------------------------------------------------------------
                // Loop to LOCAL RAID ARRAY FILES.
                for (int ctr = 0; ctr < tempLocFileList.size(); ctr++) {
                    File tempFile = tempLocFileList.get(ctr);
                    //----------------------------------------------------------
                    // if the file is existing in the local files
                    if (remoteFile.getName().equals(tempFile.getName())) {
                        log("Processing Remote File: Local Copy Existing");
                        this.localFiles.remove(ctr); // remove this file to entry
                        tempLocFileList = null; // clear the temporary list
                        // make sure this is the last iteration of the loop
                        // break is necessary
                        //>>
                        // Attempt to hash the file
                        try {
                            String hashed = RaidContext.getFileHash(tempFile);
                            if (!hashed.equalsIgnoreCase(remoteFile.getHash())) {
                                // break with exist flag false
                                log("Processing Remote File: File Signature Mismatch");
                                break; // if not equal break
                            }
                        } catch (IOException | NoSuchAlgorithmException e) {
                            log("Processing Remote File: File Signature Exception");
                            // ignore error
                            // break with exist flag false
                            break; // skipped below code if error
                        }
                        //----------------------------------------------------------
                        // exist flag true
                        log("Processing Remote File: File Signature Verified");
                        localCopyExisting = true;
                        break;
                    }
                    //----------------------------------------------------------
                } // end inner loop
                //--------------------------------------------------------------

                if (!localCopyExisting) {
                    log("Processing Remote File: Local Copy Not Existing");
                    try {
                        log("Processing Remote File: Downloading");
                        if (this.downloadRemoteFile(remoteFile)) {
                            log("Processing Remote File: Download Success");
                        } else {
                            log("Processing Remote File: Download Failed");
                        }
                    } catch (IOException e) {
                        log("Processing Remote File: Download Exception");
                        // if the downloading of file end up in an exception.
                        // continue in the next entry
                        continue;
                    }
                }

            } // end remote comparison loop

        }

        /**
         * Attempt to download the remote file from the FTP Server. the
         * exception here is thrown.
         *
         * @param raid
         * @return
         * @throws IOException
         */
        private boolean downloadRemoteFile(RaidModel raid) throws IOException {
            //----------------------------------------------------------------------
            final String fileName = raid.getName();
            final double transSize = raid.getSize().doubleValue();
            //----------------------------------------------------------------------
            try (ApacheFTPClientManager ftp = Context.app().ftp().createClientManager()) {
                //------------------------------------------------------------------
                ftp.setOnTransferListener((totalBytesTransferred, bufferSize, streamSize) -> {
                    //--------------------------------------------------------------
                    // double value of bytes transferred
                    final double trans = (double) totalBytesTransferred;
                    // percentage double expression of current acitivity
                    final double transPerc = trans / transSize;
                    // whole number of percentage
                    final int transWhole = (int) (transPerc * 100.0);
                    //--------------------------------------------------------------
                    // progress text displayed below progress bar
                    final String progressText = "Downloading " + transWhole + "% ( "
                            + RaidContext.byteStringFormat().format(totalBytesTransferred)
                            + " bytes / "
                            + RaidContext.byteStringFormat().format(transSize) + " bytes )";
                    //
                    // Do something with this text
                    //
                    Platform.runLater(() -> {
                        this.lbl_current.setText(progressText);
                        this.pb_current.setProgress(transPerc);
                    });

                    //--------------------------------------------------------------
                }); // end listener
                //------------------------------------------------------------------
                FTPFile[] listFile = ftp.getFtpClient().listFiles("bin/" + fileName);
                if (listFile.length == 0) {
                    //--------------------------------------------------------------
                    // The file is linked to a database entry but the binary file is missing
                    this.markAsMissing(raid);
                    //--------------------------------------------------------------
                    return false; // file not exist
                }
                //------------------------------------------------------------------
                // FILE DOWNLOAD
                boolean downloaded = ftp.downloadStream("bin/" + fileName, "raid/bin/" + fileName);
                return downloaded;
            } // end try
        }

        /**
         * The exception here is not thrown but caught, this is just to mark the
         * binary file as missing in the database.
         *
         * @param raid
         */
        private void markAsMissing(RaidModel raid) {
            try {
                if (RaidModel.markAsMissing(raid)) {
                }
            } catch (SQLException ex) {
                // ignore this exception
            }
        }

    } // end raid manager.

} // end class
