package Client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Home {

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

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
