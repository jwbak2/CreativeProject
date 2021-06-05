package Client.controller;

import Client.transmission.Connection;
import Client.vo.DeptInfoReqVO;
import Server.model.dto.DepartmentDetailDTO;
import Server.transmission.Protocol;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompareDepartment implements Initializable {
    private static final int FIRST_DEPT_DETAIL = 0;
    private static final int SECOND_DEPT_DETAIL = 1;

    @FXML
    private GridPane cpPaneOne;

    @FXML
    private Label admissionFeeTwo;

    @FXML
    private Label maleGrTwo;

    @FXML
    private Label femaleGrTwo;

    @FXML
    private Label enteringDomCmntyCollTwo;

    @FXML
    private Label enteringOverseasCmntyCollTwo;

    @FXML
    private Label enteringDomUnivTwo;

    @FXML
    private Label enteringOverseasUnivTwo;

    @FXML
    private Label enteringDomGrSchoolTwo;

    @FXML
    private Label enteringOverseasGrSchoolTwo;

    @FXML
    private Label enteringDomCmntyCollOne;

    @FXML
    private Label femaleGrOne;

    @FXML
    private Label maleGrOne;

    @FXML
    private Label tuitionOne;

    @FXML
    private Label enteringRateTwo;

    @FXML
    private Label overseasScholarNumberTwo;

    @FXML
    private Label domScholarNumberTwo;

    @FXML
    private Label enteringRateOne;

    @FXML
    private Label overseasScholarNumberOne;

    @FXML
    private Label domScholarNumberOne;

    @FXML
    private Label enteringOverseasGrSchoolOne;

    @FXML
    private Label enteringOverseasUnivOne;

    @FXML
    private Label enteringDomUnivOne;

    @FXML
    private Label enteringOverseasCmntyCollOne;

    @FXML
    private Label averageTuitionOne;

    @FXML
    private Label admissionFeeOne;

    @FXML
    private Label tuitionTwo;

    @FXML
    private Label enteringDomGrSchoolOne;

    @FXML
    private Button btnDeptCp;

    @FXML
    private GridPane cpPaneOne1;

    @FXML
    private Label maleEmploymentTargetTwo;

    @FXML
    private Label maleDomEmployeeTwo;

    @FXML
    private Label femaleDomEmployeeTwo;

    @FXML
    private Label maleOverseasEmployeeTwo;

    @FXML
    private Label femaleOverseasEmployeeTwo;

    @FXML
    private Label employmentRateTwo;

    @FXML
    private Label outSchoolScholarshipTwo;

    @FXML
    private Label inSchoolScholarshipTwo;

    @FXML
    private Label maleOverseasEmployeeOne;

    @FXML
    private Label femaleDomEmployeeOne;

    @FXML
    private Label maleDomEmployeeOne;

    @FXML
    private Label femaleEmploymentTargetOne;

    @FXML
    private Label rearchCostPerProfessorTwo;

    @FXML
    private Label thesisResultPerProfessorTwo;

    @FXML
    private Label numOfFulltimeProfessorTwo;

    @FXML
    private Label scholarshipPerPersonTwo;

    @FXML
    private Label thesisResultPerProfessorOne;

    @FXML
    private Label numOfFulltimeProfessorOne;

    @FXML
    private Label scholarshipPerPersonOne;

    @FXML
    private Label inSchoolScholarshipOne;

    @FXML
    private Label employmentRateOne;

    @FXML
    private Label femaleOverseasEmployeeOne;

    @FXML
    private Label rearchCostPerProfessorOne;

    @FXML
    private Label maleEmploymentTargetOne;

    @FXML
    private Label femaleEmploymentTargetTwo;

    @FXML
    private Label outSchoolScholarshipOne;

    @FXML
    private TextField inputDeptNameOne;

    @FXML
    private TextField inputDeptNameTwo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Text 감지 테스트
        Pattern pattern = Pattern.compile("대학교$");
        SuggestionProvider<String> provider = SuggestionProvider.create(Home.getUnivList());
        new AutoCompletionTextFieldBinding<>(inputDeptNameOne, provider);
        new AutoCompletionTextFieldBinding<>(inputDeptNameTwo, provider);

        inputDeptNameOne.textProperty().addListener((observable, oldValue, newValue) -> {
            Matcher matcher = pattern.matcher(newValue);

            if (matcher.find()) {
                Runnable runnable = () -> {
                    try {
                        String univName = inputDeptNameOne.getText().split(" ")[0];

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
            if (inputDeptNameOne.getText().equals("")){
                provider.clearSuggestions();
                provider.addPossibleSuggestions(Home.getUnivList());
            }

        });
        inputDeptNameTwo.textProperty().addListener((observable, oldValue, newValue) -> {
            Matcher matcher = pattern.matcher(newValue);

            if (matcher.find()) {
                Runnable runnable = () -> {
                    try {
                        String univName = inputDeptNameTwo.getText().split(" ")[0];

                        Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_LIST_OF_UNIV, univName));        // 패킷 전송

                        Protocol pt = Connection.receive();

                        ArrayList<String> univDeptList = (ArrayList<String>) pt.getBody();
                        for(int i = 0; i < univDeptList.size(); i++){
                            univDeptList.set(i, univName + " " + univDeptList.get(i));
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
            if (inputDeptNameOne.getText().equals("")){
                provider.clearSuggestions();
                provider.addPossibleSuggestions(Home.getUnivList());
            }

        });
    }

    // 비교 버튼 클릭 시
    @FXML
    void clickDeptCp(MouseEvent event) {

        Runnable runnable = () -> {
            try {
                ArrayList<DeptInfoReqVO> deptList = new ArrayList<DeptInfoReqVO>();

                String[] firstUnivAndDept = inputDeptNameOne.getText().split(" ");
                String[] secondUnivAndDept = inputDeptNameTwo.getText().split(" ");

                deptList.add(new DeptInfoReqVO(firstUnivAndDept[0], firstUnivAndDept[1]));
                deptList.add(new DeptInfoReqVO(secondUnivAndDept[0], secondUnivAndDept[1]));

                Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_DEPT_CP, deptList));        // 패킷 전송

                ArrayList<DepartmentDetailDTO> receivedDeptDetailList = receiveUnivCp();

                System.out.println(receivedDeptDetailList.size());
                Platform.runLater(() -> {
                    setUnivDetailInf(receivedDeptDetailList);    // 첫번쨰 학교 상세정보 화면에 세팅
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread th = new Thread(runnable);
        th.start();
    }

    // 비교할 학교 DTO 수신
    private ArrayList<DepartmentDetailDTO> receiveUnivCp() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // TODO 학교이름 찾기 실패 패킷 수신 예외처리

        ArrayList<DepartmentDetailDTO> result = new ArrayList<DepartmentDetailDTO>();

        try {
            ArrayList<?> tmp = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정

            // 타입 처리
            for (Object obj : tmp) {
                if (obj instanceof DepartmentDetailDTO) {
                    result.add((DepartmentDetailDTO) obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;  // UnivDTO 타입인 Object 반환
    }

    // 학교 비교 ui setting
    private void setUnivDetailInf(ArrayList<DepartmentDetailDTO> receivedDeptDetailList) {
        DepartmentDetailDTO firstDeptDetail = receivedDeptDetailList.get(FIRST_DEPT_DETAIL);
        DepartmentDetailDTO secondDeptDetail = receivedDeptDetailList.get(SECOND_DEPT_DETAIL);

        admissionFeeOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getAdmissionFee()));
        admissionFeeTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getAdmissionFee()));
        compareDeptDetailElement(admissionFeeOne, admissionFeeTwo,
                firstDeptDetail.getAdmissionFee(), secondDeptDetail.getAdmissionFee());

        tuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getTuition()));
        tuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getTuition()));
        compareDeptDetailElement(tuitionOne, tuitionTwo,
                firstDeptDetail.getTuition(), secondDeptDetail.getTuition());

        maleGrOne.setText(String.valueOf(firstDeptDetail.getMaleGr()));
        maleGrTwo.setText(String.valueOf(secondDeptDetail.getMaleGr()));
        compareDeptDetailElement(maleGrOne, maleGrTwo,
                firstDeptDetail.getMaleGr(), secondDeptDetail.getMaleGr());

        femaleGrOne.setText(String.valueOf(firstDeptDetail.getFemaleGr()));
        femaleGrTwo.setText(String.valueOf(secondDeptDetail.getFemaleGr()));
        compareDeptDetailElement(femaleGrOne, femaleGrTwo,
                firstDeptDetail.getFemaleGr(), secondDeptDetail.getFemaleGr());

        enteringDomCmntyCollOne.setText(String.valueOf(firstDeptDetail.getEnteringDomCmntyColl()));
        enteringDomCmntyCollTwo.setText(String.valueOf(secondDeptDetail.getEnteringDomCmntyColl()));
        compareDeptDetailElement(enteringDomCmntyCollOne, enteringDomCmntyCollTwo,
                firstDeptDetail.getEnteringDomCmntyColl(), secondDeptDetail.getEnteringDomCmntyColl());

        enteringOverseasCmntyCollOne.setText(String.valueOf(firstDeptDetail.getEnteringOverseasCmntyColl()));
        enteringOverseasCmntyCollTwo.setText(String.valueOf(secondDeptDetail.getEnteringOverseasCmntyColl()));
        compareDeptDetailElement(enteringOverseasCmntyCollOne, enteringOverseasCmntyCollTwo,
                firstDeptDetail.getEnteringOverseasCmntyColl(), secondDeptDetail.getEnteringOverseasCmntyColl());

        enteringDomUnivOne.setText(String.valueOf(firstDeptDetail.getEnteringDomUniv()));
        enteringDomUnivTwo.setText(String.valueOf(secondDeptDetail.getEnteringDomUniv()));
        compareDeptDetailElement(enteringDomUnivOne, enteringDomUnivTwo,
                firstDeptDetail.getEnteringDomUniv(), secondDeptDetail.getEnteringDomUniv());

        enteringOverseasUnivOne.setText(String.valueOf(firstDeptDetail.getEnteringOverseasUniv()));
        enteringOverseasUnivTwo.setText(String.valueOf(secondDeptDetail.getEnteringOverseasUniv()));
        compareDeptDetailElement(enteringOverseasUnivOne, enteringOverseasUnivTwo,
                firstDeptDetail.getEnteringOverseasUniv(), secondDeptDetail.getEnteringOverseasUniv());

        enteringDomGrSchoolOne.setText(String.valueOf(firstDeptDetail.getEnteringDomGrSchool()));
        enteringDomGrSchoolTwo.setText(String.valueOf(secondDeptDetail.getEnteringDomGrSchool()));
        compareDeptDetailElement(enteringDomGrSchoolOne, enteringDomGrSchoolTwo,
                firstDeptDetail.getEnteringDomGrSchool(), secondDeptDetail.getEnteringDomGrSchool());

        enteringOverseasGrSchoolOne.setText(String.valueOf(firstDeptDetail.getEnteringOverseasGrSchool()));
        enteringOverseasGrSchoolTwo.setText(String.valueOf(secondDeptDetail.getEnteringOverseasGrSchool()));
        compareDeptDetailElement(enteringOverseasGrSchoolOne, enteringOverseasGrSchoolTwo,
                firstDeptDetail.getEnteringOverseasGrSchool(), secondDeptDetail.getEnteringOverseasGrSchool());

        domScholarNumberOne.setText(String.valueOf(firstDeptDetail.getDomScholarNumber()));
        domScholarNumberTwo.setText(String.valueOf(secondDeptDetail.getDomScholarNumber()));
        compareDeptDetailElement(domScholarNumberOne, domScholarNumberTwo,
                firstDeptDetail.getDomScholarNumber(), secondDeptDetail.getDomScholarNumber());

        overseasScholarNumberOne.setText(String.valueOf(firstDeptDetail.getOverseasScholarNumber()));
        overseasScholarNumberTwo.setText(String.valueOf(secondDeptDetail.getOverseasScholarNumber()));
        compareDeptDetailElement(overseasScholarNumberOne, overseasScholarNumberTwo,
                firstDeptDetail.getOverseasScholarNumber(), secondDeptDetail.getOverseasScholarNumber());

        maleEmploymentTargetOne.setText(String.valueOf(firstDeptDetail.getMaleEmploymentTarget()));
        maleEmploymentTargetTwo.setText(String.valueOf(secondDeptDetail.getMaleEmploymentTarget()));
        compareDeptDetailElement(maleEmploymentTargetOne, maleEmploymentTargetTwo,
                firstDeptDetail.getMaleEmploymentTarget(), secondDeptDetail.getMaleEmploymentTarget());

        femaleEmploymentTargetOne.setText(String.valueOf(firstDeptDetail.getFemaleEmploymentTarget()));
        femaleEmploymentTargetTwo.setText(String.valueOf(secondDeptDetail.getFemaleEmploymentTarget()));
        compareDeptDetailElement(femaleEmploymentTargetOne, femaleEmploymentTargetTwo,
                firstDeptDetail.getFemaleEmploymentTarget(), secondDeptDetail.getFemaleEmploymentTarget());

        maleDomEmployeeOne.setText(String.valueOf(firstDeptDetail.getMaleDomEmployee()));
        maleDomEmployeeTwo.setText(String.valueOf(secondDeptDetail.getMaleDomEmployee()));
        compareDeptDetailElement(maleDomEmployeeOne, maleDomEmployeeTwo,
                firstDeptDetail.getMaleDomEmployee(), secondDeptDetail.getMaleDomEmployee());

        femaleDomEmployeeOne.setText(String.valueOf(firstDeptDetail.getFemaleDomEmployee()));
        femaleDomEmployeeTwo.setText(String.valueOf(secondDeptDetail.getFemaleDomEmployee()));
        compareDeptDetailElement(femaleDomEmployeeOne, femaleDomEmployeeTwo,
                firstDeptDetail.getFemaleDomEmployee(), secondDeptDetail.getFemaleDomEmployee());

        maleOverseasEmployeeOne.setText(String.valueOf(firstDeptDetail.getMaleOverseasEmployee()));
        maleOverseasEmployeeTwo.setText(String.valueOf(secondDeptDetail.getMaleOverseasEmployee()));
        compareDeptDetailElement(maleOverseasEmployeeOne, maleOverseasEmployeeTwo,
                firstDeptDetail.getMaleOverseasEmployee(), secondDeptDetail.getMaleOverseasEmployee());

        femaleOverseasEmployeeOne.setText(String.valueOf(firstDeptDetail.getFemaleOverseasEmployee()));
        femaleOverseasEmployeeTwo.setText(String.valueOf(secondDeptDetail.getFemaleOverseasEmployee()));
        compareDeptDetailElement(femaleOverseasEmployeeOne, femaleOverseasEmployeeTwo,
                firstDeptDetail.getFemaleOverseasEmployee(), secondDeptDetail.getFemaleOverseasEmployee());

        enteringRateOne.setText(firstDeptDetail.getEnteringRate() + "%");
        enteringRateTwo.setText(secondDeptDetail.getEnteringRate() + "%");
        compareDeptDetailElement(enteringRateOne, enteringRateTwo,
                firstDeptDetail.getEnteringRate(), secondDeptDetail.getEnteringRate());

        outSchoolScholarshipOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getOutSchoolScholarship()));
        outSchoolScholarshipTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getOutSchoolScholarship()));
        compareDeptDetailElement(outSchoolScholarshipOne, outSchoolScholarshipTwo,
                firstDeptDetail.getOutSchoolScholarship(), secondDeptDetail.getOutSchoolScholarship());

        inSchoolScholarshipOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getInSchoolScholarship()));
        inSchoolScholarshipTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getInSchoolScholarship()));
        compareDeptDetailElement(inSchoolScholarshipOne, inSchoolScholarshipTwo,
                firstDeptDetail.getInSchoolScholarship(), secondDeptDetail.getInSchoolScholarship());

        scholarshipPerPersonOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getScholarshipPerPerson()));
        scholarshipPerPersonTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getScholarshipPerPerson()));
        compareDeptDetailElement(scholarshipPerPersonOne, scholarshipPerPersonTwo,
                firstDeptDetail.getScholarshipPerPerson(), secondDeptDetail.getScholarshipPerPerson());

        numOfFulltimeProfessorOne.setText(String.valueOf(firstDeptDetail.getNumOfFulltimeProfessor()));
        numOfFulltimeProfessorTwo.setText(String.valueOf(secondDeptDetail.getNumOfFulltimeProfessor()));
        compareDeptDetailElement(numOfFulltimeProfessorOne, numOfFulltimeProfessorTwo,
                firstDeptDetail.getNumOfFulltimeProfessor(), secondDeptDetail.getNumOfFulltimeProfessor());

        thesisResultPerProfessorOne.setText(String.valueOf(firstDeptDetail.getThesisResultPerProfessor()));
        thesisResultPerProfessorTwo.setText(String.valueOf(secondDeptDetail.getThesisResultPerProfessor()));
        compareDeptDetailElement(thesisResultPerProfessorOne, thesisResultPerProfessorTwo,
                firstDeptDetail.getThesisResultPerProfessor(), secondDeptDetail.getThesisResultPerProfessor());

        rearchCostPerProfessorOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstDeptDetail.getRearchCostPerProfessor()));
        rearchCostPerProfessorTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondDeptDetail.getRearchCostPerProfessor()));
        compareDeptDetailElement(rearchCostPerProfessorOne, rearchCostPerProfessorTwo,
                firstDeptDetail.getRearchCostPerProfessor(), secondDeptDetail.getRearchCostPerProfessor());

        employmentRateOne.setText(firstDeptDetail.getEmploymentRate() + "%");
        employmentRateTwo.setText(secondDeptDetail.getEmploymentRate() + "%");
        compareDeptDetailElement(employmentRateOne, employmentRateTwo,
                firstDeptDetail.getEmploymentRate(), secondDeptDetail.getEmploymentRate());

    }

    // 상세정보 요소 비교 후 텍스트 색상 변경
    private void compareDeptDetailElement(Label n1, Label n2, Long el1, Long el2) {
        if(el1.equals(el2)){
            n1.setTextFill(Color.BLUE);
            n2.setTextFill(Color.BLUE);
        } else if (el1 < el2) {
            n2.setTextFill(Color.RED);
        } else if(el1 > el2){
            n1.setTextFill(Color.RED);
        }
    }
}
