package Server.transmission;

import Client.vo.*;
import Server.model.dao.IndicatorSelectionStatisticsDAO;
import Server.subcontroller.*;
import Server.model.dao.UserBookmarkDAO;
import Server.model.dao.UserDAO;
import Server.model.dto.*;

import java.util.ArrayList;

import static Server.model.Cache.CUR_YEAR;
import static Server.model.Cache.START_YEAR;

public class Controller {


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
			Department dept = new Department();

			String deptID = dept.getDepartmentID(deptInfo.getUnivName(), deptInfo.getDeptName());

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_DETAIL, dept.getAllDepartmentDetail(deptID));

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
		Rating rating = new Rating();

		// 학과 ID 검색
		String deptID = deptDetail.getDepartmentID(deptInfo.getUnivName(), deptInfo.getDeptName());

		if (deptID != null) {
			// 학과 ID 존재 -> 학과 평가 리스트 조회 -> 전송
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_RATING_LIST, rating.getDeptRatingList(deptID));

		}

	}

	// 맞춤형 평점 조회 요청 처리
	public void inquiryCustomRanking(CustomizedRankReqVO info) {
		// 사용자가 선택한 학과들을
		// 사용자가 선택한 지표들로 비교해서 순위를 제공

//		ArrayList<DeptInfoReqVO> deptList = info.getDeptList();	// 학과 리스트
//		ArrayList<String> indicators = info.getIndicators();	// 지표 리스트
//
//		CustomRank customRank = new CustomRank();
//
//		customRank.getRanking(deptList, indicators);
//		// NOTE: 데이터 분리 및 준비 (대학 이름, 학과 이름으로 학과 ID 만들기)
//		ArrayList<String> deptIdList = new ArrayList<>();		// 학과 ID 리스트
//		deptList.forEach((e) -> {
//			deptIdList.add(dept.getDepartmentID(e.getUnivName(), e.getDeptName()));
//		});
//
//		// NOTE: 한글 지표 이름 -> 테이블 이름으로 변경하기
//		ArrayList<String> tableNameOfIndicators = convert(indicators);
//		/*
//		1. 학과 ID 리스트 있으니까 2가지 경우의 수가 있음
//			1-1. 학과 ID 리스트로 정보를 다 들고 와서 한다
//			1-2. 학과 ID 리스트 정보를 DB로 넘기고 PL/SQL 로 한다.
//
//		2. StringBuilder로 각 칼럼 이름을 넣어서 칼럼 이름에 맞는 데이터만 쭉 뽑아오기? 근데 대학도 결국 뽑아야하네..?
//		3. 1,2,3 순위의 점수 배점은 어떻게? 39.3 / 33.3 / 27.3 ..?
//		4. 학과 조회를 한번에는... 안되겠네 결국 PL/SQL 써야하나..?
//		5. 그럼 결국 학과 ID 리스트, 칼럼 이름 리스트(3개 겠지?) 를 보내서 (아니 애초에 저런 덩어리들을 보낼 수 있을까?)
//		6. PL/SQL로 순위를 만들고 결국 보내야하나?
//		*/
//
//		// NOTE: 준비된 데이터를 가공해서 저장
//		ArrayList<CustomizedRankResVO> list = new ArrayList<>();
//
//
//		// NOTE: 완성된 리스트를 전송
//		Sender.send(Protocol.PT_RES, Protocol.PT_RES_CUSTOM_RANKING, list);

	}

	// 사용자 통계 조회 요청 처리
	public void inquiryUserStats() {
		// 학교 실시간 조회 순위 1 ~ 10	위
		// 가장 많이 사용한 지표 1 ~ 10 위
		// NOTE: + a (또 어떤 통계?)

		// 사용자 통계 정보가 담기는 ArrayList
		ArrayList< ArrayList<String> > statsList = new ArrayList<>();

		// 사용자 통계 정보 가져오기
		statsList.add(new Univ().getViewListOfUniv());
		statsList.add(new IndicatorSelectionStatisticsDAO().getIndicatorOfView());

		// 사용자 통계 정보 반환
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_USER_STATS, statsList);
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
			String deptID1 = deptDetail.getDepartmentID(dept1.getUnivName(), dept1.getDeptName());
			String deptID2 = deptDetail.getDepartmentID(dept2.getUnivName(), dept2.getDeptName());

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
			Sender.send(Protocol.PT_SUCC, Protocol.PT_SUCC_LOGIN, curUser);

		} else {

			Sender.send(Protocol.PT_FAIL, Protocol.PT_FAIL_LOGIN, curUser);

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
		String deptId = new Department().getDepartmentID(target.getUnivName(), target.getDeptName());

		return new DepartmentRatingDTO(target.getUserEmail(), deptId, target.getContent(), new Long(target.getScore()), target.getCreationDate());
	}

}
