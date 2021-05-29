package Client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;

public class Popup {
    @FXML
    private Text textPopupContent;

    @FXML
    private Button btnExit;

    @FXML
    void clickBtnExit(MouseEvent event) {
        javafx.stage.Popup s = (javafx.stage.Popup) btnExit.getScene().getWindow();
        s.hide();
    }
}
