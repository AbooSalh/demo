package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmployeeAdder {

    private static final String JSON_FILE_PATH = "users.json";
    private JSONObject employeeObject;

    public EmployeeAdder(JSONObject employeeObject) {
        this.employeeObject = employeeObject;
    }

    public void addEmployee() {
        // Load JSON data from file
        JSONObject jsonObject = JSONFileHandler.loadData(JSON_FILE_PATH);

        if (jsonObject != null) {
            // Get the users array
            JSONArray usersArray = jsonObject.getJSONArray("users");

            // Add the new employee to the users array
            usersArray.put(employeeObject);

            // Save the updated JSON object back to the file
            JSONFileHandler.saveData(jsonObject, JSON_FILE_PATH);

            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Failed to load JSON data from file.");
        }
    }
}
