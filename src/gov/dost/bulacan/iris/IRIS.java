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

import gov.dost.bulacan.iris.ui.Home;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;

/**
 *
 * @author Jhon Melvin
 */
public class IRIS extends Application {

    /**
     * Actual Start Method.
     *
     * @param primaryStage
     */
    private void show(Stage primaryStage) {
        primaryStage.setScene(new Scene(new Home().load()));
        primaryStage.getIcons().add(new Image(Context.app().getResourceStream("drawable/dost_logo.png")));
        primaryStage.setTitle("PSTC-Bulacan/DOST3 Information Retrieval Integrated System ( I.R.I.S. )");
        primaryStage.setMinHeight(700.0);
        primaryStage.setMinWidth(1300.0);
        //

        // on close
        primaryStage.setOnCloseRequest(close -> {
            IRIS.onCloseConfirmation(primaryStage);
            close.consume();
        });
        //
        primaryStage.show();
    }

    /**
     * Application Invocation.
     *
     * @param primaryStage
     */
    @Override
    public final void start(Stage primaryStage) {
        try {
            this.show(primaryStage);
        } catch (Exception e) {
            /**
             * Catch Start Up Exception.
             */
            e.printStackTrace();
        }
    }

    /**
     * Application Start.
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
//        HostFinder finder = new HostFinder();
//        finder.setHostName("dost-3-pc");
//        try {
//            finder.find();
//        } catch (Exception e) {
//        }
//
//        finder.getIpv4List().forEach(action -> {
//            String addr = action.getInetAddress().getHostAddress();
//            System.out.println(addr);
//        });
    }

    public static void onCloseConfirmation(Stage owner) {
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("Exit")
                .setOwner(owner)
                .setHeaderText("Close Application ?")
                .setContentText("Are you sure you want to close the application ?")
                .showAndWait();
        if (res.get().getText().equals("OK")) {
            Platform.exit(); // exit java fx
        }
    }

}
