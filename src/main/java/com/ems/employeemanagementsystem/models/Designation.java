package com.ems.employeemanagementsystem.models;

public enum Designation {
    DEVELOPER("Developer"),
    TEAM_LEADER("Team Leader"),
    MANAGER("Manager");
    private String value;

    Designation(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
