package Server.transmission;

import Client.vo.DeptInfoReqVO;
import Server.controller.DepartmentDetail;
import Server.controller.Rating;
import Server.controller.UnivDetail;
import Client.vo.LoginReqVO;
import Server.model.dao.UserDAO;
import Server.model.dto.*;
import Server.model.dao.DepartmentDAO;

import java.util.ArrayList;

public class Controller {

	public static final int CUR_YEAR = 2020;
	public static final int START_YEAR = 2018;

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

			// 대학 코드 검색
			String univCode = univDetail.getUnivId(univName);

			if (univCode != null) {
				Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUniv(univCode));
				Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getAllUnivDetail(univCode));
				Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, deptDetail.getDepartmentList(univCode));

			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			System.out.println("Controller - 대학 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_INF));
			e.printStackTrace();

		}

	}

	// 학과 상세정보 조회 요청 처리
	public void inquiryDepartmentInfo(DeptInfoReqVO deptInfo) {
		try	{
			DepartmentDetail deptDetail = new DepartmentDetail();
			UnivDetail univDetail = new UnivDetail();

			String deptID = deptDetail.getDepartmentID(univDetail.getUnivId(deptInfo.getUnivName()), deptInfo.getDeptName());

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_DETAIL, deptDetail.getAllDepartmentDetail(deptID));

		} catch (Exception e) {
			System.out.println("Controller - 학과 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_INF));
			e.printStackTrace();

		}
	}

	// 대학 비교 요청 처리
	public void compareTwoUniv(ArrayList<String> list) {

		ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

		try {
			UnivDetail univDetail = new UnivDetail();

			result.add(univDetail.getUnivDetail(univDetail.getUnivId(list.get(0)), CUR_YEAR));
			result.add(univDetail.getUnivDetail(univDetail.getUnivId(list.get(1)), CUR_YEAR));

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_CP, result);

		} catch (Exception e) {
			System.out.println("Controller - 대학 비교 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_CP));
			e.printStackTrace();

		}
	}

	public void compareTwoDept(ArrayList<String> list) {

		ArrayList<DepartmentDetailDTO> result = new ArrayList<DepartmentDetailDTO>();

		try {
			UnivDetail univDetail = new UnivDetail();
			DepartmentDetail deptDetail = new DepartmentDetail();

			result.add(deptDetail.getDepartmentDetail(univDetail.getUnivId(list.get(0)), CUR_YEAR));
			result.add(deptDetail.getDepartmentDetail(univDetail.getUnivId(list.get(1)), CUR_YEAR));

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_CP, result);

		} catch (Exception e) {
			System.out.println("Controller - 대학 비교 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_CP));
			e.printStackTrace();

		}
	}

	// 대학 평가 등록 요청 처리
	public void registerUnivRating(UnivRatingDTO content) {
		Rating rating = new Rating();

		if (rating.registerUnivRating(content)) {
			Sender.send(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_UNIV_RATING));
		}
		else {
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_RATING));
		}

	}

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
