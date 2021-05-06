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

            byte[] buffer = new byte[100];

            try{
                is.read(buffer);
                System.out.println("packet received");

            } catch(Exception e){
                e.printStackTrace();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
