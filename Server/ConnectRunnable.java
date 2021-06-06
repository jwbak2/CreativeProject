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


            int state = 1;
            while (true) {
                if (state > 0 || socket.isConnected() && !socket.isClosed())
                {
                    // 연결 상태 확인 완료
                    state = classifier.classify(receiver.receive());
                }
                else
                {
                    // 클라이언트와 연결 끊어짐
                    System.out.println("---클라이언트 통신 종료---");
                    socket.close();
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println("Process Thread에서 예외발생");
            e.printStackTrace();

        }

        System.out.println("Process Thread 종료");

    }

}
