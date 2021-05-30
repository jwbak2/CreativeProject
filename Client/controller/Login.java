package Client.controller;

import Client.transmission.Connection;
import Client.vo.LoginReqVO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldPW;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            requestLogin();
        });

    }

    private void requestLogin(){
        Runnable runnable = () -> {

            // 로그인 정보 객체 생성하여 서버로 전송
            LoginReqVO loginReqInfo = new LoginReqVO(textFieldID.getText(), textFieldPW.getText());
            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_LOGIN, loginReqInfo));


            // 수신하여 처리
            receiveLoginResult();
        };


        Thread th = new Thread(runnable);
        th.start();

    }

    private void receiveLoginResult() {

        Protocol receivePT = Connection.receive();

        if (receivePT.getProtocolType() == Protocol.PT_SUCC
                && receivePT.getProtocolCode() == Protocol.PT_SUCC_LOGIN) {

            // 로그인 성공 : 홈 화면
            Platform.runLater(this::showHomePage);

        } else {

            // 로그인 실패 : 실패 팝업
            Platform.runLater(this::showLoginFailPopUp);

        }


    }

    private void showHomePage(){

        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("./view/home.fxml"));
            primaryStage.setTitle("대학 정보 제공 프로그램");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);   // 화면 크기 고정
            primaryStage.show();
        } catch(IOException E){
            E.printStackTrace();

        }

    }
    private void showLoginFailPopUp(){

        try {
            Stage stage = (Stage) btnLogin.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/loginFail.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
