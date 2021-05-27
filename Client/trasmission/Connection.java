package Client.trasmission;

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

    public static void terminate(){    // 소켓 통신 연결 종료 시 스트림 close
        try {
            Connection.is.close();
            Connection.os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void send(Protocol sendPT) { // 패킷 전송
        try {
            os.write(sendPT.getPacket());   // 전송
            os.flush();
            System.out.println("send - 패킷 송신 완료");
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
            System.out.println("receive - 패킷 수신 완료");
            
            receivePT.setPacket(body);
        } catch (IOException e) {
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
