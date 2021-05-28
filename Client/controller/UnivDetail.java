package Client.controller;

import Server.model.dto.UnivDetailDTO;
import Server.model.dto.UnivDTO;
import Client.trasmission.Connection;
import Client.trasmission.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class UnivDetail implements Initializable{

    @FXML
    private ImageView imageUnivLogo;

    @FXML
    private Text textUnivName;

    @FXML
    private Button btnHomepage;

    @FXML
    private Text textUnivAddress;

    @FXML
    private Text textUnivType;

    @FXML
    private Text textUnivEstablishmentCls;

    @FXML
    private Text textUnivRepresentativeNumber;

    @FXML
    private Label textUnivIntroduction;

    @FXML
    private Button btnRequestUnivInf;

    @FXML
    private TextField inputUniv;

    @FXML
    private Label studentNumber;

    @FXML
    private Label admissionCompetitionRate;

    @FXML
    private Label employmentRate;

    @FXML
    private Label enteringRate;

    @FXML
    private Label educationCostPerPerson;

    @FXML
    private Label totalScholarshipBenefit;

    @FXML
    private Label foundersNumber;

    @FXML
    private Label staffForStartCompany;

    @FXML
    private Label profesorForStartCompany;

    @FXML
    private Label govermentStartCompanyFund;

    @FXML
    private Label schoolStartCompanyFund;

    @FXML
    private Label startCompanyCapital;

    @FXML
    private Label startCompanySale;

    @FXML
    private Label admissionFee;

    @FXML
    private Label medicalTuition;

    @FXML
    private Label engineeringTuition;

    @FXML
    private Label artMusPhysTuition;

    @FXML
    private Label naturalScienceTuition;

    @FXML
    private Label humanitiesSocialTuition;

    @FXML
    private Label averageTuition;

    @FXML
    private AnchorPane mainAp;

    private String homepageURL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        String[] test = {"park", "pasd", "paaaaa", "appppp", "jin", "woo"};   // 자동완성 테스트용
//        TextFields.bindAutoCompletion(inputUniv, test); // 텍스트필드 자동완성

//        mainAp.setVisible(false);   // 처음에 hide 였다가 조회누르면 show되게
    }

    @FXML
    void clickRequestBtn(MouseEvent event) {
        // input에 입력한 학교 이름 추출 + 공백 제거
        String univName = inputUniv.getText().replace(" ","");
        System.out.println("입력한 대학교 : " + univName);

        try {
            if (univName.equals("")){               // 공백일시 예외처리
                throw new Exception("univName of input is null");
            }
            requestUnivInf(univName);   // 학교 상세정보 요청

            UnivDTO univDTO = (UnivDTO) receiveUnivDTO();       // 학교 정보 receive
            System.out.println("UnivInf DTO 수신 완료 ");
            setUnivInf(univDTO);
            System.out.println("학교 정보 GUI 출력 완료");

            UnivDetailDTO univDetailDTO = (UnivDetailDTO) receiveUnivDTO(); // 학교 상세정보 receive
            System.out.println("UnivDetail DTO 수신 완료");
            setUnivDetailInf(univDetailDTO);
            System.out.println("학교 상세정보 GUI 출력 완료");

//            mainAp.setVisible(true);   // 처음에 hide 였다가 조회누르면 show되게
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void requestUnivInf(String univName){
        Protocol pt = new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_INF);  // 학교 상세정보 조회 요청 송신 패킷 생성

        byte[] serializedDTO;  // 직렬화 결과가 담기는 바이트
        serializedDTO = Connection.serializeDTO(univName);
        pt.setPacket(serializedDTO);

        System.out.println("학교 상세정보 조회 요청");
        Connection.send(pt);        // 패킷 전송
    }

    public Object receiveUnivDTO() throws Exception {
        Protocol receivePT = Connection.receive();

        if (receivePT.getProtocolType() == Protocol.PT_FAIL 
                && receivePT.getProtocolCode() == Protocol.PT_FAIL_UNIV_INF){    // 입력한 학교명이 존재하지 않을떄
            throw new Exception("입력한 학교명은 존재하지 않습니다.");             // 실패 패킷 수신 예외처리
        }

        return Connection.deserializeDTO(receivePT.getBody());  // 역직렬화된 객체가 담기는 Object 반환
    }

    public void setUnivInf(UnivDTO univDTO){    // UnivDTO GUI에 뿌려주기
        // ByteArrayInputStream 알아보기
        Image univLogo = new Image(new ByteArrayInputStream(univDTO.getUnivLogoImageFile()));
        imageUnivLogo.setImage(univLogo);
        textUnivName.setText(univDTO.getUnivName());
        homepageURL = univDTO.getUnivHomepageUrl(); // homepage url은 멤버변수로 따로 저장
                                                    // -> 하이퍼링크 클릭 이벤트에서 DTO 정보 읽을수 없어서 변수로 처리
        textUnivAddress.setText(univDTO.getUnivAddress());
        textUnivRepresentativeNumber.setText(univDTO.getUnivRepresentativeNumber());
        textUnivType.setText(univDTO.getUnivType());
        textUnivEstablishmentCls.setText(univDTO.getUnivEstablishmentCls());
        textUnivIntroduction.setText(univDTO.getUnivIntroduction());
    }

    public void setUnivDetailInf(UnivDetailDTO univDetailDTO){  // UnivDetailDTO GUI에 뿌려주기
        studentNumber.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStudentNumber()));
        admissionCompetitionRate.setText(univDetailDTO.getAdmissionCompetitionRate() + "%");
        employmentRate.setText(univDetailDTO.getEmploymentRate() + "%");
        enteringRate.setText(univDetailDTO.getEnteringRate() + "%");
        educationCostPerPerson.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEducationCostPerPerson()));
        totalScholarshipBenefit.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getTotalScholarshipBenefits()));

        foundersNumber.setText(String.valueOf(univDetailDTO.getNumberFounders()));
        startCompanySale.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanySales()));
        startCompanyCapital.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanyCapital()));
        schoolStartCompanyFund.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getSchoolStartCompanyFund()));
        govermentStartCompanyFund.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getGovernmentStartCompanyFund()));
        profesorForStartCompany.setText(String.valueOf(univDetailDTO.getProfessorForStartCompany()));
        staffForStartCompany.setText(String.valueOf(univDetailDTO.getStaffForStartCompany()));

        admissionFee.setText(String.valueOf(univDetailDTO.getAdmissionFee()));
        averageTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getAverageTuition()));
        humanitiesSocialTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getHumanitiesSocialTuition()));
        naturalScienceTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getNaturalScienceTuition()));
        artMusPhysTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getArtMusPhysTuition()));
        engineeringTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEngineeringTuition()));
        medicalTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getMedicalTuition()));
    }

    @FXML
    void moveHyperLink(MouseEvent event) {  // 홈페이지 URL 하이퍼 링크 event handler
        try {
            Desktop.getDesktop().browse(new URI(homepageURL));  // 버튼 누를시 브라우져 생성
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
