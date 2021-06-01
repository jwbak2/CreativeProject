package Server.controller;

import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;
import Server.transmission.Controller;

import java.util.ArrayList;
import java.util.HashMap;

public class UnivDetail {

	UnivDAO univDAO;
	UnivDetailDAO univDetailDAO;


	public UnivDetail() {
		// pass
	}


	public ArrayList<String> getUnivList() {
		// 대학 리스트
		HashMap<String, String> univList = Server.model.Cache.getUnivList();
		ArrayList<String> list = new ArrayList<>();

		for (String key : univList.keySet()) {
			list.add(key);
		}

		return list;
	}

	public UnivDTO getUniv(String univCode) throws Exception {
		// 대학 정보 반환
		univDAO = univDAO == null ? new UnivDAO() : univDAO;

		return univDAO.select(univCode);
	}

	public UnivDetailDTO getUnivDetail(String univCode, int year) throws Exception {
		// 대학 상세정보 반환
		univDetailDAO = univDetailDAO == null ? new UnivDetailDAO() : univDetailDAO;

		return univDetailDAO.select(univCode, year);
	}

	public ArrayList<UnivDetailDTO> getAllUnivDetail(String univCode) throws Exception {
		// 대학 상세정보 반환

		// START ~ CUR 기간의 대학 상세정보 데이터 받아오기
		ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();
		for (int i = Controller.START_YEAR; i <= Controller.CUR_YEAR; i++) {
			result.add(getUnivDetail(univCode, i));
		}

		// 연도별 대학 상세정보가 담긴 ArrayList 반환
		return result;
	}

	public String getUnivId(String univName) {
		System.out.println("조회할 학교 이름: " + univName);

		// 대학 리스트
		HashMap<String, String> univList = Server.model.Cache.getUnivList();

		// 대학 ID 탐색
		String univId = univList.get(univName);


		// 조회된 대학 ID 가 없을 때, null 반환
		return univId;
	}

}
