package com.playground.threading.baeldung;

public class ThreadJoinSample extends Thread{
    public int processingCount = 0;

    ThreadJoinSample(int processingCount) {
        this.processingCount = processingCount;
        System.out.println("Thread " + Thread.currentThread().getName() + " created");
    }

    @Override
    public void run(){
        System.out.println("Thread " + this.getName() + " started");
        while (this.processingCount > 0){
            try {
                Thread.sleep(1000);
                System.out.println("processing count value is " + this.processingCount);
            } catch (InterruptedException e) {
                System.out.println("Thread " + this.getName() + " interrupted");
            }
            this.processingCount--;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //Wiating for the joined thread to complete
        ThreadJoinSample threadJoinSample1 = new ThreadJoinSample(5);
        threadJoinSample1.start();
        System.out.println("invoking join");
        threadJoinSample1.join();
        System.out.println("Returned from join");
        System.out.println(threadJoinSample1.isAlive()); //Returns False

        //Timed waiting
        ThreadJoinSample threadJoinSample2 = new ThreadJoinSample(5);
        threadJoinSample2.start();
        System.out.println("invoking join");
        threadJoinSample2.join(2000);
        System.out.println("Returned from join");
        System.out.println(threadJoinSample2.isAlive()); //Returns true


    }

}
