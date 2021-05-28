package Server.model;

import Server.model.dao.UnivDAO;

public class Cache {	// 사용빈도 높은 데이터를 보관하는 클래스

	String[][] univList;

	public Cache() {	// 캐쉬 객체 초기화
		setUnivList();

	}

	public void setUnivList() {		// 캐쉬 객체에 학교 리스트 세팅
		try {
			this.univList = new UnivDAO().getUnivList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String[][] getUnivList() {
		return univList;
	}

}
