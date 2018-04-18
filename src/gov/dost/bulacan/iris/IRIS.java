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
import gov.dost.bulacan.iris.ui.Splash;
import gov.dost.bulacan.iris.ui.raid.Raid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.afterschoolcreatives.polaris.java.io.FileTool;
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
        final Thread initThread = new Thread(() -> {
            //------------------------------------------------------------------
            // show splash
            Platform.runLater(() -> {
                this.showSplash();
            });
            //------------------------------------------------------------------
            // wait 1 second
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            //------------------------------------------------------------------
            // start context
            try {
                Context.app().start();
                //--------------------------------------------------------------
                // close splash show main
                Platform.runLater(() -> {
                    this.closeSplash();
                    this.showRaid(primaryStage);
                });
            } catch (Exception e) {
                //--------------------------------------------------------------
                IRIS.telemetry(e, null); // no scr telemetry
                //--------------------------------------------------------------
                Platform.runLater(() -> {
                    try {
                        PolarisDialog.exceptionDialog(e)
                                .setHeaderText("Start Up Error !")
                                .setContentText(e.getMessage())
                                .setTitle("Initialization Error")
                                .showAndWait();
                    } finally {
                        System.exit(-1);
                    }

                });

            }

            //------------------------------------------------------------------
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                final Exception ex = new Exception("Uncaught Exception", e);
                ex.printStackTrace();
                IRIS.telemetry(ex, primaryStage);
                //--------------------------------------------------------------
                Platform.runLater(() -> {
                    PolarisDialog.exceptionDialog(ex)
                            .setHeaderText("Fatal Exception")
                            .setContentText("There was an unknown error that has been encountered by the system.")
                            .setTitle("System Error")
                            .showAndWait();
                    System.exit(-2);
                });
            });
            //------------------------------------------------------------------
        });
        initThread.start();
    }

    /**
     * As much as possible create a single method that will call exception
     * message so that the telemetry can be easily integrated.
     *
     * @param ex
     * @param stage
     */
    public static void telemetry(Exception ex, Stage stage) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_aa");
        final SimpleDateFormat prettyFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        final Date logDate = new Date();
        final String logText = df.format(logDate);

        //----------------------------------------------------------------------
        if (FileTool.checkFoldersQuietly("telemetry")) {
            //------------------------------------------------------------------
            // WRITE LOG FILE
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String err_log = sw.toString();
                //------------------------------------------------------
                PolarisText text = new PolarisText();
                text.writeln("Afterschool Creatives Telemetry version 1.0");
                text.writeln("Date Captured: " + prettyFormat.format(logDate));
                text.writeln("Operating System: " + System.getProperty("os.name", "No Data"));
                text.writeln("OS Version: " + System.getProperty("os.version", "No Data"));
                text.writeln("Architecture: " + System.getProperty("os.arch", "No Data"));
                text.writeln("");
                text.writeln("Exception Stack Trace Details:");
                text.writeln("");
                text.write(err_log);
                text.save(new File("telemetry/EXCEPTION" + logText + ".txt"));
            } catch (Exception logEx) {
                // ignore
            }
            //------------------------------------------------------------------
            // WRITE SCREENSHOT
            File screenshot = new File("telemetry/EXCEPTION" + logText + ".png");
            if (stage == null) {
                return;
            }
            //------------------------------------------------------------------
            if (stage.getScene() != null) {
                Scene scene = stage.getScene();
                WritableImage writableImage
                        = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
                scene.snapshot(writableImage);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", screenshot);
                } catch (Exception iox) {
                    // ignore
                }
            }
            //------------------------------------------------------------------
        }
    }

    //--------------------------------------------------------------------------
    private Stage splashStage;

    private void showSplash() {
        this.splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.initModality(Modality.APPLICATION_MODAL);
        splashStage.setWidth(600.0);
        splashStage.setHeight(600.0);
        splashStage.setResizable(false);
        Scene splashScene = new Scene(new Splash().load());
        splashScene.setFill(Color.TRANSPARENT);
        splashStage.setScene(splashScene);
        // cannot be this is calling Context and disrupts normal start up
        splashStage.getIcons().add(new Image(Context.getResourceStream("drawable/iris_dost_logo.png")));
        splashStage.centerOnScreen();
        splashStage.initStyle(StageStyle.TRANSPARENT);
        this.splashStage.showAndWait();
    }

    /**
     * Calls after the splash screen. open the main stage after the raid check
     * up.
     *
     * @param primaryStage
     */
    private void showRaid(Stage primaryStage) {
        Raid.call(() -> {
            this.showMain(primaryStage);
        });
    }

    private void closeSplash() {
        if (this.splashStage != null) {
            this.splashStage.close();
        }
    }
    //--------------------------------------------------------------------------

    /**
     * Show Main Application with primary stage.
     *
     * @param primaryStage
     */
    private void showMain(Stage primaryStage) {
        primaryStage.setScene(new Scene(new Home().load()));
        primaryStage.getIcons().add(new Image(Context.getResourceStream("drawable/iris_dost_logo.png")));
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
            System.err.println("-------------------------------");
            System.err.println("| MAIN CALL START             |");
            System.err.println("-------------------------------");
            e.printStackTrace();
        }
    }

    /**
     * Application Start.
     *
     * @param args
     */
    public static void main(String... args) {
        Application.launch(args);
    }

    public static void onCloseConfirmation(Stage owner) {
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle("Exit")
                .setOwner(owner)
                .setHeaderText("Close Application ?")
                .setContentText("Are you sure you want to close the application ?")
                .showAndWait();
        if (res.get().getText().equals("OK")) {
            if (Context.app() != null) {
                Context.app().shutdown();
            }
            Platform.exit(); // exit java fx
        }
    }

}
