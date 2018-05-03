package Netty;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

public class Futures {

    public static long operation(long x) {
        return LongStream.range(2,x).map(i -> i*i).reduce((i,j) -> i+j).getAsLong();
    }

    public static void main(String[] args) throws Exception {
        long x = 100000000000L;

        ExecutorService executor = Executors.newFixedThreadPool(100);
        Runnable task = new Runnable() {
            public void run() {
                operation(x);
            }
        };

        Callable<Long> callable = new Callable<Long>(){
            @Override
            public Long call() {
                return operation(x);
            }

        };

        Future<?> executorFuture = executor.submit(task);
        Future<?> executorCallable = executor.submit(callable);
        Future<?> executorCallable1 = executor.submit(callable);

        System.out.println("Not Done");
        executorFuture.get();
        System.out.println("Done");
        executor.shutdown();
    }

}
