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
            lowercaseFilters.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }

        return employeesList.stream()
                .filter(employee ->
                        (!lowercaseFilters.containsKey("id") || Objects.equals(employee.getEmployeeId(), Integer.parseInt(lowercaseFilters.get("id")))) &&
                                (!lowercaseFilters.containsKey("designation") || employee.getDesignation().toString().equalsIgnoreCase(lowercaseFilters.get("designation"))) &&
                                (!lowercaseFilters.containsKey("firstname") || employee.getFirstname().toLowerCase().contains(lowercaseFilters.get("firstname"))) &&
                                (!lowercaseFilters.containsKey("lastname") || employee.getLastname().toLowerCase().contains(lowercaseFilters.get("lastname")))
                )
                .toList();
    }

    @Override
    public void addEmployee(Employee employee) throws IOException {
        Employee existingEmployee = employeesRepository.getEmployeeById(employee.getEmployeeId());

        if (existingEmployee != null) {
            throw new IllegalArgumentException("Employee with ID: " + employee.getEmployeeId() + " already exists");
        }

        validateEmployeeData(employee.getFirstname(),employee.getLastname(), employee.getDesignation(), employee.getKnownLanguages());
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

        if (employeeData == null) {
            return oldEmployee;
        }

        String firstname = Optional.ofNullable(employeeData.getFirstname()).orElse(oldEmployee.getFirstname());
        String lastname = Optional.ofNullable(employeeData.getLastname()).orElse(oldEmployee.getLastname());
        Designation designation = Optional.ofNullable(employeeData.getDesignation()).orElse(oldEmployee.getDesignation());
        List<KnownLanguage> knownLanguages = Optional.ofNullable(employeeData.getKnownLanguages()).orElse(oldEmployee.getKnownLanguages());

        validateEmployeeData(firstname, lastname, designation, knownLanguages);

        Employee newEmployee = new Employee(oldEmployee.getEmployeeId(), firstname, lastname, designation, knownLanguages);


        return employeesRepository.updateEmployee(employeeId, newEmployee);
    }

    private void validateEmployeeData(String firstname, String lastname, Designation designation, List<KnownLanguage> knownLanguages) {
        if (firstname == null || firstname.trim().isEmpty()) {
            throw new IllegalArgumentException("Firstname cannot be null or empty");
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        if (designation == null) {
            throw new IllegalArgumentException("Designation cannot be null");
        }
        if (knownLanguages != null) {
            for (KnownLanguage language : knownLanguages) {
                if (language.getLanguageName() == null || language.getLanguageName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Language cannot be null or empty");
                }
                if (language.getScoreOutOf100() < 0 || language.getScoreOutOf100() > 100) {
                    throw new IllegalArgumentException("KnownLanguage score must be between 0 and 100");
                }
            }
        }
    }

    @Override
    public List<Employee> getEmployeesByKnownLanguages(String language, Integer minScore, Integer maxScore) {

        final Integer finalMinScore = (minScore != null) ? minScore : 0;
        final Integer finalMaxScore = (maxScore != null) ? maxScore : 100;

        List<Employee> employeesList = employeesRepository.getEmployees();

        return employeesList.stream()
                .filter(employee -> hasMatchingLanguageScore(employee, language, finalMinScore, finalMaxScore))
                .sorted(Comparator.comparing(employee -> findEmployeeLanguageScoreInRange(employee, language, finalMinScore, finalMaxScore)))
                .toList();
    }


    private boolean hasMatchingLanguageScore(Employee employee, String language, Integer minScore, Integer maxScore) {
        return employee.getKnownLanguages().stream()
                .anyMatch(knownLanguage -> matchesLanguageScore(knownLanguage, language, minScore, maxScore));
    }

    private boolean matchesLanguageScore(KnownLanguage knownLanguage, String language, Integer minScore, Integer maxScore) {
        return knownLanguage.getLanguageName().equalsIgnoreCase(language)
                && knownLanguage.getScoreOutOf100() >= minScore
                && knownLanguage.getScoreOutOf100() <= maxScore;
    }

    private Integer findEmployeeLanguageScoreInRange(Employee employee, String language, Integer minScore, Integer maxScore) {
        return employee.getKnownLanguages().stream()
                .filter(knownLanguage -> matchesLanguageScore(knownLanguage, language, minScore, maxScore))
                .findFirst()
                .map(KnownLanguage::getScoreOutOf100)
                .orElse(-1);
    }

}
