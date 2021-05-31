package Server.controller;

import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;

import java.util.ArrayList;

public class UnivDetail {

	UnivDAO univDAO;
	UnivDetailDAO univDetailDAO;


	public UnivDetail() {
		// pass
	}


	public String[] getUnivList() {
		// 대학 리스트
		String[][] univList = Server.model.Cache.getUnivList();
		String[] list = new String[univList.length];

		for (int i = 0; i < univList.length; i++)
		{
			list[i] = univList[i][1];
		}

		return list;
	}

	public UnivDTO getUniv(String univName) throws Exception {
		// 대학 정보 반환
		univDAO = univDAO == null ? new UnivDAO() : univDAO;

		return univDAO.select(getUnivCode(univName));
	}

	public UnivDetailDTO getUnivDetail(String univName, int year) throws Exception {
		// 대학 상세정보 반환
		String univCode = getUnivCode(univName);

		univDetailDAO = univDetailDAO == null ? new UnivDetailDAO() : univDetailDAO;

		return univDetailDAO.select(univCode, year);
	}

	public ArrayList<UnivDetailDTO> getAllUnivDetail(String univName) throws Exception {
		// 대학 상세정보 반환

		final int START_YEAR = 2018;
		final int END_YEAR = 2020;

		// START ~ END 기간의 대학 상세정보 데이터 받아오기
		ArrayList<UnivDetailDTO> tmp = new ArrayList<UnivDetailDTO>();
		for (int i = START_YEAR; i <= END_YEAR; i++) {
			tmp.add(getUnivDetail(univName, i));
		}

		// 연도별 대학 상세정보가 담긴 ArrayList 반환
		return tmp;
	}

	public String getUnivCode(String univName) {
		System.out.println("조회할 학교 이름: " + univName);

		// 대학 리스트
		String[][] univList = Server.model.Cache.getUnivList();

		// 대학 리스트 자료구조 및 탐색 - 개선 필요해보임
		String univCode = "null";
		for (int i = 0; i < univList.length; i++)
		{
			if (univList[i][1].equals(univName))
			{
				univCode = univList[i][0];
				break;
			}
		}

		return univCode;
	}

}
