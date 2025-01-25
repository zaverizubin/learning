package com.playground.threading.baeldung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Locks {


    public static class SynchronizedHashMapWithReadWriteLock {

        Map<String, String> syncHashMap = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        Lock writeLock = this.lock.writeLock();
        Lock readLock = this.lock.readLock();

        public void put(String key, String value) {
            try {
                this.writeLock.lock();
                this.syncHashMap.put(key, value);
            } finally {
                this.writeLock.unlock();
            }
        }

        public void remove(String key) {
            try {
                this.writeLock.lock();
                this.syncHashMap.remove(key);
            } finally {
                this.writeLock.unlock();
            }
        }

        //...
        public String get(String key) {
            try {
                this.readLock.lock();
                return this.syncHashMap.get(key);
            } finally {
                this.readLock.unlock();
            }
        }

        public boolean containsKey(String key) {
            try {
                this.readLock.lock();
                return this.syncHashMap.containsKey(key);
            } finally {
                this.readLock.unlock();
            }
        }
    }
}
