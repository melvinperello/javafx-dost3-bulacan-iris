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
package gov.dost.bulacan.iris.ui.training;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.IrisForm;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetColumn;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.json.JSONObject;

/**
 *
 * @author s500
 */
public class TrainingEncode extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private JFXButton btn_back;

    @FXML
    private Label lbl_modify_header;

    @FXML
    private Label lbl_modify_time;

    @FXML
    private JFXButton btn_save;

    @FXML
    private VBox vbox_ss_container;

    public TrainingEncode() {
        //
        this.cellTitles = new ArrayList<>();
        cellTitles.add("TECHNOLOGY/INFORMATION PRESENTED");
        cellTitles.add(A_1);
        cellTitles.add(A_2);
        cellTitles.add(A_3);
        cellTitles.add(A_4);
        cellTitles.add(A_5);
        cellTitles.add("RESOURCE SPEKAER");
        cellTitles.add(B_1);
        cellTitles.add(B_2);
        cellTitles.add(B_3);
        cellTitles.add(B_4);
        cellTitles.add("LOGISTIC SUPPORT");
        cellTitles.add(C_1);
        cellTitles.add(C_2);
        cellTitles.add(C_3);
        cellTitles.add("OVERALL RATING");
    }
    private final static String A_1 = "1. The choice of technologies topics presented";
    private final static String A_2 = "2. Potential application of the technologies/topics presented to my work / business";
    private final static String A_3 = "3. The additional knowledge gained from the technologies presented";
    private final static String A_4 = "4. Potential application of the technologies presented to start a new business / upgrade existing business.";
    private final static String A_5 = "5. Adequacy of time allotted for the presentation of the technology / topics including the open forum session.";
    //
    private final static String B_1 = "1. Knowledge / Competence / Grasp of the subject matter";
    private final static String B_2 = "2. Audience Rapport";
    private final static String B_3 = "3. Presentation Skill";
    private final static String B_4 = "4. Time Management";
    //
    private final static String C_1 = "1. Conductiveness of Training Materials and Kits Provided";
    private final static String C_2 = "2. Adequacy of Training Materials and Kits Provided";
    private final static String C_3 = "3. Provision of Assistance to Participants";

    private final static int HEAD_1 = 0;
    private final static int HEAD_2 = 6;
    private final static int HEAD_3 = 11;
    private final static int HEAD_OVER = 15;
    /**
     * Titles of spreadsheet.
     */
    private final ArrayList<String> cellTitles;
    /**
     *
     */
    private SpreadsheetView spreadSheetView;

    @Override
    protected void setup() {
        // attach grid base to spreadsheet
        this.spreadSheetView = new SpreadsheetView(this.fillGrid());
        // style spreadsheet.
        this.applySpreadsheetStyling(this.spreadSheetView);
        // maximize sheet to parent.
        VBox.setVgrow(this.spreadSheetView, Priority.ALWAYS);
        // attach spreadsheet to parent
        this.vbox_ss_container.getChildren().add(this.spreadSheetView);
        //
        this.btn_save.setOnMouseClicked(value -> {
            this.submitTrainingValues();
            value.consume();
        });
    }

    //--------------------------------------------------------------------------
    // Control FX Spreadsheet View.
    //--------------------------------------------------------------------------
    /**
     * Creates the framework of the spreadsheet. this segment focuses on the
     * creation and value of the cell only, no editing of width or any other
     * appearance related options.
     *
     * @return
     */
    private GridBase createGridFramework(int rowCount, int colCount) {
        GridBase grid = new GridBase(rowCount, colCount);
        return grid;
    }

    /**
     * How do you want the spreadsheet to appear.
     */
    private GridBase fillGrid() {
        //----------------------------------------------------------------------
        // since this spreadsheet has a static count declare first the vaues
        final int rowCount = 16;
        final int columnCount = 2;
        //----------------------------------------------------------------------
        // Create Grid Base.
        GridBase grid = this.createGridFramework(rowCount, columnCount);
        /**
         * Contains all the rows.
         */
        ObservableList< ObservableList< SpreadsheetCell>> rows = FXCollections.observableArrayList();
        /**
         * Iterate over rows.
         */
        for (int row = 0; row < grid.getRowCount(); row++) {
            /**
             * Create single row.
             */
            final ObservableList<SpreadsheetCell> singleRow = FXCollections.observableArrayList();
            /**
             * Fill single row with cells.
             */
            for (int column = 0; column < grid.getColumnCount(); column++) {
                singleRow.add(this.fillCellValues(row, column));
            }
            /**
             * Add single row to rows.
             */
            rows.add(singleRow);
        }
        /**
         * Add all rows to grid.
         */
        grid.setRows(rows);

        return grid;
    }

    private SpreadsheetCell createStringCell(int row, int col, int rowSpan, int colSpan) {
        return SpreadsheetCellType.STRING.createCell(row, col, rowSpan, colSpan, "");
    }

    private void applySpreadsheetStyling(SpreadsheetView spv) {
        SpreadsheetColumn columnTitle = spv.getColumns().get(0);
        columnTitle.setFixed(true); // fix not movable
        columnTitle.setPrefWidth(700.0);
        columnTitle.setResizable(false);
        //
        SpreadsheetColumn columnValue = spv.getColumns().get(1);
        columnValue.setFixed(true);
        columnValue.setPrefWidth(200.0);
        columnValue.setResizable(false);

        //----------------------------------------------------------------------
        // Styling
        //----------------------------------------------------------------------
        // Apply Stylesheet
        // the stylesheet must be in the same package as this class.
        spv.getStylesheets().add(this.getClass().getResource("spreadsheet.css").toExternalForm());
        //----------------------------------------------------------------------
        // Since Spreadsheet view is composed of observeable list and only
        // the SpreadSheetCell is a child of Control
        // styling will be applied individually in every row in every column
        // for every cel
        //----------------------------------------------------------------------
        // apply style to heeaders.
        spv.getGrid().getRows().get(HEAD_1).get(0).getStyleClass().add("row-header");
        spv.getGrid().getRows().get(HEAD_1).get(1).getStyleClass().add("row-header");

        spv.getGrid().getRows().get(HEAD_2).get(0).getStyleClass().add("row-header");
        spv.getGrid().getRows().get(HEAD_2).get(1).getStyleClass().add("row-header");

        spv.getGrid().getRows().get(HEAD_3).get(0).getStyleClass().add("row-header");
        spv.getGrid().getRows().get(HEAD_3).get(1).getStyleClass().add("row-header");

        spv.getGrid().getRows().get(HEAD_OVER).get(0).getStyleClass().add("row-overall");
        spv.getGrid().getRows().get(HEAD_OVER).get(1).getStyleClass().add("row-overall");
        //----------------------------------------------------------------------
        // style titles
        for (int row = 0; row < spv.getGrid().getRowCount(); row++) {
            if (row == HEAD_1 || row == HEAD_2 || row == HEAD_3) {
                continue; //skip;
            }
            if (HEAD_OVER == row) {
                spv.getGrid().getRows().get(row).get(1).getStyleClass().add("col-value");
                continue;
            }
            // title
            spv.getGrid().getRows().get(row).get(0).getStyleClass().add("col-titles");
            // value
            spv.getGrid().getRows().get(row).get(1).getStyleClass().add("col-value");

        }

        /**
         * Do not show row header numbers in the left side.
         */
        spv.setShowRowHeader(false);

        spv.getGrid().getColumnHeaders().addAll("Criteria", "Rating");
    }

    /**
     * Retrieve cell item in a specific row and column.
     *
     * @param row
     * @param col
     * @return
     */
    private String getCellValue(int row, int col) {
        ObservableList<SpreadsheetCell> rowList = this.spreadSheetView.getGrid().getRows().get(row);

        SpreadsheetCell cell = rowList.get(col);
        if (cell == null) {
            return "";
        }

        if (cell.getItem() == null) {
            return "";
        }

        return cell.getItem().toString();
    }

    //--------------------------------------------------------------------------
    // END Control FX Spreadsheet View.
    //--------------------------------------------------------------------------
    /**
     * How do you fill each cell ?
     *
     * @param currentRow
     * @param currentCol
     * @return
     */
    private SpreadsheetCell fillCellValues(int currentRow, int currentCol) {
        // add column
        String text = "";
        SpreadsheetCell cell = this.createStringCell(currentRow, currentCol, 1, 1);

        cell.setItem(text);
        if (currentCol == 0) {

            text = this.cellTitles.get(currentRow);
            cell.setItem(text);
            cell.setEditable(false);
            cell.setWrapText(true);

        }

        if (currentCol == 1) {
            if (currentRow == HEAD_1 || currentRow == HEAD_2 || currentRow == HEAD_3) {
                cell.setEditable(false);
            }
        }

        return cell;
    }

    private void submitTrainingValues() {
        HashMap<String, String> values = new HashMap<>();
        for (int x = 1; x <= 5; x++) {
            values.put(Integer.toString(x), this.getCellValue(x, 1));
        }
        for (int x = 7; x <= 10; x++) {
            values.put(Integer.toString(x), this.getCellValue(x, 1));
        }
        for (int x = 12; x <= 15; x++) {
            values.put(Integer.toString(x), this.getCellValue(x, 1));
        }
        
        JSONObject json = new JSONObject(values);
        System.out.println(json.toString());

    }

}
