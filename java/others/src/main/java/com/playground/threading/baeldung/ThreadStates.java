package com.playground.threading.baeldung;

public class ThreadStates {

    public void newState(){
        Runnable runnable = ()->{};
        Thread t = new Thread(runnable);
        System.out.println(t.getState());
    }

    public void runnableState(){
        Runnable runnable = ()->{};
        Thread t = new Thread(runnable);
        t.start();
        System.out.println(t.getState());
    }

    public void blockedState() throws InterruptedException {
        Thread t1 = new Thread(new DemoBlockedRunnable());
        Thread t2 = new Thread(new DemoBlockedRunnable());

        t1.start();
        t2.start();

        Thread.sleep(1000);
        System.out.println(t2.getState());
        System.exit(0);
    }


    public static Thread t1;

    public static void waitingState() {
        t1 = new Thread(()->{
            Thread t2 = new Thread(new DemoWaitingStateRunnable());
            t2.start();

            try {
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            System.exit(0);
        });
        t1.start();
    }

    public void timedWaitingState() throws InterruptedException {
        DemoTimeWaitingRunnable runnable= new DemoTimeWaitingRunnable();
        Thread t1 = new Thread(runnable);
        t1.start();

        // The following sleep will give enough time for ThreadScheduler
        // to start processing of thread t1
        Thread.sleep(1000);
        System.out.println(t1.getState());
    }

    public void terminatedState() throws InterruptedException {
        Thread t1 = new Thread(()->{});
        t1.start();
        // The following sleep method will give enough time for
        // thread t1 to complete
        Thread.sleep(1000);
        System.out.println(t1.getState());
    }

    public static class DemoBlockedRunnable implements Runnable{
        @Override
        public void run() {
            commonResource();
        }

        public static synchronized void commonResource() {
            while(true) {
                // Infinite loop to mimic heavy processing. 't1' won't leave this method when 't2' try to enter this
            }
        }
    }

    public static class DemoWaitingStateRunnable implements Runnable {
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            System.out.println(t1.getState());
        }
    }


    public static class DemoTimeWaitingRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadStates threadStates = new ThreadStates();
        //threadStates.newState();
        //threadStates.runnableState();
        //threadStates.blockedState();
        //threadStates.waitingState();
        //threadStates.timedWaitingState();
        threadStates.terminatedState();
    }

}
