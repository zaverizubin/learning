package com.playground.threading.baeldung;

import java.lang.Thread;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

    ReentrantLock lockA = new ReentrantLock();
    ReentrantLock lockB = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Deadlock deadlock = new Deadlock();

        Thread thread1 = new Thread(()->{
            deadlock.lockA.lock();
            System.out.println("Thread 1 acquired lock A");
            deadlock.lockB.lock();
            System.out.println("Thread 1 acquired lock B");
        });

        Thread thread2 = new Thread(()->{
            deadlock.lockB.lock();
            System.out.println("Thread 2 acquired lock B");
            deadlock.lockA.lock();
            System.out.println("Thread 2 acquired lock A");
        });

        thread1.start();
        thread2.start();

        Thread.sleep(2000);

        detectDeadLock();

    }

    private static void detectDeadLock(){
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.findDeadlockedThreads();
        boolean deadlock = threadIds != null && threadIds.length > 0;
        System.out.println("Deadlocks found " + deadlock);

        if(deadlock){
            Thread.getAllStackTraces().forEach((thread, stackTraceElements) -> {
                if(Arrays.stream(threadIds).anyMatch(idn -> idn == thread.getId())){
                    for (StackTraceElement stackTraceElement : stackTraceElements) {
                        System.out.printf("Class: %s MethodName: %s%n",  stackTraceElement.getClassName(), stackTraceElement.getMethodName());
                    }
                    System.out.println("");
                }

            });
        }

    }

}
