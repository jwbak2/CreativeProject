package client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
        Protocol pt = new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_INF);  // 학교 상세정보 조회 요청 패킷 생성

        String univName = inputUniv.getText();  // input에 입력한 학교 이름 추출

        System.out.println(univName);

        pt.setPacket(univName.getBytes());      // 학교이름 to byte array -> packet data에 set
        Connection.send(pt.getPacket());        // 패킷 전송
    }
}
