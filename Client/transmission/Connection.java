package Client.transmission;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.net.Socket;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void terminate(){    // 소켓 통신 연결 종료 시 스트림 close
        try {
            Connection.ois.close();
            Connection.oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void send(Protocol sendPT) { // 패킷 전송


        try {
            oos.writeObject(sendPT);   // 전송
            oos.flush();
            System.out.println("send - 패킷 송신 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static public Protocol receive() {   // 서버 -> 클라이언트 패킷 수신, 받은 패킷으로 Protocol 생성해서 반환
        Protocol receivePT = null;

        try {
            receivePT = (Protocol) ois.readObject();

            System.out.println("receive - 패킷 수신 완료");

        } catch (ClassNotFoundException | IOException e) {
            System.out.println("receive() - 패킷 수신 오류");
            e.printStackTrace();

        }

        return receivePT;
    }

    // 직렬화 후 바이트 배열 반환
    static public byte[] serializeDTO(Object obj){
        byte[] serializedDTO = null;  // 직렬화 결과가 담기는 바이트
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(obj);  // 객체 직렬화 object(string) 학교이름 to byte array -> packet data에 set

                serializedDTO = baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return serializedDTO;
    }

    // 역직렬화 후 Object 객체 반환
    static public Object deserializeDTO(byte[] bodyData){ // bodyData = 프로토콜 패킷의 바디
        Object objectMember = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bodyData)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                objectMember = ois.readObject();  // 역직렬화된 dto 객체를 읽어온다.
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return objectMember;
    }
}
