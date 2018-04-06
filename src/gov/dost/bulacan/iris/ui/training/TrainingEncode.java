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
    
    @Override
    protected void setup() {
        // row count
        int rowCount = 15;
        // column count
        int columnCount = 2;
        // grid
        GridBase grid = new GridBase(rowCount, columnCount);
        // row list
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        // iterate row
        for (int row = 0; row < grid.getRowCount(); ++row) {
            // column list
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            // iterate column
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                // add column
                SpreadsheetCell cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "value");
                cell.setStyle("-fx-pref-size: 1000px");
                list.add(cell);
            }
            
            rows.add(list);
        }
        // add row
        grid.setRows(rows);

        // attach grid base to spreadsheet
        SpreadsheetView spv = new SpreadsheetView(grid);
        
        VBox.setVgrow(spv, Priority.ALWAYS);
        this.vbox_ss_container.getChildren().add(spv);
    }
}
