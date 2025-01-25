package com.logicbic;

public class I_ThreadRaceCondition {
    private Integer counter = 0;

    public static void main (String[] args) throws InterruptedException {
        I_ThreadRaceCondition demo = new I_ThreadRaceCondition();
        Task task1 = demo.new Task();
        Thread thread1 = new Thread(task1);

        Task task2 = demo.new Task();
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }

    private void performTask () {
        int temp = this.counter;
        this.counter++;
        System.out.println(Thread.currentThread().getName() + " - before: "+temp+" after:" + this.counter);
    }

    private class Task implements Runnable {
        @Override
        public void run () {
            for (int i = 0; i < 5; i++) {
                performTask();
            }
        }
    }
}
