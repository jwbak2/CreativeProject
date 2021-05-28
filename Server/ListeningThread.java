package Server;


import Server.transmission.SocketManager;


// 클라이언트의 연결 요청을 감시하는 listening Thread

public class ListeningThread extends Thread{


    public void run(){
        System.out.println("Listen Thread 생성완료");

        ProcessingThread processingThread = new ProcessingThread();

        while(true){

            SocketManager.listen();
            processingThread.start();  // 클라이언트 1:1 처리를 위한 새 스레드 생성

        }

    }


}
