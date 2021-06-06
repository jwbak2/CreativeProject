package Server.transmission;

import Client.vo.*;
import Server.model.dao.IndicatorSelectionStatisticsDAO;
import Server.model.dao.UnivDAO;
import Server.subcontroller.*;
import Server.model.dao.UserBookmarkDAO;
import Server.model.dao.UserDAO;
import Server.model.dto.*;

import java.util.ArrayList;

import static Server.model.Cache.CUR_YEAR;
import static Server.model.Cache.START_YEAR;

public class Controller {

	// Sub controller
	private Univ univ;
	private Department dept;
	private Rating rating;
	private CustomRank customRank;

	private UserDTO curUser;


	public Controller() {
		init();
	}


	private void init() {
		univ = new Univ();
		dept = new Department();
		rating = new Rating();
		customRank = new CustomRank();
	}

	// 대학 리스트 조회 요청 처리
	public void inquiryUnivList() {

		Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, univ.getUnivList());
	}

	// 학과 리스트 조회 요청 처리
	public void inquiryDepartmentList(String univName) {
		try {

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_LIST_OF_UNIV, dept.getDepartmentList(univ.getUnivId(univName)));

		} catch (Exception e) {
			System.out.println("Controller - 학과 리스트 조회 오류");

		}
	}

	// 대학 상세정보 조회 요청 처리
	public void inquiryUnivInfo(String univName) {
		try {
			// 대학 ID 검색
			String univId = univ.getUnivId(univName);


			if (univId != null) {
				// 조회한 대학 조회수 + 1
				univ.increaseView(univId);

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
			String deptID = dept.getDepartmentID(deptInfo.getUnivName(), deptInfo.getDeptName());

			// 조회한 학과의 대학 조회수 + 1
			univ.increaseView(univ.getUnivId(deptInfo.getUnivName()));

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_DETAIL, dept.getAllDepartmentDetail(deptID));

		} catch (Exception e) {
			System.out.println("Controller - 학과 상세정보 조회 오류");
			Sender.send(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_DETAIL));
			e.printStackTrace();

		}

	}

	// 대학 평가 리스트 조회 요청 처리
	public void inquiryUnivRatingList(String univName) {

		// 대학 ID 검색
		String univId = univ.getUnivId(univName);

		if (univId != null) {
			// 대학 ID 존재 -> 대학 평가 리스트 조회 -> 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_RATING_LIST, rating.getUnivRatingList(univId));

		}

	}

	// 학과 평가 리스트 조회 요청 처리
	public void inquiryDepartmentRatingList(DeptInfoReqVO deptInfo) {

		// 학과 ID 검색
		String deptID = dept.getDepartmentID(deptInfo.getUnivName(), deptInfo.getDeptName());

		if (deptID != null) {
			// 학과 ID 존재 -> 학과 평가 리스트 조회 -> 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_RATING_LIST, rating.getDeptRatingList(deptID));

		}

	}

	// 맞춤형 평점 조회 요청 처리
	public void inquiryCustomRanking(CustomizedRankReqVO info) {
		// 사용자가 선택한 학과들을
		// 사용자가 선택한 지표들로 비교해서 순위를 제공

		ArrayList<DeptInfoReqVO> deptList = info.getDeptList();	// 학과 리스트
		ArrayList<String> indicators = info.getIndicators();	// 지표 리스트

		try {
			ArrayList<CustomizedRankResVO> list = customRank.getRanking(deptList, indicators);


			Sender.send(Protocol.PT_RES, Protocol.PT_RES_CUSTOM_RANKING, list);

		} catch (Exception e) {
			System.out.println("Controller - 맞춤형 평점 조회 오류");
			e.printStackTrace();

		}

	}

	// 사용자 통계 조회 요청 처리
	public void inquiryUserStats() {
		// 학교 실시간 조회 순위 1 ~ 10	위
		// 가장 많이 사용한 지표 1 ~ 10 위
		// NOTE: + a (또 어떤 통계?)

		// 사용자 통계 정보가 담기는 ArrayList
		ArrayList< ArrayList<RankVO> > statsList = new ArrayList<>();

		// 사용자 통계 정보 가져오기
		statsList.add(univ.getViewListOfUniv());
		statsList.add(new IndicatorSelectionStatisticsDAO().getIndicatorOfView());

		// 사용자 통계 정보 반환
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_USER_STATS, statsList);
	}

	// 북마크 조회 요청 처리
	public void inquiryBookmarkStatus(String univName) {
		UserBookmarkDAO bookmark = new UserBookmarkDAO();

		String univId = univ.getUnivId(univName);

		if (bookmark.isOnBookmark(univId, curUser.getUserEmail())) {
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_CHK_BOOKMARK, true);

		} else {
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_CHK_BOOKMARK, false);

		}
	}

	// 북마크 토글 요청 처리
	public void reqToggleBookmark(String univName) {
		UserBookmarkDAO bookmark = new UserBookmarkDAO();

		String univId = univ.getUnivId(univName);

		if (bookmark.toggleBookmark(univId, curUser.getUserEmail())) {
			System.out.println("북마크 토글 성공");
			Sender.send(Protocol.PT_SUCC, Protocol.PT_SUCC_BOOKMARK_TOGGLE);

		} else {
			System.out.println("북마크 토글 실패");
			Sender.send(Protocol.PT_FAIL, Protocol.PT_FAIL_BOOKMARK_TOGGLE);

		}

	}

	// 두 대학 비교 요청 처리
	public void compareTwoUniv(ArrayList<String> list) {

		ArrayList<UnivDetailDTO> result = new ArrayList<UnivDetailDTO>();

		try {
			String univId1 = univ.getUnivId(list.get(0));
			String univId2 = univ.getUnivId(list.get(1));
			result.add(univ.getUnivDetail(univId1, CUR_YEAR));
			result.add(univ.getUnivDetail(univId2, CUR_YEAR));

			// 조회한 대학 조회수 + 1
			univ.increaseView(univId1);
			univ.increaseView(univId2);


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
			// 학과 ID 찾기
			String deptID1 = dept.getDepartmentID(dept1.getUnivName(), dept1.getDeptName());
			String deptID2 = dept.getDepartmentID(dept2.getUnivName(), dept2.getDeptName());

			// 조회한 학과의 대학 조회수 + 1
			univ.increaseView(univ.getUnivId(dept1.getUnivName()));
			univ.increaseView(univ.getUnivId(dept2.getUnivName()));

			// ArrayList 에 저장
			result.add(dept.getDepartmentDetail(deptID1, CUR_YEAR));
			result.add(dept.getDepartmentDetail(deptID2, CUR_YEAR));

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
			Sender.send(Protocol.PT_SUCC, Protocol.PT_SUCC_LOGIN, curUser);

		} else {

			Sender.send(Protocol.PT_FAIL, Protocol.PT_FAIL_LOGIN, curUser);

		}


	}

	// 사용자 상세정보 요청 처리 (마이페이지 정보 - 사용자 정보, 즐겨찾기 내역, 사용자가 쓴 학교 평점 DTO, 사용자가 쓴 학과 평점 DTO)
	public void reqUserDetail() {

		UserBookmarkDAO bookmark = new UserBookmarkDAO();

		String email = curUser.getUserEmail();
		MyPageInfoVO mypageInfo = new MyPageInfoVO(bookmark.select(email), rating.getUnivRatingOfUser(email),
													rating.getDeptRatingOfUser(email));
		System.out.println("MyPageInfo 전송");
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_USER_DETAIL, mypageInfo);
	}


	// RatingVO 를 UnivRatingDTO 로 변환
	public UnivRatingDTO convertToUnivRatingDTO(RatingVO target) {
		String univId = univ.getUnivId(target.getUnivName());

		return new UnivRatingDTO(target.getUserEmail(), univId, target.getContent(), new Long(target.getScore()), target.getCreationDate());
	}

	// RatingVO 를 DepartmentRatingDTO 로 변환
	public DepartmentRatingDTO convertToDeptRatingDTO(RatingVO target) {
		String deptId = dept.getDepartmentID(target.getUnivName(), target.getDeptName());

		return new DepartmentRatingDTO(target.getUserEmail(), deptId, target.getContent(), new Long(target.getScore()), target.getCreationDate());
	}

}
