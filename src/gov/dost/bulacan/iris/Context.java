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
import java.io.FileNotFoundException;
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
import javax.swing.JOptionPane;
import org.afterschoolcreatives.polaris.java.io.FileTool;
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
    private final static int VERSION_CODE = 0;
    private final static String VERSION_NAME = "v.1.0 Prototype";
    private final static String PROJECT_CODE_PREFIX = "BUL3000";
    private final static String RAID_CODE_PREFIX = "RAID3000";
    // directory consta
    private final static String DIR_TEMPLATE = "template";
    private final static String DIR_TEMP = "temp";
    //

    //==========================================================================
    // Static Accessors
    //==========================================================================
    public static int getVersionCode() {
        return Context.VERSION_CODE;
    }

    public static String getVersionName() {
        return Context.VERSION_NAME;
    }

    public static String getProvinceCodePrefix() {
        return PROJECT_CODE_PREFIX;
    }

    public static String getDirectoryTemplate() {
        return DIR_TEMPLATE;
    }

    public static String getDirectoryTemp() {
        return DIR_TEMP;
    }

    public static String getTemplateSetupPrint() {
        return DIR_TEMPLATE + File.separator + "setup_print_blank.pdf";
    }

    /**
     * Temporary directory for printed setup inside temp folder.
     *
     * @return
     */
    public static String getDirectoryTempSetupPrints() {
        return getDirectoryTemp() + File.separator + "setup_prints";
    }

    public static String getRaidPrefix() {
        return RAID_CODE_PREFIX;
    }

    //==========================================================================
    // Static Methods
    //==========================================================================
    public final static String createLocalKey() {
        /**
         * Generate Key.
         */
        Calendar dateKey = Calendar.getInstance();
        String generatedKey
                = Context.getProvinceCodePrefix() // BUL3000
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
            SimpleDateFormat format = Context.app().getDateFormat();
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

    //==========================================================================
    // DOUBLE CHECK Singleton Synchronization
    //==========================================================================
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
    //--------------------------------------------------------------------------
    // Runtime
    //--------------------------------------------------------------------------
    private String auditUser;

    public String getAuditUser() {
        return auditUser;
    }

//    public void setAuditUser(String auditUser) {
//        this.auditUser = auditUser;
//    }
    //--------------------------------------------------------------------------
    // Connection Management
    //--------------------------------------------------------------------------
    private HikariConnectionPool connectionFactory;
    private ApacheFTPClientFactory ftpClientFactory;

    private Context() {
        this.loadSettings();
        //----------------------------------------------------------------------
        this.createConnectionFactory();
        this.createFtpConnectionFactory();

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

    private void loadSettings() {
        PolarisProperties prop = new PolarisProperties();
        try {
            File propFile = new File("config.prop");
            if (!propFile.exists()) {
                //this.createDefaultSettings();
            }
            prop.read(propFile);
            this.host = prop.get("host");
            this.databaseName = prop.get("databaseName");
            this.databasePort = prop.get("databasePort");
            this.databaseUser = prop.get("databaseUser");
            this.databasePass = prop.get("databasePass");
            //
            this.ftpUser = prop.get("ftpUser");
            this.ftpPass = prop.get("ftpPass");
            this.ftpPort = prop.get("ftpPort");
            //
            this.terminalUser = prop.get("terminalUser");
            //
            this.auditUser = this.terminalUser;

        } catch (IOException e) {
            // ignore
            JOptionPane.showMessageDialog(null, "Failed to load configuration file. Please check config.prop![" + e.getMessage() + "]", "Configuration Error", JOptionPane.ERROR_MESSAGE);
            this.createDefaultSettings();
            System.exit(-1);

        }
    }

    private void createDefaultSettings() {
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

        try {
            prop.write(new File("config.prop"));
        } catch (IOException e) {
            // ignore
            JOptionPane.showMessageDialog(null, "Failed to write configuration file. Please check config.prop![" + e.getMessage() + "]", "Configuration Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
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

    public HikariConnectionPool db() {
        return this.connectionFactory;
    }

    public ApacheFTPClientFactory ftp() {
        return this.ftpClientFactory;
    }

    //--------------------------------------------------------------------------
    // Resource Management
    //--------------------------------------------------------------------------
    public InputStream getResourceStream(String name) {
        return this.getClass().getResourceAsStream("/storage/" + name);
    }

    //==========================================================================
    // Format Control
    //==========================================================================
    /**
     * Get Project Money Format.
     *
     * @return
     */
    public DecimalFormat getMoneyFormat() {
        return new DecimalFormat("#,##0.00");
    }

    public DecimalFormat getDecimal2Format() {
        return new DecimalFormat("0.00");
    }

    /**
     * SAMPLE: 04-20-1997
     *
     * @return
     */
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MM-dd-yyyy");
    }

    /**
     * Get Date Format. Sample: March 08, 2018
     *
     * @return
     */
    public SimpleDateFormat getDateFormatNamed() {
        return new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMM dd, yyyy");
    }

    /**
     * SAMPLE: MAY 26, 2018 - 04:25:17 AM (12 HR)
     *
     * @return
     */
    public SimpleDateFormat getDateFormat12() {
        return new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMM dd, yyyy - hh:mm:ss a");
    }

    /**
     * SAMPLE: 06101997_162517 (24 HR FORMAT)
     *
     * @return
     */
    public SimpleDateFormat getDateFormatTimeStamp() {
        return new SimpleDateFormat("MMddyyyy_HHmmss");
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
