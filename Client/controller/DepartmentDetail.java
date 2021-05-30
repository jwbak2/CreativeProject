package Client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentDetail implements Initializable{

    @FXML
    private Text textDeptName;

    @FXML
    private Label admissionFee;

    @FXML
    private Label tuition;

    @FXML
    private Label outSchoolScholarship;

    @FXML
    private Label inSchoolScholarship;

    @FXML
    private Label scholarshipPerPerson;

    @FXML
    private Label numOfFulltimeProfessor;

    @FXML
    private Label thesisResultPerProfessor;

    @FXML
    private Label rearchCostPerProfessor;

    @FXML
    private Label maleGr;

    @FXML
    private Label femaleGr;

    @FXML
    private Label maleEmploymentTarget;

    @FXML
    private Label femaleEmploymentTarget;

    @FXML
    private Label employmentRate;

    @FXML
    private Label enteringRate;

    @FXML
    private Label enteringDomCmntyColl;

    @FXML
    private Label enteringOverseasCmntyColl;

    @FXML
    private Label enteringDomUniv;

    @FXML
    private Label enteringOverseasUniv;

    @FXML
    private Label enteringDomGrSchool;

    @FXML
    private Label enteringOverseasGrSchool;

    @FXML
    private Label domScholarNumber;

    @FXML
    private Label overseasScholarNumber;

    @FXML
    private Label maleDomEmployee;

    @FXML
    private Label femaleDomEmployee;

    @FXML
    private Label maleOverseasEmployee;

    @FXML
    private Label femaleOverseasEmployee;

    @FXML
    private Button btnDeptDetailExit;

    @FXML
    private AnchorPane apDeptDetail;

    private String test;

    public void setTextDeptName(String deptName){
        test = deptName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void clickDeptDetailExit(MouseEvent event) {
        System.out.println(test);
        StackPane root = (StackPane)btnDeptDetailExit.getScene().lookup("#spUnivDetail");
        root.getChildren().remove(apDeptDetail);
    }
}
