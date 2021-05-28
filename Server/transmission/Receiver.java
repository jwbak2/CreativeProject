package Server.transmission;

import java.io.*;
import java.net.SocketException;

public class Receiver {
	/*
	 * 클라이언트의 패킷을 수신하고
	 * 패킷의 코드에 따라 적절한 행동을 취함
	 */
	InputStream is;
	Sender sender;

	public Receiver(InputStream is, Sender sender) {
		this.is = is;
		this.sender = sender;
	}

	// Client 의 패킷 송신 대기
	public void waiting() {
		try {
			byte[] head = new byte[4];
			is.read(head);

			Protocol tmp = new Protocol(head);

			byte[] body = new byte[tmp.getBodyLength()];
			is.read(body);
			tmp.setPacket(body);

			System.out.println("---패킷 수신 완료---");
			System.out.printf("---총 길이: %d---\n", tmp.getBodyLength() + 4);

			// 패킷 분류
			classifyPacket(tmp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			try {
////				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}

	public void classifyPacket(Protocol p) {
		System.out.println("---수신한 패킷 분류---");

		int type = p.getProtocolType();
		int code = p.getProtocolCode();
		switch (type) {
			case Protocol.PT_REQ:	// 요청
				switch (code) {
					// 대학 조회 요청
					case Protocol.PT_REQ_UNIV_INF:
						sender.inquiryUnivInfo(p.getBody());

					// 대학 리스트 요청
					case Protocol.PT_REQ_UNIV_LIST:
						sender.inquiryUnivList();

					// 학교 비교 요청
					case Protocol.PT_REQ_UNIV_CP:
						sender.resUnivComp(p.getBody());


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
	}
}
