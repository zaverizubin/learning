package com.playground.threading.baeldung;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

public class ConcurrentFutureTest {

    @Test
    public void givenMultiThread_getFutureValue() throws ExecutionException, InterruptedException {
        Future<Integer> future1 = new ConcurrentFuture.SquareCalculator().calculate(10);
        Future<Integer> future2 = new ConcurrentFuture.SquareCalculator().calculate(10);

        while (!(future1.isDone() && future2.isDone())) {
            System.out.println(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        System.out.println(String.format("future1 is %s and future2 is %s", result1, result2));

        Assert.assertEquals(20, (int)result1);
        Assert.assertEquals(20, (int)result2);

    }
}
