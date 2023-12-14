package com.ems.employeemanagementsystem.services;

import com.ems.employeemanagementsystem.Utils.JsonUtils;
import com.ems.employeemanagementsystem.models.Employee;

import java.util.List;

public interface IEmployeeService {
    JsonUtils jsonUtils = new JsonUtils();

    public List<Employee> getAllEmployees();

    public Employee addEmployee();

    public Employee getEmployee();

    public void deleteEmployee(int employeeId);

    public void updateEmployee(int employeeId);
}
