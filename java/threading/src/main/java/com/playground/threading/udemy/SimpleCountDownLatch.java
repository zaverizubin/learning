package com.playground.threading.udemy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCountDownLatch {

        private final Lock lock = new ReentrantLock();
        private final Condition condition = this.lock.newCondition();
        private int count;

        public SimpleCountDownLatch(int count) {
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        /**
         * Causes the current thread to wait until the latch has counted down to zero.
         * If the current count is already zero then this method returns immediately.
         */
        public void await() throws InterruptedException {
            try{
                this.lock.lock();
                while (this.count > 0){
                    this.condition.await();
                }
            }finally {
                this.lock.unlock();
            }

        }

        /**
         *  Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
         *  If the current count already equals zero then nothing happens.
         */
        public void countDown() {
            try{
                this.lock.lock();
                if(this.count > 0){
                    this.count--;
                    if(this.count ==0){
                        this.condition.signalAll();
                    }
                }
            }finally {
                this.lock.unlock();
            }
        }

        /**
         * Returns the current count.
         */
        public int getCount() {
            try {
                this.lock.lock();
                return this.count;
            }finally {
                this.lock.unlock();
            }
        }

}
