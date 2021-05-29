package Server;

import Server.transmission.Receiver;
import Server.transmission.Sender;
import Server.transmission.SocketManager;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// ProcessThread는 현재 클라이언트와의 TCP 연결(소켓)과 1:1 대응

public class ConnectRunnable implements Runnable{

    public void run() {

        System.out.println("Process Thread 생성완료");

        try(Socket socket = SocketManager.getSocket();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream()){

            Sender sender = new Sender(os);
            Receiver receiver = new Receiver(is, sender);

            while (true) {
                receiver.waiting();
            }

        } catch (Exception e) {
            System.out.println("Process Thread에서 예외발생");
            e.printStackTrace();

        }

        System.out.println("Process Thread 종료");

    }

}
