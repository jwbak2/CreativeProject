package Server.transmission;

import Client.vo.*;
import Server.subcontroller.*;
import Server.model.dao.UserBookmarkDAO;
import Server.model.dao.UserDAO;
import Server.model.dto.*;

import java.util.ArrayList;

public class Controller {

	public static final int CUR_YEAR = 2020;
	public static final int START_YEAR = 2018;

	private UserDTO curUser;

	// 대학 리스트 조회 요청 처리
	public void inquiryUnivList() {
		Univ univ = new Univ();

		Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, univ.getUnivList());
	}

	// 학과 리스트 조회 요청 처리
	public void inquiryDepartmentList(String univName) {
		try {
			Univ univ = new Univ();
			Department deptDetail = new Department();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_LIST_OF_UNIV, deptDetail.getDepartmentList(univ.getUnivId(univName)));

		} catch (Exception e) {
			System.out.println("Controller - 학과 리스트 조회 오류");

		}
	}

	// 대학 상세정보 조회 요청 처리
	public void inquiryUnivInfo(String univName) {
		try {
			Univ univ = new Univ();

			// 대학 ID 검색
			String univId = univ.getUnivId(univName);

			if (univId != null) {
				Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univ.getUniv(univId));
				Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univ.getAllUnivDetail(univId));

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
			Department deptDetail = new Department();
			Univ univ = new Univ();

			String deptID = deptDetail.getDepartmentID(univ.getUnivId(deptInfo.getUnivName()), deptInfo.getDeptName());

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_DETAIL, deptDetail.getAllDepartmentDetail(deptID));

		} catch (Exception e) {
			System.out.println("Controller - 학과 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_DETAIL));
			e.printStackTrace();

		}

	}

	// 대학 평가 리스트 조회 요청 처리
	public void inquiryUnivRatingList(String univName) {

		Univ univ = new Univ();
		Rating rating = new Rating();

		// 대학 ID 검색
		String univId = univ.getUnivId(univName);

		if (univId != null) {
			// 대학 ID 존재 -> 대학 평가 리스트 조회 -> 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_RATING_LIST, rating.getUnivRatingList(univId));

		}

	}

	// 학과 평가 리스트 조회 요청 처리
	public void inquiryDepartmentRatingList(DeptInfoReqVO deptInfo) {

		Department deptDetail = new Department();
		Univ univ = new Univ();
		Rating rating = new Rating();

		// 학과 ID 검색
		String deptID = deptDetail.getDepartmentID(univ.getUnivId(deptInfo.getUnivName()), deptInfo.getDeptName());

		if (deptID != null) {
			// 학과 ID 존재 -> 학과 평가 리스트 조회 -> 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_RATING_LIST, rating.getDeptRatingList(deptID));

		}

	}

	// 사용자 통계 조회 요청 처리
	public void inquiryUserStats() {
		// 학교 실시간 조회 순위 1 ~ 10	위
		// 가장 많이 사용한 지표 1 ~ 10 위
		// NOTE: + a (또 어떤 통계?)

		//
		ArrayList< ArrayList<String> > statsList = new ArrayList<>();

//		statsList.add(new Univ.getViewListOfUniv());
//		statsList.add(new IndicatorSelectionStatisticsDAO().getViewListOfIndicator());

	}

	// 두 대학 비교 요청 처리
	public void compareTwoUniv(ArrayList<String> list) {

		ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

		try {
			Univ univ = new Univ();

			result.add(univ.getUnivDetail(univ.getUnivId(list.get(0)), CUR_YEAR));
			result.add(univ.getUnivDetail(univ.getUnivId(list.get(1)), CUR_YEAR));

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_CP, result);

		} catch (Exception e) {
			System.out.println("Controller - 대학 비교 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_CP));
			e.printStackTrace();

		}
	}

	// 학과 비교 요청 처리
	public void compareTwoDept(ArrayList<DeptInfoReqVO> deptInfo) {

		ArrayList<DepartmentDetailDTO> result = new ArrayList<DepartmentDetailDTO>();
		DeptInfoReqVO dept1 = deptInfo.get(0);
		DeptInfoReqVO dept2 = deptInfo.get(1);

		try {
			Department deptDetail = new Department();
			Univ univ = new Univ();

			// 학과 ID 찾기
			String deptID1 = deptDetail.getDepartmentID(univ.getUnivId(dept1.getUnivName()), dept1.getDeptName());
			String deptID2 = deptDetail.getDepartmentID(univ.getUnivId(dept2.getUnivName()), dept2.getDeptName());

			// ArrayList 에 저장
			result.add(deptDetail.getDepartmentDetail(deptID1, CUR_YEAR));
			result.add(deptDetail.getDepartmentDetail(deptID2, CUR_YEAR));

			// 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_CP, result);

		} catch (Exception e) {
			System.out.println("Controller - 학과 비교 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_CP));
			e.printStackTrace();

		}
	}

	// 대학 평가 등록 요청 처리
	public void registerUnivRating(RatingVO content) {
		Rating rating = new Rating();

		UnivRatingDTO dto = convertToUnivRatingDTO(content);

		if (rating.registerUnivRating(dto)) {
			Sender.send(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_UNIV_RATING));
		}
		else {
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_RATING));
		}

	}

	// 학과 평가 등록 요청 처리
	public void registerDepartmentRating(RatingVO content) {
		Rating rating = new Rating();

		DepartmentRatingDTO dto = convertToDeptRatingDTO(content);

		if (rating.registerDeptRating(dto)) {
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

	// 사용자 상세정보 요청 처리 (마이페이지 정보 - 사용자 정보, 즐겨찾기 내역, 사용자가 쓴 학교 평점 DTO, 사용자가 쓴 학과 평점 DTO)
	public void reqUserDetail() {

		UserBookmarkDAO userBookmarkDAO = new UserBookmarkDAO();
		Rating rating = new Rating();

		String email = curUser.getUserEmail();
		MyPageInfoVO mypageInfo = new MyPageInfoVO(userBookmarkDAO.select(email), rating.getUnivRatingOfUser(email),
													rating.getDeptRatingOfUser(email));
		System.out.println("MyPageInfo 전송");
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_USER_DETAIL, mypageInfo);
	}

	// RatingVO 를 UnivRatingDTO 로 변환
	public static UnivRatingDTO convertToUnivRatingDTO(RatingVO target) {
		String univId = new Univ().getUnivId(target.getUnivName());

		return new UnivRatingDTO(target.getUserEmail(), univId, target.getContent(), new Long(target.getScore()), target.getCreationDate());
	}

	// RatingVO 를 DepartmentRatingDTO 로 변환
	public static DepartmentRatingDTO convertToDeptRatingDTO(RatingVO target) {
		String deptId = new Department().getDepartmentID(new Univ().getUnivId(target.getUnivName()), target.getDeptName());

		return new DepartmentRatingDTO(target.getUserEmail(), deptId, target.getContent(), new Long(target.getScore()), target.getCreationDate());
	}

}
