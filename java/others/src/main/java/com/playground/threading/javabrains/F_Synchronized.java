package com.playground.threading.javabrains;

public class F_Synchronized {

    public void doCalculate() {
        Counter counter = new Counter();
        new Thread(counter, "One").start();
        new Thread(counter, "Two").start();
        new Thread(counter, "Three").start();
        new Thread(counter, "Four").start();

    }

    private class Counter implements Runnable{
        private int value =0;

        public void increment(){
            try{
                Thread.sleep(20);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            value++;
        }

        public void decrement(){
            value--;
        }

        public int getValue(){
            return this.value;
        }


        @Override
        public void run() {
            /*Critical Section (a section of code thats locked for synchronized access)
            acquiring a monitor lock(a lock thats monitored by JVM for concurrent access) on this object.

            This is structured locking. You only declare that a block of code is synchronized
            and the JVM takes care of creating/managing lock based access on it.

            An exception thrown in a critical section will cause the thread execution to leave the synchronized piece of code
             and the JVM releases the lock automatically.
            */
            synchronized (this){
                increment();
                System.out.println(Thread.currentThread().getName() + " increments: " + this.getValue());
                decrement();
                System.out.println(Thread.currentThread().getName() + " decrements: " + this.getValue());
            }
        }
    }

}
