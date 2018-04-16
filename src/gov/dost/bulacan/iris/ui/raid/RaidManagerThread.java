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
import gov.dost.bulacan.iris.RaidContext;
import gov.dost.bulacan.iris.models.RaidModel;
import static gov.dost.bulacan.iris.ui.raid.RaidDownload.DIR_BIN;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientManager;
import org.apache.commons.net.ftp.FTPFile;

// 1. Get Files From Server
// 2. Get All Files from Local raid/bin
// 3. Compare then delete local files which are not listed
// 4. Check Local Files with Remote Files
// 5. If Not Existing Download The File.
// 6. If Existing Check File Signature
public final class RaidManagerThread extends Thread {

    public RaidManagerThread() {
        this.remoteActiveFiles = null;
        this.localFiles = null;
        this.localFileFolders = null;
    }

    private List<RaidModel> remoteActiveFiles;
    private List<File> localFiles;
    private List<File> localFileFolders;

    @Override
    public final void run() {

    }

    private boolean fetchRemoteFileArray() throws SQLException {
        this.remoteActiveFiles = RaidModel.listActiveRaidArray();

        if (this.remoteActiveFiles == null) {
            return false;
        }

        if (this.remoteActiveFiles.isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean fetchLocalFileArray() throws SecurityException {
        this.localFiles = new ArrayList<>();
        this.localFileFolders = new ArrayList<>();
        //
        final File folder = new File("raid/bin");
        final File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length == 0) {
            return false;
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
        return true;
    }

    private void executeCheck() {
        for (RaidModel remoteFile : this.remoteActiveFiles) {
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
                    localCopyExisting = true;
                    this.localFiles.remove(ctr); // remove this file to entry
                    tempLocFileList = null; // clear the temporary list
                    break;
                }
                //----------------------------------------------------------
            }
            //--------------------------------------------------------------
            if (localCopyExisting) {
                // RUN HASH VERIFICATION
                // IF HASH FAILED DOWNLOAD AGAIN
            } else {
                // DOWNLOAD THEN RUN HASH VERIFICATION
            }

        } // end remote comparison loop

    }

    private void downloadRemoteFile(RaidModel raid) throws IOException {
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
                final String progressText = transWhole + "% ( "
                        + RaidContext.byteStringFormat().format(totalBytesTransferred)
                        + " bytes / "
                        + RaidContext.byteStringFormat().format(transSize) + " bytes )";
                //
                // Do something with this text
                //
                //--------------------------------------------------------------
            }); // end listener
            //------------------------------------------------------------------
            FTPFile[] listFile = ftp.getFtpClient().listFiles("bin/" + fileName);
            if (listFile.length == 0) {
                //
                // DO SOMETHING IN THIS FILE ERROR
            }
            //------------------------------------------------------------------
            // FILE DOWNLOAD
            boolean downloaded = ftp.downloadStream("bin/" + fileName, "raid/bin/" + fileName);

        } // end try
    }

} // end raid manager.
