package com.playground.threading.baeldung;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentFuture {

    public static class SquareCalculator{
        ExecutorService executor = Executors.newFixedThreadPool(2);

        public Future<Integer> calculate(int input){
            return executor.submit(() ->{
                System.out.println("calculating square for " + input);
                Thread.sleep(1000);
                return input + input;
            });
        }
    }
}
