package client.controller;

import DTO.SampleDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UnivDetail implements Initializable{

    @FXML
    private TableColumn<?, ?> colStudentNumber;

    @FXML
    private TableColumn<?, ?> colAdmissionCompetitionRate;

    @FXML
    private Circle imgUnivLogo;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void requestUnivInf(MouseEvent event) {
        /*
            직렬화, 역직렬화 추상화 가능?
            현재는 각 컨트롤러에서만 처리중
            통합적으로 처리하는 함수 만들수 있을까
         */
        /* 요청 */
        Protocol pt = new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_INF);  // 학교 상세정보 조회 요청 송신 패킷 생성

        String univName = inputUniv.getText();  // input에 입력한 학교 이름 추출


        byte[] serializedDTO;  // 직렬화 결과가 담기는 바이트
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject("금오공대");  // 객체 직렬화 object(string) 학교이름 to byte array -> packet data에 set

                serializedDTO = baos.toByteArray();
                pt.setPacket(serializedDTO);

                Connection.send(pt);        // 패킷 전송
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* 응답 */
        Protocol receivePT = Connection.receive();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(receivePT.getBody())) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectMember = ois.readObject();  // 역직렬화된 SampleDto 객체를 읽어온다.
                SampleDTO member = (SampleDTO) objectMember;
//                String member = (String) objectMember;
                System.out.println(member.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
