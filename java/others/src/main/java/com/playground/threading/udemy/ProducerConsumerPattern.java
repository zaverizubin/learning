package com.playground.threading.udemy;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerPattern {
    Semaphore full = new Semaphore(0);
    Semaphore empty = new Semaphore(10);
    Queue<Integer> queue = new ArrayDeque<>(10);
    Lock lock = new ReentrantLock();
    Random random = new Random();

    Runnable producerRunnable = () -> {
        try {
            while(true){
                this.empty.acquire();
                this.lock.lock();
                Integer item = this.random.nextInt(10);
                this.queue.add(item);
                System.out.println("Produced item : " + item);
                this.lock.unlock();
                this.full.release();
                Thread.sleep(200);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    Runnable consumerRunnable = () -> {
        try {
            while(true) {
                this.full.acquire();
                this.lock.lock();
                Integer item = this.queue.poll();
                System.out.println("Consumed item : " + item);
                this.lock.unlock();
                this.empty.release();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerPattern producerConsumerPattern = new ProducerConsumerPattern();
        Thread producerThread = new Thread(producerConsumerPattern.producerRunnable);
        Thread consumerThread = new Thread(producerConsumerPattern.consumerRunnable);

        producerThread.setDaemon(true);
        consumerThread.setDaemon(true);

        producerThread.start();
        consumerThread.start();

        Thread.sleep(10000);
    }
}
