package Server.subcontroller;

import Client.vo.RankVO;
import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;

import static Server.model.Cache.START_YEAR;
import static Server.model.Cache.CUR_YEAR;

import java.util.ArrayList;
import java.util.HashMap;

public class Univ {

	UnivDAO univDAO;
	UnivDetailDAO univDetailDAO;


	public Univ() {
		// pass
	}


	public ArrayList<String> getUnivList() {
		// 대학 리스트
		HashMap<String, String> univMap = Server.model.Cache.getUnivList();

		// 클라이언트가 사용할 수 있도록 ArrayList로 변환
		return new ArrayList<String>(univMap.keySet());
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
		for (int i = START_YEAR; i <= CUR_YEAR; i++) {
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

	public ArrayList<RankVO> getViewListOfUniv() {
		// 대학 정보 반환
		univDAO = univDAO == null ? new UnivDAO() : univDAO;

		return univDAO.getViewList();
	}

	public double getScoreByYear(String univId, ArrayList<String> idct) {
		// 해당 지표의 연도별 점수 구하기
		final double RATIO_YEAR = 0.85;
		univDetailDAO = univDetailDAO == null ? new UnivDetailDAO() : univDetailDAO;

		double total = univDetailDAO.calculateScoreByYear(START_YEAR, univId, idct);
		for (int i = START_YEAR + 1; i <= CUR_YEAR; i++) {
			System.out.println("------지표의 연도별 점수 구하기 " + i);
			total = RATIO_YEAR * univDetailDAO.calculateScoreByYear(i, univId, idct)
					+ (1 - RATIO_YEAR) * total;

		}

		return total;
	}

}
