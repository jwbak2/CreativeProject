package Server.model;

import Server.model.dao.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cache {	// 사용빈도 높은 서버 데이터를 보관하는 클래스

	private static final int MAX_SIZE = 10;
	public static final int CUR_YEAR = 2020;
	public static final int START_YEAR = 2018;

	private static HashMap<String, String> univList;	// 학과 리스트
	private static ArrayList< Pair< String, HashMap<String, String> > > deptListCollection;		// 학과 리스트
	private static HashMap< String, MinMax > minMaxOfIndicators;	// 속성의 min max 값  ->  맞춤형 평점의 점수 계산에 사용됨


	public static void init(){
		setUnivList();
		setMinMaxOfIndicators();
		deptListCollection = new ArrayList<>();
	}

	private static void setUnivList() {		// 캐쉬 객체에 학교 리스트 세팅
		try {
			univList = new UnivDAO().getUnivList();

		} catch (Exception e) {
			System.out.println("학교 리스트 캐쉬 세팅 오류");

		}
	}

	private static void setMinMaxOfIndicators() {		// 캐쉬 객체에 학교/학과 속성의 min, max 값 저장
		try {
			minMaxOfIndicators = new HashMap<>();

			String[] univIndicators = {
					"admission_competition_rate", "education_cost_per_person", "number_founders",
					"start_company_sales", "start_company_capital", "school_start_company_fund", "government_start_company_fund",
					"professor_for_start_company", "staff_for_start_company", "dormitory_accommodation_rate", "book_total", "univ_area",
					"num_of_patent_registration"
			};

			String[] deptIndicators = {
					"admission_fee", "tuition", "entering_dom_cmnty_coll", "entering_overseas_cmnty_coll",
					"entering_dom_univ", "entering_overseas_univ", "entering_dom_gr_school", "entering_overseas_gr_school",
					"dom_scholar_number", "overseas_scholar_number", "entering_rate", "male_employment_target", "female_employment_target",
					"male_dom_employee", "female_dom_employee", "male_overseas_employee", "female_overseas_employee", "employment_rate",
					"out_school_scholarship", "in_school_scholarship", "scholarship_per_person", "num_of_fulltime_professor", "thesis_result_per_professor",
					"rearch_cost_per_professor"
			};

			for (int i = START_YEAR; i <= CUR_YEAR; i++) {
				MinMaxOfIndicator[] minMaxData = new UnivDetailDAO().selectMinMax(i, univIndicators);

				for (int j = 0; j < minMaxData.length; j++) {
					MinMax minMax = new MinMax(minMaxData[j].getMin(), minMaxData[j].getMax());
					minMaxOfIndicators.put(i + " " + minMaxData[j].getName(), minMax);

				}


				minMaxData = new DepartmentDetailDAO().selectMinMax(i, deptIndicators);

				for (int j = 0; j < minMaxData.length; j++) {
					MinMax minMax = new MinMax(minMaxData[j].getMin(), minMaxData[j].getMax());
					minMaxOfIndicators.put(i + " " + minMaxData[j].getName(), minMax);

				}

			}


//			for (String key: minMaxOfIndicators.keySet()){
//				MinMax mm = minMaxOfIndicators.get(key);
//				System.out.println(key + " = " + mm.getMin() + " " + mm.getMax());
//			}


		} catch (Exception e) {

		}
	}

	public static HashMap<String, String> getUnivList() {
		return univList;
	}

	public static HashMap<String, String> getDeptList(String univId) {
		// 대학 ID 로 해당 대학의 학과 리스트를 가지고 있는지를 확인하고 반환

		for (int i = 0; i < deptListCollection.size(); i++) {
			if (univId == deptListCollection.get(i).left) {
				// 조회한 학과 리스트의 count 증가 => 조회수 순서대로 관리하기 위함
				deptListCollection.get(i).count++;

				return deptListCollection.get(i).right;
			}
		}

		return null;	// Not found
	}

	public static HashMap< String, MinMax > getMinMaxOfIndicators() {
		return minMaxOfIndicators;
	}

	public static void setDeptList(String univId, HashMap<String, String> deptList) {
		// 해당 대학의 학과 리스트를 저장

		// 학과 리스트 보관함이 꽉차면 가장 적게 조회한 학과 리스트를 삭제
		if (isTooMany()) {
			deleteLowestPair();
		}

		// 학과 리스트 저장
		Pair<String, HashMap<String, String>> data = new Pair<>(univId, deptList);
		deptListCollection.add(data);
	}

	// hashmap에 value 로 key 찾기
	public static <K, V> K getKey(Map<K, V> map, V value) {

		for (K key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}

	private static boolean isTooMany() {
		return deptListCollection.size() > MAX_SIZE;
	}

	private static void deleteLowestPair() {
		int min = deptListCollection.get(0).count;
		int minIndex = 0;

		for (int i = 1; i < MAX_SIZE; i++) {
			if (min > deptListCollection.get(i).count) {
				min = deptListCollection.get(i).count;
				minIndex = i;
			}
		}

		deptListCollection.remove(minIndex);
	}

}
