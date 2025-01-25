package com.javabrains;

import java.util.*;
import java.util.concurrent.*;

public class J_PrimeNumberWithCallable {

    public void doCalculate()  {

        List<Future<Integer>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("\nI can tell you the nth prime number. Enter n");
            int n = sc.nextInt();
            if(n==0) break;
            Callable<Integer> c = () -> PrimeNumberUtil.calculatePrime(n);

            Future<Integer> primeNumberFuture = executorService.submit(c);
            futures.add(primeNumberFuture);
        }

        Iterator<Future<Integer>> iterator = futures.iterator();
        while(iterator.hasNext()){
            Future<Integer> f = iterator.next();
            if(f.isDone()){
                try {
                    System.out.println(f.get());
                } catch (InterruptedException | ExecutionException exception) {
                    exception.printStackTrace();
                }
                iterator.remove();
            }
        }
    }

}
