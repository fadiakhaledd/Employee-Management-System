package com.ems.employeemanagementsystem.services;

import com.ems.employeemanagementsystem.Utils.JsonUtils;
import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.repositories.EmployeesRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    EmployeesRepository employeesRepository = new EmployeesRepository();
    List<Employee> getAllEmployees(Map<String, String> filters) throws IOException;

    void addEmployee(Employee employee) throws IOException;

    Employee getEmployee(int employeeId) throws IOException;

    void deleteEmployee(int employeeId) throws IOException;

    Employee updateEmployee(int id, Employee employeeData) throws IOException;

    public List<Employee> getEmployeesByKnownLanguages(String language, Integer minScore, Integer maxScore);
}
