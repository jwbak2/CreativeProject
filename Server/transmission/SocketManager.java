package Server.transmission;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


// 서버 소켓을 가지고 있는 클래스
// listen 매소드를 통해 클라이언트의 접속 요청을 받는다.

public class SocketManager {

	static int PORT;

	static ServerSocket serverSocket;
	static Socket socket;

	public static void init(int initPort){
		PORT = initPort;
		initServerSocket();
	}

	private static void initServerSocket(){		// 서버 소켓 초기ㅗ하
		try {
			serverSocket = new ServerSocket(PORT);	// 서버 소켓 생성
			serverSocket.setReuseAddress(true);		// 재사용 설정 켜기

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ServerSocket getServerSocket(){
		return serverSocket;
	}


	public static void listen() {

		while(socket != null){};	// 소켓 fetch 대기

		try {

			System.out.println("---클라이언트 접속 대기중---");

			socket = serverSocket.accept();
			System.out.println("---클라이언트 접속---");

		} catch (IOException e) {
			System.out.println("listen() 중 예외 발생");
			e.printStackTrace();

		}

	}

	public static Socket getSocket(){	// 서버 소켓 반환

		Socket returnSocket = socket;
		socket = null;

		return returnSocket;
	}



	public static void terminate(){	// 서버 종료 전 finalize
		try{
			serverSocket.close();

		}
		catch(Exception e){
			System.out.println("서버 소켓 close() 중 예외 발생");
			System.out.println(e);

		}
	}

}
