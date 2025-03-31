package com.playground.threading.jacobjenkov.locks;

public class ReEntrantLock {
    boolean isLocked = false;
    Thread  lockedBy = null;
    int     lockedCount = 0;

    public synchronized void lock()
            throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(this.isLocked && this.lockedBy != callingThread){
            wait();
        }
        this.isLocked = true;
        this.lockedCount++;
        this.lockedBy = callingThread;
    }


    public synchronized void unlock(){
        if(Thread.currentThread() == this.lockedBy){
            this.lockedCount--;

            if(this.lockedCount == 0){
                this.isLocked = false;
                notify();
            }
        }
    }

}
