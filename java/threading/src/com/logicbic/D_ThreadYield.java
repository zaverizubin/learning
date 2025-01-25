package com.logicbic;

import java.util.concurrent.TimeUnit;

public class D_ThreadYield {

    public static void main (String[] args) {
        Task task1 = new Task(true);
        Thread thread1 = new Thread(task1);
        thread1.setName("Thread 1");
        thread1.start();

        Task task2 = new Task(false);
        Thread thread2 = new Thread(task2);
        thread2.setName("Thread 2");
        thread2.start();
    }


    private static class Task implements Runnable{
        private final boolean shouldYield;
        private int c;

        public Task(boolean shouldYield) {
            this.shouldYield = shouldYield;
        }


        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " started.");

            for (int i = 0; i < 1000; i++) {
                c++;
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(this.shouldYield){
                    Thread.yield();
                }
            }

            System.out.println(threadName + " ended.");

        }
    }
}
