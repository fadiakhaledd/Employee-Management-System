package com.ems.employeemanagementsystem.services.Imp;

import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.services.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Override
    public List<Employee> getAllEmployees() {
        return jsonUtils.readJsonFile();
    }

    @Override
    public Employee addEmployee() {
        return null;
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
