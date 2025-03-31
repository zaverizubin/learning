package com.playground.threading.baeldung;

import java.util.Collections;

public class AnotherSyncExample {
    int counter = 0;

    public synchronized void setCounter(){
        this.counter += 1;
        System.out.println("setCounter value " + this.counter);
    }

    public synchronized Object getObject(){
        System.out.println("getCounter value " + this.counter);
        return this.counter;
    }

    public static void main(String[] args){
        AnotherSyncExample anotherSyncExamples = new AnotherSyncExample();

        Thread thread1 = new Thread(()-> {

                for (int i= 0; i< 10;i++){
                    anotherSyncExamples.setCounter();
                }
        });

        Thread thread2 = new Thread(() ->{

                for (int i = 0; i < 10; i++) {
                    anotherSyncExamples.getObject();
                }

        });

        thread1.start();
        thread2.start();

    }

}
