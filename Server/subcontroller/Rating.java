package Server.subcontroller;

import Server.model.dao.DepartmentRatingDAO;
import Server.model.dao.UnivRatingDAO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.util.ArrayList;

public class Rating {

	UnivRatingDAO univRatingDAO;
	DepartmentRatingDAO deptRatingDAO;

	public Rating() {

		univRatingDAO = new UnivRatingDAO();
		deptRatingDAO = new DepartmentRatingDAO();

	}

	public boolean registerUnivRating(UnivRatingDTO content) {
		// 대학 평가 등록

		return univRatingDAO.insert(content);
	}

	public boolean registerDeptRating(DepartmentRatingDTO content) {
		// 학과 평가 등록

		return deptRatingDAO.insert(content);
	}

	public ArrayList<UnivRatingDTO> getUnivRatingList(String univId) {
		// 대학 평가 리스트 반환

		return univRatingDAO.select(univId);
	}

	public ArrayList<DepartmentRatingDTO> getDeptRatingList(String deptId) {
		// 학과 평가 리스트 반환

		return deptRatingDAO.select(deptId);
	}

	public UnivRatingDTO getUnivRatingOfUser(String email) {
		// 사용자 이메일로 대학 평가 반환

		return univRatingDAO.selectByEmail(email);
	}

	public DepartmentRatingDTO getDeptRatingOfUser(String email) {
		// 사용자 이메일로 학과 평가 반환

		return deptRatingDAO.selectByEmail(email);
	}

}
