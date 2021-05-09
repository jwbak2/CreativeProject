package Client.controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private ImageView imgUser;

    @FXML
    private Circle test;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("Client/resource/img/bonobono.jpg");
        test.setFill(new ImagePattern(img));
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


}
