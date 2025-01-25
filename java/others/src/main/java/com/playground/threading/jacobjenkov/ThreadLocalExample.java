package com.playground.threading.jacobjenkov;

public class ThreadLocalExample {

    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Long> threadLocal =  ThreadLocal.withInitial(
                System::currentTimeMillis);

        @Override
        public void run() {
            this.threadLocal.set( (long) (Math.random() * 100D) );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(threadLocal.get());
        }
    }
}
