package Client.controller;

import Client.transmission.Connection;
import Client.vo.DeptInfoReqVO;
import Client.vo.LoginReqVO;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
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

    private String univName;         // 해당 학과의 학교 이름

    private String deptName;        // 학교 상세정보에서 누른 학과 이름

    private ArrayList<DepartmentDetailDTO> deptDtoList;   // 2018 ~ 2020 DeptDetailDTO 담는 어레이리스트

    public void setUnivName(String univName) { this.univName = univName; }

    public void setDeptName(String deptName){
        this.deptName = deptName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 학과 상세정보 및 학과 평가 리스트 요청
        requestDeptInf();
    }

    public void requestDeptInf(){
        // 학교 상세정보 페이지에서 학과 상세정보 조회 버튼 클릭하면
        // 타이머 클래스 사용해서 1초뒤 프로토콜 전송
        // FIXME 아무것도 클릭안하고 조회누를때 예외처리
        Timer timer = new Timer();
        // TimerTask는 추상클래스라 람다식 안됨?
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    // 해당 학교, 학과 이름 VO 생성
                    DeptInfoReqVO deptInfoReqVO = new DeptInfoReqVO(univName, deptName);

                    // 학과 상세정보 리스트 요청
                    Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_DETAIL, deptInfoReqVO));

                    // 학과 상세정보 리스트 수신
                    deptDtoList = receiveDeptDetailDTOList();

                    // 학과 평가 리스트 요청
                    Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_RATING_LIST));

                    // 학과 평가 리스트 수신
                    ArrayList<DepartmentRatingDTO> departmentRatingList = receiveDeptRatingDTOList();

                    // FIXME 0 - 2020, 1 - 2019, 2 - 2018
                    Platform.runLater(() -> {
                        // set 학과 상세정보
                        setUnivDeptList(deptDtoList.get(0));

                        // set 학과 평가 리스트
                        setDeptRatingList(departmentRatingList);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public ArrayList<DepartmentDetailDTO> receiveDeptDetailDTOList() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        ArrayList<DepartmentDetailDTO> tmp = new ArrayList<>();   //

        // 타입 처리
        ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
        for(Object obj : ar){
            if(obj instanceof DepartmentDetailDTO){
                tmp.add((DepartmentDetailDTO) obj);
            }
        }

        return tmp;
    }

    public ArrayList<DepartmentRatingDTO> receiveDeptRatingDTOList() {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // 학교 상세정보 3개년치 받아오기 univDtoList
        ArrayList<DepartmentRatingDTO> tmp = new ArrayList<>();   //

        // 타입 처리
        ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
        for(Object obj : ar){
            if(obj instanceof DepartmentRatingDTO){
                tmp.add((DepartmentRatingDTO) obj);
            }
        }

        return tmp;
    }

    // TODO 학과 평가 등록 로직

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

    public void setDeptRatingList(ArrayList<DepartmentRatingDTO> deptRatingList){
        // TODO 학과 평가 리스트 set 필요 tableView
    }
}
