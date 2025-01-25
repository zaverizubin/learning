package com.playground.core_java.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStreamOtherCollectors {

    private final Employee[] employees = {new Employee(1, "Tom", 100,"Sales", Arrays.asList("address 1", "address 2")),
            new Employee(2, "Dick", 200, "Purchase", Arrays.asList("address 3", "address 4")),
            new Employee(3, "Harry", 300, "Accounts", Arrays.asList("address 5", "address 6")),
            new Employee(3, "Sally", 300, "Accounts", Arrays.asList("address 7", "address 8")),
            new Employee(3, "Jane", 300, "Sales", Arrays.asList("address 9", "address 10"))};


    public Long collectors_toCounting() {
        return Stream.of(this.employees).filter(emp ->emp.getDepartment().equals("Accounts")).collect(Collectors.counting());
    }

    public Map<String, Set<Employee>> collectors_filtering() {
        return Stream.of(this.employees).filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(emp ->emp.getSalary()>200, Collectors.toSet())));
    }

    public String collectors_joining() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .map(Employee::getName).collect(Collectors.joining(", ", "[","]"));
    }

    public Double collectors_averaging() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.averagingInt(Employee::getSalary));
    }

    public Integer collectors_summing() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.summingInt(Employee::getSalary));
    }

    public Optional<Employee> collectors_maxBy() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));
    }

    public Map<Boolean, List<Employee>> collectors_partitioningBy() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.partitioningBy(employee ->employee.getSalary()> 200));
    }

    public Integer collectors_teeing() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.teeing(Collectors.maxBy(Comparator.comparing(Employee::getSalary)), Collectors.maxBy(Comparator.comparing(Employee::getSalary)),
                        (a, b) -> a.get().getSalary()+b.get().getSalary()));
    }

    public IntSummaryStatistics collectors_summarinzInt() {
        return Stream.of(this.employees)
                .filter(emp ->emp.getDepartment().equals("Accounts"))
                .collect(Collectors.summarizingInt(Employee::getSalary));
    }


}
