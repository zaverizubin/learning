package com.playground.threading.concurrencyutilities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo
{
    public static void main(String[] args)
    {
        float[][] matrix = new float[3][3];

        int counter = 0;
        for (int row = 0; row < matrix.length; row++)
            for (int col = 0; col < matrix[0].length; col++)
                matrix[row][col] = counter++;

        dump(matrix);

        System.out.println();
        Solver solver = new Solver(matrix);
        System.out.println();
        dump(matrix);
    }

    static void dump(float[][] matrix){
        for (int row = 0; row < matrix.length; row++){
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static class Solver {
        final int N;
        final float[][] data;
        final CyclicBarrier barrier;

        class Worker implements Runnable {
            int myRow;
            boolean done = false;

            Worker(int row) {
                this.myRow = row;
            }

            boolean done() {
                return this.done;
            }

            void processRow(int myRow) {
                System.out.println("Processing row: " + myRow);
                for (int i = 0; i < Solver.this.N; i++)
                    Solver.this.data[myRow][i] *= 10;
                this.done = true;
            }

            @Override
            public void run() {
                while (!done()) {
                    processRow(this.myRow);
                    try {
                        Solver.this.barrier.await();
                    } catch (InterruptedException ie) {
                        return;
                    } catch (BrokenBarrierException bbe) {
                        return;
                    }
                }
            }
        }

        public Solver(float[][] matrix) {
            this.data = matrix;
            this.N = matrix.length;
            this.barrier = new CyclicBarrier(this.N,
                    new Runnable() {
                        @Override
                        public void run() {
                            mergeRows();
                        }
                    });
            for (int i = 0; i < this.N; ++i)
                new Thread(new Worker(i)).start();
            waitUntilDone();
        }

        void mergeRows() {
            System.out.println("merging");
            synchronized ("abc") {
                "abc".notify();
            }
        }

        void waitUntilDone() {
            synchronized ("abc") {
                try {
                    System.out.println("main thread waiting");
                    "abc".wait();
                    System.out.println("main thread notified");
                } catch (InterruptedException ie) {
                    System.out.println("main thread interrupted");
                }
            }
        }
    }
}
