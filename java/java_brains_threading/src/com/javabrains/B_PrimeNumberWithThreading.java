package com.javabrains;

import java.util.Scanner;

public class B_PrimeNumberWithThreading {

    public void doCalculate() {
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\nI can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0) break;

            Runnable r = () -> {
                int number = PrimeNumberUtil.calculatePrime(n);
                System.out.println("\n The value of the " + n + "th prime: " + number);
            };

            Thread thread = new Thread(r);
            thread.start();
        }
    }

}
