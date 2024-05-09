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
    // private static JSONArray readJsonArrayFromFile(String filename) throws IOException {
    //     try (FileReader fileReader = new FileReader(filename)) {
    //         int data;
    //         StringBuilder jsonString = new StringBuilder();
    //         while ((data = fileReader.read()) != -1) {
    //             jsonString.append((char) data);
    //         }
    //         JSONObject jsonObject = new JSONObject(jsonString.toString());
    //         if (jsonObject.has("trips")) {
    //             return jsonObject.getJSONArray("trips");
    //         } else if (jsonObject.has("drivers")) {
    //             return jsonObject.getJSONArray("drivers");
    //         }
    //         return new JSONArray(); // Return empty array if neither "trips" nor "drivers" are found
    //     }
    // }
    

}