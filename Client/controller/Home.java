package Client.controller;

import Client.transmission.Connection;
import Server.model.dto.UnivDetailDTO;
import Server.transmission.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private ImageView imgUser;

    @FXML
    private Circle profile;

    private static ArrayList<String> univList;

    public static ArrayList<String> getUnivList(){
        return Home.univList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 프로필 이미지
        Image img = new Image("Client/resource/img/bonobono.jpg");
        profile.setFill(new ImagePattern(img));

        // 학교 리스트 요청
        univList = new ArrayList<String>();
        requestUnivList();
    }

    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    void univDetail(MouseEvent event) { // sidebar 선택 이벤트 핸들러
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/univDetail.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    void compareScore(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/compareScore.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    void compareUnivDepartment(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/compareDepartment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    void userDetail(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userDetail.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    void userStatistics(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userStatistics.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    void compareUniv(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/compareUniv.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    void requestUnivList(){
        System.out.println("학교 리스트 요청");
        // 학교 상세정보 조회 요청 송신 패킷 생성
        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_LIST));        // 패킷 전송

        Protocol receivePT = Connection.receive();  // receive data

        try {

            Object receivedBody = receivePT.getBody();

            // 타입 처리
            ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
            for (Object obj : ar) {
                if (obj instanceof String) {
                    univList.add((String) obj);
                }
            }
        }catch(Exception e){

            System.out.println("학교 리스트 수신 실패");

        }
    }
}
