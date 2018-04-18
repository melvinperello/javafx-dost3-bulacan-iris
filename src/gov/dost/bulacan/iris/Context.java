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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import org.afterschoolcreatives.polaris.java.net.ip.ApacheFTPClientFactory;
import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;
import org.afterschoolcreatives.polaris.java.util.PolarisProperties;
import org.afterschoolcreatives.polaris.java.util.StringTools;

/**
 *
 * @author Jhon Melvin
 */
public class Context {

    //==========================================================================
    // Configuration Values
    //==========================================================================
    // Project Constants.
    public final static int VERSION_CODE = 0;
    public final static String VERSION_NAME = "1.0.2-prototype-system";
    public final static String PREFIX_PROVINCE_CODE = "BUL3000";
    public final static String PREFIX_RAID_CODE = "RAID3000";
    // directory constants
    public final static String DIR_LOGS = "logs";
    public final static String DIR_TEMPLATE = "template";
    public final static String DIR_TEMP = "temp";
    public final static String DIR_TEMP_SETUP_PRINTS = DIR_TEMP + File.separator + "setup_prints";
    // FILE
    public final static String FILE_TEMPLATE_SETUP_PRINT = DIR_TEMPLATE + File.separator + "setup_print_blank.pdf";

    //==========================================================================
    // Static Methods
    //==========================================================================
    public final static String createLocalKey() {
        /**
         * Generate Key.
         */
        Calendar dateKey = Calendar.getInstance();
        String generatedKey
                = Context.PREFIX_PROVINCE_CODE // BUL3000
                + String.valueOf(dateKey.get(Calendar.YEAR)) // 2018
                + "-" // -
                + new SimpleDateFormat("MMddHHmmss").format(dateKey.getTime());
        return generatedKey;
    }

    /**
     * This allows installation of Custom Objects to the Combo box and displays
     * the toString Value of the object.
     *
     * @param comboBase the combo box control.
     * @param collection A collection of object with override toString Method.
     */
    public final static void comboBoxValueFactory(ComboBox comboBase, Object[] collection) {
        comboBase.getItems().setAll(Arrays.asList(collection));
    }

    /**
     *
     * @param textField
     * @return
     */
    public final static String filterInputControl(TextInputControl textField) {
        return StringTools.clearExtraSpaces(textField.getText().trim());
    }

    public final static void applyDateToPicker(DatePicker picker, Date dateEndorsed) {
        if (dateEndorsed != null) {
            SimpleDateFormat format = Context.getDateFormat();
            LocalDate setDate = LocalDate.parse(format.format(dateEndorsed), DateTimeFormatter.ofPattern(format.toPattern()));
            picker.setValue(setDate);
        }
    }

    /**
     * Since districts are required to be represented in roman numerals.
     *
     * @param number
     * @return
     */
    public final static String integerToRomanNumber(String number) {
        switch (number) {
            case "0":
                return "Lone District";
            case "1":
                return "I";
            case "2":
                return "II";
            case "3":
                return "III";
            case "4":
                return "IV";
            default:
                return "";
        }
    }

    //==========================================================================
    // Format Control
    //==========================================================================
    /**
     * Get Project Money Format.
     *
     * @return
     */
    public static DecimalFormat getMoneyFormat() {
        return new DecimalFormat("#,##0.00");
    }

    public static DecimalFormat getDecimal2Format() {
        return new DecimalFormat("0.00");
    }

    /**
     * SAMPLE: 04-20-1997
     *
     * @return
     */
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MM-dd-yyyy");
    }

    /**
     * Get Date Format. Sample: March 08, 2018
     *
     * @return
     */
    public static SimpleDateFormat getDateFormatNamed() {
        return new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMM dd, yyyy");
    }

    /**
     * SAMPLE: MAY 26, 2018 - 04:25:17 AM (12 HR)
     *
     * @return
     */
    public static SimpleDateFormat getDateFormat12() {
        return new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMM dd, yyyy - hh:mm:ss a");
    }

    /**
     * SAMPLE: 06101997_162517 (24 HR FORMAT) COMMONLY FOR FILES.
     *
     * @return
     */
    public static SimpleDateFormat getDateFormatTimeStamp() {
        return new SimpleDateFormat("MMddyyyy_HHmmss");
    }

    //--------------------------------------------------------------------------
    // Resource Management
    //--------------------------------------------------------------------------
    public static InputStream getResourceStream(String name) {
        return Context.class.getClass().getResourceAsStream("/storage/" + name);
    }

    //--------------------------------------------------------------------------
    /**
     * Launches the native Operating System default application to open a given
     * file.
     *
     * @param file file to open.
     * @return true or false for operation result.
     */
    public static boolean desktopOpenQuietly(File file) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(file);
                    return true;
                } catch (IOException | IllegalArgumentException ex) {
                    // ignore exception.
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    //**************************************************************************
    // INSTANCE CONTROL.
    //**************************************************************************
    //
    // Manage IRIS Context Instance.
    //
    /**
     * Instance Holder.
     */
    private static volatile Context instance;

    /**
     * Override Clone Method.
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        throw new CloneNotSupportedException("Not Allowed!");
    }

    /**
     * Double Check Locking Singleton for This Project's Context Manager.
     *
     * @return project context manager.
     */
    public static Context app() {
        Context localInstance = Context.instance;
        if (localInstance == null) {
            synchronized (Context.class) {
                localInstance = Context.instance;
                if (localInstance == null) {
                    Context.instance = localInstance = new Context();
                }
            }
        }
        return localInstance;
    }

    //==========================================================================
    // Non-Static Methods (Instance Scope)
    //==========================================================================
    private String auditUser;

    public String getAuditUser() {
        return auditUser;
    }

    //--------------------------------------------------------------------------
    // Connection Management
    //--------------------------------------------------------------------------
    private Context() {
        this.started = false;
    }

    private boolean started;

    /**
     * Starts this application context.
     *
     * @throws java.lang.Exception
     */
    public void start() throws Exception {
        if (!this.started) {
            // notify using Java Swing
            this.loadSettings();
            //----------------------------------------------------------------------
            this.createConnectionFactory();
            //----------------------------------------------------------------------
            this.createFtpConnectionFactory();
            //----------------------------------------------------------------------
        }
        this.started = true;

    }

    //--------------------------------------------------------------------------
    private HikariConnectionPool connectionFactory;
    private ApacheFTPClientFactory ftpClientFactory;

    public HikariConnectionPool db() {
        return this.connectionFactory;
    }

    public ApacheFTPClientFactory ftp() {
        return this.ftpClientFactory;
    }

    public void shutdown() {
        this.connectionFactory.close();
        this.connectionFactory = null;
        this.ftpClientFactory = null;
        //
        Context.instance = null;
    }

    //--------------------------------------------------------------------------
    private String host;
    private String databaseName;
    private String databasePort;
    private String databaseUser;
    private String databasePass;
    //--------------------------------------------------------------------------
    private String ftpUser;
    private String ftpPass;
    private String ftpPort;
    //--------------------------------------------------------------------------
    private String terminalUser;
    //--------------------------------------------------------------------------

    /**
     * Host Server.
     *
     * @return
     */
    public String getHost() {
        return host;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePass() {
        return databasePass;
    }

    /**
     * Load Settings from configuration.
     */
    private void loadSettings() throws IOException {
        PolarisProperties prop = new PolarisProperties();
        try {
            File propFile = new File("config.prop");
            if (!propFile.exists()) {
                //this.createDefaultSettings();
            }
            prop.read(propFile);
            this.host = prop.getProperty("host", null);
            this.databaseName = prop.getProperty("databaseName", null);
            this.databasePort = prop.getProperty("databasePort", null);
            this.databaseUser = prop.getProperty("databaseUser", null);
            this.databasePass = prop.getProperty("databasePass", null);
            //
            this.ftpUser = prop.getProperty("ftpUser", null);
            this.ftpPass = prop.getProperty("ftpPass", null);
            this.ftpPort = prop.getProperty("ftpPort", null);
            //
            this.terminalUser = prop.getProperty("terminalUser", null);
            //
            //
            this.auditUser = this.terminalUser;

        } catch (IOException e) {
            // ignore
            this.createDefaultSettings();
            throw e;
        }
    }

    /**
     * Call this only when prop file is missing.
     *
     * @see Context#loadSettings()
     * @throws IOException
     */
    private void createDefaultSettings() throws IOException {
        PolarisProperties prop = new PolarisProperties();
        prop.setProperty("host", "127.0.0.1");
        prop.setProperty("databaseName", "iris_bulacan_dost3");
        prop.setProperty("databasePort", "3306");
        prop.setProperty("databaseUser", "iris_db");
        prop.setProperty("databasePass", "123456");
        //
        prop.setProperty("ftpUser", "iris_ftp");
        prop.setProperty("ftpPass", "123456");
        prop.setProperty("ftpPort", "21");
        //
        prop.setProperty("terminalUser", "IRIS3000/SYS");
        //
        prop.write(new File("config.prop"));

    }

    /**
     * Creates The Connection Factory.
     */
    private void createConnectionFactory() {

        this.connectionFactory = new HikariConnectionPool();
        this.connectionFactory.setConnectionDriver(ConnectionFactory.Driver.MariaDB);
        this.connectionFactory.setDatabaseName(this.databaseName);
        this.connectionFactory.setHost(this.host);
        this.connectionFactory.setPort(this.databasePort);
        this.connectionFactory.setUsername(this.databaseUser);
        this.connectionFactory.setPassword(this.databasePass);

        this.connectionFactory.start();

    }

    private void createFtpConnectionFactory() {
        ApacheFTPClientFactory ftp = new ApacheFTPClientFactory();
        ftp.setServer(this.host);
        ftp.setUsername(this.ftpUser);
        ftp.setPassword(this.ftpPass);
        int port = 21;
        try {
            port = Integer.parseInt(this.ftpPort);
        } catch (NumberFormatException e) {
            port = 21;
        }

        ftp.setPort(port);
        this.ftpClientFactory = ftp;
    }

    /**
     * Fetch date from the server.
     *
     * @return
     */
    public Date getServerDate() {
        return new Date();
    }

    /**
     * Fetch Local Machine current date.
     *
     * @return
     */
    public Date getLocalDate() {
        return new Date();
    }

}
