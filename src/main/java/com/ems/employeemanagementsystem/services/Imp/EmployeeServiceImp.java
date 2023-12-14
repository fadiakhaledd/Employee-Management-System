package com.ems.employeemanagementsystem.services.Imp;

import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.services.IEmployeeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        return jsonUtils.readEmployeesFromJsonFile();
    }

    @Override
    public void addEmployee(Employee employee) throws IOException {
        List<Employee> employees = getAllEmployees();
        boolean existingId = employees.stream().anyMatch(emp -> emp.getEmployeeId() == employee.getEmployeeId());
        if (existingId) {
            throw new IllegalArgumentException("Student with ID: " + employee.getEmployeeId() + " already exists");
        } else {
            jsonUtils.addEmployeeToJsonFile(employee);
        }
    }

    @Override
    public Employee getEmployee() {
        return null;
    }

    @Override
    public void deleteEmployee(int employeeId) {

    }

    @Override
    public void updateEmployee(int employeeId) {

    }


}
