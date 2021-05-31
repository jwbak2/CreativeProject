package Server.transmission;

import Server.controller.DepartmentDetail;
import Server.controller.Rating;
import Server.controller.UnivDetail;
import Client.vo.LoginReqVO;
import Server.model.dao.UserDAO;
import Server.model.dto.UserDTO;
import Server.model.dao.DepartmentDAO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.util.ArrayList;

public class Controller {

	// 대학 리스트 조회 요청 처리
	private UserDTO curUser;

	// 대학 리스트 조회
	public void inquiryUnivList() {
		UnivDetail univDetail = new UnivDetail();
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, univDetail.getUnivList());
	}

	// 대학 상세정보 조회 요청 처리
	public void inquiryUnivInfo(String univName) {
		try {
			UnivDetail univDetail = new UnivDetail();
			DepartmentDetail deptDetail = new DepartmentDetail();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUniv(univName));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getAllUnivDetail(univName));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, deptDetail.getDepartmentList(univDetail.getUnivCode(univName)));

		} catch (Exception e) {
			System.out.println("Controller - 대학 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_INF));
			e.printStackTrace();

		}

	}

	// 학과 상세정보 조회 요청 처리
	public void inquiryDepartmentInfo(String deptName) {
		try	{
			DepartmentDetail deptDetail = new DepartmentDetail();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_DETAIL, deptDetail.getDepartmentDetail(deptName));

		} catch (Exception e) {
			System.out.println("Controller - 학과 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_INF));
			e.printStackTrace();

		}
	}

	// 대학 비교 요청 처리
	public void compareTwoUniv(ArrayList<String> list) {

		final int YEAR = 2020;

		try {
			UnivDetail univDetail = new UnivDetail();
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(list.get(0), YEAR));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(list.get(1), YEAR));

		} catch (Exception e) {
			System.out.println("Controller - 대학 비교 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_CP));
			e.printStackTrace();

		}
	}

	// 대학 평가 등록 요청 처리
	public void registerUnivRating(UnivRatingDTO content) {
		// TODO: 로직 필요
		Rating rating = new Rating();

		if (rating.registerUnivRating(content)) {
			Sender.send(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_UNIV_RATING));
		}
		else {
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_RATING));
		}

	}

	// 학과 평가 등록 요청 처리
	public void registerDepartmentRating(DepartmentRatingDTO content) {
		// TODO: 로직 필요
		// TODO: 로직 필요
		Rating rating = new Rating();

		if (rating.registerDeptRating(content)) {
			Sender.send(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_DEPT_RATING));
		}
		else {
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_RATING));
		}

	}

	// 로그인 요청 처리
	public void reqLogin(LoginReqVO loginInfo){

		UserDAO userDAO = new UserDAO();
		UserDTO result = userDAO.login(loginInfo.getID(), loginInfo.getPW());

		if(result != null){

			curUser = result;
			Sender.send(Protocol.PT_SUCC, Protocol.PT_SUCC_LOGIN, null);

		} else {

			Sender.send(Protocol.PT_FAIL, Protocol.PT_FAIL_LOGIN, null);

		}


	}

}
