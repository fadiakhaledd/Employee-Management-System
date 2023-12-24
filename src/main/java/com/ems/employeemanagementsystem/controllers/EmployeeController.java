package com.ems.employeemanagementsystem.controllers;

import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.services.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees(@RequestParam(required = false) Map<String, String> filters) {
        try {
            return new ResponseEntity<>(employeeService.getAllEmployees(filters), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorMap = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);

            Map<String, String> message = Map.of("Message", "Employee Added Successfully");
            return new ResponseEntity<>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            Map<String, String> errorMap = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);

            Map<String, String> message = Map.of("Message", "Employee Deleted Successfully");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, String> errorMap = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable int id,
                                                 @RequestBody(required = false) Employee employeeData) {
        try {
            Employee newStudent = employeeService.updateEmployee(id, employeeData);
            return new ResponseEntity<>(newStudent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-languages")
    public ResponseEntity<Object> getEmployeesByKnownLanguages(
            @RequestParam("lang") String language,
            @RequestParam(value = "minScore", required = false) Integer minScore,
            @RequestParam(value = "maxScore", required = false) Integer maxScore) {

        return new ResponseEntity<>(employeeService.getEmployeesByKnownLanguages(language, minScore, maxScore), HttpStatus.OK);
    }

}
