package Server;

import Server.controller.RequestHandler;
import Server.transmission.*;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// ProcessThread는 현재 클라이언트와의 TCP 연결(소켓)과 1:1 대응

public class ClientRunnable implements Runnable{

    public void run() {

        System.out.println("Process Thread 생성완료");

        try(Socket socket = SocketManager.getSocket();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream()){

            Sender sender = new Sender(os);
            Receiver receiver = new Receiver(is);
            Classifier classifier = new Classifier();

            while (true) {
                RequestHandler reqHandler = classifier.classify(receiver.receive());
                reqHandler.handleRequest();

                System.out.println("요청 핸들링 끝");
                do {
                    System.out.println("응답 전송");
                    sender.send(reqHandler.getType(), reqHandler.getCode(), reqHandler.getBody());
                } while (reqHandler.hasMessage());
            }

        } catch (Exception e) {
            System.out.println("Process Thread에서 예외발생");
            e.printStackTrace();

        }

        System.out.println("Process Thread 종료");

    }

}
