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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import org.afterschoolcreatives.polaris.java.net.ip.HostFinder;
import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;

/**
 *
 * @author Jhon Melvin
 */
public class Context {

    //--------------------------------------------------------------------------
    // Project Constants.
    private final static int VERSION_CODE = 0;
    private final static String VERSION_NAME = "v.1.0 Prototype";
    private final static String PROJECT_CODE_PREFIX = "3000";
    // directory consta
    private final static String DIR_TEMPLATE = "template";
    private final static String DIR_TEMP = "temp";
    //--------------------------------------------------------------------------

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

    public static String getDirectoryTempSetupPrints() {
        return DIR_TEMP + File.separator + "setup_prints";
    }

    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
    // Static Methods.
    //--------------------------------------------------------------------------
    /**
     * This allows installation of Custom Objects to the Combo box and displays
     * the toString Value of the object.
     *
     * @param comboBase the combo box control.
     * @param collection A collection of object with override toString Method.
     */
    public static void comboBoxValueFactory(ComboBox comboBase, Object[] collection) {
        comboBase.getItems().setAll(collection);
        comboBase.setCellFactory((Object param) -> {
            final ListCell cell = new ListCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.toString());
                    } else {
                        setText(null);
                    }
                }
            };
            return cell;
        });
        comboBase.setButtonCell((ListCell) comboBase.getCellFactory().call(null));
    } // end of Combo Box Value Factory.

    //--------------------------------------------------------------------------
    // Instance Scope.
    //--------------------------------------------------------------------------
    private ConnectionFactory connectionFactory;

    /**
     * Initialize Object.
     */
    private Context() {
        this.createConnectionFactory();
    }

    /**
     * Creates The Connection Factory.
     */
    private void createConnectionFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setConnectionDriver(ConnectionFactory.Driver.MariaDB);
        this.connectionFactory.setDatabaseName("iris_bulacan_dost3");
        this.connectionFactory.setHost("127.0.0.1");
        this.connectionFactory.setPort("3306");
        this.connectionFactory.setUsername("dost3bulacan");
        this.connectionFactory.setPassword("123456");
    }

    public ConnectionFactory db() {
        return this.connectionFactory;
    }

    public InputStream getResourceStream(String name) {
        return this.getClass().getResourceAsStream("/storage/" + name);
    }

    /**
     * Get Project Money Format.
     *
     * @return
     */
    public DecimalFormat getMoneyFormat() {
        return new DecimalFormat("#,##0.00");
    }

    /**
     * Get Project Date Format.
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

    public SimpleDateFormat getDateFormat12() {
        return new SimpleDateFormat("MMMMMMMMMMMMMMMMMMMM dd, yyyy - hh:mm:ss a");
    }

    /**
     * Get Project Timestamp format for naming files or other things.
     *
     * @return
     */
    public SimpleDateFormat getDateFormatTimeStamp() {
        return new SimpleDateFormat("MMddyyyy_HHmmss");
    }

    public String generateTimestampKey() {
        /**
         * Generate Key.
         */
        Calendar dateKey = Calendar.getInstance();
        String generatedKey = Context.getProvinceCodePrefix()
                + String.valueOf(dateKey.get(Calendar.YEAR))
                + "-"
                + new SimpleDateFormat("MMddHHmmss").format(dateKey.getTime());
        return generatedKey;
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

    /**
     * Launches the native Operating System default application to open a given
     * file.
     *
     * @param file file to open.
     * @return true or false for operation result.
     */
    public boolean desktopOpenQuietly(File file) {
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

    public String intToRoman(String number) {
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

}
