package Server.controller;

import Server.model.dao.DepartmentDAO;
import Server.model.dto.DepartmentDTO;

public class DepartmentDetail {


	public DepartmentDetail() {
	}

	public DepartmentDTO getDepartmentDetail(String deptName) {
		// 학과 상세정보 조회

		return null;
	}

	public String[] getDepartmentList(String univCode) throws Exception {
		// 특정 대학의 학과 리스트 반환
		DepartmentDAO deptDAO = new DepartmentDAO();
		return deptDAO.getDepartmentList(univCode);
	}


}
