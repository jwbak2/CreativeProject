package Server.transmission;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

// Client 가 전송한 패킷을 수신

public class Receiver {

	private InputStream is;

	public Receiver(InputStream is) {
		this.is = is;
	}


	public Protocol receive() {
		Protocol tmp = null;

		try {
			// head 수신 및 설정
			byte[] head = new byte[Protocol.LEN_HEADER];
			is.read(head);

			tmp = new Protocol(head);


			// body 수신 및 설정
			byte[] body = new byte[tmp.getBodyLength()];
			is.read(body);

			tmp.setPacket(body);
			System.out.println("패킷 수신 완료");

		} catch (SocketException e) {
			System.out.println("소켓 예외 발생");
			e.printStackTrace();
			tmp = null;

		} catch (IOException e) {
			System.out.println("입출력 예외 발생");
			e.printStackTrace();
			tmp = null;

		}

		return tmp;
	}

}
