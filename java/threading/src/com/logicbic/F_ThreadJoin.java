package com.logicbic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class F_ThreadJoin {

    public static void main (String[] args) {
        final List<Integer> integers = Arrays.asList(10, 12, 13, 14, 15, 20);

        //thread A
        new Thread(() -> {
            List<FactorialCalculator> threads = new ArrayList<>();
            for (Integer integer : integers) {
                FactorialCalculator calc = new FactorialCalculator(integer);
                threads.add(calc);
                calc.start();
            }

            for (FactorialCalculator calc : threads) {
                System.out.println(calc.getNumber() + "! = " + calc.getFactorial());
                try {
                    calc.join();
                    System.out.println(calc.getNumber() + "! = " + calc.getFactorial());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread A completes");
        }).start();
    }

    private static class FactorialCalculator extends Thread {

        private final int number;
        private BigDecimal factorial;

        FactorialCalculator (int number) {
            this.number = number;
        }

        @Override
        public void run () {
            this.factorial = calculateFactorial(this.number);
        }

        private static BigDecimal calculateFactorial (int number) {
            BigDecimal factorial = BigDecimal.ONE;
            for (int i = 1; i <= number; i++) {
                factorial = factorial.multiply(new BigDecimal(i));
            }
            return factorial;
        }

        public BigDecimal getFactorial () {
            return this.factorial;
        }

        public int getNumber () {
            return this.number;
        }
    }
}
