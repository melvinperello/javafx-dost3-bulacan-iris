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
package gov.dost.bulacan.iris.ui.project;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.models.ProjectModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;

/**
 *
 * @author Jhon Melvin
 */
public class ProjectDetailsView extends PolarisFxController {

    @FXML
    private JFXButton btn_print;

    @FXML
    private JFXButton btn_edit_project;

    @FXML
    private JFXButton btn_back;

    /**
     * Recommended Constructor for viewing the details.
     *
     * @param parentPane
     * @param model
     */
    public ProjectDetailsView(Pane parentPane, ProjectModel model) {
        this.viewParentPane = parentPane;
        this.projectModel = model;
    }

    private final Pane viewParentPane;
    private final ProjectModel projectModel;

    @Override
    protected void setup() {
        /**
         * Back To Projects View.
         */
        this.btn_back.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectView().load());
            value.consume();
        });

        this.btn_edit_project.setOnMouseClicked(value -> {
            this.changeRoot(new ProjectDetailsEdit(this.getRootPane(), this.projectModel).load());
            value.consume();
        });
    }

}
