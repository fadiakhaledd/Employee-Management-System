package com.ems.employeemanagementsystem.repositories;

import com.ems.employeemanagementsystem.Utils.JsonUtils;
import com.ems.employeemanagementsystem.models.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeesRepository {
    static Map<Integer, Employee> employeesMap = new HashMap<>();
    JsonUtils jsonUtils = new JsonUtils();

    public EmployeesRepository() {
        try {
            List<Employee> employees = jsonUtils.readEmployeesFromJsonFile();

            for (Employee employee : employees) {
                employeesMap.put(employee.getEmployeeId(), employee);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employeesMap.values());
    }

    public static void setEmployees(Map<Integer, Employee> employees) {
        EmployeesRepository.employeesMap = employees;
    }

    public void addEmployee(Employee employee) throws IOException {
        Employee existingEmployee = employeesMap.get(employee.getEmployeeId());
        if (existingEmployee != null) {
            throw new IllegalArgumentException("Employee with ID: " + employee.getEmployeeId() + " already exists");
        } else {
            employeesMap.put(employee.getEmployeeId(), employee);
        }
        jsonUtils.writeEmployeesToJsonFile(getEmployees());
    }

    public Employee getEmployeeById(int employeeId) {
        return employeesMap.get(employeeId);
    }

    public void deleteEmployee(int employeeId) throws IOException {
        employeesMap.remove(employeeId);
        jsonUtils.writeEmployeesToJsonFile(getEmployees());
    }

    public Employee updateEmployee(int employeeId, Employee newEmployeeData) throws IOException {
        employeesMap.put(employeeId, newEmployeeData);
        jsonUtils.writeEmployeesToJsonFile(getEmployees());

        return employeesMap.get(employeeId);
    }
}
