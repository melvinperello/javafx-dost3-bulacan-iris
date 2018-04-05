package gov.dost.bulacan.iris.ui.certmaker;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.afterschoolcreatives.polaris.java.util.PolarisProperties;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisDialog;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This program is intended to automate the creation of certifications using
 * Microsoft Excel.
 *
 * @author Jhon Melvin Perello
 */
public class CertMaker extends PolarisFxController {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_corporation;

    @FXML
    private TextArea txt_content;

    @FXML
    private TextField txt_controlno;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_import;

    @FXML
    private TextField txt_start;

    @FXML
    private TextField txt_end;

    @FXML
    private Button btn_certdirectory;

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back_to_home;

    /**
     * Default Constructor.
     */
    public CertMaker() {
        //
    }

    //--------------------------------------------------------------------------
    // Adobe Acro Forms Key Values
    //--------------------------------------------------------------------------
    /**
     * ADOBE ACROFORMS certificate name key.
     */
    public final static String FRM_NAME = "frm_name";
    /**
     * ADOBE ACROFORMS certificate corporation key.
     */
    public final static String FRM_CORPORATION = "frm_corporation";
    /**
     * ADOBE ACROFORMS certificate content key.
     */
    public final static String FRM_CONTENT = "frm_content";
    /**
     * ADOBE ACROFORMS certificate control number key.
     */
    public final static String FRM_CONTROL = "frm_control";

    //--------------------------------------------------------------------------
    // Excel Columns
    //--------------------------------------------------------------------------
    public final static int COL_QUARTER = 0;
    public final static int COL_CONTROL_NO = 1;
    public final static int COL_NAME = 2;
    public final static int COL_COMPANY = 3;
    //--------------------------------------------------------------------------
    // Files and Directories
    //--------------------------------------------------------------------------
    /**
     * A public folder that must contain all the PDF Templates.
     */
    public final static String DIR_TEMPLATE = Context.getDirectoryTemplate();
    /**
     * A public folder that will hold the generated certificates.
     */
    public final static String DIR_CERTIFICATES = "certificates";
    /**
     * Certification file.
     */
    public final static String TEMPLATE_GMP_CERTIFICATE = "certification_template.pdf";

    //--------------------------------------------------------------------------
    // Controller Coverage
    //--------------------------------------------------------------------------
    /**
     * Initialization method.
     */
    @Override
    protected void setup() {
        ProjectHeader.attach(hbox_header);
        Home.addEventBackToHome(btn_back_to_home, this);

        this.txt_start.setText("Start");
        this.txt_end.setText("End");

        this.loadText();
        /**
         * Clear Text.
         */
        this.btn_clear.setOnMouseClicked(value -> {
            this.txt_content.setText("");
            this.txt_corporation.setText("");
            this.txt_controlno.setText("");
            this.txt_name.setText("");

            this.saveText();
            value.consume();
        });

        /**
         * Stamp.
         */
        this.btn_create.setOnMouseClicked((MouseEvent value) -> {
            String name = this.txt_name.getText().trim();
            String corporation = this.txt_corporation.getText().trim();
            String content = this.txt_content.getText().trim();
            String control = this.txt_controlno.getText().trim();
            /**
             * Stamp now.
             */
            try {
                if (CertMaker.stampCertificate(name, corporation, content, control)) {
                    this.showInformationMessage("Certificate successfully created.");
                } else {
                    this.showWarningMessage("Failed to create certificate.");
                }

            } catch (DocumentException | IOException e) {
                PolarisDialog.exceptionDialog(e);
            }

            this.saveText();
            value.consume();
        });

        /**
         * Import Excel.
         */
        this.btn_import.setOnMouseClicked(value -> {
            if (readExcelAndStamp(txt_content.getText().trim())) {
                showInformationMessage("Successfully Stamped all required entries.");
            }
            this.saveText();
            value.consume();
        });

        this.btn_certdirectory.setOnMouseClicked(value -> {
            boolean suuported = false;
            if (Desktop.isDesktopSupported()) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    try {
                        Desktop.getDesktop().open(new File("certificates"));
                    } catch (Exception e) {
                        this.showErrorMessage("The folder is not existing or is empty. Try creating certificates first before opening.");
                    }
                    suuported = true;
                }
            }
            if (!suuported) {
                this.showWarningMessage("Action Not Supported in this Operating System");
            }

            this.saveText();
            value.consume();
        });
    }

    private final static String PROP_FILE = "certificatemaker.prop";

    private void saveText() {
        /**
         * Save Texts.
         */
        PolarisProperties prop = new PolarisProperties();
        prop.setProperty("txt_name", this.txt_name.getText().trim());
        prop.setProperty("txt_corporation", this.txt_corporation.getText().trim());
        prop.setProperty("txt_content", this.txt_content.getText().trim());
        prop.setProperty("txt_controlno", this.txt_controlno.getText().trim());
        prop.setProperty("txt_start", this.txt_start.getText().trim());
        prop.setProperty("txt_end", this.txt_end.getText().trim());
        try {
            prop.write(new File(PROP_FILE));
        } catch (IOException e) {
            // ignore
        }
    }

    private void loadText() {
        PolarisProperties prop = new PolarisProperties();
        try {
            prop.read(new File(PROP_FILE));
            this.txt_name.setText(prop.getProperty("txt_name", ""));
            this.txt_corporation.setText(prop.getProperty("txt_corporation", ""));
            this.txt_content.setText(prop.getProperty("txt_content", ""));
            this.txt_controlno.setText(prop.getProperty("txt_controlno", ""));
            this.txt_start.setText(prop.getProperty("txt_start", ""));
            this.txt_end.setText(prop.getProperty("txt_end", ""));
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Display a warning message with this stage as owner.
     *
     * @param message
     */
    private void showWarningMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.WARNING)
                .setTitle(this.getStage().getTitle())
                .setHeaderText("Warning")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    /**
     * Displays an information message.
     *
     * @param message
     */
    private void showInformationMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.INFORMATION)
                .setTitle(this.getStage().getTitle())
                .setHeaderText("Information")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    private void showErrorMessage(String message) {
        PolarisDialog.create(PolarisDialog.Type.ERROR)
                .setTitle(this.getStage().getTitle())
                .setHeaderText("Something Went Wrong !")
                .setContentText(message)
                .setOwner(this.getStage())
                .showAndWait();
    }

    /**
     * Displays a confirmation message.
     *
     * @param message
     * @return
     */
    private boolean showConfirmation(String message) {
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> res = PolarisDialog.create(PolarisDialog.Type.CONFIRMATION)
                .setTitle(this.getStage().getTitle())
                .setHeaderText("Confirmation")
                .setContentText(message)
                .setOwner(this.getStage())
                .setButtons(yesButton, cancelButton)
                .showAndWait();
        return res.get().getButtonData().equals(ButtonBar.ButtonData.YES);
    }

    /**
     * Creates a certificate based on the template.
     *
     * @param name The name of the certificate holder.
     * @param corporation The name of the corporation.
     * @param content The content of the certificate.
     * @param control The name of the control number.
     * @return true or false if successfully created.
     * @throws com.itextpdf.text.DocumentException
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException if some of the required directories
     * or files is not existing.
     */
    public static boolean stampCertificate(
            String name,
            String corporation,
            String content,
            String control)
            throws DocumentException, FileNotFoundException, IOException {
        /**
         * Attempt to check the template directory.
         */
        if (!FileTool.checkFoldersQuietly(CertMaker.DIR_TEMPLATE)) {
            throw new FileNotFoundException("Template directory cannot be created.");
        }
        /**
         * Attempt to check the certificates directory.
         */
        if (!FileTool.checkFoldersQuietly(CertMaker.DIR_CERTIFICATES)) {
            throw new FileNotFoundException("Certificates directory cannot be created.");
        }
        /**
         * Template File Path.
         */
        String templatePath = CertMaker.DIR_TEMPLATE + File.separator + CertMaker.TEMPLATE_GMP_CERTIFICATE;
        File templateFile = new File(templatePath);

        /**
         * Check if the template file is not existing.
         */
        if (!templateFile.exists()) {
            throw new FileNotFoundException("Certificate Template not existing.");
        }
        /**
         * Initialize Stream.
         */
        PdfStamper stamper = null;
        PdfReader reader = null;
        try {
            /**
             * Read the template.
             */
            reader = new PdfReader(templateFile.getAbsolutePath());
            /**
             * Output file.
             */
            File stampedCertificatePdf = new File(CertMaker.DIR_CERTIFICATES + File.separator + control + "_" + corporation + ".pdf");

            stamper = new PdfStamper(reader, new FileOutputStream(stampedCertificatePdf));
            AcroFields form = stamper.getAcroFields();
            form.setField(CertMaker.FRM_NAME, name);
            form.setField(CertMaker.FRM_CORPORATION, corporation);
            form.setField(CertMaker.FRM_CONTENT, content);
            form.setField(CertMaker.FRM_CONTROL, control);

            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } finally {
            try {
                if (stamper != null) {
                    stamper.close();
                }
            } catch (DocumentException | IOException e) {
                // ignore close
            }

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // ignore close
            }
        }

        /**
         * Return true if not exceptions were encountered. and to know that the
         * file was created and stamped.
         */
        return true;
    }

    /**
     * Read Excel.
     *
     * @param certificateContent
     * @return
     */
    public boolean readExcelAndStamp(String certificateContent) {
        String content = certificateContent;
        int start = 0;
        int end = 0;
        try {

            String startText = this.txt_start.getText().trim();
            String endText = this.txt_end.getText().trim();

            if (startText.equalsIgnoreCase("START")) {
                startText = "5";
            }

            if (endText.equalsIgnoreCase("END")) {
                endText = "-1";
            }

            start = Integer.parseInt(startText);
            end = Integer.parseInt(endText);

            if (start > end && end > 0) {
                // starting value greater than ending value
                this.showWarningMessage("Starting Offset must be lower than Ending Offset.");
                return false;
            }

            if (start <= 4) {
                this.showWarningMessage("Starting Offset must be greater than 4.");
                return false;
            }
        } catch (NumberFormatException e) {
            this.showWarningMessage("Invalid Starting Offset or Ending Offset.");
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Excel Control File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (xlsx and xls)", "*.xlsx", "*.xls");
            fileChooser.getExtensionFilters().setAll(extFilter);
            // select
            File selectedFile = fileChooser.showOpenDialog(this.getStage());

            if (selectedFile == null) {
                return false;
            }

            /**
             * Get Data.
             */
            ArrayList<ExcelObject> data = excelReadContents(selectedFile, start, end);
            /**
             * Stamp each object.
             */
            int stampedCount = 0;
            for (ExcelObject excelObject : data) {
                boolean successfullyStamped = CertMaker.stampCertificate(
                        excelObject.getName(),
                        excelObject.getCompany(),
                        content,
                        excelObject.getQualifiedControlNo());
                if (successfullyStamped) {
                    stampedCount++;
                }
            }

            if (stampedCount != data.size()) {
                // something happened
                this.showWarningMessage("Failed to Stamp some entries in the excel. please re-run the stamping to assure correctness of data.");
                return false;
            }

        } catch (IOException | DocumentException io_ex) {
            // some exceptions
            PolarisDialog.exceptionDialog(io_ex);
            return false;
        } catch (IndexOutOfBoundsException index_ex) {
            this.showWarningMessage("Ending Offset is greater than the last row of the excel file.");
            return false;
        }

        /**
         * OK.
         */
        return true;
    }

    /**
     * Get the absolute excel last row. Excel last row is 0 based. adding +1
     * will give the absolute excel last row.
     *
     * @param sheet
     * @return
     */
    public static int getSheetLastRow(XSSFSheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Reads the contents of the excel file.
     *
     * @param excelFile the excel file.
     * @param start corresponding to the starting excel row.
     * @param end corresponding to the ending excel row.
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static ArrayList<ExcelObject> excelReadContents(
            /* Parameters */
            File excelFile, int start, int end
    /* Exceptions */
    ) throws FileNotFoundException, IOException, IndexOutOfBoundsException {
        /* Method Code Coverage */
        // 0 based adjustments
        start = (start - 1);
        if (start < 0) {
            start = 0;
        }
        end = (end - 1);

        FileInputStream excelFileStream = null;
        try {
            // process excel
            excelFileStream = new FileInputStream(excelFile);
            XSSFWorkbook excel = new XSSFWorkbook(excelFileStream);
            /**
             * The excel Sheet.
             */
            XSSFSheet excelSheet = excel.getSheetAt(0);

            /**
             * Check if the ending offset is greater than the last row of the
             * excel.
             */
            if (end > CertMaker.getSheetLastRow(excelSheet)) {
                throw new IndexOutOfBoundsException("The ending offset is greater than the last row of the excel.");
            }

            /**
             * If ending is negative get the last row as ending offset.
             */
            if (end < 0) {
                end = CertMaker.getSheetLastRow(excelSheet) - 1;
            }

            final ArrayList<ExcelObject> excelContents = new ArrayList<>();
            for (int row = start; row <= end; row++) {
                XSSFRow excelRow = excelSheet.getRow(row);
                ExcelObject object = new ExcelObject();
                object.setQuarter(getCellStringValue(excelRow, COL_QUARTER));
                object.setControlNo(getCellStringValue(excelRow, COL_CONTROL_NO));
                object.setName(getCellStringValue(excelRow, COL_NAME));
                object.setCompany(getCellStringValue(excelRow, COL_COMPANY));
                excelContents.add(object);
            }

            return excelContents;
        } finally {
            if (excelFileStream != null) {
                try {
                    excelFileStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Get cells from row.
     *
     * @param row
     * @param cell
     * @return
     */
    public static String getCellStringValue(XSSFRow row, int cell) {
        return row.getCell(cell, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
    }

    /**
     * Excel Object representing the columns of the excel file record.
     */
    public static class ExcelObject {

        private String quarter;
        private String controlNo;
        private String name;
        private String company;

        /**
         * Initialize Strings with empty content.
         */
        public ExcelObject() {
            this.quarter = "";
            this.controlNo = "";
            this.name = "";
            this.company = "";
        }

        /**
         * Get the fully qualified control number.
         *
         * @return
         */
        public String getQualifiedControlNo() {
            return quarter + controlNo;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        public void setControlNo(String controlNo) {
            this.controlNo = controlNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        @Override
        public String toString() {
            return this.quarter + this.controlNo + "\t" + this.name + "\t\t\t" + this.company;
        }

    } // end of excel object.

}
