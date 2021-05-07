package Transmission;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	int port;
	ServerSocket serverSocket = null;
	Socket socket = null;

	public Connection(int port)
	{
		this.port = port;
	}

	public ServerSocket connect() {
		try {
			// 서버 생성
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);	// 재사용 설정 켜기
		} catch (IOException e) {
			e.printStackTrace();
		}

		return serverSocket;
	}

	public Socket getSocket() {
		try {
			System.out.println("클라이언트 접속 대기중...");
			socket = serverSocket.accept();
			System.out.println("---클라이언트 접속---");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return socket;
	}
}
