package com.playground.threading.baeldung.waitNotify;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable {
    private Data load;

    public Receiver(final Data load) {
        this.load = load;
    }

    public void run() {
        for(String receivedMessage = this.load.receive(); !"End".equals(receivedMessage);  receivedMessage = this.load.receive()) {

            System.out.println(receivedMessage);

            //Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
    }
}