package Server;


import Server.transmission.SocketManager;

public class ListenThread extends Thread{


    public void run(){
        System.out.println("Listen Thread 생성완료");

        ProcessThread processThread = new ProcessThread();

        while(true){

            SocketManager.listen();
            processThread.start();  // TCP 연결 시 스레드 생성

        }

    }


}
