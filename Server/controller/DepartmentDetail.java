package Server.controller;

import Server.model.dao.DepartmentDAO;
import Server.model.dao.DepartmentDetailDAO;
import Server.model.dto.DepartmentDTO;
import Server.model.dto.DepartmentDetailDTO;

public class DepartmentDetail {

	DepartmentDAO deptDAO;
	DepartmentDetailDAO deptDetailDAO;

	public DepartmentDetail() {
	}

	public DepartmentDetailDTO getDepartmentDetail(String deptName) throws Exception {
		// 학과 상세정보 조회
		deptDetailDAO = deptDetailDAO == null ? new DepartmentDetailDAO() : deptDetailDAO;

		// TODO: DAO 오류 수정 필요
//		return deptDetailDAO.select(deptName, 2020);
		return null;
	}

	public String[] getDepartmentList(String univCode) throws Exception {
		// 특정 대학의 학과 리스트 반환
		deptDAO = deptDAO == null ? new DepartmentDAO() : deptDAO;

		return deptDAO.getDepartmentList(univCode);
	}


}
