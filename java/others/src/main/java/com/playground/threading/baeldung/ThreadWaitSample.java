package com.playground.threading.baeldung;

public class ThreadWaitSample {

    public static class ClassA implements Runnable{
        int sum;

        @Override
        public void run() {
            synchronized (this){
                int i = 0;
                while (i < 100000) {
                    this.sum += i;
                    i++;
                }
                notifyAll(); //Does nothing for the thread waiting on this object's monitor lock until the current thread completes.
                //Only after completion of the next block will waiting threads be able to proceed.
                while (i < 1000000) {
                    this.sum += i;
                    i++;
                }
            }
        }
    }

    public static class ClassB implements Runnable {

        private final ClassA classAInstance;

        public ClassB(final ClassA classAInstance) {
            this.classAInstance = classAInstance;
        }

        public void run(){
            synchronized (this.classAInstance) {
                while (this.classAInstance.sum == 0) {
                    System.out.println("Waiting for ThreadB to complete...");
                    try {
                        this.classAInstance.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("ThreadB has completed. " + "Sum from that thread is: " + this.classAInstance.sum);
            }
        }

    }

    public static void main(String[] args) {
        ClassA classA = new ClassA();
        ClassB classB = new ClassB(classA);

        Thread thread1 = new Thread(classB);
        Thread thread2 = new Thread(classA);

        thread1.start();
        thread2.start();
    }



}


