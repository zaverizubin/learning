package com.playground.threading.jacobjenkov;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockTimeoutExample {

    public static void main(String[] args) throws InterruptedException {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Runnable runnable1 = new Runnable1TimeOut(lock1, lock2);
        Runnable runnable2 = new Runnable2TimeOut(lock1, lock2);

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

    }

    private static class Runnable1TimeOut implements Runnable {
        Lock lock1;
        Lock lock2;

        public Runnable1TimeOut(final Lock lock1, final Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        private boolean tryLockBothLocks() {
            String threadName = Thread.currentThread().getName();

            try {
                boolean lock1Succeeded = this.lock1.tryLock(1000, TimeUnit.MILLISECONDS);
                if (!lock1Succeeded) {
                    return false;
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted while trying to lock Lock 1");
                return false;
            }

            try {
                boolean lock2Succeeded = this.lock2.tryLock(1000, TimeUnit.MILLISECONDS);
                if (!lock2Succeeded) {
                    this.lock1.unlock();
                    return false;
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted while trying to lock Lock 2");
                return false;
            }
            return true;
        }

        private void sleep(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            while (true) {
                int failureCount = 0;
                while (!tryLockBothLocks()) {
                    failureCount++;
                    System.out.println(threadName + " failed to lock both locks." + " Waiting a bit before retrying [" + failureCount + "]");
                    sleep(100L * (long) Math.random());
                }
                if (failureCount > 0) {
                    System.out.println(threadName + " succeeded in locking both threads after " + failureCount + " attempts");
                }

                //Do work now that both locks are acquired.

                //unlock
                this.lock2.unlock();
                this.lock1.unlock();
                break;
            }
        }
    }

    private static class Runnable2TimeOut implements Runnable {
        Lock lock1;
        Lock lock2;

        public Runnable2TimeOut(final Lock lock1, final Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        private boolean tryLockBothLocks() {
            String threadName = Thread.currentThread().getName();

            try {
                boolean lock2Succeeded = this.lock2.tryLock(1000, TimeUnit.MILLISECONDS);
                if (!lock2Succeeded) {
                    return false;
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted while trying to lock Lock 1");
                return false;
            }

            try {
                boolean lock1Succeeded = this.lock1.tryLock(1000, TimeUnit.MILLISECONDS);
                if (!lock1Succeeded) {
                    this.lock2.unlock();
                    return false;
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted while trying to lock Lock 2");
                return false;
            }
            return true;
        }

        private void sleep(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            while (true) {
                int failureCount = 0;
                while (!tryLockBothLocks()) {
                    failureCount++;
                    System.out.println(threadName + " failed to lock both locks." + " Waiting a bit before retrying [" + failureCount + "]");
                    sleep(100L * (long) Math.random());
                }
                if (failureCount > 0) {
                    System.out.println(threadName + " succeeded in locking both threads after " + failureCount + " attempts");
                }

                //Do work now that both locks are acquired.

                //unlock
                this.lock1.unlock();
                this.lock2.unlock();
                break;
            }
        }
    }
}
