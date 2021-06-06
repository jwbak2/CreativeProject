package Server.transmission;

import Client.vo.CustomizedRankReqVO;
import Client.vo.DeptInfoReqVO;
import Client.vo.LoginReqVO;
import Client.vo.RatingVO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.util.ArrayList;

// Client 로부터 수신한 패킷을 분류

public class Classifier {

	private final Controller controller = new Controller();

	public int classify(Protocol pt) {
		if (pt == null)
			return -1;

		// 패킷 분류
		int type = pt.getProtocolType();
		int code = pt.getProtocolCode();
		Object body = pt.getBody();

		switch (type) {
			case Protocol.PT_REQ:	// 요청
				switch (code) {
					// 대학 조회 요청
					case Protocol.PT_REQ_UNIV_INF:
						System.out.println("~대학 조회 요청");
						controller.inquiryUnivInfo( (String) body);
						break;

					// 학과 조회 요청
					case Protocol.PT_REQ_DEPT_DETAIL:
						System.out.println("~학과 조회 요청");
						controller.inquiryDepartmentInfo( (DeptInfoReqVO) body);
						break;

					// 대학 리스트 요청
					case Protocol.PT_REQ_UNIV_LIST:
						System.out.println("~대학 리스트 요청");
						controller.inquiryUnivList();
						break;

					// 학과 리스트 요청
					case Protocol.PT_RES_DEPT_LIST_OF_UNIV:
						System.out.println("~학과 리스트 요청");
						controller.inquiryDepartmentList( (String) body);
						break;

					// 대학 비교 요청
					case Protocol.PT_REQ_UNIV_CP:
						System.out.println("~대학 비교 요청");
						controller.compareTwoUniv((ArrayList<String>) body);
						break;

					// 학과 비교 요청
					case Protocol.PT_REQ_DEPT_CP:
						System.out.println("~학과 비교 요청");
						controller.compareTwoDept( (ArrayList<DeptInfoReqVO>) body);
						break;

					// 대학 평점 등록
					case Protocol.PT_REQ_UNIV_RATING:
						System.out.println("~대학 평점 등록 요청");
						controller.registerUnivRating( (RatingVO) body);
						break;

					// 학과 평점 등록
					case Protocol.PT_REQ_DEPT_RATING:
						System.out.println("~학과 평점 등록 요청");
						controller.registerDepartmentRating( (RatingVO) body);
						break;

					// 대학 평가 리스트 요청
					case Protocol.PT_REQ_UNIV_RATING_LIST:
						System.out.println("~대학 평가 리스트 요청");
						controller.inquiryUnivRatingList( (String) body);
						break;

					// 학과 평가 리스트 요청
					case Protocol.PT_REQ_DEPT_RATING_LIST:
						System.out.println("~학과 평가 리스트 요청");
						controller.inquiryDepartmentRatingList( (DeptInfoReqVO) body);
						break;

					// 맞춤형 평점 조회
					case Protocol.PT_REQ_CUSTOM_RANKING:
						System.out.println("~맞춤형 평점 조회 요청");
						controller.inquiryCustomRanking( (CustomizedRankReqVO) body);
						break;

					// 사용자 통계 조회
					case Protocol.PT_REQ_USER_STATS:
						System.out.println("~사용자 통계 조회 요청");
						controller.inquiryUserStats();
						break;

					// 대학 북마크 조회 요청
					case Protocol.PT_REQ_CHK_BOOKMARK:
						System.out.println("~대학 북마크 조회 요청");
						controller.inquiryBookmarkStatus( (String) body);
						break;

					// 북마크 상태 변경(토글) 요청
					case Protocol.PT_REQ_TOGGLE_BOOKMARK:
						System.out.println("~북마크 상태 변경 요청");
						controller.reqToggleBookmark( (String) body );
						break;

					// 사용자 로그인 요청
					case Protocol.PT_REQ_LOGIN:
						System.out.println("~로그인 요청");
						controller.reqLogin((LoginReqVO)body);
						break;

					// 사용자 마이페이지 정보 요청 (즐겨찾기, 사용자가 작성한 대학/학과 평가)
					case Protocol.PT_REQ_USER_DETAIL:
						System.out.println("~마이페이지 정보");
						controller.reqUserDetail();
						break;

					default:
						System.out.println("---알 수 없는 패킷---");
						break;
				}
				break;

			case Protocol.PT_RES:	// 응답
				break;

			case Protocol.PT_SUCC:	// 성공
				break;

			case Protocol.PT_FAIL:	// 실패
				break;

			case Protocol.PT_EXIT:	// 종료
				return -1;

			default:
				System.out.println("---알 수 없는 패킷---");
				break;
		}

		return 1;

	}

}
