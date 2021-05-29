package Client;

import Client.trasmission.Connection;
import Client.trasmission.Protocol;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        String serverIp = "220.122.124.124";    // 사설 ip 220.122.124.124
        int port = 5500;

        // TODO 서버 접속 실패시 예외처리 필요
        //new Connection(serverIp, port);
        System.out.println("IP : " + serverIp + ", Port : " + port);
        System.out.println("서버 접속 성공");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("./resource/font/NotoSansKR-Bold.otf"), 10);    // 외부 폰트 사용

        Parent root = FXMLLoader.load(getClass().getResource("./view/home.fxml"));
        primaryStage.setTitle("대학 정보 제공 프로그램");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);   // 화면 크기 고정
        primaryStage.show();
    }

    public static void terminate() {
//        Protocol packet = new Protocol(Protocol.PT_EXIT, Protocol.PT_EXIT);
//        Connection.send(packet);    // 종료 패킷 송신
//        Connection.terminate();                 // 소켓 통신 종료
        System.exit(0);                   //클라이언트 종료
    }
}
