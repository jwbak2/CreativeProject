package client.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
    static Socket socket;
    static OutputStream os;
    static InputStream is;

    public Connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);  //통신 소켓 생성

            is = socket.getInputStream();   //get stream
            os = socket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void send(byte[] packet) { // 패킷 전송
        try {
            os.write(packet);
            System.out.println("packet transferred");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public byte[] receive() {   // 서버 -> 클라이언트 패킷 수신

        byte[] buffer = new Protocol().getPacket(); // default 바이트 배열 get
                                                    // 일단 최대 바이트 읽음 *수정필요*
        try{
            is.read(buffer);
            System.out.println("packet received");

        } catch(Exception e){
            e.printStackTrace();

        }

        return buffer;  // 버퍼 반환
    }
}
