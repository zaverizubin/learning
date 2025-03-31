package com.playground.threading.jacobjenkov.blockingqueue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue {

    private final List<Object> queue = new LinkedList<>();
    private int  limit = 10;

    public BlockingQueue(int limit){
        this.limit = limit;
    }


    public synchronized void enqueue(Object item) throws InterruptedException  {
        while(this.queue.size() == this.limit) {
            wait();
        }
        this.queue.add(item);
        if(this.queue.size() == 1) {
            notifyAll();
        }
    }


    public synchronized Object dequeue() throws InterruptedException{
        while(this.queue.isEmpty()){
            wait();
        }
        if(this.queue.size() == this.limit){
            notifyAll();
        }

        return this.queue.remove(0);
    }

}