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

import gov.dost.bulacan.iris.RaidContext;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 *
 * @author s500
 */
public class HashThread extends Thread {

    /**
     * Local file to perform hashing.
     */
    private File localFile;

    /**
     * When hashing was completed.
     */
    private Runnable onCompletion;

    /**
     * Encountered an exception.
     */
    private Runnable onException;

    //==========================================================================
    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    public void setOnCompletion(Runnable onCompletion) {
        this.onCompletion = onCompletion;
    }
    //==========================================================================
    /**
     * Caught Exception.
     */
    private Exception caughtException;

    /**
     * Hash Result.
     */
    private String fileHashValue;

    /**
     * Returns the encountered exception during hashing.
     *
     * @return
     */
    public Exception getCaughtException() {
        return caughtException;
    }

    public String getFileHashValue() {
        return fileHashValue;
    }

    //----------------------------------------------------------------------
    /**
     * Is This thread successfully run.
     */
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void run() {
        //----------------------------------------------------------------------
        this.success = false;
        this.fileHashValue = "";
        //----------------------------------------------------------------------
        try {
            this.fileHashValue = RaidContext.getFileHash(this.localFile);
            this.fileHashValue = this.fileHashValue.toUpperCase(Locale.ENGLISH);
            this.success = true;
        } catch (IOException | NoSuchAlgorithmException ex) {
            this.fileHashValue = "";
            this.success = false;
            //------------------------------------------------------------------
            if (this.onException != null) {
                this.caughtException = ex;
                this.onException.run();
            }
            //------------------------------------------------------------------
        }
        //======================================================================
        if (onCompletion != null) {
            this.onCompletion.run();
        }
    }

}
