package com.logicbic;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class J_ThreadLock {
    public static void main (String[] args) throws InterruptedException {
        J_ThreadLock demo = new J_ThreadLock();
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 before call "+ LocalDateTime.now());
            demo.syncMethod("from thread1");
            System.out.println("thread1 after call "+LocalDateTime.now());
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 before call "+LocalDateTime.now());
            demo.syncMethod("from thread2");
            System.out.println("thread2 after call "+LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
    }

    private synchronized void syncMethod (String msg) {
        System.out.println("in the sync method "+msg+" "+ LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
