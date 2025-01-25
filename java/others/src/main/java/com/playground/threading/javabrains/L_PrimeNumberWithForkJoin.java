package com.playground.threading.javabrains;

import java.util.concurrent.ForkJoinPool;

public class L_PrimeNumberWithForkJoin {

    public void doCalculate() {

       int[] inputNumbers = {2,3,4,5,6,7,8,9,10};
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        CalculatePrimeTask calculatePrimeTask = new CalculatePrimeTask(inputNumbers, 0, inputNumbers.length-1);
        Integer result  = forkJoinPool.invoke(calculatePrimeTask);
        System.out.println("The sum of primes: "  + result);

    }

}
