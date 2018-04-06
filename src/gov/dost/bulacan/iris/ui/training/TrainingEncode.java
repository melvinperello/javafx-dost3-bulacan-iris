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

    @Override
    protected void setup() {
        // row count
        int rowCount = 15;
        // column count
        int columnCount = 2;
        // Create Grid Base.
        GridBase grid = new GridBase(rowCount, columnCount);

        // row list
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        // iterate row
        for (int row = 0; row < grid.getRowCount(); ++row) {
            // column list
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();

            for (int column = 0; column < grid.getColumnCount(); ++column) {
                // add column
                String text = "";
                SpreadsheetCell cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, text);

                if (column == colTitle && row == 0) {
                    text = A_1;
                } else if (column == colTitle && row == 1) {
                    text = A_2;
                } else if (column == colTitle && row == 2) {
                    text = A_3;
                } else if (column == colTitle && row == 3) {
                    text = A_4;
                } else if (column == colTitle && row == 4) {
                    text = A_5;
                } else if (column == colTitle && row == 5) {
                    text = "TECHNOLOGY INFORMATION PRESENTED";
                } else if (column == colTitle && row == 6) {
                    text = B_1;
                } else if (column == colTitle && row == 7) {
                    text = B_2;
                } else if (column == colTitle && row == 8) {
                    text = B_3;
                } else if (column == colTitle && row == 9) {
                    text = B_4;
                } else if (column == colTitle && row == 10) {
                    text = "RESOURCE SPEKAER";
                } else if (column == colTitle && row == 11) {
                    text = C_1;
                } else if (column == colTitle && row == 12) {
                    text = C_2;
                } else if (column == colTitle && row == 13) {
                    text = C_3;
                } else if (column == colTitle && row == 14) {
                    text = C_3;
                } else if (column == colTitle && row == 15) {
                    text = "OVERALL RATING OF THE ACTIVITY";
                }
                cell.setItem(new String(text));
                if (column == 0) {

                    cell.setEditable(false);
                    cell.setWrapText(true);

                }
                list.add(cell);
            }

            rows.add(list);
        }
        // add row
        grid.setRows(rows);

        // attach grid base to spreadsheet
        SpreadsheetView spv = new SpreadsheetView(grid);
        SpreadsheetColumn columnTitle = spv.getColumns().get(0);
        columnTitle.setFixed(true);
        columnTitle.setMinWidth(700.0);
        columnTitle.setPrefWidth(700.0);
        columnTitle.setMaxWidth(700.0);
//        columnTitle.setResizable(false);
        SpreadsheetColumn columnValue = spv.getColumns().get(1);
        columnValue.setFixed(true);
        columnValue.setMinWidth(200.0);
        columnValue.setPrefWidth(200.0);
        columnValue.setMaxWidth(200.0);

        VBox.setVgrow(spv, Priority.ALWAYS);
        this.vbox_ss_container.getChildren().add(spv);
    }

    /**
     * Creates the framework of the spreadsheet. this segment focuses on the
     * creation and value of the cell only, no editing of width or any other
     * appearance related options.
     *
     * @return
     */
    private GridBase createGridFramework() {

    }

}
