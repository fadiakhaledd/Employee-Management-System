package com.ems.employeemanagementsystem.services.Imp;

import com.ems.employeemanagementsystem.models.Designation;
import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.models.KnownLanguage;
import com.ems.employeemanagementsystem.services.IEmployeeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Override
    public List<Employee> getAllEmployees(Map<String, String> filters) {
        List<Employee> employeesList = employeesRepository.getEmployees();

        Map<String, String> lowercaseFilters = new HashMap<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (entry.getKey().toLowerCase().equals("designation")) {
                lowercaseFilters.put(entry.getKey().toLowerCase(), entry.getValue().toUpperCase());
                continue;
            }
            lowercaseFilters.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }
        Set<String> mapKeys = lowercaseFilters.keySet();

        return employeesList.stream()
                .filter(employee ->
                        (!mapKeys.contains("id") || employee.getEmployeeId() == Integer.parseInt(lowercaseFilters.get("id"))) &&
                                (!mapKeys.contains("designation") || employee.getDesignation().toString().equals(lowercaseFilters.get("designation"))) &&
                                (!mapKeys.contains("firstname") || employee.getFirstname().toLowerCase().contains(lowercaseFilters.get("firstname"))) &&
                                (!mapKeys.contains("lastname") || employee.getLastname().toLowerCase().contains(lowercaseFilters.get("lastname")))
                ).toList();
    }

    @Override
    public void addEmployee(Employee employee) throws IOException {
        employeesRepository.addEmployee(employee);
    }

    @Override
    public Employee getEmployee(int employeeId) {
        Employee employee = employeesRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NullPointerException("Employee with ID: " + employeeId + " does not exist");
        }
        return employee;
    }

    @Override
    public void deleteEmployee(int employeeId) throws IOException {
        Employee employee = employeesRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NullPointerException("Employee with ID: " + employeeId + " does not exist");
        }
        employeesRepository.deleteEmployee(employeeId);
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee employeeData) throws IOException {
        Employee oldEmployee = employeesRepository.getEmployeeById(employeeId);

        if (oldEmployee == null) {
            throw new NullPointerException("Employee with ID: " + employeeId + " does not exist");
        }
        if (employeeData == null){
            return oldEmployee;
        }

        String firstname = Optional.ofNullable(employeeData.getFirstname()).orElse(oldEmployee.getFirstname());
        String lastname = Optional.ofNullable(employeeData.getLastname()).orElse(oldEmployee.getLastname());
        Designation designation = Optional.ofNullable(employeeData.getDesignation()).orElse(oldEmployee.getDesignation());
        List<KnownLanguage> knownLanguages = Optional.ofNullable(employeeData.getKnownLanguages()).orElse(oldEmployee.getKnownLanguages());

        Employee newEmployee = new Employee(oldEmployee.getEmployeeId(), firstname, lastname, designation, knownLanguages);

        return employeesRepository.updateEmployee(employeeId, newEmployee);
    }

}
