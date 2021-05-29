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

//			ArrayList<String> arr = (ArrayList<String>) deserializeDTO(body);

//			int cnt = 0;
//			for (int i = 0; i < arr.size(); i++)
//				System.out.println(arr.remove(i));

			tmp.setPacket(body);

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

	static public Object deserializeDTO(byte[] bodyData){ // bodyData = 프로토콜 패킷의 바디
		Object objectMember = null;

		try (ByteArrayInputStream bais = new ByteArrayInputStream(bodyData)) {
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
//				ois.reset();
				objectMember = ois.readObject();  // 역직렬화된 dto 객체를 읽어온다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectMember;
	}

}
