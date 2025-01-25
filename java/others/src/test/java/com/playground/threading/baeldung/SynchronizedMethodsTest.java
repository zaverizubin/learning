package com.playground.threading.baeldung;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SynchronizedMethodsTest {

    @Test
    public void givenMultiThread_whenNonSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods summation = new SynchronizedMethods();

        IntStream.range(0,1000).forEach(count -> service.submit(summation::calculateSum));
        boolean result = service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, summation.getSum());

    }

    @Test
    public void givenMultiThread_whenSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods summation = new SynchronizedMethods();

        IntStream.range(0,1000).forEach(count -> service.submit(summation::calculateSum));
        boolean result = service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, summation.getSum());
    }

    @Test
    public void givenMultiThread_whenSyncStaticMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);


        IntStream.range(0,1000).forEach(count -> service.submit(SynchronizedMethods::calculateSumStaticSync));
        boolean result = service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, SynchronizedMethods.getStaticSum());
    }

    @Test
    public void givenMultiThread_whenSyncStaticMethodBlock() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);


        IntStream.range(0,1000).forEach(count -> service.submit(SynchronizedMethods::calculateSumStaticSyncBlock));
        boolean result = service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, SynchronizedMethods.getStaticSum());
    }

}
