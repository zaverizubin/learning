package com.logicbic;

import java.util.ArrayList;
import java.util.List;

public class K_UnsafeThreadAccess {
    private List<String> list;

    public static void main (String[] args) throws InterruptedException {
        K_UnsafeThreadAccess obj = new K_UnsafeThreadAccess();

        Thread thread1 = new Thread(() ->  System.out.println("thread1 : " + System.identityHashCode(obj.getList())));
        Thread thread2 = new Thread(() -> System.out.println("thread2 : " + System.identityHashCode(obj.getList())));

        thread1.start();
        thread2.start();
    }

    List<String> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
