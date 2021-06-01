package Server.model;

import Server.model.dao.UnivDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class Cache {	// 사용빈도 높은 서버 데이터를 보관하는 클래스

	private static final int MAX_SIZE = 10;

	private static HashMap<String, String> univList;
	private static ArrayList<Pair<String, HashMap<String, String>>> deptListCollection;

	public static void init(){
		setUnivList();
		deptListCollection = new ArrayList<>();
	}

	private static void setUnivList() {		// 캐쉬 객체에 학교 리스트 세팅
		try {
			univList = new UnivDAO().getUnivList();
		} catch (Exception e) {
			e.printStackTrace();
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
