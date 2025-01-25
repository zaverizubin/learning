package com.playground.threading.jacobjenkov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<PoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks){
        this.taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);

        for(int i=0; i<noOfThreads; i++){
            PoolThreadRunnable poolThreadRunnable =  new PoolThreadRunnable(this.taskQueue);
            this.runnables.add(poolThreadRunnable);
        }
        for(PoolThreadRunnable runnable : this.runnables){
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) throws IllegalStateException{
        if(this.isStopped) throw new IllegalStateException("ThreadPool is stopped");
        this.taskQueue.offer(task);
    }

    public synchronized void stop(){
        this.isStopped = true;
        for(PoolThreadRunnable runnable : this.runnables){
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while(!this.taskQueue.isEmpty()) {
            try {
                wait(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadPool threadPool = new ThreadPool(3, 10);

        for(int i=0; i<10; i++) {
            int taskNo = i;
            threadPool.execute( () -> {
                String message = Thread.currentThread().getName() + ": Task " + taskNo ;
                System.out.println(message);
            });
        }
        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();
    }

}
