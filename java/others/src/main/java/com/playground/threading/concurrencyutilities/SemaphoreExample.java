package com.playground.threading.concurrencyutilities;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        final Pool pool = new Pool();

        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            try {
                while (true) {
                    String item;
                    System.out.println(name + " acquiring " + (item = pool.getItem()));
                    Thread.sleep(200 + (int) (Math.random() * 100));
                    System.out.println(name + " putting back " + item);
                    pool.putItem(item);
                }
            } catch (InterruptedException ie) {
                System.out.println(name + "interrupted");
            }
        };
        ExecutorService[] executors = new ExecutorService[Pool.MAX_AVAILABLE + 1];
        for (int i = 0; i < executors.length; i++) {
            executors[i] = Executors.newSingleThreadExecutor();
            executors[i].execute(r);
        }
    }

    static final class Pool {
        public static final int MAX_AVAILABLE = 10;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
        private final String[] items = new String[MAX_AVAILABLE];
        private final boolean[] used = new boolean[MAX_AVAILABLE];

        Pool() {
            for (int i = 0; i < this.items.length; i++){
                this.items[i] = "I" + i;
            }
        }

        private synchronized String getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!this.used[i]) {
                    this.used[i] = true;
                    return this.items[i];
                }
            }
            return null; // not reached
        }

        private synchronized boolean markAsUnused(String item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (Objects.equals(item, this.items[i])) {
                    if (this.used[i]) {
                        this.used[i] = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        String getItem() throws InterruptedException {
            this.available.acquire();
            return getNextAvailableItem();
        }

        void putItem(String item) {
            if (markAsUnused(item)) {
                this.available.release();
            }
        }
    }
}


