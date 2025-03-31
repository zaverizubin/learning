package com.javabrains;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class L_PrimeNumberWithSemaphores {

    public void doCalculate() {

        Semaphore semaphore  = new Semaphore(3, true);

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\nI can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0) break;
            
            Runnable r = () -> {
                try {
                    semaphore.acquire();
                    System.out.println("Now calculating for " + n);
                    int number = PrimeNumberUtil.calculatePrime(n);
                    System.out.println("\n The value of the " + n + "th prime: " + number);
                } catch (InterruptedException exception) {
                    System.out.println("Interrupted while acquiring semaphore lock");
                }finally {
                    semaphore.release();
                }
            };

            Thread thread = new Thread(r);
            thread.start();
        }
    }

}
