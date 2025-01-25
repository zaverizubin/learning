package com.playground.threading.jacobjenkov;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Awaiter waiter = new Awaiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();

        Thread.sleep(4000);
    }

    public static class Awaiter implements Runnable{
        CountDownLatch latch = null;

        public Awaiter(CountDownLatch latch){
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                this.latch.await();
                System.out.println("Awaiter proceeds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Decrementer implements Runnable{
        CountDownLatch latch = null;

        public Decrementer(CountDownLatch latch){
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Countdown latch remaining: "  + this.latch.getCount());
                Thread.sleep(1000);
                this.latch.countDown();

                System.out.println("Countdown latch remaining: "  + this.latch.getCount());
                Thread.sleep(1000);
                this.latch.countDown();

                System.out.println("Countdown latch remaining: "  + this.latch.getCount());
                Thread.sleep(1000);
                this.latch.countDown();

                System.out.println("Countdown latch remaining: "  + this.latch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
