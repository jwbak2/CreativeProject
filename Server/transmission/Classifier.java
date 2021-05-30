package Server.transmission;

import Client.vo.LoginReqVO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
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
					case Protocol.PT_REQ_DEPT_INF:
						controller.inquiryDepartmentInfo( (String) body);
						break;

					// 대학 리스트 요청
					case Protocol.PT_REQ_UNIV_LIST:
						controller.inquiryUnivList();
						break;

					// 학교 비교 요청
					case Protocol.PT_REQ_UNIV_CP:
						controller.compareTwoUniv( (ArrayList<String>) body);
						break;

					// 대학 평점 등록
					case Protocol.PT_REQ_UNIV_RATING:
						controller.registerUnivRating( (UnivRatingDTO) body);

						// 학과 평점 등록
					case Protocol.PT_REQ_DEPT_RATING:
						controller.registerDepartmentRating( (DepartmentRatingDTO) body);

						// 대학, 학과 비교 -> 아마 분리 해아할 듯
						// 대학 학과를 비교하려면 대학, 학과를 선택할 수 있어야지
						// 대학 리스트 요청, 학과 리스트 요청도 있을 듯?
//					case Protocol.대학비교요청:
//						sender.compareTwoUniv(p.getBody());
//
//					case Protocol.학과비교요청:
//						sender.compareTwoDepartment(p.getBody());

					case Protocol.PT_REQ_LOGIN:
						controller.reqLogin((LoginReqVO)body);
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

	// 역직렬화 후 Object 객체 반환
	static public Object deserializeByteArray(byte[] bodyData) { // bodyData = 프로토콜 패킷의 바디
		Object obj = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bodyData)) {
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
				obj = ois.readObject();  // 역직렬화된 dto 객체를 읽어온다.
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return obj;
	}

//	public Object deserializeByteArray(byte[] obj) {
//		try {
//			bais = new ByteArrayInputStream(obj);
//			ois = new ObjectInputStream(bais);
//
//			return ois.readObject();
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//
//		}
//
//		return null;
//	}
}
