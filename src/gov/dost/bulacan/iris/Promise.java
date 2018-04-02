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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Jhon Melvin
 */
public class Promise {

    public static Promise make() {
        return new Promise();
    }

    public Promise then() {
        return this;
    }

    public Promise fulfilled() {
        return this;
    }

    public Promise broken() {
        return this;
    }

    public Promise done() {
        return this;
    }

    public Promise until() {
        return this;
    }

    /**
     * A pending promise can only ever lead to either a fulfilled state or a
     * rejected state once and only once, which can avoid some pretty complex
     * error scenarios. This means that we can only ever return a promise once.
     * If we want to rerun a function that uses promises, we need to create a
     * new one.
     *
     * @return
     */
    public Promise pending() {
        return this;
    }

    public Promise rejected() {
        return this;
    }

    public void swear() {

    }

    public static void main(String[] args) {
        //----------------------------------------------------------------------
        // Promise Cretion sample.
        Promise.make()
                .then() // action 1 
                .then() // will wait for action 1 before doing this
                .then()
                .until() // do this until a condition is met
                .then()
                .pending() // while the promise is running callback or updates 
                .fulfilled() // when all the promises are completed
                .rejected() // when promise encounters an error
                .broken() // when break was called (cancel)
                .done() // promise is completed fullfilled or broken
                .swear(); // start running this promise
        //----------------------------------------------------------------------
        // non blocking
        ExecutorService service = Executors.newCachedThreadPool();
        for (int xx = 0; xx < 1000; xx++) {
            Future future = service.submit(() -> {
                long y = 0;
                for (long x = -999999999; x < 999999999; x++) {
                    y = x;
                }
                System.out.println(y);
            });
            System.out.println("hi");
        }
        //----------------------------------------------------------------------
        // future to get the value of a service once it is completed
//        try {
//            future.get();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Promise.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Promise.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("hello");

    }
}
