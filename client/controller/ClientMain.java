package client.controller;

import com.sun.media.sound.SoftTuning;
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
    public void init() throws Exception {
        String serverIp = "192.168.231.243";
        int port = 5500;

        new Connection(serverIp, port);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("./resource/font/NotoSansKR-Bold.otf"), 10);    // 외부 폰트 사용

        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        primaryStage.setTitle("대학 정보 제공 프로그램");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);   // 화면 크기 고정
        primaryStage.show();
    }


}
