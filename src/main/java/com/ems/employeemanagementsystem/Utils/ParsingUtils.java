package com.ems.employeemanagementsystem.Utils;

import com.ems.employeemanagementsystem.models.Designation;
import com.ems.employeemanagementsystem.models.Employee;
import com.ems.employeemanagementsystem.models.KnownLanguage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {
    public Employee parseJsonObjectToEmployee(JSONObject jsonEmployee) {
        String firstName = jsonEmployee.getString("FirstName");
        String lastName = jsonEmployee.getString("LastName");
        int employeeId = jsonEmployee.getInt("EmployeeID");

        String designationStr = jsonEmployee.getString("Designation");
        Designation designation = parseDesignation(designationStr);

        JSONArray knownLanguagesJsonArray = jsonEmployee.getJSONArray("KnownLanguages");
        List<KnownLanguage> knownLanguages = parseKnownLanguagesList(knownLanguagesJsonArray);

        return new Employee(firstName, lastName, employeeId, designation, knownLanguages);
    }

    public JSONObject parseEmployeeToJsonObject(Employee employee) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FirstName", employee.getFirstname());
        jsonObject.put("LastName", employee.getLastname());
        jsonObject.put("EmployeeID", employee.getEmployeeId());
        jsonObject.put("Designation", employee.getDesignation().toString().toUpperCase());

        List<KnownLanguage> knownLanguages = employee.getKnownLanguages();
        JSONArray knownLanguagesArray = new JSONArray();
        for (KnownLanguage knownLanguage : knownLanguages) {
            JSONObject languageJson = new JSONObject();
            languageJson.put("LanguageName", knownLanguage.getLanguageName());
            languageJson.put("ScoreOutOf100", knownLanguage.getScoreOutOf100());
            knownLanguagesArray.put(languageJson);
        }
        jsonObject.put("KnownLanguages", knownLanguagesArray);
        return jsonObject;
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
