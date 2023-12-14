package com.ems.employeemanagementsystem.services;

import com.ems.employeemanagementsystem.Utils.JsonUtils;
import com.ems.employeemanagementsystem.models.Employee;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    JsonUtils jsonUtils = new JsonUtils();

    public List<Employee> getAllEmployees() throws IOException;

    public void addEmployee(Employee employee) throws IOException;

    public Employee getEmployee();

    public void deleteEmployee(int employeeId);

    public void updateEmployee(int employeeId);
}
