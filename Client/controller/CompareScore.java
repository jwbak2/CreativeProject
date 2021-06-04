package Client.controller;

import Client.transmission.Connection;
import Server.transmission.Protocol;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompareScore implements Initializable {

    @FXML
    private AnchorPane firstAP;

    @FXML
    private ComboBox<?> comboFirstIndicator;

    @FXML
    private ComboBox<?> comboSecondIndicator;

    @FXML
    private ComboBox<?> comboThirdIndicator;

    @FXML
    private TableView<?> tableDept;

    @FXML
    private TableColumn<?, ?> colSelectedUniv;

    @FXML
    private TableColumn<?, ?> colSelectedDept;

    @FXML
    private Button btnAddDept;

    @FXML
    private TextField inputDeptName;

    @FXML
    private Button btnSelectScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Text 감지 테스트
        Pattern pattern = Pattern.compile("대학교$");
        SuggestionProvider<String> provider = SuggestionProvider.create(Home.getUnivList());
        new AutoCompletionTextFieldBinding<>(inputDeptName, provider);

        inputDeptName.textProperty().addListener((observable, oldValue, newValue) -> {
            Matcher matcher = pattern.matcher(newValue);

            if (matcher.find()) {
                Runnable runnable = () -> {
                    try {
                        String univName = inputDeptName.getText().split(" ")[0];

                        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_LIST_OF_UNIV, univName));        // 패킷 전송

                        Protocol pt = Connection.receive();

                        ArrayList<String> univDeptList = (ArrayList<String>) pt.getBody();

                        provider.clearSuggestions();
                        provider.addPossibleSuggestions(univDeptList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                Thread th = new Thread(runnable);
                th.start();

            }
            if (inputDeptName.getText().equals("")){
                provider.clearSuggestions();
                provider.addPossibleSuggestions(Home.getUnivList());
            }

        });

    }

    @FXML
    void clickAddDept(MouseEvent event) {

    }


}
