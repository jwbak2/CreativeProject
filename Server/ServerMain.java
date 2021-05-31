package Server;

import Server.model.Cache;
import Server.model.DBCP;
import Server.transmission.SocketManager;

public class ServerMain {


	static final int PORT = 5500;


	public static void main(String[] args) {

		init();
		launch();
		//terminate();
	}


	private static void init(){		// 서버 초기화 작업

		System.out.println("서버 초기화");

		TP.init(5, 15, 5); 	// 쓰레드 풀 초기화 (시간단위 : 초)
		DBCP.init();	  										// DB 커넥션 풀 초기화
		SocketManager.init(PORT);								// 서버 소켓 초기화
		Cache.init(); 										    // Cache 초기화

		System.out.println("서버 초기화 완료");
	}

	private static void launch(){

		while(true) {
			SocketManager.listen();                // 클라이언트의 TCP 연결 요청 대기
			TP.execute(new ConnectRunnable());    // 클라이언트 1:1 처리를 위한 새 스레드 생성
		}

	}


	private static void terminate(){	// 서버 종료 작업

		System.out.println("서버 종료 전 작업");

		DBCP.terminate();
		SocketManager.terminate();
		TP.terminate();

		System.out.println("서버 종료");
	}

}
