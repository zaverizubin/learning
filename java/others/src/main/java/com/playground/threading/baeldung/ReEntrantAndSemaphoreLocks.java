package com.playground.threading.baeldung;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntrantAndSemaphoreLocks {

    public static class SequenceGenerator{
        private int currentValue = 0;

        public int increment(){
            this.currentValue +=1;
            return this.currentValue;
        }
    }

    public static class SequenceGeneratorWithReentrantLock extends SequenceGenerator{
        private int currentValue = 0;
        private final ReentrantLock mutexLock = new ReentrantLock();

        @Override
        public int increment(){
            try{
                this.mutexLock.lock();

                this.currentValue +=1;
                return this.currentValue;
            }finally {
                this.mutexLock.unlock();
            }
        }
    }

    public static class SequenceGeneratorWithSemaphore extends SequenceGenerator{
        private int currentValue = 0;
        private final Semaphore mutexLock = new Semaphore(1);

        @Override
        public int increment(){
            try{
                this.mutexLock.acquire();
                this.currentValue +=1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.mutexLock.release();
            }
            return this.currentValue;
        }
    }

    public static void getUniqueSequences(SequenceGenerator sequenceGenerator) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();

        List<Future<Integer>> futures = new ArrayList<>();

        for(int count = 0; count< 1000; count++){
            futures.add(executorService.submit(sequenceGenerator::increment));
        }
        for (Future<Integer> future: futures) {
            uniqueSequences.add(future.get());
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();

        if(sequenceGenerator instanceof SequenceGeneratorWithReentrantLock || sequenceGenerator instanceof  SequenceGeneratorWithSemaphore){
            System.out.println("UniqueSequences count for synchronized access is " + uniqueSequences.size());
        }else{
            System.out.println("UniqueSequences count for un-synchronized access is " + uniqueSequences.size());
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        getUniqueSequences(new SequenceGenerator());
        getUniqueSequences(new SequenceGeneratorWithReentrantLock());
        getUniqueSequences(new SequenceGeneratorWithSemaphore());
    }

}
