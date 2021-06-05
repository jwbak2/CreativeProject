package Server.subcontroller;

import Client.vo.DeptInfoReqVO;
import Server.model.Cache;
import Server.model.Pair;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.DepartmentDetailDTO;
import Server.model.dto.UnivDetailDTO;

import java.util.ArrayList;
import java.util.HashMap;

import static Server.model.Cache.CUR_YEAR;
import static Server.model.Cache.START_YEAR;

public class CustomRank {

	final int UNIV = 0;
	final int DEPT = 1;
	final int NUM_OF_INDICATORS = 3;

	// deptList = 학과 리스트, indicators = 사용자가 선택한 지표 (1, 2, 3, ... 순위)
	public void getRanking(ArrayList<DeptInfoReqVO> deptList, ArrayList<String> indicators) throws Exception {
		// 학과 정보, 대학 정보 얻어오기
		// 지표 분리
		// 학과 대학의 속성 데이터 불러오기

		// 각 대학별 점수 측정
		for (int i = 0; i < deptList.size(); i++) {
			// 대학 상세정보 가져오기
			Univ univSC = new Univ();
			String univId =	univSC.getUnivId(deptList.get(i).getUnivName());
			ArrayList<UnivDetailDTO> univ = univSC.getAllUnivDetail(univId);

			// 학과 상세정보 가져오기
			Department deptSC = new Department();
			String deptId = deptSC.getDepartmentID(deptList.get(i).getUnivName(), deptList.get(i).getDeptName());
			ArrayList<DepartmentDetailDTO> dept = deptSC.getAllDepartmentDetail(deptId);

			// 각 지표 별로 계산 해야함 (순위별 퍼센티지를 위해서)
			int[] scoresByIdct = calculateByIndicator(univ, dept, indicators);

			// 지표 분리

			// 점수 계산

		}

	}

	// 지표로부터  학교 속성/학과 속성  을 분리
	public ArrayList<String> classifyIndicators(String indicators) {
		ArrayList<String> list = new ArrayList<>();

		switch (indicators) {
			case "입학경쟁률": case "1인당 교육비": case "학생의 창업 및 창업 지원 현황":
			case "기숙사 수용률": case "도서자료 보유량": case "교지 면적": case "특허 출원 및 등록 실적":
				list.add("UNIV");
				break;

			case "등록금 현황": case "졸업생의 진학 현황": case "졸업생의 취업 현황":
			case "장학금 수혜 현황": case "전임교원 연구실적": case "연구비 수혜 실적":
				list.add("DEPT");
				break;

			default:
				System.out.println("알 수 없는 지표");
				break;
		}

		switch (indicators) {
			case "입학경쟁률":
				list.add("admission_competition_rate");
				break;

			case "1인당 교육비":
				list.add("education_cost_per_person");
				break;

			case "학생의 창업 및 창업 지원 현황":
				list.add("number_founders");
				list.add("start_company_sales");
				list.add("start_company_capital");
				list.add("school_start_company_fund");
				list.add("government_start_company_fund");
				list.add("professor_for_start_company");
				list.add("staff_for_start_company");
				break;

			case "기숙사 수용률":
				list.add("dormitory_accommodation_rate");
				break;

			case "도서자료 보유량":
				list.add("book_total");
				break;

			case "교지 면적":
				list.add("univ_area");
				break;

			case "특허 출원 및 등록 실적":
				list.add("num_of_patent_registration");
				break;

			case "등록금 현황":
				list.add("admission_fee");
				list.add("tuition");
				break;

			case "졸업생의 진학 현황":
				list.add("entering_dom_cmnty_coll");
				list.add("entering_overseas_cmnty_coll");
				list.add("entering_dom_univ");
				list.add("entering_overseas_univ");
				list.add("entering_dom_gr_school");
				list.add("entering_overseas_gr_school");
				list.add("dom_scholar_number");
				list.add("overseas_scholar_number");
				list.add("entering_rate");
				break;

			case "졸업생의 취업 현황":
				list.add("male_employment_target");
				list.add("female_employment_target");
				list.add("male_dom_employee");
				list.add("female_dom_employee");
				list.add("male_overseas_employee");
				list.add("female_overseas_employee");
				list.add("employment_rate");
				break;

			case "장학금 수혜 현황":
				list.add("out_school_scholarship");
				list.add("in_school_scholarship");
				list.add("scholarship_per_person");
				break;

			case "전임교원 연구실적":
				list.add("num_of_fulltime_professor");
				list.add("thesis_result_per_professor");
				break;

			case "연구비 수혜 실적":
				list.add("rearch_cost_per_professor");
				break;

			default:
				System.out.println("알 수 없는 지표");
				break;
		}

		return list;
	}

	//  각 지표의 연도별 점수
	public int[] calculateScoreUniv(ArrayList<UnivDetailDTO> univ, ArrayList<String> smallIndicators) {
		System.out.println("지표 연도별 점수 계산");
		int[] scores = new int[CUR_YEAR - START_YEAR + 1];
		HashMap< String, Pair<Long, Long> > minMaxList = Cache.getMinMaxOfIndicators();

		// 각 연도마다 지표에 해당하는 속성(들)에 대한 점수 계산
		for (int i = START_YEAR; i <= CUR_YEAR; i++) {
			System.out.println("지표에 속하는 속성의 연도별 점수 계산");

			for (int j = 0; j < smallIndicators.size(); j++) {
				Pair<Long, Long> minMax = minMaxList.get(i + " " + smallIndicators.get(j));
				Long min = minMax.left;
				Long max = minMax.left;

				System.out.println("in 각 지표의 연도별 점수 " + univ.get(i - START_YEAR).getYear());
				double score = ((double) getValue(univ.get(i - START_YEAR), smallIndicators.get(j))) / (max - min);
			}
		}


		return scores;

	}

	//
	public int[] calculateScoreDept(ArrayList<DepartmentDetailDTO> dept, ArrayList<String> list) {
		int[] scores = new int[CUR_YEAR - START_YEAR + 1];
		HashMap< String, Pair<Long, Long> > minMaxList = Cache.getMinMaxOfIndicators();

		for (int i = START_YEAR; i < CUR_YEAR; i++) {
//			minMaxList.get(i + " " + list.get());
		}

		return null;

	}

	// 학과 평점을 지표별로 계산
	public int[] calculateByIndicator(ArrayList<UnivDetailDTO> univ, ArrayList<DepartmentDetailDTO> dept, ArrayList<String> indicators) {
		int[] result = null;


		for (int j = 0; j < NUM_OF_INDICATORS; j++) {
			ArrayList<String> smallIndicators = classifyIndicators(indicators.get(j));

			String whichOne = smallIndicators.get(0);
			// 지표의 연도별 점수 START_YEAR ~ CUR_YEAR

			int[] scoresByYear;
			if (whichOne.equals("UNIV")) {
				scoresByYear = calculateScoreUniv(univ, smallIndicators);

			} else if (whichOne.equals("DEPT")) {
				scoresByYear = calculateScoreDept(dept, smallIndicators);

			}


		}

		return result;
	}

	//
//	public int calculateByYear(int year Long value) {
//
//	}

	public Long getValue(UnivDetailDTO dto, String indicator) {
		switch (indicator) {
			case "admission_competition_rate":
				dto.getAdmissionCompetitionRate();
				break;

			case "education_cost_per_person":
				dto.getEducationCostPerPerson();
				break;

			case "number_founders":
				dto.getNumberFounders();
				break;

			case "start_company_sales":

				break;

			case "start_company_capital":

				break;

			case "school_start_company_fund":

				break;

			case "government_start_company_fund":

				break;

			case "professor_for_start_company":

				break;

			case "staff_for_start_company":
				break;

			case "dormitory_accommodation_rate":
				break;

			case "book_total":
				break;

			case "univ_area":
				break;

			case "num_of_patent_registration":
				break;
		}
		return null;
	}
	public Long getValue(DepartmentDetailDTO dto, String indicator) {
		switch (indicator) {
			case "admission_fee":

				break;

			case "tuition":

				break;

			case "entering_dom_cmnty_coll":

				break;

			case "entering_overseas_cmnty_coll":

				break;

			case "entering_dom_univ":

				break;

			case "entering_overseas_univ":

				break;

			case "entering_dom_gr_school":

				break;

			case "entering_overseas_gr_school":

				break;

			case "dom_scholar_number":

				break;

			case "overseas_scholar_number":

				break;

			case "entering_rate":

				break;

			case "male_employment_target":

				break;

			case "female_employment_target":

				break;

			case "male_dom_employee":

				break;

			case "female_dom_employee":

				break;

			case "male_overseas_employee":

				break;

			case "female_overseas_employee":

				break;

			case "employment_rate":

				break;

			case "out_school_scholarship":

				break;

			case "in_school_scholarship":

				break;

			case "scholarship_per_person":

				break;

			case "num_of_fulltime_professor":

				break;

			case "thesis_result_per_professor":

				break;

			case "rearch_cost_per_professor":

				break;

			default:
				System.out.println("알 수 없는 지표");
				break;
		}

		return null;
	}
}
