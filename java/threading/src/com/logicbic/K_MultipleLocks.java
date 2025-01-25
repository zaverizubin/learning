package com.logicbic;

import java.util.ArrayList;
import java.util.List;

public class K_MultipleLocks {

        private volatile List<String> list1;
        private volatile List<String> list2;
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        public static void main (String[] args) throws InterruptedException {
            K_MultipleLocks obj = new K_MultipleLocks();

            Thread thread1 = new Thread(() -> {
                System.out.println("thread1 list1 : " + System.identityHashCode(obj.getList1()));
                System.out.println("thread1 list2 : " + System.identityHashCode(obj.getList2()));
            });
            Thread thread2 = new Thread(() -> {
                System.out.println("thread2 list1 : " + System.identityHashCode(obj.getList1()));
                System.out.println("thread2 list2 : " + System.identityHashCode(obj.getList2()));
            });

            thread1.start();
            thread2.start();
        }

        private List<String> getList1 () {
            if (list1 == null) {
                synchronized (lock1) {
                    if (list1 == null) {
                        list1 = new ArrayList<>();
                    }
                }
            }
            return list1;
        }

        private List<String> getList2 () {
            if (list2 == null) {
                synchronized (lock2) {
                    if (list2 == null) {
                        list2 = new ArrayList<>();
                    }
                }
            }
            return list2;
        }

}
