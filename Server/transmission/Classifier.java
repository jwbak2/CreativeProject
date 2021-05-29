package Server.transmission;

import Server.controller.RequestHandler;
import Server.controller.UnivDetail;
import Server.controller.UnivList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

// Client 로부터 수신한 패킷을 분류

public class Classifier {

	private ByteArrayInputStream bais;
	private ObjectInputStream ois;

	public RequestHandler classify(Protocol p) {
		if (p == null)
			return null;

		// 패킷 분류
		int type = p.getProtocolType();
		int code = p.getProtocolCode();

		Object body = null;
		if (p.getBodyLength() > 0)
			body = deserializeByteArray(p.getBody());

		switch (type) {
			case Protocol.PT_REQ:	// 요청
				switch (code) {
					// 대학 조회 요청
					case Protocol.PT_REQ_UNIV_INF:
						return new UnivDetail(body);

						// 대학 리스트 요청
					case Protocol.PT_REQ_UNIV_LIST:
						return new UnivList(body);

						// 학교 비교 요청
					case Protocol.PT_REQ_UNIV_CP:
//						sender.resUnivComp(p.getBody());


						// 대학 평점 등록
//					case Protocol.PT_REQ_REG_UNIV_RATING:
//						sender.registerUnivRating(p.getBody());

						// 학과 평점 등록
//					case Protocol.PT_REQ_REG_DEPA_RATING:
//						sender.registerDepartmentRating(p.getBody());

						// 대학, 학과 비교 -> 아마 분리 해아할 듯
						// 대학 학과를 비교하려면 대학, 학과를 선택할 수 있어야지
						// 대학 리스트 요청, 학과 리스트 요청도 있을 듯?
//					case Protocol.대학비교요청:
//						sender.compareTwoUniv(p.getBody());
//
//					case Protocol.학과비교요청:
//						sender.compareTwoDepartment(p.getBody());
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

		return null;
	}

	public Object deserializeByteArray(byte[] obj) {
		try {
			bais = new ByteArrayInputStream(obj);
			ois = new ObjectInputStream(bais);

			return ois.readObject();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

		return null;
	}

}