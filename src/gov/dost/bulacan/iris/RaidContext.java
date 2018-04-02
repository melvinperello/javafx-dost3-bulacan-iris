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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Jhon Melvin
 */
public class RaidContext {

    public final static long MAX_SIZE = 52428800;
    public final static String RAID_INFO = "RAID System (version 0.00)";
    public final static String BIN_DIR = "bin";

    public final static String createRaidKey() {
        /**
         * Generate Key.
         */
        Calendar dateKey = Calendar.getInstance();
        String generatedKey
                = Context.getRaidPrefix()// BUL3000
                + String.valueOf(dateKey.get(Calendar.YEAR)) // 2018
                + "_" // -
                + new SimpleDateFormat("MMddHHmmss").format(dateKey.getTime());
        return generatedKey;
    }

    public static DecimalFormat byteStringFormat() {
        return new DecimalFormat("#,##0");
    }

    /**
     * Checks if the file is a regular file.
     *
     * @param file
     * @return
     */
    public static boolean isFile(File file) {
        return file.isFile();
    }

    public static String getFileExtension(String path) {
        return com.google.common.io.Files.getFileExtension(path);
    }

    public static String getFileName(String path) {
        String name = com.google.common.io.Files.getNameWithoutExtension(path);
        if (name.length() > 250) {
            return name.substring(0, 249);
        }
        return name;
    }

    /**
     * Get the file size in bytes. an IOException or Null file will return a
     * size of 0
     *
     * @param file
     * @return long value in bytes.
     */
    public static long getFileSize(File file) throws IOException {
        if (file == null) {
            throw new IOException("Null File");
        }
        return Files.size(file.toPath());
    }

    public static String getStringFileSize(long bytes) {
        boolean si = true;
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Get SHA1 Digital Signature.
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileHash(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return byteToString(hashFile(fis));
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * Get SHA1 Digital Signature from a open input stream.
     *
     * @param fis
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileHash(FileInputStream fis) throws IOException, NoSuchAlgorithmException {
        return byteToString(hashFile(fis));
    }

    //==========================================================================
    // File Hashing
    //==========================================================================
    public final static String HASH_ALGORITHM = "SHA1";

    private static byte[] hashFile(FileInputStream fis) throws IOException, NoSuchAlgorithmException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(HASH_ALGORITHM);
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        return complete.digest();
    }

    /**
     * Converts a byte array to HEX String.
     *
     * @param digest
     * @return
     */
    private static String byteToString(byte[] digest) {
        byte[] b = digest;
        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
