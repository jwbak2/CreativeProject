package server;

//import server.dto.SampleDTO;
import server.model.DBCP;
import server.transmission.Connection;
import server.transmission.Protocol;
import server.transmission.Receiver;
import server.transmission.Sender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		int port = 5500;
		Connection conn = new Connection(port);
		DBCP.init();	  //DB 커넥션 풀 초기화
		ServerSocket serverSocket = null;
		Socket socket = null;

		// 서버 연결
		serverSocket = conn.connect();

		// 소켓 할당
		socket = conn.getSocket();

		try {
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();

			/*
				서버는 사용자와 연결된 직후에 데이터를 보내는 경우를 제외하고는,
				모두 사용자로부터 요청을 받고, 응답을 하는 구조로 돌아가는 것으로 보임
			*/
			Receiver rcvr = new Receiver(is, os);
			Sender sndr = new Sender(os);

			while (true) {

				rcvr.waiting();
//				try {
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//				rcvr.waiting();
//				try {
//					// 사용자로부터 요청 받기
//
//					System.out.println("패킷 받음");
//
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				// 사용자로부터 요청을 받음
//				byte[] head = new byte[4];
//				is.read(head);
//
//				int type = head[0];


//				switch()
//				head[Protocol.LEN_TYPE + Protocol.LEN_CODE];

//				byte[] body = new byte[];
//				is.read();

				// 사용자의 요청이 어떤 타입인지 확인

//				byte[] header = new byte[Protocol.LEN_HEADER];
//				System.out.println("헤드 읽기 대기");
//				is.read(header);
//
//				Protocol pt = new Protocol(header);
//				int bodyLength = pt.getBodyLength();
//
//				byte[] body = new byte[bodyLength];
//				is.read(body);
//
//				System.out.println("바디 읽기 대기");
//				System.out.println(body.length);
//				System.out.println("직렬화");
//				try (ByteArrayInputStream bais = new ByteArrayInputStream(body)) {
//					try (ObjectInputStream ois = new ObjectInputStream(bais)) {
//						Object objectMember = ois.readObject();
//						String smpl = (String) objectMember;
//
//						System.out.println(smpl);
//					}
//					catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					}
//				}
//
//				System.out.println("데이터 전송 시작");
//				SampleDTO tmp = new SampleDTO("갯강구", 2, 10);	// 직렬화할 객체
//				byte[] serializedDTO;	// 직렬화 결과가 담기는 바이트
//				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//					try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
//						oos.writeObject(tmp);	// tmp 객체 직렬화
//
//						serializedDTO = baos.toByteArray();
//					}
//				}
//				System.out.println("직렬화 완료");
//
//				// 직렬화 완료된 데이터 사용자에게 전달
//				Protocol protocol = new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF);
//
//				// 패킷 바디에 직렬화 객체 삽입
//				protocol.setPacket(serializedDTO);
//
//				// 전송
//				os.write(protocol.getPacket());
//				os.flush();
//				System.out.println("데이터 전송 완료");

//				ObjectOutputStream oos = new ObjectOutputStream(os);
//				ObjectInputStream ois = new ObjectInputStream(is);

//				oos.writeObject(tmp);
//				oos.flush();


//				byte[] head = new byte[4];
//				is.read(head);
//
//				head[Protocol.LEN_TYPE + Protocol.LEN_CODE];
//
//				byte[] body = new byte[];
//				is.read();
				// Receiver 에게 전달...?
			}

//			os.close();
//			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("BYE!");

			// 통신 관련 종료
			serverSocket.close();
			socket.close();
		}
	}
}
