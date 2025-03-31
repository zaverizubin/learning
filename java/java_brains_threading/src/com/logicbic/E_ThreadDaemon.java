package com.logicbic;

import java.util.ArrayList;
import java.util.List;

public class E_ThreadDaemon {
    static List<String> list = new ArrayList<>();

    public static void main (String[] args) throws InterruptedException {
        MemoryWatcherThread.start();
        for (int i = 0; i < 10000000; i++) {
            String str = "str" + i;
            list.add(str);
        }
        System.out.println("end of main method");
    }

    private static class MemoryWatcherThread implements Runnable{

        public static void start () {
            Thread thread = new Thread(new MemoryWatcherThread());
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.setDaemon(true);
            thread.start();
        }
        @Override
        public void run() {
            long memoryUsed = getMemoryUsed();
            System.out.println("Memory used :" + memoryUsed + " MB");

            while (true) {
                long memoryUsed1 = getMemoryUsed();
                if (memoryUsed != memoryUsed1) {
                    memoryUsed = memoryUsed1;
                    System.out.println("Memory used :" + memoryUsed + " MB");
                }
            }
        }

        private long getMemoryUsed () {
            return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        }
    }
}
