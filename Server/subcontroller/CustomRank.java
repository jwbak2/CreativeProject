package Server.subcontroller;

import Client.vo.CustomizedRankResVO;
import Client.vo.DeptInfoReqVO;
import Server.model.dao.IndicatorSelectionStatisticsDAO;

import java.util.ArrayList;
import java.util.Collections;

public class CustomRank {

	final int NUM_OF_INDICATORS = 3;
	final double[] RATIO_OF_INDICATORS = {42.3, 33.3, 24.3};	// 1순위 ~ NUM_OF_INDICATORS 순위

	// deptList = 학과 리스트, indicators = 사용자가 선택한 지표 (1, 2, 3, ... 순위)
	public ArrayList<CustomizedRankResVO> getRanking(ArrayList<DeptInfoReqVO> deptList, ArrayList<String> indicators) throws Exception {

		ArrayList<String>[] idctList = new ArrayList[NUM_OF_INDICATORS];

		for (int i = 0; i < NUM_OF_INDICATORS; i++) {
			System.out.println("지표명: " + indicators.get(i));

			// 사용자가 선택한 지표의 조회수 + 1
			new IndicatorSelectionStatisticsDAO().increaseIndicatorView(indicators.get(i));

			// 지표를 속성 리스트로 변환
			idctList[i] = classifyIndicators(indicators.get(i));
		}

		Univ univSC = new Univ();
		Department deptSC = new Department();

		ArrayList<CustomizedRankResVO> result = new ArrayList<>();

		double[] scores = new double[deptList.size()];
		for (int i = 0; i < deptList.size(); i++) {

			String univName = deptList.get(i).getUnivName();
			String deptName = deptList.get(i).getDeptName();
			String univId = univSC.getUnivId(univName);
			String deptId = deptSC.getDepartmentID(univName, deptName);

			// 조회한 학과의 대학 조회수 1 증가
			univSC.increaseView(univId);

			System.out.println("--각 학교 점수 구하기 " + univName + " " + deptName);

			double[] deptScore = new double[NUM_OF_INDICATORS];
			for (int j = 0; j < NUM_OF_INDICATORS; j++) {
				String type = idctList[j].get(0);
				System.out.println("----각 지표별 점수 구하기, type: " + type);

				if (type.equals("UNIV")) {
					deptScore[j] += univSC.getScoreByYear(univId, new ArrayList<>(idctList[j].subList(1, idctList[j].size())));

				} else {
					deptScore[j] += deptSC.getScoreByYear(deptId, new ArrayList<>(idctList[j].subList(1, idctList[j].size())));

				}

			}

			scores[i] = getTotalScore(deptScore);
			System.out.println("--학교 점수 " + scores[i]);

			result.add(new CustomizedRankResVO(univName, deptName, deptScore[0], deptScore[1], deptScore[2], scores[i]));
		}

		Collections.sort(result, Collections.reverseOrder());
		return result;

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

	public double getTotalScore(double[] scores) {
		double result = 0.0;

		for (int i = 0; i < scores.length; i++) {
			scores[i] *= RATIO_OF_INDICATORS[i];
			result += scores[i];
			System.out.println(i + "----번째까지 합산 지표 점수: " + result);
		}

		return result;
	}
}
