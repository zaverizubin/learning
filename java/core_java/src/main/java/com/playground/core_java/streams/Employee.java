package com.playground.core_java.streams;

import java.util.List;

public class Employee {

    private String name;
    private int id;
    private int salary;
    private String department;
    private List<String> addresses;


    public Employee( int id, String name, int salary, String department, List<String> addresses) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.department = department;
        this.addresses = addresses;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<String> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(final List<String> addresses) {
        this.addresses = addresses;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

}
