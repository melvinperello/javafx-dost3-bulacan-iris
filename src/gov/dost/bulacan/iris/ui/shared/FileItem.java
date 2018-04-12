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
package gov.dost.bulacan.iris.ui.shared;

import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.FileExtensions;
import gov.dost.bulacan.iris.RaidContext;
import gov.dost.bulacan.iris.models.SystemFileModel;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.afterschoolcreatives.polaris.javafx.fxml.PolarisFxController;
import org.afterschoolcreatives.polaris.javafx.scene.control.PolarisCustomListAdapter;

/**
 *
 * @author Jhon Melvin
 */
public class FileItem extends PolarisFxController implements PolarisCustomListAdapter.Listable {

    @FXML
    private ImageView img_icon;

    @FXML
    private Label lbl_file_name;

    @FXML
    private Label lbl_description;

    public FileItem() {
        this.documentModel = null;
    }

    private SystemFileModel documentModel;

    public SystemFileModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(SystemFileModel documentModel) {
        this.documentModel = documentModel;
    }

    //--------------------------------------------------------------------------
    @Override
    protected void setup() {
        this.lbl_file_name.setText(this.documentModel.getDocName());
        final String f_ext = this.documentModel.getLinkedModel().getExtenstion().toLowerCase(Locale.ENGLISH);
        //----------------------------------------------------------------------
        final String prettyName = FileExtensions.recognizeFile(f_ext);
        final String f_icon = FileExtensions.getDisplayIcon(prettyName);
        this.img_icon.setImage(new Image(Context
                .getResourceStream("drawable/file_extensions/" + f_icon)));
        //----------------------------------------------------------------------
        String descriptiveText = "";
        final String prettySize = RaidContext.getStringFileSize(this.getDocumentModel().getLinkedModel().getSize());
        descriptiveText = prettyName + " ( " + prettySize + " ) " + " - "
                + this.documentModel.auditToString();

        this.lbl_description.setText(descriptiveText);
    }

    @Override
    public Pane getCellGraphic() {
        return this.getRootPane();
    }

}
