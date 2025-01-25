package com.playground.core_java.streams;

import java.util.*;
import java.util.stream.Stream;

public class JavaParallelStream {

    private final Employee[] employees = {
            new Employee(1, "Tom", 100,"Sales", Arrays.asList("address 1", "address 2")),
            new Employee(2, "Dick", 200, "Purchase", Arrays.asList("address 3", "address 4")),
            new Employee(3, "Harry", 300, "Accounts", Arrays.asList("address 5", "address 6")),
            new Employee(3, "Sally", 300, "Accounts", Arrays.asList("address 7", "address 8")),
            new Employee(3, "Jane", 300, "Sales", Arrays.asList("address 9", "address 10"))};


    public Integer reduceInParallel(){
        Stream<Integer> reducedParams = Arrays.asList(1, 2, 3).parallelStream();
        return reducedParams.reduce(10, Integer::sum, (a, b) ->{
            System.out.println("combiner was called");
            return a+b;
        });


    }


}
