package com.playground.threading.baeldung;

public class SynchronizedMethods {

    private int sum = 0;

    private static int staticSum = 0;

    public void calculateSum(){
        this.sum += 1;
    }

    public synchronized void  calculateSumSync(){
        this.sum +=1;
    }

    public static synchronized void calculateSumStaticSync() {
        staticSum = staticSum + 1;
    }

    public static void calculateSumStaticSyncBlock(){
        synchronized (SynchronizedMethods.class) {
            staticSum = staticSum + 1;
        }
    }

    public int getSum(){
        return this.sum;
    }

    public static int getStaticSum(){
        return staticSum;
    }

}
