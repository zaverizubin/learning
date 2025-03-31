package com.playground.threading.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.concurrent.*;

public class RunnableAndCallable {

    public static class EventLoggingTask implements  Runnable{
        private Logger logger  = LoggerFactory.getLogger(EventLoggingTask.class);

        @Override
        public void run() {
            this.logger.info("Message");
        }
    }

    public static class Factorial implements Callable<Integer>{
        int number;

        public Factorial(final int number) {
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            int fact = 1;
            if(this.number <= 0){
                throw new InvalidParameterException("Number should be positive");
            }
            for(int count = this.number; count > 1; count--) {
                fact = fact * count;
            }

            return fact;
        }
    }

    private static void doRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(new EventLoggingTask());
        System.out.println(future.get());
        executorService.shutdown();
    }

    private static void doCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new Factorial(0 ));
        try{
            future.get();
        }catch (Exception ex){
            ex.getCause();
        }
        executorService.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        doRunnable();
        doCallable();
    }

}
