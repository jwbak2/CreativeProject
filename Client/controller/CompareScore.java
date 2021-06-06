package Client.controller;

import Client.transmission.Connection;
import Client.view.tablemodel.ResultScore;
import Client.view.tablemodel.UnivAndDept;
import Client.vo.CustomizedRankReqVO;
import Client.vo.CustomizedRankResVO;
import Client.vo.DeptInfoReqVO;
import Server.transmission.Protocol;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private TableView<UnivAndDept> tableDept;

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

    @FXML
    private Button btnResetDeptTable;

    @FXML
    private TableView<ResultScore> tableResultScore;

    @FXML
    private TableColumn<?, ?> colResultUniv;

    @FXML
    private TableColumn<?, ?> colResultDept;

    @FXML
    private TableColumn<?, ?> colResultScore;

    @FXML
    private TableColumn<?, ?> colFirstScore;

    @FXML
    private TableColumn<?, ?> colSecondScore;

    @FXML
    private TableColumn<?, ?> colThirdScore;

    private ObservableList<UnivAndDept> selectedDeptList;

    private ObservableList<ResultScore> resultScoreList;

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
                        for(int i = 0; i < univDeptList.size(); i++){
                            univDeptList.set(i, univName+ " " + univDeptList.get(i));
                        }

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

        // 테이블 컬럼 set
        colSelectedUniv.setCellValueFactory(new PropertyValueFactory<>("univ"));
        colSelectedDept.setCellValueFactory(new PropertyValueFactory<>("dept"));

        colResultUniv.setCellValueFactory(new PropertyValueFactory<>("univName"));
        colResultDept.setCellValueFactory(new PropertyValueFactory<>("deptName"));
        colResultScore.setCellValueFactory(new PropertyValueFactory<>("scoreOfIdct1"));
        colFirstScore.setCellValueFactory(new PropertyValueFactory<>("scoreOfIdct2"));
        colSecondScore.setCellValueFactory(new PropertyValueFactory<>("scoreOfIdct3"));
        colThirdScore.setCellValueFactory(new PropertyValueFactory<>("scoreOfTotal"));

        // selectedDeptList 초기화
        selectedDeptList = FXCollections.observableArrayList();

        // resultScoreList 초기화
        resultScoreList = FXCollections.observableArrayList();
    }

    // 학과 추가
    @FXML
    void clickAddDept(MouseEvent event) {
        if (inputDeptName.getText() == null){
            // TODO 예외처리 필요
            System.out.println("학과 이름을 검색해주세요");
            return;
        }

        String univName = inputDeptName.getText().split(" ")[0];
        String deptName = inputDeptName.getText().split(" ")[1];

        UnivAndDept univAndDept = new UnivAndDept(new SimpleStringProperty(univName), new SimpleStringProperty(deptName));

        selectedDeptList.add(univAndDept);
        tableDept.setItems(selectedDeptList);
        inputDeptName.clear();
    }

    @FXML
    void clickSelectCustomRating(MouseEvent event) {
        Runnable runnable = () -> {
            try {
                // 학과
                ArrayList<DeptInfoReqVO> deptList = new ArrayList<>();

                for(int i = 0; i < selectedDeptList.size(); i++){
                    String univName = selectedDeptList.get(i).getUniv();
                    String deptName = selectedDeptList.get(i).getDept();

                    deptList.add(new DeptInfoReqVO(univName, deptName));
                }

                // 지표
                ArrayList<String> indicatorList = new ArrayList<>();

                indicatorList.add((String) comboFirstIndicator.getValue());
                indicatorList.add((String) comboSecondIndicator.getValue());
                indicatorList.add((String) comboThirdIndicator.getValue());

                CustomizedRankReqVO customizedRankReqVO = new CustomizedRankReqVO(deptList, indicatorList);


                Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_CUSTOM_RANKING, customizedRankReqVO));        // 패킷 전송

                Protocol pt = Connection.receive();

                ArrayList<CustomizedRankResVO> rankList = (ArrayList<CustomizedRankResVO>) pt.getBody();

                for(int i = 0; i < rankList.size(); i++){
                    resultScoreList.add(
                        new ResultScore(
                            new SimpleStringProperty(rankList.get(i).getUnivName()),
                            new SimpleStringProperty(rankList.get(i).getDeptName()),
                            new SimpleStringProperty(Double.toString(rankList.get(i).getScoreOfIdct1())),
                            new SimpleStringProperty(Double.toString(rankList.get(i).getScoreOfIdct2())),
                            new SimpleStringProperty(Double.toString(rankList.get(i).getScoreOfIdct3())),
                            new SimpleStringProperty(Double.toString(rankList.get(i).getScoreOfTotal()))
                        )
                    );
                }

                tableResultScore.setItems(resultScoreList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread th = new Thread(runnable);
        th.start();
    }

    // 초기화 버튼 클릭 시
    @FXML
    void clickResetDeptTable(MouseEvent event) {
        tableDept.getItems().clear();
        tableResultScore.getItems().clear();

        selectedDeptList = FXCollections.observableArrayList();
        resultScoreList = FXCollections.observableArrayList();
    }
}
