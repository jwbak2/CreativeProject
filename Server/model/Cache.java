package Server.model;

import Server.model.dao.UnivDAO;

public class Cache {	// 사용빈도 높은 서버 데이터를 보관하는 클래스

	private static String[][] univList;

	public static void init(){
		setUnivList();
	}

	private static void setUnivList() {		// 캐쉬 객체에 학교 리스트 세팅
		try {
			univList = new UnivDAO().getUnivList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String[][] getUnivList() {
		return univList;
	}

}
