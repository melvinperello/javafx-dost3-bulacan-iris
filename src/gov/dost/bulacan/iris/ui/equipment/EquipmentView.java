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
package gov.dost.bulacan.iris.ui.equipment;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.PolarisForm;
import gov.dost.bulacan.iris.ui.Home;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author Jhon Melvin
 */
public class EquipmentView extends PolarisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXButton btn_back_to_home;

    @FXML
    private ListView<EquipmentViewListItem> list_equipment;

    public EquipmentView() {
        this.observeableListItems = FXCollections.observableArrayList();
    }
    private final ObservableList<EquipmentViewListItem> observeableListItems;

    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);
        Home.addEventBackToHome(this.btn_back_to_home, this);

        for (int x = 0; x < 10; x++) {
            EquipmentViewListItem listItem = new EquipmentViewListItem();
            listItem.load();
            this.observeableListItems.add(listItem);
        }

        this.list_equipment.getItems().setAll(this.observeableListItems);

        this.list_equipment.setCellFactory(new Callback<ListView<EquipmentViewListItem>, ListCell<EquipmentViewListItem>>() {
            @Override
            public ListCell<EquipmentViewListItem> call(ListView<EquipmentViewListItem> param) {
                return new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            EquipmentViewListItem listItem = (EquipmentViewListItem) item;
                            setGraphic(listItem.getRootPane());
                        }

                    }
                };
            }
        });

        this.list_equipment.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends EquipmentViewListItem> observable, EquipmentViewListItem oldValue, EquipmentViewListItem newValue) -> {
            System.out.println("changed");
        });
    }

}
