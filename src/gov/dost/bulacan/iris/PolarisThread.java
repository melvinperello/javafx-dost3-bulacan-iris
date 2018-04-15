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
package gov.dost.bulacan.iris;

/**
 *
 * @author Jhon Melvin
 */
public class PolarisThread extends Thread {

    public PolarisThread() {
        this.started = false;
        this.onStart = null;
        this.onRunning = null;
        this.onException = null;
        this.onCompletion = null;
        this.caughtException = null;
    }
    private volatile boolean started;
    private Runnable onStart;
    private Runnable onRunning;
    private Runnable onException;
    private Runnable onCompletion;
    //
    private Exception caughtException;

    @Override
    public final void run() {
        try {
            //------------------------------------------------------------------
            this.started = true; // mark flag as started.
            if (this.onStart != null) {
                this.onStart.run();
            }
            //------------------------------------------------------------------
            if (this.onRunning != null) {
                this.onRunning.run();
            }
            //------------------------------------------------------------------
            // on complete routine
            if (this.onCompletion != null) {
                this.onCompletion.run();
            }
            //------------------------------------------------------------------
        } catch (RuntimeException re) {
            // Unchecked Exceptions
            if (this.onException != null) {
                this.caughtException = re;
                this.onException.run();
            }
        } catch (Exception ex) {
            // Checked Exceptions
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            if (this.onException != null) {
                this.caughtException = ex;
                this.onException.run();
            }
        }
    }

    @Override
    public synchronized void start() {
        if (started) {
            /**
             * It is never legal to start a thread more than once. In
             * particular, a thread may not be restarted once it has completed
             * execution.
             */
            return; // No-Op.
        }
        super.start();
    }

    public boolean isStarted() {
        return started;
    }

    public Exception getCaughtException() {
        return caughtException;
    }

    public PolarisThread setOnStart(Runnable onStart) {
        this.onStart = onStart;
        return this;
    }

    public PolarisThread setOnRunning(Runnable onRunning) {
        this.onRunning = onRunning;
        return this;
    }

    public PolarisThread setOnException(Runnable onException) {
        this.onException = onException;
        return this;
    }

    public PolarisThread setOnCompletion(Runnable onCompletion) {
        this.onCompletion = onCompletion;
        return this;
    }

}
