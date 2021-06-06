package Client.controller;

import Client.view.tablemodel.RatingInfo;
import Client.vo.RatingVO;
import Server.model.dto.UnivDetailDTO;
import Server.model.dto.UnivDTO;
import Client.transmission.Connection;
import Server.model.dto.UnivRatingDTO;
import Server.transmission.Protocol;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.controlsfx.control.textfield.TextFields;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static Server.transmission.Protocol.PT_SUCC_BOOKMARK_TOGGLE;

public class UnivDetail implements Initializable {

    @FXML
    public Button bookMarkButton;

    @FXML
    private StackPane spUnivDetail;

    @FXML
    private ImageView imageUnivLogo;

    @FXML
    private Text textUnivName;

    @FXML
    private Button btnHomepage;

    @FXML
    private Text textUnivAddress;

    @FXML
    private Text textUnivType;

    @FXML
    private Text textUnivEstablishmentCls;

    @FXML
    private Text textUnivRepresentativeNumber;

    @FXML
    private Label textUnivIntroduction;

    @FXML
    private Button btnRequestUnivInf;

    @FXML
    private TextField inputUniv;

    @FXML
    private Label studentNumber;

    @FXML
    private Label admissionCompetitionRate;

    @FXML
    private Label employmentRate;

    @FXML
    private Label enteringRate;

    @FXML
    private Label educationCostPerPerson;

    @FXML
    private Label totalScholarshipBenefit;

    @FXML
    private Label foundersNumber;

    @FXML
    private Label staffForStartCompany;

    @FXML
    private Label profesorForStartCompany;

    @FXML
    private Label govermentStartCompanyFund;

    @FXML
    private Label schoolStartCompanyFund;

    @FXML
    private Label startCompanyCapital;

    @FXML
    private Label startCompanySale;

    @FXML
    private Label admissionFee;

    @FXML
    private Label medicalTuition;

    @FXML
    private Label engineeringTuition;

    @FXML
    private Label artMusPhysTuition;

    @FXML
    private Label naturalScienceTuition;

    @FXML
    private Label humanitiesSocialTuition;

    @FXML
    private Label averageTuition;

    @FXML
    private Label dormitoryAccommodationRate;

    @FXML
    private Label dispatchedStudent;

    @FXML
    private Label bookTotal;

    @FXML
    private Label univArea;

    @FXML
    private Label numOfFulltimeProfessor;

    @FXML
    private Label researchCostPerProfessor;

    @FXML
    private Label numOfPatentRegistration;

    @FXML
    private AnchorPane mainAp;

    @FXML
    private Tab tabUnivDeptList;

    @FXML
    private Tab tabUnivMap;

    @FXML
    private WebView webView;

    @FXML
    private ListView<?> tableDeptList;

    @FXML
    private Button btnDeptDetail;

    @FXML
    private AnchorPane apUnivMain;

    @FXML
    private ComboBox<String> comboIndicator;

    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    private TextField inputUnivRatingContent;

    @FXML
    private Button btnRegisterUnivRating;

    @FXML
    private TableView<RatingInfo> tableUnivRating;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colContent;

    @FXML
    private TableColumn<?, ?> colScore;

    @FXML
    private Rating univRating;



    private boolean checkTabUnivMap;

    private String homepageURL;

    private String selectedDeptName;

    private UnivDTO univDTO; //가장 최근 조회한 UnivDTO

    private ArrayList<UnivDetailDTO> univDtoList;   // 2018 ~ 2020 UnivDetailDTO 담는 어레이리스트

    private ObservableList<RatingInfo> observableUnivRatingList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFields.bindAutoCompletion(inputUniv, Home.getUnivList()); // 텍스트필드 자동완성

        // tab 처음 상태 초기
        checkTabUnivMap = false;

        // 대학교 입력할떄 엔터누르는 이벤트 추가
        inputUniv.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                requestUniv();
            }
        });

        // 조회 버튼 클릭 이벤트 추가
        btnRequestUnivInf.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> requestUniv());

        // 학과 리스트에서 학과 선택 이벤트 추가
        tableDeptList.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<?> observable, Object oldValue, Object newValue) -> selectedDeptName = newValue.toString()
        );

        // 지도 API
        tabUnivMap.setOnSelectionChanged((event) -> {
            if(!checkTabUnivMap){
                checkTabUnivMap = true;

                initAndLoadMapAPI();
            }
        });

        // tableView column setting
        colDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("ratingContent"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        // 북마크 버튼 클릭 시
        bookMarkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> bookMarkToggle());

    }

    private void bookMarkToggle(){

        Runnable runnable = () -> {

            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_TOGGLE_BOOKMARK, univDTO.getUnivName()));

            Protocol receivePT = Connection.receive();

            // if 즐겨찾기 등록이 성공한 경우
            if (receivePT.getProtocolCode() == PT_SUCC_BOOKMARK_TOGGLE && bookMarkButton.getTextFill() == Color.BLACK) {

                setBookMarkButton(1);   // 버튼을 즐겨찾기 등록된 상태로 변경

            } else if (receivePT.getProtocolCode() == PT_SUCC_BOOKMARK_TOGGLE){

                setBookMarkButton(0);   // 버튼을 즐겨찾기 해제된 상태로 변경

            } else {

                System.out.println("즐겨찾기 등록 실패");

            }

        };

        Thread th = new Thread(runnable);
        th.start();

    }

    private void bookMarkButtonInit(String univName) {


        // 서버에 즐겨찾기 등록상태를 확인 요청
        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_CHK_BOOKMARK, univName));

        // if 즐겨찾기가 등록된 학교
        if ((Boolean) Connection.receive().getBody()){

            setBookMarkButton(1);       // 버튼을 즐겨찾기 등록된 상태로 변경

        } else {

            setBookMarkButton(0);

        }

    }

    // code가 0의 경우 black으로 1의 경우 yellow로
    private void setBookMarkButton(int code){

        Platform.runLater(() -> {

            if(code == 1) {
                bookMarkButton.setText("즐겨찾기 해제");
                bookMarkButton.setTextFill(Color.YELLOW);

            } else {
                bookMarkButton.setText("즐겨찾기 등록");
                bookMarkButton.setTextFill(Color.BLACK);
            }

        });

    }



    //지도 API
    private void initAndLoadMapAPI() {

        WebEngine webEngine = webView.getEngine();
        //html 로드
//        String MapAPIHtmlFileDir = "C:\\Users\\vxc79\\IdeaProjects\\GetGangGoo\\src\\Client\\resource\\MapAPI.html";
        String MapAPIHtmlFileDir = "Client/resource/MapAPI.html";

        try {
            File htmlFile = new File(MapAPIHtmlFileDir);

            //파싱, 주소 수정, 저장
            Document htmlDoc = Jsoup.parse(htmlFile, "UTF-8");

            Element addressTag = htmlDoc.getElementById("address");

            if(univDTO != null) {
                addressTag.text(univDTO.getUnivAddress());
            } else {
                addressTag.text("경상북도 구미시 대학로 61 (양호동, 금오공과대학교)");
            }

            PrintWriter writer = new PrintWriter(htmlFile, "UTF-8");
            writer.write(htmlDoc.html());
            writer.flush();
            writer.close();

            webEngine.load(htmlFile.toURI().toString());

        } catch (IOException IOE) {
            IOE.printStackTrace();
            System.out.println("지도 API IO 예외 발생");

        }

    }

    // 학교 정보 요청
    void requestUniv() {
        Runnable runnable = () -> {     // 다른 스레드로 처리
            try {
//                // 조회 버튼 클릭 후 학과 리스트 체크 불리언 초기화
                // Tab Map
                checkTabUnivMap = false;


                // 학교 상세정보 요청
                sendUnivInf();

                // 응답 처리
                univDTO = receiveUnivDTO();
                univDtoList = receiveUnivDetailDTO();

                Platform.runLater(() -> {   // UI 변경 코드는 외부 스레드에서 처리 불가능하기에 runLater 매소드 사용
                    // 학교 소개 tab
                    setUnivInf(univDTO);    // 람다식으로 변경

                    // 2020년 데이터만 학교 상세정보 tab에 set
                    // FIXME 0 - 2020, 1 - 2019, 2 - 2018 상수로 수정 필요
                    setUnivDetailInf(univDtoList.get(0));   // 멤버변수의 ArrayList에서 가져옴

                });

                //즐겨찾기 버튼 초기화
                bookMarkButtonInit(univDTO.getUnivName());

                // 학교의 학과 리스트 요청 - 다른 스레드 생성해서 요청
                requestDeptListOfUniv();

                // 학교 평가 리스트 요청
                requestUnivRatingList();


            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread th = new Thread(runnable);
        th.start();
    }



    // 학교 프로토콜 전송
    void sendUnivInf() throws Exception {
        // input에 입력한 학교 이름 추출 + 공백 제거
        String univName = inputUniv.getText().replace(" ", "");
        System.out.println("입력한 대학교 : " + univName);

        System.out.println("학교 상세정보 조회 요청");

        if (univName.equals("")) {               // 공백일시 예외처리
            throw new Exception("univName of input is null");
        }

        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_INF, univName));        // 패킷 전송
    }

    // 학교 DTO 수신
    private UnivDTO receiveUnivDTO() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        if (receivePT.getProtocolType() == Protocol.PT_FAIL
                && receivePT.getProtocolCode() == Protocol.PT_FAIL_UNIV_INF) {    // 입력한 학교명이 존재하지 않을떄

            // 조회 실패 팝업창
            try {
                Stage stage = (Stage) btnRequestUnivInf.getScene().getWindow(); //
                Popup pu = new Popup();
                Parent root = FXMLLoader.load(getClass().getResource("../view/popup.fxml"));

                pu.getContent().add(root);
                pu.setAutoHide(true); // 포커스 이동시 창 숨김
                pu.show(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new Exception("입력한 학교명은 존재하지 않습니다.");             // 실패 패킷 수신 예외처리
        }

        return (UnivDTO) receivedBody;       // 학교 정보 receive
    }

    // 학과 상세정보 DTO list 수신
    private ArrayList<UnivDetailDTO> receiveUnivDetailDTO() {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // 학교 상세정보 3개년치 받아오기 univDtoList
        ArrayList<UnivDetailDTO> tmp = new ArrayList<>();   //

        // 타입 처리
        ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
        for (Object obj : ar) {
            if (obj instanceof UnivDetailDTO) {
                tmp.add((UnivDetailDTO) obj);
            }
        }

        return tmp;
    }

    // 학교 기본정보 ui setting
    private void setUnivInf(UnivDTO univDTO) {    // UnivDTO GUI에 뿌려주기
        Image univLogo = new Image(new ByteArrayInputStream(univDTO.getUnivLogoImageFile()));// ByteArrayInputStream 알아보기
        imageUnivLogo.setImage(univLogo);
        textUnivName.setText(univDTO.getUnivName());
        homepageURL = univDTO.getUnivHomepageUrl(); // homepage url은 멤버변수로 따로 저장
        // -> 하이퍼링크 클릭 이벤트에서 DTO 정보 읽을수 없어서 변수로 처리
        textUnivAddress.setText(univDTO.getUnivAddress());
        textUnivRepresentativeNumber.setText(univDTO.getUnivRepresentativeNumber());
        textUnivType.setText(univDTO.getUnivType());
        textUnivEstablishmentCls.setText(univDTO.getUnivEstablishmentCls());
        textUnivIntroduction.setText(univDTO.getUnivIntroduction());
    }

    // 학교 상세정보 ui setting
    private void setUnivDetailInf(UnivDetailDTO univDetailDTO) {  // UnivDetailDTO GUI에 뿌려주기
        studentNumber.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStudentNumber()));
        admissionCompetitionRate.setText(univDetailDTO.getAdmissionCompetitionRate() + "%");
        employmentRate.setText(univDetailDTO.getEmploymentRate() + "%");
        enteringRate.setText(univDetailDTO.getEnteringRate() + "%");
        educationCostPerPerson.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEducationCostPerPerson()));
        totalScholarshipBenefit.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getTotalScholarshipBenefits()));

        foundersNumber.setText(String.valueOf(univDetailDTO.getNumberFounders()));
        startCompanySale.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanySales()));
        startCompanyCapital.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getStartCompanyCapital()));
        schoolStartCompanyFund.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getSchoolStartCompanyFund()));
        govermentStartCompanyFund.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getGovernmentStartCompanyFund()));
        profesorForStartCompany.setText(String.valueOf(univDetailDTO.getProfessorForStartCompany()));
        staffForStartCompany.setText(String.valueOf(univDetailDTO.getStaffForStartCompany()));

        admissionFee.setText(String.valueOf(univDetailDTO.getAdmissionFee()));
        averageTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getAverageTuition()));
        humanitiesSocialTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getHumanitiesSocialTuition()));
        naturalScienceTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getNaturalScienceTuition()));
        artMusPhysTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getArtMusPhysTuition()));
        engineeringTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getEngineeringTuition()));
        medicalTuition.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getMedicalTuition()));

        dormitoryAccommodationRate.setText(String.valueOf(univDetailDTO.getDormitoryAccommodationRate()));
        dispatchedStudent.setText(String.valueOf(univDetailDTO.getDispatchedStudent()));
        bookTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getBookTotal()));
        univArea.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getUnivArea()));
        numOfFulltimeProfessor.setText(String.valueOf(univDetailDTO.getNumOfFulltimeProfessor()));
        researchCostPerProfessor.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getResearchCostPerProfessor()));
        numOfPatentRegistration.setText(NumberFormat.getNumberInstance(Locale.US).format(univDetailDTO.getNumOfPatentRegistration()));
    }



    // 학교의 학과 리스트 요청
    public void requestDeptListOfUniv() {
        try {
            String univName = inputUniv.getText().replace(" ", "");

            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_LIST_OF_UNIV, univName));

            ArrayList<String> univDeptList = receiveUnivDeptList();

            // 학교 평가 리스트 요청
            requestUnivRatingList();

            Platform.runLater(() -> {
                // 학과 리스트 tab
                setUnivDeptList(univDeptList);  // deptList Listview 추가
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 학교의 학과리스트 list 수신
    private ArrayList<String> receiveUnivDeptList() {
        Protocol receivePT = Connection.receive();

        ArrayList<String> tmp  = null;

        try {
            Object receivedBody = receivePT.getBody();

            // 학과 리스트
            tmp = new ArrayList<>();   //

            // 타입 처리
            ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
            for (Object obj : ar) {
                if (obj instanceof String) {
                    tmp.add((String) obj);
                }
            }
        } catch(NullPointerException NP){
            System.out.println("학과 리스트 수신 중 예외 발생");

        }

        return tmp;
    }

    // 학교의 학과리스트 ui setting
    private void setUnivDeptList(ArrayList<String> deptList) {  //
        ObservableList list = FXCollections.observableArrayList(deptList);
        tableDeptList.setItems(list);
    }


    // 학교 평가 리스트 요청
    private void requestUnivRatingList(){
        String univName = inputUniv.getText().replace(" ", "");
        // 학교 평가 리스트 요청
        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_RATING_LIST, univName));

        // 학교 평가 리스트 수신
        ArrayList<UnivRatingDTO> univRatingList = receiveUnivRatingList();

        Platform.runLater(() -> {
            // set 학과 평가 리스트
            setUnivRatingList(univRatingList);
        });
    }

    // 학교 평가 리스트 수신
    private ArrayList<UnivRatingDTO> receiveUnivRatingList(){
        Protocol receivePT = Connection.receive();

        ArrayList<UnivRatingDTO> tmp = null;

        try {
            Object receivedBody = receivePT.getBody();

            tmp = new ArrayList<>();   //

            // 타입 처리
            ArrayList<?> ar = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정
            for (Object obj : ar) {
                if (obj instanceof UnivRatingDTO) {
                    tmp.add((UnivRatingDTO) obj);
                }
            }
        } catch(NullPointerException NPE){
            System.out.println("학과 평가 리스트 수신 중 예외 발생;");
        }

        return tmp;
    }

    // 학교 평가 리스트 ui setting
    private void setUnivRatingList(ArrayList<UnivRatingDTO> univRatingList){
        observableUnivRatingList = FXCollections.observableArrayList();

        for (int i = 0; i < univRatingList.size(); i++) {
            // Date to String
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            String creationDate = transFormat.format(univRatingList.get(i).getCreationDate());

            observableUnivRatingList.add(new RatingInfo(new SimpleStringProperty(creationDate),
                    new SimpleStringProperty(univRatingList.get(i).getContent()),
                    makeRating(univRatingList.get(i).getScore())));
        }


        tableUnivRating.setItems(observableUnivRatingList);
    }

    // double 값받아서 Rating 클래스로 반환
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


    // 홈페이지 버튼 클릭 시
    @FXML
    private void moveHyperLink(MouseEvent event) {  // 홈페이지 URL 하이퍼 링크 event handler
        try {
            Desktop.getDesktop().browse(new URI(homepageURL));  // 버튼 누를시 브라우져 생성
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // 학과 상세정보 조회 버튼 클릭 시
    @FXML
    private void clickBtnDeptDetail(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/departmentDetail.fxml"));
            Parent root = loader.load();    // Parent load, 여기서 controller init도 됨

            // UnivDetail 컨트롤러에서 DepartmentDetail 컨트롤러로 값넘겨줌
            DepartmentDetail controller = loader.getController();
            controller.setUnivName(textUnivName.getText()); // 학교 이름
            controller.setDeptName(selectedDeptName);       // 학과 이름

            spUnivDetail.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 연도별 비교 버튼 클릭 시
    @FXML
    private void clickUnivDetailYearCp(MouseEvent event) {
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
            case "재학생 수":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("재학생 수", univDtoList.get(i).getStudentNumber())
                    ));
                }
                break;
            case "입학경쟁률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("입학경쟁률", univDtoList.get(i).getAdmissionCompetitionRate())
                    ));
                }
                break;
            case "취업률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("취업률", univDtoList.get(i).getEmploymentRate())
                    ));
                }
                break;
            case "진학률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("진학률", univDtoList.get(i).getEnteringRate())
                    ));
                }
                break;
            case "1인당 교육비":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("1인당 교육비", univDtoList.get(i).getEducationCostPerPerson())
                    ));
                }
                break;
            case "장학금 수혜 현황":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("장학금 수혜 현황", univDtoList.get(i).getTotalScholarshipBenefits())
                    ));
                }
                break;
            case "창업자 수":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("창업자 수", univDtoList.get(i).getNumberFounders())
                    ));
                }
                break;
            case "창업기업 매출액":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("창업기업 매출액", univDtoList.get(i).getStartCompanySales())
                    ));
                }
                break;
            case "창업기업 자본금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("창업기업 자본금", univDtoList.get(i).getStartCompanyCapital())
                            ));
                }
                break;
            case "정부 창업 지원액":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("교비 창업 지원액", univDtoList.get(i).getSchoolStartCompanyFund())
                            ));
                }
                break;
            case "교비 창업 지원액":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("정부 창업 지원액", univDtoList.get(i).getGovernmentStartCompanyFund())
                            ));
                }
                break;
            case "창업 전담 교원":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("창업 전담 교원", univDtoList.get(i).getProfessorForStartCompany())
                            ));
                }
                break;
            case "창업 전담 직원":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("창업 전담 직원", univDtoList.get(i).getStaffForStartCompany())
                            ));
                }
                break;
            case "입학금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("입학금", univDtoList.get(i).getAdmissionFee())
                    ));
                }
                break;
            case "평균 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("평균 등록금", univDtoList.get(i).getAverageTuition())
                            ));
                }
                break;
            case "인문사회 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("인문사회 등록금", univDtoList.get(i).getHumanitiesSocialTuition())
                    ));
                }
                break;
            case "자연과학 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("자연과학 등록금", univDtoList.get(i).getNaturalScienceTuition())
                    ));
                }
                break;
            case "예체능 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("예체능 등록금", univDtoList.get(i).getArtMusPhysTuition()) ));
                }
                break;
            case "공학 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("공학 등록금", univDtoList.get(i).getEngineeringTuition())
                    ));
                }
                break;
            case "의학 등록금":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("의학 등록금", univDtoList.get(i).getMedicalTuition())
                    ));
                }
                break;
            case "기숙사 수용률":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("기숙사 수용률", univDtoList.get(i).getDormitoryAccommodationRate())
                    ));
                }
                break;
            case "외국대학 파견인원":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("외국대학 파견인원", univDtoList.get(i).getDispatchedStudent())
                    ));
                }
                break;
            case "도서자료 총계":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("도서자료 총계", univDtoList.get(i).getBookTotal())
                    ));
                }
                break;
            case "교지 면적":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("교지 면적", univDtoList.get(i).getUnivArea())
                    ));
                }
                break;
            case "연구비 수혜 실적":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("전임교원 수", univDtoList.get(i).getNumOfFulltimeProfessor()),
                            new XYChart.Data<String, Number>("전임교원 1인당 연구비", univDtoList.get(i).getResearchCostPerProfessor())
                    ));
                }
                break;
            case "특허 출원 및 등록 실적":
                for (int i = 0; i < 3; i++) {
                    series[i].setData(FXCollections.observableArrayList(
                            new XYChart.Data<String, Number>("국내/해외 특허 등록", univDtoList.get(i).getNumOfPatentRegistration())
                    ));
                }
                break;
        }

        // barChart에 series 추가
        for(int i = 0; i < 3; i++){
            barChart.getData().add(series[i]);
        }
    }

    // 학교 평가 등록 버튼 클릭 시
    @FXML
    void clickRegisterUnivRating(MouseEvent event) {
        if(!(Login.user.getAffiliatedSchool().equals(textUnivName.getText()))){
            showRatingAuthFail();
            System.out.println("학과 평가 등록 권한이 없습니다.");
            return;
        }

        Runnable runnable = () -> {
            String univName = inputUniv.getText().replace(" ", "");
            String userEmail = Login.user.getUserEmail();
            String content = inputUnivRatingContent.getText();
            int score = (int) univRating.getRating();
            java.sql.Date creationDate = new java.sql.Date(System.currentTimeMillis());

            RatingVO ratingVO = new RatingVO(univName, userEmail, content, score, creationDate);

            // 학교 평가 등록 요청
            Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_RATING, ratingVO));

            // 학교 평가 등록 결과 수신
            Protocol receivePT = Connection.receive();

            if (receivePT.getProtocolType() == Protocol.PT_SUCC
                    && receivePT.getProtocolCode() == Protocol.PT_SUCC_UNIV_RATING) {
                // Date to String
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");

                System.out.println(creationDate);
                // table view 리스트에 추가
                observableUnivRatingList.add(new RatingInfo(new SimpleStringProperty(transFormat.format(creationDate)),
                        new SimpleStringProperty(content),
                        makeRating(score)));

                Platform.runLater(() -> {
                    showLoginSuccPopUp();

                    tableUnivRating.refresh();
                });

            } else {
                Platform.runLater(this::showLoginFailPopUp);
            }

        };

        Thread th = new Thread(runnable);
        th.start();
    }

    private void showLoginFailPopUp() {
        try {
            Stage stage = (Stage) inputUnivRatingContent.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/ratingSucc.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoginSuccPopUp() {
        try {
            Stage stage = (Stage) inputUnivRatingContent.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/ratingSucc.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRatingAuthFail() {
        try {
            Stage stage = (Stage) inputUnivRatingContent.getScene().getWindow(); //
            Popup pu = new Popup();
            Parent root = FXMLLoader.load(getClass().getResource("../view/authFail.fxml"));

            pu.getContent().add(root);
            pu.setAutoHide(true); // 포커스 이동시 창 숨김
            pu.show(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
