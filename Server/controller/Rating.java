package Server.controller;

import Server.model.dao.DepartmentRatingDAO;
import Server.model.dao.UnivRatingDAO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

public class Rating {


	public Rating() {
	}

	public boolean registerUnivRating(UnivRatingDTO content) {
		// 대학 평가 등록
		UnivRatingDAO dao = new UnivRatingDAO();
		return dao.insertUnivRating(content);

	}

	public boolean registerDeptRating(DepartmentRatingDTO content) {
		// 학과 평가 등록
		DepartmentRatingDAO dao = new DepartmentRatingDAO();
		return dao.insertDepartmentRating(content);

	}
}
