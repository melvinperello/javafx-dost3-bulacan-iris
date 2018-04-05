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
package gov.dost.bulacan.iris.ui;

import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.ui.certmaker.CertMaker;
import gov.dost.bulacan.iris.ui.directory.DirHome;
import gov.dost.bulacan.iris.ui.equipment.EquipmentView;
import gov.dost.bulacan.iris.ui.project.ProjectView;
import gov.dost.bulacan.iris.ui.scholarship.ScholarshipHome;
import gov.dost.bulacan.iris.ui.shared.SharedHome;
import gov.dost.bulacan.iris.ui.training.TrainingHome;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author Jhon Melvin
 */
public class Home extends IrisForm {

    @FXML
    private HBox hbox_header;

    @FXML
    private HBox menu_projects;

    @FXML
    private HBox menu_equipment;

    @FXML
    private HBox menu_trainings;

    @FXML
    private HBox menu_directory;

    @FXML
    private HBox menu_scholar;

    @FXML
    private HBox menu_gmp;

    @FXML
    private HBox menu_documents;

    @FXML
    private HBox menu_about;

    @Override
    protected void setup() {
        ProjectHeader.attach(hbox_header);
        /**
         * Open Projects.
         */
        this.menu_projects.setOnMouseClicked(value -> {
            ProjectView projectView = new ProjectView();
            this.changeRoot(projectView.load());
            value.consume();
        });
        /**
         * Open Equipments.
         */
        this.menu_equipment.setOnMouseClicked(value -> {
            EquipmentView equipmentView = new EquipmentView();
            this.changeRoot(equipmentView.load());
            value.consume();
        });

        this.menu_trainings.setOnMouseClicked(value -> {
            this.changeRoot(new TrainingHome().load());
            value.consume();
        });

        /**
         * Open Equipments.
         */
        this.menu_directory.setOnMouseClicked(value -> {
            DirHome fx = new DirHome();
            this.changeRoot(fx.load());
            value.consume();
        });

        /**
         * Open Equipments.
         */
        this.menu_documents.setOnMouseClicked(value -> {
            this.changeRoot(new SharedHome().load());
            value.consume();
        });

        this.menu_scholar.setOnMouseClicked(value -> {
            this.changeRoot(new ScholarshipHome().load());
            value.consume();
        });

        this.menu_gmp.setOnMouseClicked(value -> {
            this.changeRoot(new CertMaker().load());
            value.consume();
        });

    }

    /**
     * Add Event to a button that will change the view to the home page.
     *
     * @param button
     * @param controller
     */
    public static void addEventBackToHome(Node button, PolarisFxController controller) {
        button.setOnMouseClicked(value -> {
            invokeEventBackToHome(controller);
            value.consume();
        });
    }

    public static void invokeEventBackToHome(PolarisFxController controller) {
        Home home = new Home();
        controller.changeRoot(home.load());
    }

}
