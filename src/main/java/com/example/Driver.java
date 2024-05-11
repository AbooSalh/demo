package com.example;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver extends Employee {
    public Driver(String name, String id, String password) {
        super(name, id, "driver", password);
    }

    public void viewAssignedTrips() {
        boolean foundTrips = false;
        JSONArray tripsArray = JSONFileHandler.loadData("trips.json").getJSONArray("trips");
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripObj = tripsArray.getJSONObject(i);
            if (tripObj.has("assignedDriver")) {
                JSONObject assignedDriverObj = tripObj.getJSONObject("assignedDriver");
                String driverId = assignedDriverObj.getString("name");
                if (driverId.equals(this.getName())) {
                    foundTrips = true;
                    String tripId = tripObj.getString("id");
                    String source = tripObj.getString("source");
                    String destination = tripObj.getString("destination");
                    int numberOfStops = tripObj.getInt("numberOfStops");
                    int availableSeats = tripObj.getInt("availableSeats");
                    double price = tripObj.getDouble("price");
                    boolean oneWay = tripObj.getBoolean("oneWay");
    
                    // Get assigned vehicle information
                    JSONObject vehicleObj = tripObj.getJSONObject("vehicle");
                    String vehicleType = vehicleObj.getString("type");
                    int vehicleCapacity = vehicleObj.getInt("capacity");
                    String vehicleLicensePlate = vehicleObj.getString("licensePlate");
    
                    System.out.println("Trip ID: " + tripId);
                    System.out.println("Source: " + source);
                    System.out.println("Destination: " + destination);
                    System.out.println("Number of Stops: " + numberOfStops);
                    System.out.println("Available Seats: " + availableSeats);
                    System.out.println("Price: " + price);
                    System.out.println("One Way: " + oneWay);
                    System.out.println("Assigned Vehicle:");
                    System.out.println("Type: " + vehicleType);
                    System.out.println("Capacity: " + vehicleCapacity);
                    System.out.println("License Plate: " + vehicleLicensePlate);
                }
            }
        }
        if (!foundTrips) {
            System.out.println("No assigned trips.");
        }
    }
    
}
