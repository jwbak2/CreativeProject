package Transmission;

import java.io.OutputStream;

public class Sender {
	/*
	 * 클라이언트의 요청에 따라 적절한 데이터를 반환함
	 */
	OutputStream os;

	public Sender(OutputStream os) {
		this.os = os;
	}

	public void sending() {

	}
}
