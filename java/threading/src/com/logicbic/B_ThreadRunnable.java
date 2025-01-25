package com.logicbic;

public class B_ThreadRunnable {

    public static void main (String[] args) {
        MyThreadRunner threadRunnable = new MyThreadRunner();
        Thread thread = new Thread(threadRunnable);
        thread.start();

        MyThreadRunner threadRunnable2 = new MyThreadRunner();
        Thread thread2 = new Thread(threadRunnable2);
        thread2.start();

        System.out.println(Thread.currentThread()
                .getName());
    }

    private static class MyThreadRunner implements Runnable {
        @Override
        public void run () {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
