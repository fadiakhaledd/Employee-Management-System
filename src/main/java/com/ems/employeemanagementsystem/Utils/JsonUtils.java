package com.ems.employeemanagementsystem.Utils;

import com.ems.employeemanagementsystem.models.Employee;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
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
        List<Employee> employees = new ArrayList<>();

        try {
            String jsonString = new String(Files.readAllBytes(path));
            JSONArray employeesJsonArray = new JSONArray(jsonString);

            for (int i = 0; i < employeesJsonArray.length(); i++) {
                JSONObject employeeJsonObject = employeesJsonArray.getJSONObject(i);
                Employee employee = parsingUtils.parseJsonObjectToEmployee(employeeJsonObject);
                employees.add(employee);
            }

        } catch (IOException exception) {
            throw new IOException(exception);
        }
        return employees;
    }

    public void addEmployeeToJsonFile(Employee employee) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(JSON_FILE));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonString.toString());

            jsonArray.put(parsingUtils.parseEmployeeToJsonObject(employee));

            writer = new BufferedWriter(new FileWriter(JSON_FILE));
            writer.write(jsonArray.toString(2));

        } catch (IOException exception) {
            throw new IOException(exception);
        } finally {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        }
    }
}