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
import gov.dost.bulacan.iris.ui.SplashScreen;
import gov.dost.bulacan.iris.ui.raid.Raid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.util.PolarisProperties;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jhon Melvin
 */
public class IRIS extends Application {

    private final static long SPLASH_DELAY = 2000;

    private static final Logger logger = LoggerFactory.getLogger(IRIS.class);

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
                Thread.sleep(SPLASH_DELAY);
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
                logger.error("Uncaught Exception", e);
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
        Scene splashScene = new Scene(new SplashScreen().load());
        splashScene.setFill(Color.TRANSPARENT);
        splashStage.setScene(splashScene);
//        splashStage.setWidth(600.0);
//        splashStage.setHeight(600.0);
        splashStage.setResizable(false);
        // cannot be this is calling Context and disrupts normal start up
        splashStage.getIcons().add(new Image(Context.getResourceStream("drawable/iris_dost_logo.png")));
        splashStage.centerOnScreen();
        splashStage.initStyle(StageStyle.TRANSPARENT);
        this.splashStage.showAndWait();
    }

    private boolean checkIfRaiding() {
        final String defaultMinuteInterval = Context.RAID_DEFAULT_INTERVAL; // 15 minutes

        final PolarisProperties configProp = new PolarisProperties();
        final Date nowDate = new Date();
        final File configFile = new File("config.prop");
        final String raidLastKey = "raidLast";
        final String raidIntervalKey = "raidIntervalMinutes";

        String raidInterval = null;
        String raidLast = null;

        try {
            configProp.read(configFile);
            raidInterval = configProp.getProperty(raidIntervalKey, null);
            raidLast = configProp.getProperty(raidLastKey, null);
            //------------------------------------------------------------------

            if (raidInterval != null) {
                try {
                    long val = Long.parseLong(raidInterval);
                    if (val < 0) {
                        throw new NumberFormatException("Negative");
                    }
                } catch (NumberFormatException e) {
                    configProp.put(raidIntervalKey, defaultMinuteInterval);
                    logger.warn("Scheduler: interval = INVALID");
                }
            } else {
                logger.warn("Scheduler: interval = NULL");
                configProp.put(raidIntervalKey, defaultMinuteInterval);
            }
            //------------------------------------------------------------------
            if (raidLast != null) {
                try {
                    long val = Long.parseLong(raidLast);
                    if (val < 0) {
                        throw new NumberFormatException("Negative");
                    }
                } catch (NumberFormatException e) {
                    logger.warn("Scheduler: lastRaid = INVALID");
                    configProp.put(raidLastKey, String.valueOf(new Date().getTime()));

                }
            } else {
                logger.warn("Scheduler: lastRaid = NULL");
                configProp.put(raidLastKey, String.valueOf(new Date().getTime()));
            }

            configProp.write(configFile);

        } catch (IOException e) {
            // Unable to read DON't Run Raid..
            logger.error("Scheduler: Skipping ... cannot read properties", e);
            return false;
        }

        //----------------------------------------------------------------------
        try {
            configProp.read(configFile);
            raidInterval = configProp.getProperty(raidIntervalKey, null);
            raidLast = configProp.getProperty(raidLastKey, null);
        } catch (IOException e) {
            logger.error("Scheduler: Skipping ... cannot recheck properties", e);
            return false;
        }

        //----------------------------------------------------------------------
        long raidIntevalLong = Long.parseLong(raidInterval);
        long raidLastLong = Long.parseLong(raidLast);

        if (raidIntevalLong == 0) {
            logger.debug("Scheduler: 0 Interval Skip key Found");
            return false;
        }
        //----------------------------------------------------------------------
        long dateNowLong = nowDate.getTime();
        long elapseTime = dateNowLong - raidLastLong;
        long elapseTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapseTime);
        logger.debug("Current Elapse Time: {} sec / RAID Interval Required {} sec", elapseTimeInSeconds, (raidIntevalLong * 60));
        if (elapseTimeInSeconds > (raidIntevalLong * 60)) {
            try {
                configProp.put(raidLastKey, String.valueOf(dateNowLong));
                configProp.write(configFile);
            } catch (IOException e) {
                // ignore write error
            }
            logger.debug("Scheduler: Running ... interval exceeded");
            return true;
        }

        logger.debug("Scheduler: Skipping ... not exceeding interval");

        return false;
    }

    /**
     * Calls after the splash screen. open the main stage after the raid check
     * up.
     *
     * @param primaryStage
     */
    private void showRaid(Stage primaryStage) {
        if (checkIfRaiding()) {
            Raid.call(() -> {
                this.showMain(primaryStage);
            });
        } else {
            this.showMain(primaryStage);
        }
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
        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, (event) -> {
            if (event.getCode().equals(KeyCode.F11)) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });
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
