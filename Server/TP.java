package Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TP {

    private static ThreadPoolExecutor POOL;

    public static void init(int minPoolSize, int maxPoolSize, long keepAliveTime){

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        POOL = new ThreadPoolExecutor(minPoolSize, maxPoolSize ,keepAliveTime, TimeUnit.SECONDS, queue);

    }

    public static void terminate(){
        POOL.shutdownNow();
    }

    public static void execute(Runnable runnable){
        POOL.execute(runnable);
    }

}
