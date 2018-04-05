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
package gov.dost.bulacan.iris.ui.scholarship;

import com.jfoenix.controls.JFXButton;
import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.IrisForm;
import gov.dost.bulacan.iris.models.ProjectModel;
import gov.dost.bulacan.iris.models.ScholarInformationModel;
import gov.dost.bulacan.iris.ui.ProjectHeader;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author s500
 */
public class ScholarEdit extends IrisForm {
    
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
    private Label lbl_scholar_id;
    
    @FXML
    private TextField txt_student_number;
    
    @FXML
    private TextField txt_last_name;
    
    @FXML
    private TextField txt_ext_name;
    
    @FXML
    private TextField txt_first_name;
    
    @FXML
    private TextField txt_middle_name;
    
    @FXML
    private ComboBox<String> cmb_gender;
    
    @FXML
    private TextField txt_course;
    
    @FXML
    private ComboBox<Integer> cmb_year;
    
    @FXML
    private TextField txt_section;
    
    @FXML
    private TextField txt_university;
    
    @FXML
    private TextField txt_mobile_no;
    
    @FXML
    private TextField txt_tel_no;
    
    @FXML
    private ComboBox<ScholarInformationModel.ScholarType> cmb_scholarship;
    
    @FXML
    private ComboBox<ScholarInformationModel.ScholarType.Merit> cmb_type;
    
    @FXML
    private TextField txt_email;
    
    public ScholarEdit(ScholarInformationModel model) {
        this.scholarModel = model;
        this.addingMode = (model == null);
        
    }
    
    private final ScholarInformationModel scholarModel;
    private final boolean addingMode;
    
    @Override
    protected void setup() {
        ProjectHeader.attach(this.hbox_header);

        //----------------------------------------------------------------------
        // Initialize combo box.
        //----------------------------------------------------------------------
        this.cmb_gender.getItems().setAll(Arrays.asList(ScholarInformationModel.Gender.LIST));
        this.cmb_year.getItems().setAll(Arrays.asList(ScholarInformationModel.YearLevel.LIST));
        this.cmb_scholarship.getItems().setAll(Arrays.asList(ScholarInformationModel.ScholarType.LIST));
        this.cmb_type.getItems().setAll(Arrays
                .asList(ScholarInformationModel.ScholarType.Merit.LIST));
        
        this.cmb_gender.getSelectionModel().selectFirst();
        this.cmb_year.getSelectionModel().selectFirst();
        this.cmb_scholarship.getSelectionModel().selectFirst();
        this.cmb_type.getSelectionModel().selectFirst();
        //
        if (this.addingMode) {
            this.lbl_scholar_id.setText(Context.createLocalKey());
        } else {
            // nothing to initialize on editing mode.
            this.preloadData();
        }
        
        this.btn_back.setOnMouseClicked(value -> {
            this.changeRoot(new ScholarshipHome().load());
            value.consume();
        });
        
        this.btn_save.setOnMouseClicked(value -> {
            if (this.addingMode) {
                if (this.insert()) {
                    this.changeRoot(new ScholarshipHome().load());
                }
            } else {
                if (this.update()) {
                    this.changeRoot(new ScholarshipHome().load());
                }
            }
        });
    }
    
    private void preloadData() {
        this.lbl_scholar_id.setText(this.scholarModel.getScholarId());
        this.txt_student_number.setText(this.scholarModel.getStudentNumber());
        this.txt_last_name.setText(this.scholarModel.getLastName());
        this.txt_ext_name.setText(this.scholarModel.getExtName());
        this.txt_first_name.setText(this.scholarModel.getFirstName());
        this.txt_middle_name.setText(this.scholarModel.getMiddleName());

        // capital classification combo
        String gender = this.scholarModel.getGender();
        for (Object item : this.cmb_gender.getItems()) {
            String activity = item.toString();
            if (activity.equalsIgnoreCase(gender)) {
                this.cmb_gender.getSelectionModel().select(activity);
                break;
            }
        }
        //
        Integer scholarType = this.scholarModel.getScholarType();
        for (Object item : this.cmb_scholarship.getItems()) {
            ScholarInformationModel.ScholarType scholarObject = (ScholarInformationModel.ScholarType) item;
            if (scholarObject.getValue().intValue() == scholarType.intValue()) {
                this.cmb_scholarship.getSelectionModel().select((ScholarInformationModel.ScholarType) item);
                break;
            }
        }
        //
        Integer meritType = this.scholarModel.getMeritType();
        for (Object item : this.cmb_type.getItems()) {
            ScholarInformationModel.ScholarType.Merit scholarObject = (ScholarInformationModel.ScholarType.Merit) item;
            if (scholarObject.getValue().intValue() == meritType.intValue()) {
                this.cmb_type.getSelectionModel().select((ScholarInformationModel.ScholarType.Merit) item);
                break;
            }
        }
        //
        this.txt_course.setText(this.scholarModel.getCourse());
        //
        Integer yearLevel = this.scholarModel.getYear();
        for (Integer item : this.cmb_year.getItems()) {
            Integer scholarObject = item;
            if (scholarObject.intValue() == yearLevel.intValue()) {
                this.cmb_year.getSelectionModel().select(item);
                break;
            }
        }
        //
        this.txt_section.setText(this.scholarModel.getSection());
        this.txt_university.setText(this.scholarModel.getUniversity());
        this.txt_mobile_no.setText(this.scholarModel.getMobileNo());
        this.txt_tel_no.setText(this.scholarModel.getTelNo());
        this.txt_email.setText(this.scholarModel.getMail());
    }

    //--------------------------------------------------------------------------
    // Form Values.
    //--------------------------------------------------------------------------
    private String frmStudentNumber;
    private String frmLastName;
    private String frmExtName;
    private String frmFirstName;
    private String frmMiddleName;
    private String frmGender;
    private Integer frmScholarship;
    private Integer frmScholarMerit;
    private String frmCourse;
    private Integer frmYearLevel;
    private String frmSection;
    private String frmUniversity;
    private String frmMobileNo;
    private String frmTelNo;
    private String frmEmail;
    
    private void submit() {
        this.frmStudentNumber = Context.filterInputControl(txt_student_number);
        this.frmLastName = Context.filterInputControl(txt_last_name);
        this.frmExtName = Context.filterInputControl(txt_ext_name);
        this.frmFirstName = Context.filterInputControl(txt_first_name);
        this.frmMiddleName = Context.filterInputControl(txt_middle_name);
        this.frmGender = this.cmb_gender.getSelectionModel().getSelectedItem();
        //
        this.frmScholarship = this.cmb_scholarship.getSelectionModel().getSelectedItem().getValue();
        this.frmScholarMerit = this.cmb_type.getSelectionModel().getSelectedItem().getValue();
        //
        this.frmCourse = Context.filterInputControl(txt_course);
        this.frmYearLevel = this.cmb_year.getSelectionModel().getSelectedItem();
        this.frmSection = Context.filterInputControl(txt_section);
        this.frmUniversity = Context.filterInputControl(txt_university);
        this.frmMobileNo = Context.filterInputControl(txt_mobile_no);
        this.frmTelNo = Context.filterInputControl(txt_tel_no);
        this.frmEmail = Context.filterInputControl(txt_email);
    }

    /**
     * Update add scholar method.
     *
     * @return
     */
    private boolean insert() {
        this.submit();
        
        if (this.frmStudentNumber.isEmpty()) {
            this.showWarningMessage(null, "Please enter the student number");
            return false;
        }
        
        ScholarInformationModel model = new ScholarInformationModel();
        model.setScholarId(this.lbl_scholar_id.getText());
        model.setStudentNumber(frmStudentNumber);
        model.setLastName(frmLastName);
        model.setExtName(frmExtName);
        model.setFirstName(frmFirstName);
        model.setMiddleName(frmMiddleName);
        model.setGender(frmGender);
        model.setScholarType(frmScholarship);
        model.setMeritType(frmScholarMerit);
        model.setCourse(frmCourse);
        model.setYear(frmYearLevel);
        model.setSection(frmSection);
        model.setUniversity(frmUniversity);
        model.setMobileNo(frmMobileNo);
        model.setTelNo(frmTelNo);
        model.setMail(frmEmail);
        
        boolean inserted = false;
        try {
            inserted = ScholarInformationModel.insert(model);
            if (inserted) {
                this.showInformationMessage(null, "Successfully added to the database.");
            } else {
                this.showWarningMessage(null, "Cannot be added to the database at the moment. Please try again later.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to insert to the database");
        }
        return inserted;
        
    }

    /**
     * Update scholarship information.
     *
     * @return
     */
    private boolean update() {
        this.submit();
        
        if (this.frmStudentNumber.isEmpty()) {
            this.showWarningMessage(null, "Please enter the student number");
            return false;
        }
        
        ScholarInformationModel model = this.scholarModel;
//        model.setStudentNumber(frmStudentNumber);
        model.setLastName(frmLastName);
        model.setExtName(frmExtName);
        model.setFirstName(frmFirstName);
        model.setMiddleName(frmMiddleName);
        model.setGender(frmGender);
        model.setScholarType(frmScholarship);
        model.setMeritType(frmScholarMerit);
        model.setCourse(frmCourse);
        model.setYear(frmYearLevel);
        model.setSection(frmSection);
        model.setUniversity(frmUniversity);
        model.setMobileNo(frmMobileNo);
        model.setTelNo(frmTelNo);
        model.setMail(frmEmail);
        
        boolean updated = false;
        try {
            updated = ScholarInformationModel.update(model);
            if (updated) {
                this.showInformationMessage(null, "Successfully updated the database.");
            } else {
                this.showWarningMessage(null, "Cannot be updated at the moment. Please try again later.");
            }
        } catch (SQLException ex) {
            this.showExceptionMessage(ex, null, "Failed to update the database");
        }
        return updated;
    }
    
}
