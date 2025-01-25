package com.playground.threading.jacobjenkov.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CalculatorReentrantLock {

    Lock lock = new ReentrantLock();

    public double getResult() {
        return this.result;
    }

    public void setResult(final double value) {
        try {
            this.lock.lock();
            this.result = value;
        } finally {
            this.lock.unlock();
        }

    }

    private volatile double result = 0.0D;

    public void add(double value) {
        try {
            this.lock.lock();
            this.result += value;
        } finally {
            this.lock.unlock();
        }
    }

    public void subtract(double value) {
        try {
            this.lock.lock();
            this.result -= value;
        } finally {
            this.lock.unlock();
        }
    }

    public void calculate(Calculation... calculations) {
        try {
            this.lock.lock();

            for (Calculation calculation : calculations) {
                switch (calculation.type) {
                    case Calculation.ADDITION:
                        add(calculation.value);
                        break;
                    case Calculation.SUBTRACTION:
                        subtract(calculation.value);
                        break;
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    public static class Calculation {
        public static final int UNSPECIFIED = -1;
        public static final int ADDITION = 0;
        public static final int SUBTRACTION = 1;
        public double value;
        int type = UNSPECIFIED;

        public Calculation(int type, double value) {
            this.type = type;
            this.value = value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CalculatorReentrantLock calculatorReentrantLock = new CalculatorReentrantLock();
        Calculation[] calculations = new Calculation[]{new Calculation(Calculation.ADDITION, 5), new Calculation(Calculation.SUBTRACTION, 2), new Calculation(Calculation.ADDITION, 1)};

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i=0; i <6; i++){
            executorService.submit(()->{
                calculatorReentrantLock.calculate(calculations);
                System.out.println(Thread.currentThread().getName() + ":  " + calculatorReentrantLock.getResult());
            });
        }
        executorService.shutdown();
    }
}
