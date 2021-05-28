package Server;

import Server.model.Cache;
import Server.model.DBCP;
import Server.transmission.SocketManager;

public class ServerMain {


	static final int PORT = 5500;


	public static void main(String[] args) {

		init();	// 서버 초기화

	}


	private static void init(){		// 서버 초기화 작업

		System.out.println("서버 초기화");

		SocketManager.init(PORT);	// 서버 소켓 초기화
		DBCP.init();	  //DB 커넥션 풀 초기화
		Cache.init(); 	  //Cache 초기화


		ListeningThread listen = new ListeningThread();
		listen.start();								// 클라이언트의 연결 요청 감시하는 스레드 생성

		System.out.println("서버 초기화 완료");
	}


	private static void terminate(){	// 서버 종료 작업

		System.out.println("서버 종료 전 작업");

		DBCP.terminate();
		SocketManager.terminate();

		System.out.println("서버 종료");
	}

}
