package com.playground.threading.baeldung.waitNotify;

public class Data {
    private String packet;

    // True if receiver should wait, False if sender should wait
    private boolean transfer = true;

    public synchronized String receive() {
        while (this.transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        this.transfer = true;

        String returnPacket = this.packet;
        notifyAll();
        return returnPacket;
    }

    public synchronized void send(String packet) {
        while (!this.transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        this.transfer = false;

        this.packet = packet;
        notifyAll();
    }

    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();
    }
}
