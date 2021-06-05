package Client;

import Client.transmission.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ClientMain extends Application {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int PORT = 5500;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void init() {
        new Connection(SERVER_IP, PORT);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("./resource/font/NotoSansKR-Bold.otf"), 10);    // 외부 폰트 사용

        Parent root = FXMLLoader.load(getClass().getResource("./view/login.fxml"));
        primaryStage.setTitle("로그인");
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
