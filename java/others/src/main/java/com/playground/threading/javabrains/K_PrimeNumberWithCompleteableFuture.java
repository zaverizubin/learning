package com.playground.threading.javabrains;

import java.util.Scanner;
import java.util.concurrent.*;

public class K_PrimeNumberWithCompleteableFuture {


    public void doCalculate()  {

        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("\nI can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0) break;

            CompletableFuture.supplyAsync(() -> PrimeNumberUtil.calculatePrime(n)).thenAccept(System.out::println);

        }


    }

}
