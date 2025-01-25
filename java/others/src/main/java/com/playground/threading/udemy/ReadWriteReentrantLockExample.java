package com.playground.threading.udemy;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteReentrantLockExample {

    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });

            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();
        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.println(String.format("Reading took %d ms", endReadingTime - startReadingTime));
    }

    public static class InventoryDatabase {
        private final TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private final Lock readLock = this.reentrantReadWriteLock.readLock();
        private final Lock writeLock = this.reentrantReadWriteLock.writeLock();
        private final Lock lock = new ReentrantLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            //lock.lock();
            this.readLock.lock();
            try {
                Integer fromKey = this.priceToCountMap.ceilingKey(lowerBound);

                Integer toKey = this.priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrices = this.priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }

                return sum;
            } finally {
                this.readLock.unlock();
                //lock.unlock();
            }
        }

        public void addItem(int price) {
            //lock.lock();
            this.writeLock.lock();
            try {
                Integer numberOfItemsForPrice = this.priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    this.priceToCountMap.put(price, 1);
                } else {
                    this.priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }

            } finally {
                this.writeLock.unlock();
                /// lock.unlock();
            }
        }

        public void removeItem(int price) {
            //lock.lock();
            this.writeLock.lock();
            try {
                Integer numberOfItemsForPrice = this.priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    this.priceToCountMap.remove(price);
                } else {
                    this.priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                this.writeLock.unlock();
                // lock.unlock();
            }
        }
    }

}
