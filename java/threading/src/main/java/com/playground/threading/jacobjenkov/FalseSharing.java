package com.playground.threading.jacobjenkov;

public class FalseSharing {

    public static void main(String[] args) {
        FalseSharing falseSharing = new FalseSharing();
        //falseSharing.falseSharingExample();
        falseSharing.nonFalseSharingExample();
    }

    public void nonFalseSharingExample(){
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        iterate(counter1, counter2);
    }

    public void falseSharingExample(){
        //This method is slower because of cache invalidation and updates
        Counter counter1 = new Counter();
        Counter counter2 = counter1;

        iterate(counter1, counter2);
    }

    private void iterate(Counter counter1, Counter counter2) {
        long iterations = 1_000_000_000;

        Thread thread1 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for(long i=0; i<iterations; i++) {
                counter1.count1++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time: " + (endTime - startTime));
        });
        Thread thread2 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for(long i=0; i<iterations; i++) {
                counter2.count2++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time: " + (endTime - startTime));
        });

        thread1.start();
        thread2.start();
    }

    public static class Counter {
       // @jdk.internal.vm.annotation.Contended
        public volatile long count1 = 0;
        public volatile long count2 = 0;
    }
}
