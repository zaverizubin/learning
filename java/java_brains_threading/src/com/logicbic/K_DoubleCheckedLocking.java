package com.logicbic;

import java.util.ArrayList;
import java.util.List;

public class K_DoubleCheckedLocking {

    private volatile List<String> list;

    public static void main (String[] args) throws InterruptedException {
        K_DoubleCheckedLocking obj = new K_DoubleCheckedLocking();

        Thread thread1 = new Thread(() ->  System.out.println("thread1 : " + System.identityHashCode(obj.getList())));
        Thread thread2 = new Thread(() -> System.out.println("thread2 : " + System.identityHashCode(obj.getList())));

        thread1.start();
        thread2.start();
    }

    private List<String> getList () {
        if (this.list == null) {
            synchronized (this){
                if(this.list == null){
                    this.list = new ArrayList<>();
                }

            }

        }
        return this.list;
    }
}
