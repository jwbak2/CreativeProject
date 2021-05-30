package Client.controller;

import Client.transmission.Connection;
import Server.model.dto.DepartmentDetailDTO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Timer;
import java.util.TimerTask;

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

    private String deptName;

    public void setDeptName(String deptName){
        this.deptName = deptName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestDeptDetail();
    }

    public void requestDeptDetail(){

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_DETAIL, deptName));

                Protocol receivePT = Connection.receive();
                Object receivedBody = receivePT.getBody();

                DepartmentDetailDTO departmentDetailDTO = (DepartmentDetailDTO) receivedBody;

                Platform.runLater(() -> setUnivDeptList(departmentDetailDTO));
            }
        };

        timer.schedule(timerTask, 1000);
    }


    @FXML
    void clickDeptDetailExit(MouseEvent event) {
        System.out.println(deptName);
        StackPane root = (StackPane)btnDeptDetailExit.getScene().lookup("#spUnivDetail");
        root.getChildren().remove(apDeptDetail);
    }

    public void setUnivDeptList(DepartmentDetailDTO departmentDetailDTO){
        admissionFee.setText(String.valueOf(departmentDetailDTO.getAdmissionFee()));
        tuition.setText(String.valueOf(departmentDetailDTO.getTuition()));
        outSchoolScholarship.setText(String.valueOf(departmentDetailDTO.getOutSchoolScholarship()));
        inSchoolScholarship.setText(String.valueOf(departmentDetailDTO.getInSchoolScholarship()));
        scholarshipPerPerson.setText(String.valueOf(departmentDetailDTO.getScholarshipPerPerson()));
        numOfFulltimeProfessor.setText(String.valueOf(departmentDetailDTO.getNumOfFulltimeProfessor()));
        thesisResultPerProfessor.setText(String.valueOf(departmentDetailDTO.getThesisResultPerProfessor()));
        rearchCostPerProfessor.setText(String.valueOf(departmentDetailDTO.getRearchCostPerProfessor()));
        maleGr.setText(String.valueOf(departmentDetailDTO.getMaleGr()));
        femaleGr.setText(String.valueOf(departmentDetailDTO.getFemaleGr()));
        maleEmploymentTarget.setText(String.valueOf(departmentDetailDTO.getMaleEmploymentTarget()));
        femaleEmploymentTarget.setText(String.valueOf(departmentDetailDTO.getFemaleEmploymentTarget()));
        employmentRate.setText(String.valueOf(departmentDetailDTO.getEmploymentRate()));

        enteringRate.setText(String.valueOf(departmentDetailDTO.getEnteringRate()));
        enteringDomCmntyColl.setText(String.valueOf(departmentDetailDTO.getEnteringDomCmntyColl()));
        enteringOverseasCmntyColl.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasCmntyColl()));
        enteringDomUniv.setText(String.valueOf(departmentDetailDTO.getEnteringDomUniv()));
        enteringOverseasUniv.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasUniv()));
        enteringDomGrSchool.setText(String.valueOf(departmentDetailDTO.getEnteringDomGrSchool()));
        enteringOverseasGrSchool.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasGrSchool()));
        domScholarNumber.setText(String.valueOf(departmentDetailDTO.getDomScholarNumber()));
        overseasScholarNumber.setText(String.valueOf(departmentDetailDTO.getOverseasScholarNumber()));
        maleDomEmployee.setText(String.valueOf(departmentDetailDTO.getMaleDomEmployee()));
        femaleDomEmployee.setText(String.valueOf(departmentDetailDTO.getFemaleDomEmployee()));
        maleOverseasEmployee.setText(String.valueOf(departmentDetailDTO.getMaleOverseasEmployee()));
        femaleOverseasEmployee.setText(String.valueOf(departmentDetailDTO.getFemaleOverseasEmployee()));
    }
}
