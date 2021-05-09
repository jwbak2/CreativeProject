package Server.transmission;

import java.io.OutputStream;

public class Sender {
	/*
	 * 클라이언트의 요청에 따라 적절한 데이터를 반환함
	 */
	OutputStream os;

	public Sender(OutputStream os) {
		this.os = os;
	}

	public void sendingUnivInfo() {
		// 대학 정보를 요청
		// 난 뭘 해야 할까
		// 일단 클라이언트가 보낸 대학 이름의 ID가 있는지 확인하고 (2차원 스트링 배열 받아오고)
		// 1. 아이디가 없다 -> fail 전송
		// 2. 아이디가 있다 -> DAO를 통해서 조회 후에 DTO를 패킷에 담아서 전송



	}
}
