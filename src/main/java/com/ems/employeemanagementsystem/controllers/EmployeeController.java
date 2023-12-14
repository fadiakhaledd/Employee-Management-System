package com.ems.employeemanagementsystem.controllers;

import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.services.IEmployeeService;
import com.ems.employeemanagementsystem.services.Imp.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees(){
       try {
           return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);

       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
}
