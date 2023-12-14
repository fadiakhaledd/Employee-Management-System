package com.ems.employeemanagementsystem.models;

import java.util.List;

public class Employee {
    String firstname;
    String lastname;
    int employeeId;
    Designation designation;
    List<KnownLanguage> knownLanguages;

    public Employee(int employeeId, String firstname, String lastname,
                    Designation designation, List<KnownLanguage> knownLanguages) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.employeeId = employeeId;
        this.designation = designation;
        this.knownLanguages = knownLanguages;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public List<KnownLanguage> getKnownLanguages() {
        return knownLanguages;
    }

    public void setKnownLanguages(List<KnownLanguage> knownLanguages) {
        this.knownLanguages = knownLanguages;
    }
}

