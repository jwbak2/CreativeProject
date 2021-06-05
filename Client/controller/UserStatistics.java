package Client.controller;

import Client.transmission.Connection;
import Client.view.tablemodel.RankInfo;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserStatistics implements Initializable {

    @FXML
    private TableView<RankInfo> tableViewOfUniv;

    @FXML
    private TableColumn<?, ?> colUnivRanking;

    @FXML
    private TableColumn<?, ?> colUnivName;

    @FXML
    private TableView<RankInfo> tableViewOfIndicator;

    @FXML
    private TableColumn<?, ?> colIndicatorRanking;

    @FXML
    private TableColumn<?, ?> colIndicatorName;

    private ObservableList<RankInfo> univList;

    private ObservableList<RankInfo> indicatorList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        univList = FXCollections.observableArrayList();
        indicatorList = FXCollections.observableArrayList();

        colUnivRanking.setCellValueFactory(new PropertyValueFactory<>("rank"));
        colUnivName.setCellValueFactory(new PropertyValueFactory<>("content"));

        colIndicatorRanking.setCellValueFactory(new PropertyValueFactory<>("rank"));
        colIndicatorName.setCellValueFactory(new PropertyValueFactory<>("content"));

        requestUserStatistics();
    }

    private void requestUserStatistics(){
        Runnable runnable = () -> {

            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_USER_STATS));

            Protocol receivePT = Connection.receive();
            Object receivedBody = receivePT.getBody();

            ArrayList<ArrayList> receiveList = new ArrayList<>();   //

            // 타입 처리
            // 서버에서 ArrayList 2개를 포함하는 ArrayList 수신함
            ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
            for (Object obj : ar) {
                if (obj instanceof ArrayList) {
                    receiveList.add((ArrayList) obj);
                }
            }

            System.out.println(receiveList.size());

            // univView ArrayList
            for(int i = 0; i < receiveList.get(0).size(); i++){
                univList.add(new RankInfo(new SimpleStringProperty(Integer.toString(i + 1))
                        ,new SimpleStringProperty((String) receiveList.get(0).get(i))));
            }

            // indicatorView ArrayList
            for(int i = 0; i < receiveList.get(1).size(); i++){
                indicatorList.add(new RankInfo(new SimpleStringProperty(Integer.toString(i + 1))
                        ,new SimpleStringProperty((String) receiveList.get(1).get(i))));
            }

            Platform.runLater(() -> {
                tableViewOfUniv.setItems(univList);
                tableViewOfIndicator.setItems(indicatorList);
            });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
