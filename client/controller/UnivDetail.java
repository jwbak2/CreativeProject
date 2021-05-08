package client.controller;

import DTO.UnivDTO;
import DTO.UnivDetailDTO;
import client.controller.trasmission.Connection;
import client.controller.trasmission.Protocol;
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

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
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
        mainAp.setVisible(false);   // 처음에 hide 였다가 조회누르면 show되게
    }

    @FXML
    void clickRequestBtn(MouseEvent event) {
        /*
            직렬화, 역직렬화 추상화? 가능?
            현재는 각 컨트롤러에서만 처리중
            통합적으로 처리하는 함수 만들수 있을까
            -> 중간발표 이후 처리할게요

            잘못 입력했을때 예외처리 필요 클라이언트 - 서버 둘다
            -> 실패 패킷?
         */
        // input에 입력한 학교 이름 추출 + 공백 제거
        String univName = inputUniv.getText().replace(" ","");

        try {
            if (univName.equals("")){               // 공백일시 예외처리
                throw new Exception("univName of input is null");
            }

            requestUnivInf(univName);   // 학교 상세정보 요청

            UnivDTO univDTO = (UnivDTO) receiveDTO();       // 학교 정보 receive
            setUnivInf(univDTO);

            UnivDetailDTO univDetailDTO = (UnivDetailDTO) receiveDTO(); // 학교 상세정보 receive
            setUnivDetailInf(univDetailDTO);

            mainAp.setVisible(true);   // 처음에 hide 였다가 조회누르면 show되게
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void requestUnivInf(String univName){
        Protocol pt = new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_INF);  // 학교 상세정보 조회 요청 송신 패킷 생성

        byte[] serializedDTO;  // 직렬화 결과가 담기는 바이트
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(univName);  // 객체 직렬화 object(string) 학교이름 to byte array -> packet data에 set

                serializedDTO = baos.toByteArray();
                pt.setPacket(serializedDTO);

                Connection.send(pt);        // 패킷 전송
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object receiveDTO() throws Exception {
        /*
            실패 로직 추가 필요
         */
        Protocol receivePT = Connection.receive();

        if (receivePT.getProtocolType() == Protocol.PT_FAIL 
                && receivePT.getProtocolCode() == Protocol.PT_FAIL_UNIV_INF){    // 입력한 학교명이 존재하지 않을떄
            throw new Exception("입력한 학교명은 존재하지 않습니다.");             // 실패 패킷 수신 예외처리 
        }

        Object objectMember = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(receivePT.getBody())) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                objectMember = ois.readObject();  // 역직렬화된 SampleDto 객체를 읽어온다.
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return objectMember;
    }

    public void setUnivInf(UnivDTO univDTO){    // UnivDTO GUI에 뿌려주기
        Image univLogo = new Image(Arrays.toString(univDTO.getUnivLogoImageFile()));
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
        studentNumber.setText(String.valueOf(univDetailDTO.getStudentNumber()));
        admissionCompetitionRate.setText(String.valueOf(univDetailDTO.getAdmissionCompetitionRate()));
        employmentRate.setText(String.valueOf((univDetailDTO.getEmploymentRate())));
        enteringRate.setText(String.valueOf(univDetailDTO.getEnteringRate()));
        educationCostPerPerson.setText(String.valueOf(univDetailDTO.getEducationCostPerPerson()));
        totalScholarshipBenefit.setText(String.valueOf(univDetailDTO.getTotalScholarshipBenefits()));

        foundersNumber.setText(String.valueOf(univDetailDTO.getNumberFounders()));
        startCompanySale.setText(String.valueOf(univDetailDTO.getStartCompanySales()));
        startCompanyCapital.setText(String.valueOf(univDetailDTO.getStartCompanyCapital()));
        schoolStartCompanyFund.setText(String.valueOf(univDetailDTO.getSchoolStartCompanyFund()));
        govermentStartCompanyFund.setText(String.valueOf(univDetailDTO.getGovernmentStartCompanyFund()));
        profesorForStartCompany.setText(String.valueOf(univDetailDTO.getProfessorForStartCompany()));
        staffForStartCompany.setText(String.valueOf(univDetailDTO.getStaffForStartCompany()));

        admissionFee.setText(String.valueOf(univDetailDTO.getAdmissionFee()));
        averageTuition.setText(String.valueOf(univDetailDTO.getAverageTuition()));
        humanitiesSocialTuition.setText(String.valueOf(univDetailDTO.getHumanitiesSocialTuition()));
        naturalScienceTuition.setText(String.valueOf(univDetailDTO.getNaturalScienceTuition()));
        artMusPhysTuition.setText(String.valueOf(univDetailDTO.getArtMusPhysTuition()));
        engineeringTuition.setText(String.valueOf(univDetailDTO.getEngineeringTuition()));
        medicalTuition.setText(String.valueOf(univDetailDTO.getMedicalTuition()));
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
