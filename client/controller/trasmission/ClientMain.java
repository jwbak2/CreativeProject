package client.controller.trasmission;

import client.controller.trasmission.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String serverIp = "192.168.231.243";    // 사설 ip
        int port = 5500;

//        new Connection(serverIp, port);   // test
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("../resource/font/NotoSansKR-Bold.otf"), 10);    // 외부 폰트 사용

        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        primaryStage.setTitle("대학 정보 제공 프로그램");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);   // 화면 크기 고정
        primaryStage.show();
    }

    public static void terminate(){
//        Protocol packet = new Protocol(Protocol.PT_EXIT, Protocol.PT_EXIT);
//        Connection.send(packet.getPacket());    // 종료 패킷 송신
        try {
            Connection.is.close();
            Connection.os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);                   //클라이언트 종료
    }
}
