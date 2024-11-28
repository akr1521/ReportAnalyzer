package com.reportgenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer salary;
    private  Integer managerId;

    private List<Employee> subordinates = new ArrayList<>();

    public Employee(Integer id, String firstName, String lastName, Integer salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }
}
