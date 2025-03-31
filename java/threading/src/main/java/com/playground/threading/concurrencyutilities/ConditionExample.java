package com.playground.threading.concurrencyutilities;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }

    static class Shared {
        private final Lock lock;
        private final Condition condition;
        private char c;
        private volatile boolean available;

        Shared() {
            this.available = false;
            this.lock = new ReentrantLock();
            this.condition = this.lock.newCondition();
        }

        Lock getLock() {
            return this.lock;
        }

        char getSharedChar() {
            this.lock.lock();
            try {
                while (!this.available)
                    try {
                        this.condition.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                this.available = false;
                this.condition.signal();
            } finally {
                this.lock.unlock();
                return this.c;
            }
        }

        void setSharedChar(char c) {
            this.lock.lock();
            try {
                while (this.available)
                    try {
                        this.condition.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                this.c = c;
                this.available = true;
                this.condition.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }

    static class Producer extends Thread {
        private final Lock l;
        private final Shared s;

        Producer(Shared s) {
            this.s = s;
            this.l = s.getLock();
        }

        @Override
        public void run() {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                this.l.lock();
                this.s.setSharedChar(ch);
                System.out.println(ch + " produced by producer.");
                this.l.unlock();
            }
        }
    }

    static class Consumer extends Thread {
        private final Lock l;
        private final Shared s;

        Consumer(Shared s) {
            this.s = s;
            this.l = s.getLock();
        }

        @Override
        public void run() {
            char ch;
            do {
                this.l.lock();
                ch = this.s.getSharedChar();
                System.out.println(ch + " consumed by consumer.");
                this.l.unlock();
            }
            while (ch != 'Z');
        }
    }

}
