package Client.controller;

import Client.transmission.Connection;
import Client.vo.MyPageInfoVO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserDetail implements Initializable {


    @FXML
    Label userEmailLabel;
    @FXML
    Label userNameLabel;
    @FXML
    Label userTelNumLabel;
    @FXML
    Label userAreaLabel;
    @FXML
    Label userResidentRegistrationNumberLabel;
    @FXML
    Label userAffiliatedSchoolLabel;
    @FXML
    Label userAffiliatedDepartmentLabel;


    @FXML
    Label univRatingLabel;
    @FXML
    Label univRatingCreationDateLabel;
    @FXML
    Label univRatingContentLabel;

    @FXML
    Label deptRatingLabel;
    @FXML
    Label deptRatingCreationDateLabel;
    @FXML
    Label deptRatingContentLabel;

    @FXML
    TableView<String> bookMarkTable;
    @FXML
    TableColumn<String, String> bookMarkCol;



    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("UserDetail initialize");
        Runnable runnable = () -> {     // 다른 스레드로 처리
            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_USER_DETAIL));
            Protocol receivePT = Connection.receive();

            MyPageInfoVO myPageInfo = (MyPageInfoVO) receivePT.getBody();

            if(myPageInfo != null) {
                Platform.runLater(() -> {
                    setUserDetailPage(myPageInfo);
                });
            }
        };

        Thread th = new Thread(runnable);
        th.start();


    }

    private void setUserDetailPage(MyPageInfoVO myPageInfo) {

        ArrayList<String> bookMarkList = myPageInfo.getBookmark();

        //사용자 정보 세팅
        userEmailLabel.setText(Login.user.getUserEmail());
        userNameLabel.setText(Login.user.getUserName());
        userTelNumLabel.setText(Login.user.getUserPhoneNumber().toString());
        userAreaLabel .setText(Login.user.getUserArea());
        userResidentRegistrationNumberLabel.setText(Login.user.getResidentRegistrationNumber().toString());
        userAffiliatedSchoolLabel.setText(Login.user.getAffiliatedSchool());
        userAffiliatedDepartmentLabel.setText(Login.user.getAffiliatedDepartment());


        //평가 세팅
        UnivRatingDTO univRating = myPageInfo.getUnivRating();
        DepartmentRatingDTO deptRating = myPageInfo.getDeptRating();

        if(univRating != null){
            univRatingLabel.setText(univRating.getScore().toString());
            univRatingCreationDateLabel.setText(univRating.getCreationDate().toString());
            univRatingContentLabel.setText(univRating.getContent());

        } else {
            univRatingLabel.setText("null");
            univRatingCreationDateLabel.setText("null");
            univRatingContentLabel.setText("null");

        }

        if(deptRating != null){
            deptRatingLabel.setText(deptRating.getScore().toString());
            deptRatingCreationDateLabel.setText(deptRating.getCreationDate().toString());
            deptRatingContentLabel.setText(deptRating.getContent());

        } else {
            deptRatingLabel.setText("null");
            deptRatingCreationDateLabel.setText("null");
            deptRatingContentLabel.setText("null");

        }

        //북마크 세팅팅

        bookMarkCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));

        ObservableList<String> data = FXCollections.observableArrayList(bookMarkList);

        bookMarkTable.setItems(data);



    }




}
