package Server;

import Server.transmission.*;


import java.io.*;
import java.net.Socket;

// ProcessThread는 현재 클라이언트와의 TCP 연결(소켓)과 1:1 대응

public class ConnectRunnable implements Runnable{

    public void run() {

        System.out.println("Process Thread 생성완료");

        try(Socket socket = SocketManager.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())){

            Sender sender = new Sender(oos);
            Receiver receiver = new Receiver(ois);
            Classifier classifier = new Classifier();

            while (true) {
                classifier.classify(receiver.receive());

//              RequestHandler reqHandler = classifier.classify(receiver.receive());
//              reqHandler.handleRequest();

//              System.out.println("요청 핸들링 끝");
//              do {
//                  System.out.println("응답 전송");
//                  sender.send(reqHandler.getType(), reqHandler.getCode(), reqHandler.getBody());
//              } while (reqHandler.hasMessage());
            }

        } catch (Exception e) {
            System.out.println("Process Thread에서 예외발생");
            e.printStackTrace();

        }

        System.out.println("Process Thread 종료");

    }

}
