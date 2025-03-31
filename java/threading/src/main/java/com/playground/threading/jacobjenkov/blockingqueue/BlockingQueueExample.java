package com.playground.threading.jacobjenkov.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {

    public static class Producer implements Runnable{
        BlockingQueue<String> blockingQueue;

        public Producer(BlockingQueue<String> blockingQueue){
            this.blockingQueue = blockingQueue;
        }


        @Override
        public void run() {
            while(true){
                this.blockingQueue.add("" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer implements Runnable{
        BlockingQueue<String> blockingQueue;

        public Consumer(BlockingQueue<String> blockingQueue){
            this.blockingQueue = blockingQueue;
        }


        @Override
        public void run() {
            while(true){
                String item = this.blockingQueue.poll();
                System.out.println(item);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        Producer producer  = new Producer(blockingQueue);
        Consumer consumer  = new Consumer(blockingQueue);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        Thread.sleep(10000);
    }
}
