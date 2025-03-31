package com.playground.core_java.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStreamOperations {

    private final Employee[] employees = {new Employee(1, "Tom", 100, "Sales", Arrays.asList("address 1", "address 2")),
            new Employee(2, "Tom", 200, "Purchase", Arrays.asList("address 3", "address 4")),
            new Employee(3, "Tom", 300, "Accounts", Arrays.asList("address 5", "address 6"))};

    private final Set<Integer> integerSet = new HashSet<>(Arrays.asList(21,23,12,16));

    private final List<List<String>> namesNested = Arrays.asList(Arrays.asList("Jeff", "Bezos"),
            Arrays.asList("Bill", "Gates"),
            Arrays.asList("Mark", "Zuckerberg"));


    public Collection<Integer> map() {
       return Stream.of(this.employees).map(Employee::getId).collect(Collectors.toList());
    }

    public Collection<Employee> filter() {
        return Stream.of(this.employees).filter(emp -> emp.getSalary() > 100).collect(Collectors.toList());
    }

    public void forEach() {
        Stream.of(this.employees).forEach(emp -> emp.setSalary(emp.getSalary() + 10));
    }

    public void forEachOrdered(){
        String str = "Zubin Zaveri";
        System.out.println("****forEach without using parallel****");

        str.chars().forEach(s -> System.out.print((char) s));
        System.out.println("\n****forEach with using parallel****");

        str.chars().parallel().forEach(s -> System.out.print((char) s));
        System.out.println("\n****forEachOrdered with using parallel****");

        str.chars().parallel().forEachOrdered(s -> System.out.print((char) s));
    }

    public Collection<Integer> sorted(){
        return this.integerSet.stream().sorted().collect(Collectors.toList());
    }

    public Collection<Employee> sortedComparator(){
        return Stream.of(this.employees).sorted(Comparator.comparing(Employee::getId)).collect(Collectors.toList());
    }

    public Optional<Employee> findFirst() {
        return Stream.of(this.employees).filter(emp -> emp.getSalary() > 100).findFirst();
    }

    public boolean anyMatch(){
        return Stream.of(this.employees).anyMatch(employee -> employee.getId()==1);
    }

    public boolean allMatch(){
        return Stream.of(this.employees).allMatch(employee -> employee.getSalary()> 100);
    }

    public Integer[] toArray() {
        return Stream.of(this.employees).map(Employee::getId).toArray(Integer[]::new);
    }

    public List<String> flatMap() {
        return this.namesNested.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Optional<Employee> max(){
        return Stream.of(this.employees).max(Comparator.comparing(Employee::getSalary));
    }

    public Integer reduce(){
        return Stream.of(this.employees).reduce(0, (subtotal, employee) -> subtotal + employee.getSalary(), Integer::sum);
    }

    public List<Employee> peek() {
        return Stream.of(this.employees).peek(emp -> emp.setSalary(emp.getSalary() + 10)).peek(System.out::println).collect(Collectors.toList());
    }

    public void takeWhile(){
        Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck")
                .takeWhile(n -> n.length() % 2 != 0)
                .forEach(System.out::println);
    }

}
