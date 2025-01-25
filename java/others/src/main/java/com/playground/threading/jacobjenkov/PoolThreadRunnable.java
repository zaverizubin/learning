package com.playground.threading.jacobjenkov;

import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable{
    private Thread        thread    = null;
    private BlockingQueue<Runnable> taskQueue = null;
    private boolean       isStopped = false;

    public PoolThreadRunnable(BlockingQueue<Runnable> queue){
        this.taskQueue = queue;
    }

    @Override
    public void run(){
        this.thread = Thread.currentThread();
        while(!isStopped()){
            try{
                Runnable runnable = this.taskQueue.take();
                runnable.run();
            } catch(Exception e){
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        this.isStopped = true;
        //break pool thread out of dequeue() call.
        this.thread.interrupt();
    }

    public synchronized boolean isStopped(){
        return this.isStopped;
    }
}
