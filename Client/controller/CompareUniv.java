package Client.controller;

import Client.trasmission.Connection;
import Client.trasmission.Protocol;
import Server.model.dto.UnivDetailDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        TextFields.bindAutoCompletion(inputFirstUniv, Home.getUnivList()); // 텍스트필드 자동완성
//        TextFields.bindAutoCompletion(inputSecondUniv, Home.getUnivList()); // 텍스트필드 자동완성
    }

    @FXML
    void compareUnivDetail(MouseEvent event) {
        ArrayList<String> univList = new ArrayList<String>();

        String firstUniv = inputFirstUniv.getText().replace(" ", "");
        String secondUniv = inputSecondUniv.getText().replace(" ", "");

        univList.add(firstUniv);
        univList.add(secondUniv);

        requestTwoOfUnivDetail(univList);

        ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

        try {
            ArrayList<?> tmp = (ArrayList<?>) receiveUnivDTO();  // 읽어온 어레이리스트 처리 과정
            for(Object obj : tmp){
                if(obj instanceof UnivDetailDTO){
                    result.add((UnivDetailDTO) obj);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // FIXME  0, 1 코드 수정할거임 test용
        setUnivDetailInf(result.get(0));    // 첫번쨰 학교 상세정보 화면에 세팅
        setUnivDetailInf(result.get(1));    // 두번쨰 학교 상세정보 화면에 세팅
    }

    void requestTwoOfUnivDetail(ArrayList<String> univList) {
        Protocol pt = new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_CP);  // 학교 상세정보 조회 요청 송신 패킷 생성

        byte[] serializedDTO;  // 직렬화 결과가 담기는 바이트
        serializedDTO = Connection.serializeDTO(univList);
        pt.setPacket(serializedDTO);

        System.out.println("학교 상세정보 비교 요청");
        Connection.send(pt);        // 패킷 전송
    }

    public Object receiveUnivDTO() throws Exception {
        Protocol receivePT = Connection.receive();

        // 입력한 학교명이 존재하지 않을때
//        if (receivePT.getProtocolType() == Protocol.PT_FAIL
//                && receivePT.getProtocolCode() == Protocol.PT_FAIL_UNIV_INF) {    // 입력한 학교명이 존재하지 않을떄
//
//            try{
//                Stage stage = (Stage) BtnCompareUnivDetail.getScene().getWindow();
//                Popup pu = new Popup();
//                Parent root = FXMLLoader.load(getClass().getResource("../view/popup.fxml"));
//
//                pu.getContent().add(root);
//                pu.setAutoHide(true); // 포커스 이동시 창 숨김
//                pu.show(stage);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//
//            throw new Exception("입력한 학교명은 존재하지 않습니다.");             // 실패 패킷 수신 예외처리
//        }

        return Connection.deserializeDTO(receivePT.getBody());  // 역직렬화된 객체가 담기는 Object 반환
    }

    public void setUnivDetailInf(UnivDetailDTO univDetailDTO) {  // UnivDetailDTO GUI에 뿌려주기
        studentNumberOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStudentNumber()));
        admissionCompetitionRateOne.setText(univDetailDTO.getAdmissionCompetitionRate() + "%");
        employmentRateOne.setText(univDetailDTO.getEmploymentRate() + "%");
        enteringRateOne.setText(univDetailDTO.getEnteringRate() + "%");
        educationCostPerPersonOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEducationCostPerPerson()));
        totalScholarshipBenefitOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getTotalScholarshipBenefits()));

        foundersNumberOne.setText(String.valueOf(univDetailDTO.getNumberFounders()));
        startCompanySaleOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanySales()));
        //startCompanyCapitalOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanyCapital()));
        schoolStartCompanyFundOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getSchoolStartCompanyFund()));
        govermentStartCompanyFundOne.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getGovernmentStartCompanyFund()));
        profesorForStartCompanyOne.setText(String.valueOf(univDetailDTO.getProfessorForStartCompany()));
        staffForStartCompanyOne.setText(String.valueOf(univDetailDTO.getStaffForStartCompany()));

        admissionFeeTwo.setText(String.valueOf(univDetailDTO.getAdmissionFee()));
        //averageTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getAverageTuition()));
        humanitiesSocialTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getHumanitiesSocialTuition()));
        naturalScienceTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getNaturalScienceTuition()));
        artMusPhysTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getArtMusPhysTuition()));
        engineeringTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEngineeringTuition()));
        medicalTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getMedicalTuition()));

        dormitoryAccommodationRateTwo.setText(String.valueOf(univDetailDTO.getDormitoryAccommodationRate()));
        dispatchedStudentTwo.setText(String.valueOf(univDetailDTO.getDispatchedStudent()));
        bookTotalTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getBookTotal()));
        univAreaTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getUnivArea()));
        numOfFulltimeProfessorTwo.setText(String.valueOf(univDetailDTO.getNumOfFulltimeProfessor()));
        researchCostPerProfessorTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getResearchCostPerProfessor()));
        numOfPatentRegistrationTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getNumOfPatentRegistration()));
    }

    public void compareUnivDetailElement(Long el1, Long el2){

    }
}
