package com.javabrains;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E_ThreadJoin {

    public void doCalculate() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        Runnable statusReporter = () -> {
            try {
                while(true) {
                    Thread.sleep(5000);
                    printThreads(threads);
                }
            } catch (InterruptedException e) {
                System.out.println("Status report thread interrupted. Ending status updates");
            }
        };
        Thread reporterThread = new Thread(statusReporter);
        reporterThread.setDaemon(true);
        reporterThread.start();

        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("\n I can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0){
                try{
                    System.out.println("Waiting for all threads to finish...");
                    waitForThreads(threads);
                    System.out.println("Done! " + threads.size() + " primes calculated");
                    break;
                }catch (InterruptedException ex){
                    System.out.println("\n Got interrupted while waiting for threads. Quitting....");
                }
            }
            Runnable r = ()->{
                int number = PrimeNumberUtil.calculatePrime(n);
                System.out.println("\n Result:");
                System.out.println("\n Value of " + n + "th prime: " + number);
            };
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }
    }


    private void printThreads(List<Thread> threads) {
        System.out.print("\n Thread status: ");
        for (Thread thread:threads) {
            System.out.print(thread.getState() + " ");
        }
        System.out.println("");
    }

    private void waitForThreads(List<Thread> threads) throws InterruptedException{
        for(Thread thread: threads){
            thread.join();
        }
    }

}
