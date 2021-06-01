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
			while ((pt = (Protocol) ois.readObject()) == null) {
				System.out.println("Receiver.receive() - null");
				return null;
			}
			System.out.println("패킷 수신 완료");

		} catch (EOFException e) {
			System.out.println("receive() - End Of File 예외 발생");
			try { ois.close(); } catch (IOException ioe) { e.printStackTrace(); }

		} catch (SocketException e) {
			System.out.println("receive() - 소켓 예외 발생");
			try { ois.close(); } catch (IOException ioe) { e.printStackTrace(); }

		} catch (ClassNotFoundException | IOException e) {
			System.out.println("receive() - 입출력 예외 발생");
			try { ois.close(); } catch (IOException ioe) { e.printStackTrace(); }

		}

		return pt;
	}

}
