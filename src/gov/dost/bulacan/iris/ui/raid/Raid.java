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
import gov.dost.bulacan.iris.IRIS;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.MariaDB;
import gov.dost.bulacan.iris.PolarisText;
import gov.dost.bulacan.iris.RaidContext;
import gov.dost.bulacan.iris.models.RaidModel;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jhon Melvin
 */
public class Raid extends IrisForm {

    private static final Logger logger = LoggerFactory.getLogger(Raid.class);

    @FXML
    private ProgressBar pb_current;

    @FXML
    private Label lbl_current;

    private Runnable onCompletion;

    public void setOnCompletion(Runnable onCompletion) {
        this.onCompletion = onCompletion;
    }

    public static void call(Runnable showPrimary) {
        //----------------------------------------------------------------------
        final Stage raidStage = new Stage();
        final Raid raidFx = new Raid();
        //----------------------------------------------------------------------
        raidFx.setOnCompletion(() -> {
            Platform.runLater(() -> {
                raidStage.close();
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
                showPrimary.run();
            });
        });
        //----------------------------------------------------------------------
        Pane root = raidFx.load();
        //----------------------------------------------------------------------

        raidStage.setScene(new Scene(root, 600.0, 146.0));
        raidStage.initModality(Modality.APPLICATION_MODAL);
        raidStage.setTitle(RaidContext.RAID_INFO);
        raidStage.getIcons()
                .add(new Image(Context
                        .getResourceStream("drawable/raid/icon_128.png")));
        raidStage.setResizable(false);
        raidStage.setOnCloseRequest(value -> {

            value.consume();
        });
        raidStage.show();
    }

    @Override
    protected void setup() {
        RaidManagerThread rmThread = new RaidManagerThread();
        rmThread.setLbl_current(lbl_current);
        rmThread.setPb_current(pb_current);

        /**
         * On Completion. setter value.
         */
        rmThread.setOnCompletion(() -> {
            //------------------------------------------------------------------
            // LOGGING
            try {
                final String logName = "RAID_LOG_ " + new SimpleDateFormat("yyyyMMdd_hhmmssa").format(new Date());
                if (FileTool.checkFolders(Context.DIR_LOGS)) {
                    PolarisText text = new PolarisText();
                    text.setAppendMode(false);
                    // not null
                    if (rmThread.getLogBuilder() != null) {
                        // not empty
                        if (!rmThread.getLogBuilder().toString().isEmpty()) {
                            text.write(rmThread.getLogBuilder().toString());
                            text.save(new File(Context.DIR_LOGS + File.separator + logName + ".txt"));
                        }
                    }
                }
            } catch (IOException e) {
                // ignore log error
            }
            //------------------------------------------------------------------
            this.onCompletion.run();
        });

        /**
         * Set On Error Exception.
         */
        rmThread.setOnError((message, ex) -> {
            if (ex == null) {
                this.showWaitErrorMessage("RAID Algorithm Error !", message);
            } else {
                /**
                 * TELEMETRY.
                 */
                IRIS.telemetry(ex, null);
                this.showExceptionMessage(ex, "RAID Algorithm Error !", message);
            }
        });

        rmThread.setName("RAID-THREAD");

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

        /**
         * Log This Thread.
         *
         * @param log
         */
        private void log(Object log) {
            logger.debug(String.valueOf(log));
//            System.out.println(String.valueOf(log));
            if (this.logBuilder != null) {
                this.logBuilder.append(String.valueOf(log));
                this.logBuilder.append("\n");
            }
        }

        private StringBuilder logBuilder;

        public StringBuilder getLogBuilder() {
            return logBuilder;
        }

        //----------------------------------------------------------------------
        private ProgressBar pb_current;
        private Label lbl_current;

        public void setPb_current(ProgressBar pb_current) {
            this.pb_current = pb_current;
        }

        public void setLbl_current(Label lbl_current) {
            this.lbl_current = lbl_current;
        }
        //----------------------------------------------------------------------

        public RaidManagerThread() {
            this.remoteActiveFiles = null;
            this.localFiles = null;
            this.localFileFolders = null;
            this.onError = null;
            this.onCompletion = null;
        }
        //----------------------------------------------------------------------
        private List<RaidModel> remoteActiveFiles;
        private List<File> localFiles;
        private List<File> localFileFolders;
        //----------------------------------------------------------------------
        private Runnable onCompletion;

        public void setOnCompletion(Runnable onCompletion) {
            this.onCompletion = onCompletion;
        }

        //----------------------------------------------------------------------
        @FunctionalInterface
        public interface OnError {

            void onError(String message, Exception ex);
        }

        private OnError onError;

        public void setOnError(OnError onError) {
            this.onError = onError;
        }
        //----------------------------------------------------------------------

        /**
         * This method is called on various exception routines in this raid
         * manager.
         *
         * @param message
         * @param ex
         */
        private void showError(String message, Exception ex) {
            if (this.onError != null) {
                //--------------------------------------------------------------
                Platform.runLater(() -> {
                    /**
                     * Run Error.
                     */
                    onError.onError(message, ex);
                    /**
                     * There is a showWait message called above after the OK
                     * button is pressed main menu will be shown.
                     *
                     * THIS METHOD MUST BE ONLY CALLED ONCE. the RAID ALGORITHM
                     * SHOULD BE CANCELED ONCE A PROCESS FAILED.
                     */

                    if (this.onCompletion != null) {
                        // SHOW MAIN STAGE WHEN OK IS CLICKED
                        this.onCompletion.run();
                    }
                });
                //--------------------------------------------------------------
            }
            logger.error("RAID Algorithm ended up with an error", ex);
//            log("\n\nRAID Algorithm: Ended up with an error at " + Context.getDateFormat12().format(new Date()));
        }

        @Override
        public final void run() {
            this.logBuilder = new StringBuilder("");
            //------------------------------------------------------------------
            Platform.runLater(() -> {
                this.lbl_current.setText("Automatic Backup Started . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
            //------------------------------------------------------------------
            // Run Manager
            log("RAID Algorithm: Started at " + Context.getDateFormat12().format(new Date()) + "\n");
            if (!this.runRaidManager()) {
                /**
                 * When the RAID manager raises a false flag this means one of
                 * the processes have failed. the below code will not be
                 * executed.
                 *
                 *
                 * NOTE: When processes raises a false flag this means the
                 * showError method was called.
                 */
                return;
            }
            log("\n\nRaid Manager: Finished with no errors at " + Context.getDateFormat12().format(new Date()));
            //------------------------------------------------------------------
            Platform.runLater(() -> {
                this.lbl_current.setText("Automatic Backup Finished");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
            //------------------------------------------------------------------
            // Pause 1 Second.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            //------------------------------------------------------------------
            // Run on competion Routine.
            if (this.onCompletion != null) {
                this.onCompletion.run();
            }
        }

        /**
         * RAID ALGORITHM.
         */
        private boolean runRaidManager() {
            //------------------------------------------------------------------
            // Fetch Remote Files
            //------------------------------------------------------------------
            if (!this.processFetchRemoteFiles()) {
                return false;
            }
            //------------------------------------------------------------------
            // Fetch Local Files
            //------------------------------------------------------------------
            if (!this.processFetchLocalFiles()) {
                return false;
            }
            //------------------------------------------------------------------
            // Compare Files
            //------------------------------------------------------------------
            if (!this.processCompareFiles()) {
                return false;
            }
            //------------------------------------------------------------------
            // Clean Up Unindexed Files.
            //------------------------------------------------------------------
            this.processCleanLocal();
            //------------------------------------------------------------------
            // Clean Up Temp Files.
            //------------------------------------------------------------------
            this.processCleanTemp();
            //------------------------------------------------------------------
            // RUN SQL Backup
            //------------------------------------------------------------------
            if (!this.processDatabaseBackup()) {
                return false;
            }
            return true;
        }

        private void processCleanTemp() {
            log("Clean Up: Cleaning Local Temporary Files . . .");

            Platform.runLater(() -> {
                this.lbl_current.setText("Cleaning temporary local files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            try {
                File parentFile = new File(Context.DIR_TEMP);

                if (!parentFile.exists()) {
                    log("Clean Up: Local Temporary Files is empty . . .");
                    return;
                }

                /**
                 * Delete Temp.
                 */
                FileTool.deleteFile(Context.DIR_TEMP);

//                Files.walkFileTree(Paths.get(parentFile.toURI()), new SimpleFileVisitor<Path>() {
//                    @Override
//                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                        Files.delete(file);
//                        return FileVisitResult.CONTINUE;
//                    }
//                    
//                    @Override
//                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                        Files.delete(dir);
//                        return FileVisitResult.CONTINUE;
//                    }
//                    
//            });
            } catch (IOException e) {
                // ignore
                log("Clean Up: Failed to clean temporary files.");
            }
        }

        /**
         * First Process in RAID Algorithm.
         */
        private boolean processFetchRemoteFiles() {
            Platform.runLater(() -> {
                this.lbl_current.setText("Fetching Remote Files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
            //
            try {
                this.fetchRemoteFileArray();
            } catch (SQLException e) {
                this.showError("There was an error while fetching the remote files.", e);
                return false;
            }
            //------------------------------------------------------------------
            log("Remote Files: " + this.remoteActiveFiles.size());
            log("Remote Files: Successfully Fetched");
            return true;
        }

        /**
         * Second Process in RAID Algorithm.
         */
        private boolean processFetchLocalFiles() {
            Platform.runLater(() -> {
                this.lbl_current.setText("Fetching Local Files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            log("Local Files: Fetching");
            try {
                this.fetchLocalFileArray();
            } catch (SecurityException e) {
                this.showError("There was an error while fetching the local files.", e);
                return false;
            }
            //----------------------------------------------------------------------
            log("Local Files: " + " File( " + this.localFiles.size() + " ) / Folders ( " + this.localFileFolders.size() + " )");
            log("Local Files: Successfully Fetched");
            return true;
        }

        /**
         * Third Process in RAID Algorithm.
         */
        private boolean processCompareFiles() {
            try {
                if (FileTool.checkFolders("raid/bin")) {
                    this.executeCheck();
                } else {
                    this.showError("Failed to create local directory.", null);
                    log("Local Files: Failed to create local directory.");
                }
            } catch (IOException ex) {
                this.showError("There was an error while creating the local directory.", ex);
                return false;
            }
            return true;
        }

        /**
         * Fourth Process in RAID Algorithm.
         */
        private void processCleanLocal() {
            Platform.runLater(() -> {
                this.lbl_current.setText("Cleaning unindexed local files . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            log("Clean Up: Checking Local Trash Files");
            if (this.localFiles == null) {
                log("Clean Up: No Trash Files");
                return;
            }

            if (this.localFiles.isEmpty()) {
                log("Clean Up: No Trash Files");
                return;
            }

            log("Clean Up: " + this.localFiles.size() + " Trash Files Found");
            log("Clean Up: Deleting Trash Files");

            Iterator<File> localFileIterator = this.localFiles.iterator();
            while (localFileIterator.hasNext()) {
                File trashFile = localFileIterator.next();
                try {
                    // attempt to delete
                    if (trashFile.delete()) {
                        // remove from list
                        localFileIterator.remove();
                    }
                } catch (Exception e) {
                    log("Clean Up: Failed to clean " + trashFile.getName());
                }

            }
            log("Clean Up: Local Files Cleaned -> " + this.localFiles.size() + " Trash Files Left");

        }

        private boolean processDatabaseBackup() {
            Platform.runLater(() -> {
                this.lbl_current.setText("Backing up database . . .");
                this.pb_current.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });

            try {
                log("SQL: Locating Jar");
                final String jarPath = MariaDB.locateJar();
                log("SQL: Jar Located -> " + jarPath);
                final String sqlDir = jarPath + File.separator + "raid" + File.separator + "sql";
                log("SQL: Checking SQL Directory -> " + sqlDir);
                if (!FileTool.checkFoldersQuietly(sqlDir)) {
                    log("SQL: Failed to create SQL Directory");
                    this.showError("Failed to create SQL Backup Directory", null);
                    return false; // skip backup
                }
                final String DB_NAME = Context.createLocalKey();
                //--------------------------------------------------------------
                final File mysql_exe = new File(jarPath + File.separator + "maria_bin/mysql.exe");
                final File mysqldump_exe = new File(jarPath + File.separator + "maria_bin/mysqldump.exe");

                if (!mysql_exe.exists()) {
                    log("SQL: maria_bin/mysql.exe not existing");
                    this.showError("Cannot execute database backup maria_bin/mysql.exe not existing", null);
                    return false;
                }

                if (!mysqldump_exe.exists()) {
                    log("SQL: maria_bin/mysqldump.exe not existing");
                    this.showError("Cannot execute database backup maria_bin/mysqldump.exe not existing", null);
                    return false;
                }

                boolean sqlBackupExecuted = MariaDB.backup(
                        Context.app().getHost(),
                        Context.app().getDatabaseUser(),
                        Context.app().getDatabasePass(),
                        Context.app().getDatabaseName(),
                        sqlDir + File.separator + DB_NAME + ".sql"
                );

                if (!sqlBackupExecuted) {
                    this.showError("Failed to execute database backup.", null);
                    return false;
                }
            } catch (IOException | URISyntaxException e) {
                this.showError("There was an error while backing up the database.", e);
                return false;
            }

            return true;
        }

        //----------------------------------------------------------------------
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

        /**
         * Silent on exception with FTP connection.
         */
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
