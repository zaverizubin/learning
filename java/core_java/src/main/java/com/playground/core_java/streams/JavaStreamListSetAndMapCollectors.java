package com.playground.core_java.streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStreamListSetAndMapCollectors {

    private final Employee[] employees = {new Employee(1, "Tom", 100,"Sales", Arrays.asList("address 1", "address 2")),
            new Employee(2, "Dick", 200, "Purchase", Arrays.asList("address 3", "address 4")),
            new Employee(3, "Harry", 300, "Accounts", Arrays.asList("address 5", "address 6")),
            new Employee(3, "Sally", 300, "Accounts", Arrays.asList("address 7", "address 8")),
            new Employee(3, "Jane", 300, "Sales", Arrays.asList("address 9", "address 10"))};

    public Collection<Integer> collectors_toList() {
       return Stream.of(this.employees).map(Employee::getId).collect(Collectors.toList());
    }

    public Collection<Integer> collectors_toUnmodifiableList() {
        return Stream.of(this.employees).map(Employee::getId).collect(Collectors.toUnmodifiableList());
    }

    public Set<Integer> collectors_toUnmodifiableSet() {
        return Stream.of(this.employees).map(Employee::getId).collect(Collectors.toUnmodifiableSet());
    }

    public LinkedList<Employee> collectors_toCollection() {
        return Stream.of(this.employees).filter(employee -> employee.getSalary()> 200).collect(Collectors.toCollection(LinkedList::new));
    }

    public Map<Employee, List<String>> toMap() {
        return Stream.of(this.employees).collect(Collectors.toMap(Function.identity(), Employee::getAddresses));
    }

    public Map<String, Integer> toMapWithKeyCollisions() {
        return Stream.of(this.employees).collect(Collectors.toMap(Employee::getDepartment, Employee::getSalary, Integer::sum));
    }

    public Map<String, Integer> toMapWithKeyCollisionsAndIntoTreeMap() {
        return Stream.of(this.employees).collect(Collectors.toMap(Employee::getDepartment, Employee::getSalary, Integer::sum, TreeMap::new));
    }

}
