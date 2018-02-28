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

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;

/**
 *
 * @author Jhon Melvin
 */
public class Context {

    public final static String PROJECT_CODE_PREFIX = "STC3000";

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
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setConnectionDriver(ConnectionFactory.Driver.MariaDB);
        this.connectionFactory.setDatabaseName("iris_bulacan_dost3");
        this.connectionFactory.setHost("localhost");
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
        return new DecimalFormat("#,###.00");
    }

    /**
     * Get Project Format.
     *
     * @return
     */
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MM-dd-yyyy");
    }

}
