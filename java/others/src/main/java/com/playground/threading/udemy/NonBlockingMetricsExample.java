package com.playground.threading.udemy;

import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingMetricsExample {

    public static void main(String[] args) {

    }

    private static class Metric {
        private final AtomicReference<InternalMetric> internalMetric = new AtomicReference<>(new InternalMetric());

        public void addSample(long sample) {
            InternalMetric currentState;
            InternalMetric newState;
            do {
                currentState = this.internalMetric.get();
                newState = new InternalMetric();
                newState.sum = currentState.sum + sample;
                newState.count = currentState.count + 1;
            } while (!this.internalMetric.compareAndSet(currentState, newState));
        }

        public double getAverage() {
            InternalMetric newResetState = new InternalMetric();
            InternalMetric currentState;
            double average;
            do {
                currentState = this.internalMetric.get();
                average = (double) currentState.sum / currentState.count;
            } while (!this.internalMetric.compareAndSet(currentState, newResetState));

            return average;
        }

        private static class InternalMetric {
            public long count;
            public long sum;
        }
    }

}
