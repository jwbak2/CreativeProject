package Server.transmission;

import Client.vo.DeptInfoReqVO;
import Client.vo.LoginReqVO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.util.ArrayList;

// Client 로부터 수신한 패킷을 분류

public class Classifier {

	private final Controller controller = new Controller();

	public void classify(Protocol pt) {
		if (pt == null)
			return;

		// 패킷 분류
		int type = pt.getProtocolType();
		int code = pt.getProtocolCode();
		Object body = pt.getBody();

		switch (type) {
			case Protocol.PT_REQ:	// 요청
				switch (code) {
					// 대학 조회 요청
					case Protocol.PT_REQ_UNIV_INF:
						controller.inquiryUnivInfo( (String) body);
						break;

					// 학과 조회 요청
					case Protocol.PT_REQ_DEPT_DETAIL:
						controller.inquiryDepartmentInfo( (DeptInfoReqVO) body);
						break;

					// 대학 리스트 요청
					case Protocol.PT_REQ_UNIV_LIST:
						controller.inquiryUnivList();
						break;

					// 학과 리스트 요청
					case Protocol.PT_RES_DEPT_LIST_OF_UNIV:
						controller.inquiryDepartmentList( (String) body);
						break;

					// 학교 비교 요청
					case Protocol.PT_REQ_UNIV_CP:
						controller.compareTwoUniv( (ArrayList<DeptInfoReqVO>) body);
						break;

					// 학과 비교 요청
					case Protocol.PT_REQ_DEPT_CP:
						controller.compareTwoDept((ArrayList<String>) body);
						break;

					// 대학 평점 등록
					case Protocol.PT_REQ_UNIV_RATING:
						controller.registerUnivRating( (UnivRatingDTO) body);
						break;

					// 학과 평점 등록
					case Protocol.PT_REQ_DEPT_RATING:
						controller.registerDepartmentRating( (DepartmentRatingDTO) body);
						break;

					// 대학 평가 리스트 요청
					case Protocol.PT_REQ_UNIV_RATING_LIST:
						controller.inquiryUnivRatingList( (String) body);
						break;

					// 학과 평가 리스트 요청
					case Protocol.PT_REQ_DEPT_RATING_LIST:
						controller.inquiryDepartmentRatingList( (DeptInfoReqVO) body);
						break;

					// 맞춤형 평점...?


					// 사용자 통계..?
					case Protocol.PT_REQ_USER_STATS:
						controller.inquiryUserStats();
						break;

					// 사용자 로그인 요청
					case Protocol.PT_REQ_LOGIN:
						controller.reqLogin((LoginReqVO)body);
						break;

					// 사용자 마이페이지 정보 요청
					case Protocol.PT_REQ_USER_DETAIL:
						controller.reqUserDetail();
						break;

					// 즐겨찾기 요청...?


					default:
						System.out.println("---알 수 없는 패킷---");
						break;
				}
				break;

			case Protocol.PT_RES:	// 응답
				switch (code) {

				}
				// pass
				break;

			case Protocol.PT_SUCC:	// 성공
				switch (code) {

				}
				// pass
				break;

			case Protocol.PT_FAIL:	// 실패
				switch (code) {

				}
				// pass
				break;

			default:
				// pass
				break;
		}

		return;
	}

}
