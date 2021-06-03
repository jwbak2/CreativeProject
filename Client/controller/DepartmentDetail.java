package Client.controller;

import Client.transmission.Connection;
import Client.vo.DeptInfoReqVO;
import Client.vo.RatingInfo;
import Client.vo.RatingVO;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.DepartmentRatingDTO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentDetail implements Initializable{

    @FXML
    private Text textDeptName;

    @FXML
    private Label admissionFee;

    @FXML
    private Label tuition;

    @FXML
    private Label outSchoolScholarship;

    @FXML
    private Label inSchoolScholarship;

    @FXML
    private Label scholarshipPerPerson;

    @FXML
    private Label numOfFulltimeProfessor;

    @FXML
    private Label thesisResultPerProfessor;

    @FXML
    private Label rearchCostPerProfessor;

    @FXML
    private Label maleGr;

    @FXML
    private Label femaleGr;

    @FXML
    private Label maleEmploymentTarget;

    @FXML
    private Label femaleEmploymentTarget;

    @FXML
    private Label employmentRate;

    @FXML
    private Label enteringRate;

    @FXML
    private Label enteringDomCmntyColl;

    @FXML
    private Label enteringOverseasCmntyColl;

    @FXML
    private Label enteringDomUniv;

    @FXML
    private Label enteringOverseasUniv;

    @FXML
    private Label enteringDomGrSchool;

    @FXML
    private Label enteringOverseasGrSchool;

    @FXML
    private Label domScholarNumber;

    @FXML
    private Label overseasScholarNumber;

    @FXML
    private Label maleDomEmployee;

    @FXML
    private Label femaleDomEmployee;

    @FXML
    private Label maleOverseasEmployee;

    @FXML
    private Label femaleOverseasEmployee;

    @FXML
    private Button btnDeptDetailExit;

    @FXML
    private AnchorPane apDeptDetail;

    @FXML
    private Tab tabDeptRating;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private ComboBox<String> comboIndicator;

    @FXML
    private TableView<RatingInfo> tableDeptRating;

    @FXML
    private TextField inputRatingContent;

    @FXML
    private org.controlsfx.control.Rating deptRating;

    @FXML
    private TableColumn<RatingInfo, String> colRatingDate;

    @FXML
    private TableColumn<RatingInfo, String> colRatingContent;

    @FXML
    private TableColumn<RatingInfo, org.controlsfx.control.Rating> colRatingScore;

    private ObservableList<RatingInfo> observableList;

    private String univName;         // 해당 학과의 학교 이름

    private String deptName;        // 학교 상세정보에서 누른 학과 이름

    private ArrayList<DepartmentDetailDTO> deptDtoList;   // 2018 ~ 2020 DeptDetailDTO 담는 어레이리스트

    public void setUnivName(String univName) { this.univName = univName; }

    public void setDeptName(String deptName){
        this.deptName = deptName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 학과 상세정보 및 학과 평가 리스트 요청
//        if(deptName != null){   // 아무것도 클릭안하고 조회누를때 예외처리
//            textDeptName.setText(deptName);
//
//            requestDeptInf();
//        }

        // Text 감지 테스트
        Pattern logTimePattern = Pattern.compile("대학교$");

        inputRatingContent.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            Matcher logTimeMatcher = logTimePattern.matcher(newValue);

            if(logTimeMatcher.find()){
                //가장 첫번째 감지하는 부분이 Group이 아니기에 1번째로 지정합니다.
                System.out.println("ㅁㄴㅇㅁㄴㅇ"); // 50
            }

        });

        // tablecolumn cell value 설정
        colRatingDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        colRatingContent.setCellValueFactory(new PropertyValueFactory<>("ratingContent"));
        colRatingScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        // tablecolumn cell 렌더링 설정
        colRatingContent.setCellFactory(tc -> {
            TableCell<RatingInfo, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(colRatingContent.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
    }

    // double 값받아서 RatingInfo 클래스로 반환
    private org.controlsfx.control.Rating makeRating(double rate) {
        org.controlsfx.control.Rating rating = new org.controlsfx.control.Rating();
        rating.setOrientation(Orientation.HORIZONTAL);
        rating.setUpdateOnHover(false);
        rating.setPartialRating(false);
        rating.setRating(rate);
        rating.setDisable(true);
        rating.setMaxHeight(30);
        return rating;
    }

    // 학과 상세정보 요청
    private void requestDeptInf(){
        // 학교 상세정보 페이지에서 학과 상세정보 조회 버튼 클릭하면
        // 타이머 클래스 사용해서 1초뒤 프로토콜 전송

        Timer timer = new Timer();
        // TimerTask는 추상클래스라 람다식 안됨
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    // 해당 학교, 학과 이름 VO 생성
                    DeptInfoReqVO deptInfoReqVO = new DeptInfoReqVO(univName, deptName);

                    // 학과 상세정보 리스트 요청
                    Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_DETAIL, deptInfoReqVO));

                    // 학과 상세정보 리스트 수신
                    deptDtoList = receiveDeptDetailDTOList();

                    // 학과 평가 리스트
//                    requestDeptRatingList();

                    // FIXME 0 - 2020, 1 - 2019, 2 - 2018
                    Platform.runLater(() -> {
                        // set 학과 상세정보
                        setUnivDeptDetail(deptDtoList.get(0));
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(timerTask, 500);
    }

    // 학과 상세정보 DTO List 수신
    private ArrayList<DepartmentDetailDTO> receiveDeptDetailDTOList() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        ArrayList<DepartmentDetailDTO> tmp = new ArrayList<>();   //

        // 타입 처리
        ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
        for(Object obj : ar){
            if(obj instanceof DepartmentDetailDTO){
                tmp.add((DepartmentDetailDTO) obj);
            }
        }

        return tmp;
    }

    // 학과 상세정보 ui setting
    private void setUnivDeptDetail(DepartmentDetailDTO departmentDetailDTO){
        admissionFee.setText(String.valueOf(departmentDetailDTO.getAdmissionFee()));
        tuition.setText(String.valueOf(departmentDetailDTO.getTuition()));
        outSchoolScholarship.setText(String.valueOf(departmentDetailDTO.getOutSchoolScholarship()));
        inSchoolScholarship.setText(String.valueOf(departmentDetailDTO.getInSchoolScholarship()));
        scholarshipPerPerson.setText(String.valueOf(departmentDetailDTO.getScholarshipPerPerson()));
        numOfFulltimeProfessor.setText(String.valueOf(departmentDetailDTO.getNumOfFulltimeProfessor()));
        thesisResultPerProfessor.setText(String.valueOf(departmentDetailDTO.getThesisResultPerProfessor()));
        rearchCostPerProfessor.setText(String.valueOf(departmentDetailDTO.getRearchCostPerProfessor()));
        maleGr.setText(String.valueOf(departmentDetailDTO.getMaleGr()));
        femaleGr.setText(String.valueOf(departmentDetailDTO.getFemaleGr()));
        maleEmploymentTarget.setText(String.valueOf(departmentDetailDTO.getMaleEmploymentTarget()));
        femaleEmploymentTarget.setText(String.valueOf(departmentDetailDTO.getFemaleEmploymentTarget()));
        employmentRate.setText(String.valueOf(departmentDetailDTO.getEmploymentRate()));

        enteringRate.setText(String.valueOf(departmentDetailDTO.getEnteringRate()));
        enteringDomCmntyColl.setText(String.valueOf(departmentDetailDTO.getEnteringDomCmntyColl()));
        enteringOverseasCmntyColl.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasCmntyColl()));
        enteringDomUniv.setText(String.valueOf(departmentDetailDTO.getEnteringDomUniv()));
        enteringOverseasUniv.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasUniv()));
        enteringDomGrSchool.setText(String.valueOf(departmentDetailDTO.getEnteringDomGrSchool()));
        enteringOverseasGrSchool.setText(String.valueOf(departmentDetailDTO.getEnteringOverseasGrSchool()));
        domScholarNumber.setText(String.valueOf(departmentDetailDTO.getDomScholarNumber()));
        overseasScholarNumber.setText(String.valueOf(departmentDetailDTO.getOverseasScholarNumber()));
        maleDomEmployee.setText(String.valueOf(departmentDetailDTO.getMaleDomEmployee()));
        femaleDomEmployee.setText(String.valueOf(departmentDetailDTO.getFemaleDomEmployee()));
        maleOverseasEmployee.setText(String.valueOf(departmentDetailDTO.getMaleOverseasEmployee()));
        femaleOverseasEmployee.setText(String.valueOf(departmentDetailDTO.getFemaleOverseasEmployee()));
    }


    // 학과 평가 리스트 요청
    private void requestDeptRatingList(){
        Runnable runnable = () -> {
            // 학과 평가 리스트 요청
            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_RATING_LIST));

            // 학과 평가 리스트 수신
            ArrayList<DepartmentRatingDTO> departmentRatingList = receiveDeptRatingDTOList();

            Platform.runLater(() -> {
                // set 학과 평가 리스트
                setDeptRatingList(departmentRatingList);
            });
        };

        Thread th = new Thread(runnable);
        th.start();
    }

    // 학과 평가 리스트 수신
    private ArrayList<DepartmentRatingDTO> receiveDeptRatingDTOList() {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // 학교 상세정보 3개년치 받아오기 univDtoList
        ArrayList<DepartmentRatingDTO> tmp = new ArrayList<>();   //

        // 타입 처리
        ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
        for(Object obj : ar){
            if(obj instanceof DepartmentRatingDTO){
                tmp.add((DepartmentRatingDTO) obj);
            }
        }

        return tmp;
    }

    // 학과 평가 리스트 ui setting
    private void setDeptRatingList(ArrayList<DepartmentRatingDTO> deptRatingList){
        // TODO 학과 평가 리스트 set 필요 tableView
        observableList = FXCollections.observableArrayList();

        for(int i = 0; i < deptRatingList.size(); i++){
            // Date to String
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String creationDate = transFormat.format(deptRatingList.get(i).getCreationDate());

            observableList.add(new RatingInfo(new SimpleStringProperty(creationDate),
                    new SimpleStringProperty(deptRatingList.get(i).getContent()),
                    makeRating(deptRatingList.get(i).getScore())));
        }

        tableDeptRating.setItems(observableList);
    }


    // 뒤로가기 버튼 클릭
    @FXML
    private void clickDeptDetailExit(MouseEvent event) {
        System.out.println(deptName);
        StackPane root = (StackPane)btnDeptDetailExit.getScene().lookup("#spUnivDetail");
        root.getChildren().remove(apDeptDetail);
    }

    // 연도별 비교 조회 이벤트
    @FXML
    private void clickDeptDetailYearCp(MouseEvent event) {
        String indicatorName = comboIndicator.getValue();

        // 이전 barChart 내용 초기화
        barChart.getData().clear();

        // barChart 요소 init
        XYChart.Series[] series = new XYChart.Series[3];

        for(int i = 0; i < 3; i++){
            series[i] = new XYChart.Series<String, Number>();
        }

        // 요소 이름 초기화
        series[0].setName("2018");
        series[1].setName("2019");
        series[2].setName("2020");

        switch (indicatorName) {
            case "입학금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("재학생 수", deptDtoList.get(i).getAdmissionFee())
                    ));
                }
                break;
            case "등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("등록금", deptDtoList.get(i).getTuition())
                    ));
                }
                break;
            case "국내 전문대학 진학":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국내 전문대학 진학", deptDtoList.get(i).getEnteringDomCmntyColl())
                    ));
                }
                break;
            case "국외 전문대학 진학":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국외 전문대학 진학", deptDtoList.get(i).getEnteringOverseasCmntyColl())
                    ));
                }
                break;
            case "국내 대학 진학":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국내 대학 진학", deptDtoList.get(i).getEnteringDomUniv())
                    ));
                }
                break;
            case "국외 대학 진학":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국외 대학 진학", deptDtoList.get(i).getEnteringOverseasUniv())
                    ));
                }
                break;
            case "국내 대학원 진학":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국내 대학원 진학", deptDtoList.get(i).getEnteringDomGrSchool())
                    ));
                }
                break;
            case "국외 대학원 진학:":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국외 대학원 진학", deptDtoList.get(i).getEnteringOverseasGrSchool())
                    ));
                }
                break;
            case "국내 진학자 계":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국내 진학자 계", deptDtoList.get(i).getDomScholarNumber())
                    ));
                }
                break;
            case "국외 진학자 계":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국외 진학자 계", deptDtoList.get(i).getOverseasScholarNumber())
                    ));
                }
                break;
            case "진학률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("진학률", deptDtoList.get(i).getEnteringRate())
                    ));
                }
                break;
            case "남자 취업대상자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("남자 취업대상자", deptDtoList.get(i).getMaleEmploymentTarget())
                    ));
                }
                break;
            case "여자 취업대상자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("여자 취업대상자", deptDtoList.get(i).getFemaleEmploymentTarget())
                    ));
                }
                break;
            case "남자 국내 취업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("남자 국내 취업자", deptDtoList.get(i).getMaleDomEmployee())
                    ));
                }
                break;
            case "여자 국내 취업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("여자 국내 취업자", deptDtoList.get(i).getFemaleDomEmployee())
                    ));
                }
                break;
            case "남자 해외 취업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("남자 해외 취업자", deptDtoList.get(i).getMaleOverseasEmployee())
                    ));
                }
                break;
            case "여자 해외 취업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("여자 해외 취업자", deptDtoList.get(i).getFemaleOverseasEmployee())
                    ));
                }
                break;
            case "취업률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("취업률", deptDtoList.get(i).getEmploymentRate())
                    ));
                }
                break;
            case "교외 장학금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("교외 장학금", deptDtoList.get(i).getOutSchoolScholarship())
                    ));
                }
                break;
            case "교내 장학금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("교내 장학금", deptDtoList.get(i).getInSchoolScholarship())
                    ));
                }
                break;
            case "1인당 장학금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("1인당 장학금", deptDtoList.get(i).getScholarshipPerPerson())
                    ));
                }
                break;
            case "전임교원 수":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("전임교원 수", deptDtoList.get(i).getNumOfFulltimeProfessor())
                    ));
                }
                break;
            case "전임교원 1인당 논문 실적":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("전임교원 1인당 논문 실적", deptDtoList.get(i).getThesisResultPerProfessor())
                    ));
                }
                break;
            case "전임교원 1인당 연구비":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("전임교원 1인당 연구비", deptDtoList.get(i).getRearchCostPerProfessor())
                    ));
                }
                break;
            case "남자 졸업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("남자 졸업자", deptDtoList.get(i).getMaleGr())
                    ));
                }
                break;
            case "여자 졸업자":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("여자 졸업자", deptDtoList.get(i).getFemaleGr())
                    ));
                }
                break;
        }

        // barChart에 series 추가
        for(int i = 0; i < 3; i++){
            barChart.getData().add(series[i]);
        }
    }


    // 학과 평가 등록
    @FXML
    private void clickRegisterDeptRating(MouseEvent event) {
        Runnable runnable = () -> {
            String univName = this.univName;
            String deptName = this.deptName;
            // FIXME 세션기능 필요
            String userEmail = null;
            String content = inputRatingContent.getText();
            int score = (int) deptRating.getRating();
            java.sql.Date creationDate = new java.sql.Date(System.currentTimeMillis());

            RatingVO ratingVO = new RatingVO(univName, deptName, userEmail, content, score, creationDate);

            // 학과 평가 등록 요청
            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_RATING, ratingVO));

            // 학과 평가 등록 결과 수신
            Protocol receivePT = Connection.receive();

            if (receivePT.getProtocolType() == Protocol.PT_SUCC
                    && receivePT.getProtocolCode() == Protocol.PT_SUCC_DEPT_RATING)
            {
                Platform.runLater(this::showLoginSuccPopUp);
                
                // TODO tableView에 추가
            } else {
                Platform.runLater(this::showLoginFailPopUp);
            }

        };

        Thread th = new Thread(runnable);
        th.start();
    }

    private void showLoginSuccPopUp(){
        try {
            Stage stage = (Stage) inputRatingContent.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/ratingSucc.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoginFailPopUp(){
        try {
            Stage stage = (Stage) inputRatingContent.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/ratingFail.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
