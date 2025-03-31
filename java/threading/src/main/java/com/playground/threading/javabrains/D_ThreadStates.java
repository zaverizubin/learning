package com.playground.threading.javabrains;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D_ThreadStates {

    /*1.New
    2.Runnable
    3.Blocked
    4.Waiting
    5.Timed Waiting
    6.Terminated
     */

    public void doCalculate(){
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
                reporterThread.interrupt();
                break;
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

}
