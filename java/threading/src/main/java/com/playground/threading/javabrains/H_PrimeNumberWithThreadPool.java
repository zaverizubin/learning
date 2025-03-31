package com.playground.threading.javabrains;

import java.util.Scanner;
import java.util.concurrent.*;

public class H_PrimeNumberWithThreadPool {

    public void doCalculate() {

        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(3);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        //ExecutorService executorService = Executors.newSingleThreadExecutor();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable reporterRunnable = () ->{
            System.out.println("running report ");
            System.out.println("Active threads: " + executorService.getActiveCount());
            System.out.println("Completed threads " + executorService.getCompletedTaskCount());
        };

        scheduledExecutorService.scheduleAtFixedRate(reporterRunnable, 1 ,5, TimeUnit.SECONDS);


        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\nI can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0) break;
            Runnable r = () -> {
                int number = PrimeNumberUtil.calculatePrime(n);
                System.out.println("\n The value of the " + n + "th prime: " + number);
            };

            executorService.execute(r);
        }
    }

}
