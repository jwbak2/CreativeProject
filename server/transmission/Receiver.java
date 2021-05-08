package src.server.Transmission;

import java.io.IOException;
import java.io.InputStream;

public class Receiver {
	/*
	* 클라이언트의 패킷을 수신하고
	* 패킷의 코드에 따라 적절한 행동을 취함
	*/
	InputStream is;

	public Receiver(InputStream is) {
		this.is = is;
	}

	public void waiting() {
		try {
			byte[] head = new byte[4];
			is.read(head);

			int type = head[0];
			switch (type) {
				case Protocol.PT_REQ:
					break;

				case Protocol.PT_RES:
					break;

				case Protocol.PT_SUCC:
					break;

				case Protocol.PT_FAIL:
					break;

				default:
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
