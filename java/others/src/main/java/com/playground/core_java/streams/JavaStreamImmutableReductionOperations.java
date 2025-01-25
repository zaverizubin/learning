package com.playground.core_java.streams;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class JavaStreamImmutableReductionOperations {

    private final Employee[] employees = {new Employee(1, "Tom", 100, "Sales", Arrays.asList("address 1", "address 2")),
            new Employee(2, "Tom", 200, "Purchase", Arrays.asList("address 3", "address 4")),
            new Employee(3, "Tom", 300, "Accounts", Arrays.asList("address 5", "address 6"))};

    private final Set<Integer> integerSet = new HashSet<>(Arrays.asList(21,23,12,16));

    public Integer intStreamReduce(){
       return IntStream.range(1,6).reduce(Integer::sum).orElse(-1);
    }

    public Integer intStreamReduceWithIdentity(){
       return IntStream.range(1,6).reduce(1, Integer::sum);
    }


    public Integer intStreamReduceWithParallel(){
        return IntStream.range(1, 4)
                .parallel()
                .reduce(10, (a, b) -> a * b); //incorrect results.
    }

    public Integer intStreamReduceWithCombinator(){
        return Stream.of("2", "3", "4", "5")
                .parallel()
                .reduce(0, (integer, s) -> Integer.sum(integer, Integer.parseInt(s)), Integer::sum);
    }

    public String reduceMin(){
        return Stream.of("banana", "pie", "apple")
                .min(String::compareTo)
                .orElse("None");
    }

    public double reduceAverage(){
        Double d = LongStream.range(1, 10).average().orElse(-1);
        Long l = Stream.of("banana", "pie", "apple").mapToLong(s -> 1L).reduce(0, Long::sum);

        return d * l;
    }

}
