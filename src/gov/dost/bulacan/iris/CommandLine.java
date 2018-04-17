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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jhon Melvin
 */
public class CommandLine {

    /**
     * Runs a Windows Command Line Command.
     *
     * @param cli sing line command.
     * @return ArrayList of results.
     */
    public static ArrayList<String> run(String cli) {
        // ResultString Holder
        ArrayList<String> cmdOutput = new ArrayList<>();
        // Creates the process
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cli);
        // redirect the error to standard input stream
        processBuilder.redirectErrorStream(true);
        // start the process
        Process cmdProcess;
        try {
            cmdProcess = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));

            reader.lines().forEach(resLine -> {
                cmdOutput.add(resLine);
            });

            cmdOutput.add("Exit Code: " + cmdProcess.exitValue());
        } catch (IOException ex) {
            // no results
        }
        return cmdOutput;
    }

    public static String multipleCommand(ArrayList<String> commands) {
        Iterator<String> commandIterate = commands.iterator();
        String command = "";
        while (commandIterate.hasNext()) {
            String currentLine = commandIterate.next();
            command += currentLine;

            if (commandIterate.hasNext()) {
                command += " & ";
            }
        }
        return command;
    }

    public static void startCLI(String command) {

        try {
            Runtime.getRuntime().exec("cmd /c start " + command);

        } catch (IOException ex) {

        }

    }

}
