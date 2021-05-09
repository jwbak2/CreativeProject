package Client.controller.trasmission;

import java.io.*;
import java.net.Socket;

public class Connection {
    static Socket socket;
    static OutputStream os;
    static InputStream is;

    public Connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);  //통신 소켓 생성
            os = socket.getOutputStream();
            is = socket.getInputStream();   //get stream
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void send(Protocol sendPT) { // 패킷 전송
        try {
            os.write(sendPT.getPacket());   // 전송
            os.flush();
            System.out.println("send - 데이터 전송 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Thread thread = new Thread(){
//            public void run() {
//
//            }
//        };
    }

    static public Protocol receive() {   // 서버 -> 클라이언트 패킷 수신, 받은 패킷으로 Protocol 생성해서 반환
        Protocol receivePT = null;       // header 패킷 수신후 bodyLength 확인후 body 패킷 부분 읽음
        byte[] header = new byte[Protocol.LEN_HEADER];              // header 길이 만크의 바이트 배열
        int bodyLength;
        byte[] body;

        try {
            is.read(header);
            receivePT = new Protocol(header);
            bodyLength = receivePT.getBodyLength();

            body = new byte[bodyLength];            // header에 포함된 bodyLength따라 만들어진 가변 배열
            is.read(body);

            receivePT.setPacket(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receivePT;
    }
}
