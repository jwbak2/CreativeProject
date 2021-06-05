package Server.subcontroller;

import Server.model.dao.DepartmentDAO;
import Server.model.dao.DepartmentDetailDAO;
import Server.model.dto.DepartmentDetailDTO;

import static Server.model.Cache.START_YEAR;
import static Server.model.Cache.CUR_YEAR;

import java.util.ArrayList;
import java.util.HashMap;

public class Department {

	DepartmentDAO deptDAO;
	DepartmentDetailDAO deptDetailDAO;

	public Department() {
	}

	public DepartmentDetailDTO getDepartmentDetail(String deptId, int year) throws Exception {
		// 학과 상세정보 조회 - 학과 ID, 연도
		deptDetailDAO = deptDetailDAO == null ? new DepartmentDetailDAO() : deptDetailDAO;

		return deptDetailDAO.select(deptId, year);
	}

	public ArrayList<DepartmentDetailDTO> getAllDepartmentDetail(String deptId) throws Exception {
		// 학과의 모든 연도 상세정보 조회 - 학과 ID
		ArrayList<DepartmentDetailDTO> result = new ArrayList<DepartmentDetailDTO>();

		for (int i = START_YEAR; i <= CUR_YEAR; i++) {
			result.add(getDepartmentDetail(deptId, i));
		}

		// 연도별 대학 상세정보가 담긴 ArrayList 반환
		return result;
	}

	public ArrayList<String> getDepartmentList(String univId) throws Exception {
		// 특정 대학의 학과 리스트 반환

		// 이미 해당 대학의 학과 리스트를 가지고 있는지 확인
		HashMap<String, String> deptMap = Server.model.Cache.getDeptList(univId);


		if (deptMap == null) {
			// 해당 대학의 학과 리스트가 없음 -> DAO 를 통해 찾아옴
			deptDAO = deptDAO == null ? new DepartmentDAO() : deptDAO;

			deptMap = deptDAO.getDepartmentList(univId);

			// 찾아온 학과 리스트를 Server.Cache 에 저장
			// Why? : 사람들이 많이 조회하는 대학의 학과 리스트를 유지해서, 조회 속도 개선

			Server.model.Cache.setDeptList(univId, deptMap);
		}

		// 클라이언트가 사용할 수 있게 ArrayList 로 전송
		return new ArrayList<String>(deptMap.keySet());
	}

	public String getDepartmentID(String univName, String deptName) {
		// 대학 이름과 학과 이름으로 학과 ID 조회
		deptDAO = deptDAO == null ? new DepartmentDAO() : deptDAO;

		String univId = new Univ().getUnivId(univName);

		if (univId != null) {
			return deptDAO.getDeptID(univId, deptName);
		} else {
			return null;
		}

	}


	public double getScoreByYear(String univId, ArrayList<String> idct) {
		final double RATIO_YEAR = 0.85;
		deptDetailDAO = deptDetailDAO == null ? new DepartmentDetailDAO() : deptDetailDAO;

		double total = deptDetailDAO.calculateScoreByYear(START_YEAR, univId, idct);
		for (int i = START_YEAR + 1; i <= CUR_YEAR; i++) {
			total = RATIO_YEAR * deptDetailDAO.calculateScoreByYear(i, univId, idct)
					+ (1 - RATIO_YEAR) * total;

		}

		return total;
	}

}
