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

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

/**
 *
 * @author DOST
 */
public class MariaBackupRestore {

    /**
     * Get the Jar absolute location
     *
     * @return
     * @throws URISyntaxException
     */
    private static String getJarPath() throws URISyntaxException {
        CodeSource codeSource = MariaBackupRestore.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        System.out.println("JAR LOCATION: " + jarDir);
        return jarDir;
    }

    /**
     * Backup the current data.
     *
     * @param host the database server can be local host or remote host.
     * @param user
     * @param password
     * @param database database name
     * @param savePath where to save the file
     * @return 0 for success 1 for invalid path 2 for SQL error.
     */
    public static int backup(
            String host,
            String user,
            String password,
            String database,
            String savePath
    ) {
        try {
            String jarDir = MariaBackupRestore.getJarPath();

            ArrayList<String> commands = new ArrayList<>();
            commands.add("pushd " + jarDir);
            commands.add("cd " + "maria_bin");
            commands.add("mysqldump.exe --host=" + host + " --user=" + user + " --password=" + password + " --add-drop-database  --databases " + database + " > \"" + savePath + "\"");

            String a = CommandLine.multipleCommand(commands);
            ArrayList<String> result = CommandLine.run(a);
            if (result.size() == 1) {
                if (result.get(0).equalsIgnoreCase("Exit Code: 0")) {
                    return 0; // success
                }
            }
        } catch (URISyntaxException e) {
            return 1; // uri syntax
        }
        return 2; // others
    }

    /**
     *
     * @param host Host on where to restore the data.
     * @param user
     * @param pass
     * @param backUpLocation location of the backup file.
     * @return
     */
    public static int restore(
            String host,
            String user,
            String pass,
            String backUpLocation
    ) {
        try {
            String jarDir = MariaBackupRestore.getJarPath();

            ArrayList<String> commands = new ArrayList<>();
            commands.add("pushd " + jarDir);
            commands.add("cd " + "maria_bin");
            commands.add("mysql.exe --host " + host + " --user=" + user + " --password=" + pass + " < \"" + backUpLocation + "\"");

            String a = CommandLine.multipleCommand(commands);
            ArrayList<String> result = CommandLine.run(a);
            if (result.size() == 1) {
                if (result.get(0).equalsIgnoreCase("Exit Code: 0")) {
                    return 0; // success
                }
            }
        } catch (URISyntaxException e) {
            return 1; // uri syntax
        }
        return 2; // others
    }
}
