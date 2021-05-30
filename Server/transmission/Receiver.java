package Server.transmission;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

// Client 가 전송한 패킷을 수신

public class Receiver {

	private ObjectInputStream ois;

	public Receiver(ObjectInputStream ois) {
		this.ois = ois;
	}


	public Protocol receive() {
		Protocol pt = null;

		try {
			// head 수신 및 설정
			pt = (Protocol) ois.readObject();
			System.out.println("패킷 수신 완료");

//		} catch (SocketException e) {
//			System.out.println("소켓 예외 발생");
//			e.printStackTrace();
//			pt = null;

		} catch (ClassNotFoundException | IOException e) {
			System.out.println("입출력 예외 발생");
			e.printStackTrace();
			pt = null;

		}

		return pt;
	}

}
