package Server;

import Server.model.Cache;
import Server.model.DBCP;
import Server.model.dao.UnivDAO;
import Server.transmission.Connection;
import Server.transmission.Receiver;
import Server.transmission.Sender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	// 미리 DB 에서 가져올 데이터(ex. 학교 리스트) 보관
	public static Cache serverCache = new Cache();

	public static void main(String[] args) {
		final int PORT = 5500;
		Connection conn = new Connection(PORT);
		DBCP.init();	  //DB 커넥션 풀 초기화


		ServerSocket serverSocket = null;
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;


		// 서버 연결
		serverSocket = conn.connect();
		System.out.println("서버 오픈");

		// 실행 시간을 줄이기 위해,
		// 서버가 미리 필요한 데이터 가져오기
		loadInitData();
		System.out.println("데이터 로드 완료");


		// 소켓 할당
		socket = conn.getSocket();

		try {
			os = socket.getOutputStream();
			is = socket.getInputStream();

			Sender sender = new Sender(os);
			Receiver receiver = new Receiver(is, sender);

			while (true) {
				receiver.waiting();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("BYE!");

			// 통신 관련 종료
			try {
				is.close();
				os.close();
				serverSocket.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("--Server Down---");
		}
	}

	public static void loadInitData() {
		try {
			// 학교 리스트 가져오기
			serverCache.setUnivList(new UnivDAO().getUnivList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
