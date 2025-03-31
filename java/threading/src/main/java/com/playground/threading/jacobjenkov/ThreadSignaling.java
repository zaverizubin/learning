package com.playground.threading.jacobjenkov;

public class ThreadSignaling {
    public static void main(String[] args) {

        SignalObject signalObject = new SignalObject();

        Thread waiter = new Thread(()->{
            try {
                signalObject.doWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-0");


        Thread notifier = new Thread(()->{
            signalObject.doNotify();
        }, "Thread-1");

        waiter.start();
        notifier.start();
    }

    public static class SignalObject{
        public void doWait() throws InterruptedException {
            synchronized (this){
                System.out.println("Waiter thread before wait");
                this.wait();
                System.out.println("Waiter thread after wait");
            }
        }

        public void doNotify() {
            synchronized (this){
                System.out.println("Notifier thread before notify");
                this.notify();
                System.out.println("Notifier thread after notify");
            }
        }

        public void doNotifyAll() {
            synchronized (this){
                this.notify();
            }
        }
    }
}
