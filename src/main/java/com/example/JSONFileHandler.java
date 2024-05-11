package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class JSONFileHandler {
    public static JSONObject loadData(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            StringBuilder jsonData = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonData.append((char) character);
            }
            return new JSONObject(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveData(JSONObject data, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}