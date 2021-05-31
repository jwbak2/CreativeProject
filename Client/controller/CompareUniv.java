package Client.controller;

import Client.transmission.Connection;
import Server.transmission.Protocol;
import Server.model.dto.UnivDetailDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class CompareUniv implements Initializable {
    @FXML
    private TextField inputFirstUniv;

    @FXML
    private TextField inputSecondUniv;

    @FXML
    private Button BtnCompareUnivDetail;

    @FXML
    private GridPane cpPaneOne1;

    @FXML
    private Label humanitiesSocialTuitionTwo;

    @FXML
    private Label naturalScienceTuitionTwo;

    @FXML
    private Label artMusPhysTuitionTwo;

    @FXML
    private Label engineeringTuitionTwo;

    @FXML
    private Label medicalTuitionTwo;

    @FXML
    private Label dormitoryAccommodationRateTwo;

    @FXML
    private Label dispatchedStudentTwo;

    @FXML
    private Label bookTotalTwo;

    @FXML
    private Label univAreaTwo;

    @FXML
    private Label numOfFulltimeProfessorTwo;

    @FXML
    private Label medicalTuitionOne;

    @FXML
    private Label engineeringTuitionOne;

    @FXML
    private Label artMusPhysTuitionOne;

    @FXML
    private Label naturalScienceTuitionOne;

    @FXML
    private Label numOfPatentRegistrationTwo;

    @FXML
    private Label researchCostPerProfessorTwo;

    @FXML
    private Label numOfPatentRegistrationOne;

    @FXML
    private Label researchCostPerProfessorOne;

    @FXML
    private Label numOfFulltimeProfessorOne;

    @FXML
    private Label univAreaOne;

    @FXML
    private Label bookTotalOne;

    @FXML
    private Label dispatchedStudentOne;

    @FXML
    private Label dormitoryAccommodationRateOne;

    @FXML
    private Label humanitiesSocialTuitionOne;

    @FXML
    private GridPane cpPaneOne;

    @FXML
    private Label studentNumberTwo;

    @FXML
    private Label employmentRateTwo;

    @FXML
    private Label enteringRateTwo;

    @FXML
    private Label educationCostPerPersonTwo;

    @FXML
    private Label totalScholarshipBenefitTwo;

    @FXML
    private Label foundersNumberTwo;

    @FXML
    private Label startCompanySaleTwo;

    @FXML
    private Label startCompanyCapitalTwo;

    @FXML
    private Label schoolStartCompanyFundTwo;

    @FXML
    private Label educationCostPerPersonOne;

    @FXML
    private Label enteringRateOne;

    @FXML
    private Label employmentRateOne;

    @FXML
    private Label admissionCompetitionRateOne;

    @FXML
    private Label admissionFeeTwo;

    @FXML
    private Label staffForStartCompanyTwo;

    @FXML
    private Label profesorForStartCompanyTwo;

    @FXML
    private Label govermentStartCompanyFundTwo;

    @FXML
    private Label staffForStartCompanyOne;

    @FXML
    private Label profesorForStartCompanyOne;

    @FXML
    private Label govermentStartCompanyFundOne;

    @FXML
    private Label schoolStartCompanyFundOne;

    @FXML
    private Label startCompanySaleOne;

    @FXML
    private Label foundersNumberOne;

    @FXML
    private Label totalScholarshipBenefitOne;

    @FXML
    private Label admissionFeeOne;

    @FXML
    private Label averageTuitionOne;

    @FXML
    private Label studentNumberOne;

    @FXML
    private Label admissionCompetitionRateTwo;

    @FXML
    private Label startCompanyCapitalOne1;

    @FXML
    private Label firstUnivName;

    @FXML
    private Label secondUnivName;

    private static final int FIRST_UNIV_DETAIL = 0;
    private static final int SECOND_UNIV_DETAIL = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        TextFields.bindAutoCompletion(inputFirstUniv, Home.getUnivList()); // 텍스트필드 자동완성
//        TextFields.bindAutoCompletion(inputSecondUniv, Home.getUnivList()); // 텍스트필드 자동완성
    }

    @FXML
    void compareUnivDetail(MouseEvent event) {

        Runnable runnable = () -> {
            try {
                String[] univList = new String[2];

                univList[0] = inputFirstUniv.getText().replace(" ", "");
                univList[1] = inputSecondUniv.getText().replace(" ", "");

                Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_CP, univList));        // 패킷 전송

                ArrayList<UnivDetailDTO> receivedUnivDetailList = receiveUnivCp();

                Platform.runLater(() -> {
                    setUnivDetailInf(receivedUnivDetailList);    // 첫번쨰 학교 상세정보 화면에 세팅
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread th = new Thread(runnable);
        th.start();
    }

    void sendUnivCp(String[] univList) {
        // 학교 상세정보 조회 요청 송신 패킷 생성

        System.out.println("학교 상세정보 비교 요청");
    }

    public ArrayList<UnivDetailDTO> receiveUnivCp() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // TODO 학교이름 찾기 실패 패킷 수신 예외처리

        ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

        try {
            ArrayList<?> tmp = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정

            // 타입 처리
            for (Object obj : tmp) {
                if (obj instanceof UnivDetailDTO) {
                    result.add((UnivDetailDTO) obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;  // UnivDTO 타입인 Object 반환
    }

    public void setUnivDetailInf(ArrayList<UnivDetailDTO> receivedUnivDetailList) {  // UnivDetailDTO GUI에 뿌려주기
        UnivDetailDTO firstUnivDetail = receivedUnivDetailList.get(FIRST_UNIV_DETAIL);
        UnivDetailDTO secondUnivDetail = receivedUnivDetailList.get(SECOND_UNIV_DETAIL);

        studentNumberOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getStudentNumber()));
        studentNumberTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getStudentNumber()));
        compareUnivDetailElement(studentNumberOne, studentNumberTwo, firstUnivDetail.getStudentNumber(), secondUnivDetail.getStudentNumber());


    }

    public void compareUnivDetailElement(Label n1, Label n2, Long el1, Long el2) {
        if (el1 < el2) {
            n1.setTextFill(Color.RED);
        } else {
            n2.setTextFill(Color.RED);
        }
    }
}
