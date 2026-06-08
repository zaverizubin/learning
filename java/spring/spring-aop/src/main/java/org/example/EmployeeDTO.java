package org.example;


public class EmployeeDTO {

    private int id;

    private String name;

    private int age;

    public EmployeeDTO() {}

    public EmployeeDTO(int id) {
        this.id = id;
    }

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return this.name;}

    public void setName(String name) {this.name = name;}

    public int getAge() {return this.age;}

    public void setAge(int age) {this.age = age;}

    public String toString() {
        return "EmployeeDTO [id=" + this.id + ", name=" + this.name + ", age=" + this.age + "]";
    }
}
