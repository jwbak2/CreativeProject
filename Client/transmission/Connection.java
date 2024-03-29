package Client.transmission;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.net.Socket;

import Client.ClientMain;
import Server.transmission.Protocol;

public class Connection {
    static Socket socket;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;

    public Connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);  //통신 소켓 생성
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());   //get stream

            System.out.println("IP : " + ClientMain.SERVER_IP + ", Port : " + ClientMain.PORT);
            System.out.println("서버 접속 성공");
        } catch (Exception e) {
            System.out.println("서버 접속 실패");
            //e.printStackTrace();
        }
    }

    public static void terminate(){    // 소켓 통신 연결 종료 시 스트림 close
        try {
            Connection.ois.close();
            Connection.oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void send(Protocol sendPT) { // 패킷 전송
        try {
            oos.writeObject(sendPT);   // 전송
            oos.flush();
            System.out.println("send - 패킷 송신 완료");

        } catch (Exception e) {
            System.out.println("패킷 송신 중 예외 발생");
            //e.printStackTrace();
        }

    }

    static public Protocol receive() {   // 서버 -> 클라이언트 패킷 수신, 받은 패킷으로 Protocol 생성해서 반환
        Protocol receivePT = null;

        try {
            receivePT = (Protocol) ois.readObject();

            System.out.println("receive - 패킷 수신 완료");

        } catch (ClassNotFoundException | IOException | NullPointerException e ) {
            System.out.println("receive() - 패킷 수신 오류");
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return receivePT;
    }
}
