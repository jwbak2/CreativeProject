package Server.transmission;

import java.io.*;

public class Receiver {
	/*
	* 클라이언트의 패킷을 수신하고
	* 패킷의 코드에 따라 적절한 행동을 취함
	*/
	InputStream is;
	OutputStream os;
	Controller superman;

	public Receiver(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
		superman = new Controller(is, os);
	}

	public void waiting() {
		try {
			byte[] head = new byte[4];
			is.read(head);
			Protocol tmp = new Protocol(head);
			byte[] body = new byte[tmp.getBodyLength()];
			is.read(body);
			tmp.setPacket(body);
//			tmp.getBody();
			System.out.println("역직렬화");
			String univName = "";
			try (ByteArrayInputStream bais = new ByteArrayInputStream(tmp.getBody())) {
				try (ObjectInputStream ois = new ObjectInputStream(bais)) {
					Object objectMember = ois.readObject();
					univName = (String) objectMember;

					System.out.println(univName);
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

			int type = tmp.getProtocolType();
			switch (type) {
				case Protocol.PT_REQ:
					handleRequest(univName, tmp.getProtocolCode());
					break;

				case Protocol.PT_RES:
					// pass
					break;

				case Protocol.PT_SUCC:
					// pass
					break;

				case Protocol.PT_FAIL:
					// pass
					break;

				default:
					// pass
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleRequest(String univName, int code) {
		System.out.println("handleRequest");
		switch (code) {
			case Protocol.PT_REQ_UNIV_INF:
				// 대학 정보를 요청
				// 난 뭘 해야 할까
				// 일단 클라이언트가 보낸 대학 이름의 ID가 있는지 확인하고 (2차원 스트링 배열 받아오고)
				// 1. 아이디가 없다 -> fail 전송
				// 2. 아이디가 있다 -> DAO를 통해서 조회 후에 DTO를 패킷에 담아서 전송

				// 아니 일단 진우 말대로 프로토콜 만들어서 전송이나 해주자.
				superman.getUnivData(univName, new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF));
				break;
			default:
				System.out.println("handleRequest default");
				break;
		}
	}
}
