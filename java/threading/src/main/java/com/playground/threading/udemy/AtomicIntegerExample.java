package com.playground.threading.udemy;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
        public static void main(String[] args) throws InterruptedException {
            InventoryCounter inventoryCounter = new InventoryCounter();
            IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
            DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

            incrementingThread.start();
            decrementingThread.start();

            incrementingThread.join();
            decrementingThread.join();

            System.out.println("We currently have " + inventoryCounter.getItems() + " items");
        }

        public static class DecrementingThread extends Thread {

            private final InventoryCounter inventoryCounter;

            public DecrementingThread(InventoryCounter inventoryCounter) {
                this.inventoryCounter = inventoryCounter;
            }

            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    this.inventoryCounter.decrement();
                }
            }
        }

        public static class IncrementingThread extends Thread {

            private final InventoryCounter inventoryCounter;

            public IncrementingThread(InventoryCounter inventoryCounter) {
                this.inventoryCounter = inventoryCounter;
            }

            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    this.inventoryCounter.increment();
                }
            }
        }

        private static class InventoryCounter {
            private final AtomicInteger items = new AtomicInteger(0);

            public void increment() {
                this.items.incrementAndGet();
            }

            public void decrement() {
                this.items.decrementAndGet();
            }

            public int getItems() {
                return this.items.get();
            }
        }

}
