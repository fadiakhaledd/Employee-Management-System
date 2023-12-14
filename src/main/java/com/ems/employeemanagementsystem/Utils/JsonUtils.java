package com.ems.employeemanagementsystem.Utils;

import com.ems.employeemanagementsystem.models.Employee;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String JSON_FILE = "Employees.json";
    Path path = Paths.get(JSON_FILE);


    ParsingUtils parsingUtils = new ParsingUtils();

    public List<Employee> readEmployeesFromJsonFile() throws IOException {
        String jsonString = new String(Files.readAllBytes(path));
        JSONArray jsonArray = new JSONArray(jsonString);
        return convertJsonArrayToEmployee(jsonArray);
    }

    public List<Employee> convertJsonArrayToEmployee(JSONArray jsonArray) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject employeeJsonObject = jsonArray.getJSONObject(i);
            Employee employee = parsingUtils.parseJsonObjectToEmployee(employeeJsonObject);
            employees.add(employee);
        }
        return employees;
    }

    public void writeEmployeesToJsonFile(List<Employee> employeeList) throws IOException {
        JSONArray jsonArray = convertEmployeesListToJsonArray(employeeList);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(JSON_FILE));
            writer.write(jsonArray.toString(2));

        } catch (IOException exception) {
            throw new IOException(exception);
        } finally {
            if (writer != null) writer.close();
        }
    }

    public JSONArray convertEmployeesListToJsonArray(List<Employee> employeeList) {
        JSONArray jsonArray = new JSONArray();
        for (Employee employee : employeeList) {
            jsonArray.put(parsingUtils.parseEmployeeToJsonObject(employee));
        }
        return jsonArray;
    }
}