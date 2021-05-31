package Client.controller;

import Client.transmission.Connection;
import Server.transmission.Protocol;
import Server.model.dto.UnivDetailDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class CompareUniv implements Initializable {
    @FXML
    private TextField inputFirstUniv;

    @FXML
    private TextField inputSecondUniv;

    @FXML
    private Button BtnCompareUnivDetail;

    @FXML
    private GridPane cpPaneOne1;

    @FXML
    private Label humanitiesSocialTuitionTwo;

    @FXML
    private Label naturalScienceTuitionTwo;

    @FXML
    private Label artMusPhysTuitionTwo;

    @FXML
    private Label engineeringTuitionTwo;

    @FXML
    private Label medicalTuitionTwo;

    @FXML
    private Label dormitoryAccommodationRateTwo;

    @FXML
    private Label dispatchedStudentTwo;

    @FXML
    private Label bookTotalTwo;

    @FXML
    private Label univAreaTwo;

    @FXML
    private Label numOfFulltimeProfessorTwo;

    @FXML
    private Label medicalTuitionOne;

    @FXML
    private Label engineeringTuitionOne;

    @FXML
    private Label artMusPhysTuitionOne;

    @FXML
    private Label naturalScienceTuitionOne;

    @FXML
    private Label numOfPatentRegistrationTwo;

    @FXML
    private Label researchCostPerProfessorTwo;

    @FXML
    private Label numOfPatentRegistrationOne;

    @FXML
    private Label researchCostPerProfessorOne;

    @FXML
    private Label numOfFulltimeProfessorOne;

    @FXML
    private Label univAreaOne;

    @FXML
    private Label bookTotalOne;

    @FXML
    private Label dispatchedStudentOne;

    @FXML
    private Label dormitoryAccommodationRateOne;

    @FXML
    private Label humanitiesSocialTuitionOne;

    @FXML
    private GridPane cpPaneOne;

    @FXML
    private Label studentNumberTwo;

    @FXML
    private Label employmentRateTwo;

    @FXML
    private Label enteringRateTwo;

    @FXML
    private Label educationCostPerPersonTwo;

    @FXML
    private Label totalScholarshipBenefitTwo;

    @FXML
    private Label foundersNumberTwo;

    @FXML
    private Label startCompanySaleTwo;

    @FXML
    private Label startCompanyCapitalTwo;

    @FXML
    private Label schoolStartCompanyFundTwo;

    @FXML
    private Label educationCostPerPersonOne;

    @FXML
    private Label enteringRateOne;

    @FXML
    private Label employmentRateOne;

    @FXML
    private Label admissionCompetitionRateOne;

    @FXML
    private Label admissionFeeTwo;

    @FXML
    private Label staffForStartCompanyTwo;

    @FXML
    private Label profesorForStartCompanyTwo;

    @FXML
    private Label govermentStartCompanyFundTwo;

    @FXML
    private Label staffForStartCompanyOne;

    @FXML
    private Label profesorForStartCompanyOne;

    @FXML
    private Label govermentStartCompanyFundOne;

    @FXML
    private Label schoolStartCompanyFundOne;

    @FXML
    private Label startCompanySaleOne;

    @FXML
    private Label foundersNumberOne;

    @FXML
    private Label totalScholarshipBenefitOne;

    @FXML
    private Label admissionFeeOne;

    @FXML
    private Label averageTuitionOne;

    @FXML
    private Label averageTuitionTwo;

    @FXML
    private Label studentNumberOne;

    @FXML
    private Label admissionCompetitionRateTwo;

    @FXML
    private Label startCompanyCapitalOne;

    @FXML
    private Label firstUnivName;

    @FXML
    private Label secondUnivName;

    private static final int FIRST_UNIV_DETAIL = 0;
    private static final int SECOND_UNIV_DETAIL = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        TextFields.bindAutoCompletion(inputFirstUniv, Home.getUnivList()); // 텍스트필드 자동완성
//        TextFields.bindAutoCompletion(inputSecondUniv, Home.getUnivList()); // 텍스트필드 자동완성
    }

    @FXML
    void compareUnivDetail(MouseEvent event) {

        Runnable runnable = () -> {
            try {
                ArrayList<String> univList = new ArrayList<String>();

                univList.add(inputFirstUniv.getText().replace(" ", ""));
                univList.add(inputSecondUniv.getText().replace(" ", ""));

                Connection.send(new Protocol(Protocol.PT_REQ, Protocol.PT_REQ_UNIV_CP, univList));        // 패킷 전송

                ArrayList<UnivDetailDTO> receivedUnivDetailList = receiveUnivCp();

                Platform.runLater(() -> {
                    setUnivDetailInf(receivedUnivDetailList);    // 첫번쨰 학교 상세정보 화면에 세팅
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread th = new Thread(runnable);
        th.start();
    }

    public ArrayList<UnivDetailDTO> receiveUnivCp() throws Exception {
        Protocol receivePT = Connection.receive();
        Object receivedBody = receivePT.getBody();

        // TODO 학교이름 찾기 실패 패킷 수신 예외처리

        ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

        try {
            ArrayList<?> tmp = (ArrayList<?>) receivedBody;  // 읽어온 어레이리스트 처리 과정

            // 타입 처리
            for (Object obj : tmp) {
                if (obj instanceof UnivDetailDTO) {
                    result.add((UnivDetailDTO) obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;  // UnivDTO 타입인 Object 반환
    }

    public void setUnivDetailInf(ArrayList<UnivDetailDTO> receivedUnivDetailList) {  // UnivDetailDTO GUI에 뿌려주기
        UnivDetailDTO firstUnivDetail = receivedUnivDetailList.get(FIRST_UNIV_DETAIL);
        UnivDetailDTO secondUnivDetail = receivedUnivDetailList.get(SECOND_UNIV_DETAIL);

        studentNumberOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getStudentNumber()));
        studentNumberTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getStudentNumber()));
        compareUnivDetailElement(studentNumberOne, studentNumberTwo, firstUnivDetail.getStudentNumber(), secondUnivDetail.getStudentNumber());

        admissionCompetitionRateOne.setText(firstUnivDetail.getAdmissionCompetitionRate() + "%");
        admissionCompetitionRateTwo.setText(secondUnivDetail.getAdmissionCompetitionRate() + "%");
        compareUnivDetailElement(admissionCompetitionRateOne, admissionCompetitionRateTwo, firstUnivDetail.getAdmissionCompetitionRate(), secondUnivDetail.getAdmissionCompetitionRate());

        employmentRateOne.setText(firstUnivDetail.getEmploymentRate() + "%");
        employmentRateTwo.setText(secondUnivDetail.getEmploymentRate() + "%");
        compareUnivDetailElement(employmentRateOne, employmentRateTwo, firstUnivDetail.getEmploymentRate(), secondUnivDetail.getEmploymentRate());

        enteringRateOne.setText(firstUnivDetail.getEnteringRate() + "%");
        enteringRateTwo.setText(secondUnivDetail.getEnteringRate() + "%");
        compareUnivDetailElement(enteringRateOne, enteringRateTwo, firstUnivDetail.getEnteringRate(), secondUnivDetail.getEnteringRate());

        educationCostPerPersonOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getEducationCostPerPerson()));
        educationCostPerPersonTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getEducationCostPerPerson()));
        compareUnivDetailElement(educationCostPerPersonOne, educationCostPerPersonTwo, firstUnivDetail.getEducationCostPerPerson(), secondUnivDetail.getEducationCostPerPerson());

        totalScholarshipBenefitOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getTotalScholarshipBenefits()));
        totalScholarshipBenefitTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getTotalScholarshipBenefits()));
        compareUnivDetailElement(totalScholarshipBenefitOne, totalScholarshipBenefitTwo, firstUnivDetail.getTotalScholarshipBenefits(), secondUnivDetail.getTotalScholarshipBenefits());

        foundersNumberOne.setText(String.valueOf(firstUnivDetail.getNumberFounders()));
        foundersNumberTwo.setText(String.valueOf(secondUnivDetail.getNumberFounders()));
        compareUnivDetailElement(foundersNumberOne, foundersNumberTwo, firstUnivDetail.getNumberFounders(), secondUnivDetail.getNumberFounders());

        startCompanySaleOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getStartCompanySales()));
        startCompanySaleTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getStartCompanySales()));
        compareUnivDetailElement(startCompanySaleOne, startCompanySaleTwo, firstUnivDetail.getStartCompanySales(), secondUnivDetail.getStartCompanySales());

        startCompanyCapitalOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getStartCompanyCapital()));
        startCompanyCapitalTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getStartCompanyCapital()));
        compareUnivDetailElement(startCompanyCapitalOne, startCompanyCapitalTwo, firstUnivDetail.getStartCompanyCapital(), secondUnivDetail.getStartCompanyCapital());

        schoolStartCompanyFundOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getSchoolStartCompanyFund()));
        schoolStartCompanyFundTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getSchoolStartCompanyFund()));
        compareUnivDetailElement(schoolStartCompanyFundOne, schoolStartCompanyFundTwo, firstUnivDetail.getSchoolStartCompanyFund(), secondUnivDetail.getSchoolStartCompanyFund());

        govermentStartCompanyFundOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getGovernmentStartCompanyFund()));
        govermentStartCompanyFundTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getGovernmentStartCompanyFund()));
        compareUnivDetailElement(govermentStartCompanyFundOne, govermentStartCompanyFundTwo, firstUnivDetail.getGovernmentStartCompanyFund(), secondUnivDetail.getGovernmentStartCompanyFund());

        profesorForStartCompanyOne.setText(String.valueOf(firstUnivDetail.getProfessorForStartCompany()));
        profesorForStartCompanyTwo.setText(String.valueOf(secondUnivDetail.getProfessorForStartCompany()));
        compareUnivDetailElement(profesorForStartCompanyOne, profesorForStartCompanyTwo, firstUnivDetail.getProfessorForStartCompany(), secondUnivDetail.getProfessorForStartCompany());

        staffForStartCompanyOne.setText(String.valueOf(firstUnivDetail.getStaffForStartCompany()));
        staffForStartCompanyTwo.setText(String.valueOf(secondUnivDetail.getStaffForStartCompany()));
        compareUnivDetailElement(staffForStartCompanyOne, staffForStartCompanyTwo, firstUnivDetail.getStaffForStartCompany(), secondUnivDetail.getStaffForStartCompany());

        admissionFeeOne.setText(String.valueOf(firstUnivDetail.getAdmissionFee()));
        admissionFeeTwo.setText(String.valueOf(secondUnivDetail.getAdmissionFee()));
        compareUnivDetailElement(admissionFeeOne, admissionFeeTwo, firstUnivDetail.getAdmissionFee(), secondUnivDetail.getAdmissionFee());

        averageTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getAverageTuition()));
        averageTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getAverageTuition()));
        compareUnivDetailElement(averageTuitionOne, averageTuitionTwo, firstUnivDetail.getAverageTuition(), secondUnivDetail.getAverageTuition());

        humanitiesSocialTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getHumanitiesSocialTuition()));
        humanitiesSocialTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getHumanitiesSocialTuition()));
        compareUnivDetailElement(humanitiesSocialTuitionOne, humanitiesSocialTuitionTwo, firstUnivDetail.getHumanitiesSocialTuition(), secondUnivDetail.getHumanitiesSocialTuition());

        naturalScienceTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getNaturalScienceTuition()));
        naturalScienceTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getNaturalScienceTuition()));
        compareUnivDetailElement(naturalScienceTuitionOne, naturalScienceTuitionTwo, firstUnivDetail.getNaturalScienceTuition(), secondUnivDetail.getNaturalScienceTuition());

        artMusPhysTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getArtMusPhysTuition()));
        artMusPhysTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getArtMusPhysTuition()));
        compareUnivDetailElement(artMusPhysTuitionOne, artMusPhysTuitionTwo, firstUnivDetail.getArtMusPhysTuition(), secondUnivDetail.getArtMusPhysTuition());

        engineeringTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getEngineeringTuition()));
        engineeringTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getEngineeringTuition()));
        compareUnivDetailElement(engineeringTuitionOne, engineeringTuitionTwo, firstUnivDetail.getEngineeringTuition(), secondUnivDetail.getEngineeringTuition());

        medicalTuitionOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getMedicalTuition()));
        medicalTuitionTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getMedicalTuition()));
        compareUnivDetailElement(medicalTuitionOne, medicalTuitionTwo, firstUnivDetail.getMedicalTuition(), secondUnivDetail.getMedicalTuition());

        dormitoryAccommodationRateOne.setText(String.valueOf(firstUnivDetail.getDormitoryAccommodationRate()));
        dormitoryAccommodationRateTwo.setText(String.valueOf(secondUnivDetail.getDormitoryAccommodationRate()));
        compareUnivDetailElement(dormitoryAccommodationRateOne, dormitoryAccommodationRateTwo, firstUnivDetail.getDormitoryAccommodationRate(), secondUnivDetail.getDormitoryAccommodationRate());

        dispatchedStudentOne.setText(String.valueOf(firstUnivDetail.getDispatchedStudent()));
        dispatchedStudentTwo.setText(String.valueOf(secondUnivDetail.getDispatchedStudent()));
        compareUnivDetailElement(dispatchedStudentOne, dispatchedStudentTwo, firstUnivDetail.getDispatchedStudent(), secondUnivDetail.getDispatchedStudent());

        bookTotalOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getBookTotal()));
        bookTotalTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getBookTotal()));
        compareUnivDetailElement(bookTotalOne, bookTotalTwo, firstUnivDetail.getBookTotal(), secondUnivDetail.getBookTotal());

        univAreaOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getUnivArea()));
        univAreaTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getUnivArea()));
        compareUnivDetailElement(univAreaOne, univAreaTwo, firstUnivDetail.getUnivArea(), secondUnivDetail.getUnivArea());

        numOfFulltimeProfessorOne.setText(String.valueOf(firstUnivDetail.getNumOfFulltimeProfessor()));
        numOfFulltimeProfessorTwo.setText(String.valueOf(secondUnivDetail.getNumOfFulltimeProfessor()));
        compareUnivDetailElement(numOfFulltimeProfessorOne, numOfFulltimeProfessorTwo, firstUnivDetail.getNumOfFulltimeProfessor(), secondUnivDetail.getNumOfFulltimeProfessor());

        researchCostPerProfessorOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getResearchCostPerProfessor()));
        researchCostPerProfessorTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getResearchCostPerProfessor()));
        compareUnivDetailElement(researchCostPerProfessorOne, researchCostPerProfessorTwo, firstUnivDetail.getResearchCostPerProfessor(), secondUnivDetail.getResearchCostPerProfessor());

        numOfPatentRegistrationOne.setText(NumberFormat.getNumberInstance(Locale.US).format(firstUnivDetail.getNumOfPatentRegistration()));
        numOfPatentRegistrationTwo.setText(NumberFormat.getNumberInstance(Locale.US).format(secondUnivDetail.getNumOfPatentRegistration()));
        compareUnivDetailElement(numOfPatentRegistrationOne, numOfPatentRegistrationTwo, firstUnivDetail.getNumOfPatentRegistration(), secondUnivDetail.getNumOfPatentRegistration());
    }

    public void compareUnivDetailElement(Label n1, Label n2, Long el1, Long el2) {
        if (el1 < el2) {
            n2.setTextFill(Color.RED);
        } else if(el1 > el2){
            n1.setTextFill(Color.RED);
        }
    }
}
