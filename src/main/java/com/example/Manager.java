package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;

import java.util.ArrayList;

public class Manager extends Employee {
    private Driver assignedDriver;
    private Vehicle vehicle; // Include vehicle information
    private Trip newTrip;

    public Manager(String name, String id, String password) {
        super(name, id, "manager", password);
    }

    public void TripAdder(Driver assignedDriver, Vehicle vehicle, Trip newTrip) {
        this.assignedDriver = assignedDriver;
        this.newTrip = newTrip;
        this.vehicle = vehicle; // Initialize vehicle
    }

    public void addTrip(Driver assignedDriver, Vehicle vehicle, Trip newTrip) {
        // Read existing trips from file
        JSONArray trips = readTripsFromFile();

        // Create a JSON object for the new trip
        JSONObject tripObj = new JSONObject();
        tripObj.put("id", newTrip.getId());
        tripObj.put("type", newTrip.getType());
        tripObj.put("source", newTrip.getSource());
        tripObj.put("destination", newTrip.getDestination());
        tripObj.put("oneWay", newTrip.isOneWay());
        tripObj.put("numberOfStops", newTrip.getNumberOfStops());
        tripObj.put("availableSeats", newTrip.getAvailableSeats());
        tripObj.put("price", newTrip.getPrice());
        tripObj.put("assignedDriver", newTrip.getAssignedDriver().toJSON());
        if (newTrip.getVehicle() != null) {
            tripObj.put("vehicle", newTrip.getVehicle().toJSON());
        } else {
            tripObj.put("vehicle", "");
        }

        // Add the new trip to the existing trips
        trips.put(tripObj);

        // Write the updated trips back to the file
        writeTripsToFile(trips);
    }

    // Method to read trips from JSON file
    private JSONArray readTripsFromFile() {
        JSONArray trips = new JSONArray();
        try (FileReader fileReader = new FileReader("trips.json")) {
            int data;
            StringBuilder jsonString = new StringBuilder();
            while ((data = fileReader.read()) != -1) {
                jsonString.append((char) data);
            }
            trips = new JSONArray(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trips;
    }

    // Method to write trips to JSON file
    private void writeTripsToFile(JSONArray trips) {
        try (FileWriter fileWriter = new FileWriter("trips.json")) {
            fileWriter.write(trips.toString());
            System.out.println("Trips data has been updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a trip by its ID
    public void deleteTrip(String tripId) {
        JSONArray trips = readTripsFromFile(); // Read existing trips from file
        JSONArray updatedTrips = new JSONArray();

        for (int i = 0; i < trips.length(); i++) {
            JSONObject tripObj = trips.getJSONObject(i);
            if (!tripObj.getString("id").equals(tripId)) {
                // Add the trip to updatedTrips if its ID doesn't match the given tripId
                updatedTrips.put(tripObj);
            }
        }

        writeTripsToFile(updatedTrips); // Write the updated trips back to the file
    }

    public void addDriver(Driver driver) {
        JSONObject parernt = new JSONObject();
        JSONArray drivers = readDriversFromFile(); // Read existing drivers from file
        JSONObject driverObj = new JSONObject();
        driverObj.put("id", driver.getId());
        driverObj.put("name", driver.getName());
        driverObj.put("password", driver.getPassword());
        drivers.put(driverObj); // Add the new driver to the array
        parernt.put("drivers", drivers);

        writeDriversToFile(drivers); // Write the updated drivers back to the file
    }

    // Method to read drivers from JSON file
    private JSONArray readDriversFromFile() {
        JSONObject obj = JSONFileHandler.loadData("drivers.json");
        return obj.getJSONArray("drivers");
    }

    // Method to write drivers to JSON file
    private void writeDriversToFile(JSONArray drivers) {
        try (FileWriter fileWriter = new FileWriter("drivers.json")) {
            fileWriter.write(drivers.toString());
            System.out.println("Drivers data has been updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportDrivers() {
        System.out.println("Drivers");
        JSONFileHandler handler = new JSONFileHandler();
        JSONObject data = handler.loadData("drivers.json");
        System.out.println(data.toString());
        System.out.println(data.getJSONArray("drivers"));
    }

}
