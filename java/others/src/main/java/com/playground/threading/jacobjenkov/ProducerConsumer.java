package com.playground.threading.jacobjenkov;

public class ProducerConsumer {

    public static void main(String[] args)
    {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }

    public static class Shared{
        private char c;
        private volatile boolean writeable = true;

        synchronized void setSharedChar(char c) {
            while (!this.writeable)
                try
                {
                    wait();
                }
                catch (InterruptedException ie){
                    //ignore
                }
            this.c = c;
            this.writeable = false;
            notify();
        }

        synchronized char getSharedChar(){
            while (this.writeable)
                try{
                    wait();
                }
                catch (InterruptedException ie)
                {
                    //ignore
                }
            this.writeable = true;
            notify();
            return this.c;
        }
    }

    public static class  Producer extends Thread{
        private final Shared s;

        Producer(Shared s){
            this.s = s;
        }

        @Override
        public void run(){
            for (char ch = 'A'; ch <= 'Z'; ch++){
                synchronized(this.s) {
                    this.s.setSharedChar(ch);
                    System.out.println(ch + " produced by producer.");
                }
            }
        }
    }

    public static class  Consumer extends Thread{
        private final Shared s;
        Consumer(Shared s){
            this.s = s;
        }

        @Override
        public void run(){
            char ch;
            do{
                synchronized(this.s) {
                    ch = this.s.getSharedChar();
                    System.out.println(ch + " consumed by consumer.");
                }
            }
            while (ch != 'Z');
        }
    }

}