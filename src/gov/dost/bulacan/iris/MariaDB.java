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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author DOST
 */
public class MariaDB {

    /**
     * Runs a Windows Command Line Command.
     *
     * @param command sing line command.
     * @param outStream contains the reply of the command prompt.
     * @return exit value
     * @throws java.io.IOException
     */
    public static int run(String command, StringBuilder outStream) throws IOException {
        // ResultString Holder 
        final StringBuilder stringFeed = new StringBuilder("");
        // Creates the process
        final ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        // redirect the error to standard input stream
        processBuilder.redirectErrorStream(true);
        // start the process
        Process cmdProcess = processBuilder.start();
        // create a reader for the process
        BufferedReader reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
        //----------------------------------------------------------------------
        // feed the stream to the string feed.
        reader.lines().forEach(resLine -> {
            stringFeed.append(resLine);
            stringFeed.append("\n");
        });
        //----------------------------------------------------------------------
        // delete last character if blank space.
        if (stringFeed.length() != 0) {
            char lastChar = stringFeed.charAt(stringFeed.length() - 1);
            if (lastChar == '\n') {
                stringFeed.deleteCharAt(stringFeed.length() - 1);
            }
        }
        //----------------------------------------------------------------------
        // pass string feed to outstream
        outStream = stringFeed;
        // return exit value
        return cmdProcess.exitValue();
    }

    public static String createMultipleCommands(String... commands) {
        Iterator<String> commandIterate = Arrays.asList(commands).iterator();
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

    public static String locateJar() throws URISyntaxException {
        CodeSource codeSource = MariaDB.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        return jarDir;
    }

    public static boolean backup(
            String host,
            String user,
            String password,
            String database,
            String savePath
    ) throws URISyntaxException, IOException {

        String jarDir = MariaDB.locateJar();
        final String[] commandArray = {
            "pushd " + jarDir,
            "cd " + "maria_bin",
            "mysqldump.exe --host=" + host + " --user=" + user + " --password=" + password + " --add-drop-database  --databases " + database + " > \"" + savePath + "\""

        };

        final String a = MariaDB.createMultipleCommands(commandArray);
        StringBuilder outputStream = null;
        if (MariaDB.run(a, outputStream) == 0) {
            return true;
        }

        if (outputStream != null) {
            System.out.println("RES");
            System.out.println(outputStream.toString());
        }

        return false;
    }

}
