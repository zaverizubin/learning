package com.playground.threading.jacobjenkov;

import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "A");
        ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "B");

        new Thread(exchangerRunnable1).start();
        Thread.sleep(5000);
        new Thread(exchangerRunnable2).start();
    }

    public static class ExchangerRunnable implements Runnable{
        Exchanger<String> exchanger;
        String object;

        public ExchangerRunnable(Exchanger<String> exchanger, String object) {
            this.exchanger = exchanger;
            this.object = object;
        }

        public void run() {
            try {
                Object previous = this.object;
                this.object = this.exchanger.exchange(this.object);
                System.out.println(Thread.currentThread().getName() + " exchanged " + previous + " for " + this.object
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
