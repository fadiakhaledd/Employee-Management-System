package com.ems.employeemanagementsystem.Utils;

import com.ems.employeemanagementsystem.models.Designation;
import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.models.KnownLanguage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String JSON_FILE = "Employees.json";

    public List<Employee> readJsonFile() {
        List<Employee> employees = new ArrayList<>();

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
            JSONArray employeesJsonArray = new JSONArray(jsonString);

            for (int i = 0; i < employeesJsonArray.length(); i++) {
                JSONObject employeeJsonObject = employeesJsonArray.getJSONObject(i);
                Employee employee = parseEmployeeObject(employeeJsonObject);
                employees.add(employee);
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return employees;
    }

    private Employee parseEmployeeObject(JSONObject jsonEmployee) {
        String firstName = jsonEmployee.getString("FirstName");
        String lastName = jsonEmployee.getString("LastName");
        int employeeId = jsonEmployee.getInt("EmployeeID");

        String designationStr = jsonEmployee.getString("Designation");
        Designation designation = parseDesignation(designationStr);

        JSONArray knownLanguagesJsonArray = jsonEmployee.getJSONArray("KnownLanguages");
        List<KnownLanguage> knownLanguages = parseKnownLanguagesList(knownLanguagesJsonArray);

        return new Employee(firstName, lastName, employeeId, designation, knownLanguages);
    }

    private Designation parseDesignation(String designationStr) {
        switch (designationStr.toUpperCase()) {
            case "DEVELOPER":
                return Designation.DEVELOPER;
            case "MANAGER":
                return Designation.MANAGER;
            case "TEAM_LEADER":
                return Designation.TEAM_LEADER;
            default:
                throw new IllegalArgumentException("Invalid designation: " + designationStr +
                        ". Please provide one of the following designations: DEVELOPER, MANAGER, TEAM_LEADER");
        }
    }

    private List<KnownLanguage> parseKnownLanguagesList(JSONArray knownLanguagesJsonArray) {
        List<KnownLanguage> knownLanguages = new ArrayList<>();
        for (int i = 0; i < knownLanguagesJsonArray.length(); i++) {
            JSONObject knownLanguageJsonObject = knownLanguagesJsonArray.getJSONObject(i);
            KnownLanguage knownLanguage = parseKnownLanguageObject(knownLanguageJsonObject);
            knownLanguages.add(knownLanguage);
        }
        return knownLanguages;
    }
    private KnownLanguage parseKnownLanguageObject(JSONObject knownLanguageJsonObject) {
        String languageName = knownLanguageJsonObject.getString("LanguageName");
        int scoreOutOf100 = knownLanguageJsonObject.getInt("ScoreOutOf100");
        return new KnownLanguage(languageName, scoreOutOf100);
    }
}
