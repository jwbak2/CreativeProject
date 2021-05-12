package Server.transmission;

import java.io.*;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void classifyPacket(Protocol p) {
		System.out.println("---수신한 패킷 분류---");

		int type = p.getProtocolType();
		int code = p.getProtocolCode();
		switch (type) {
			case Protocol.PT_REQ:	// 요청
				switch (code) {
					case Protocol.PT_REQ_UNIV_INF:
						sender.inquiryUnivInfo(p.getBody());

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
