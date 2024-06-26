package com.example;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONObject tripsData = JSONFileHandler.loadData("trips.json");
        JSONArray trips = tripsData.getJSONArray("trips");

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
        tripsData.put("trips", trips);
        JSONFileHandler.saveData(tripsData, "trips.json");
    }

    public void deleteTrip(String tripId) {
        JSONArray trips = JSONFileHandler.loadData("trips.json").getJSONArray("trips");
        // Read existing trips from file

        if (trips.length() == 0) {
            System.out.println("No trips found.");
            return;
        }

        JSONArray updatedTrips = new JSONArray();

        for (int i = 0; i < trips.length(); i++) {
            JSONObject tripObj = trips.getJSONObject(i);
            if (!tripObj.getString("id").equals(tripId)) {
                // Add the trip to updatedTrips if its ID doesn't match the given tripId
                updatedTrips.put(tripObj);
            }
        }

        // Convert the JSONArray to a JSONObject
        JSONObject data = new JSONObject();
        data.put("trips", updatedTrips);

        // Save the updated trips back to the file
        JSONFileHandler.saveData(data, "trips.json");
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
        System.out.println(parernt.toString());
        JSONFileHandler.saveData(parernt, "drivers.json");
        JSONObject parernt2 = new JSONObject();
        JSONArray drivers2 = JSONFileHandler.loadData("users.json").getJSONArray("users"); // Read existing drivers from
                                                                                           // file
        JSONObject driverObj2 = new JSONObject();
        driverObj2.put("id", driver.getId());
        driverObj2.put("name", driver.getName());
        driverObj2.put("password", driver.getPassword());
        driverObj2.put("type", "driver");
        drivers2.put(driverObj2); // Add the new driver to the array
        parernt2.put("users", drivers2);
        JSONFileHandler.saveData(parernt2, "users.json");
    }

    public void addVehicle(Vehicle vehicle) {
        JSONObject parernt = new JSONObject();
        JSONArray vehicles = JSONFileHandler.loadData("vehicles.json").getJSONArray("vehicles");
        JSONObject vehicleObj = new JSONObject();
        vehicleObj.put("id", Main.generateID());
        vehicleObj.put("type", vehicle.getType());
        vehicleObj.put("capacity", vehicle.getCapacity());
        vehicleObj.put("licensePlate", vehicle.getLicensePlate());
        vehicles.put(vehicleObj); // Add the new vehicle to the array
        parernt.put("vehicles", vehicles);
        JSONFileHandler.saveData(parernt, "vehicles.json");
    }

    // Method to read drivers from JSON file
    private JSONArray readDriversFromFile() {
        JSONObject obj = JSONFileHandler.loadData("drivers.json");
        return obj.getJSONArray("drivers");
    }

    public void reportDrivers() {
        System.out.println("Drivers");
        System.out.println(JSONFileHandler.loadData("drivers.json").getJSONArray("drivers").toString());
        System.out.println("Trips");
        System.out.println(JSONFileHandler.loadData("trips.json"));
        System.out.println("Vehicles");
        System.out.println(JSONFileHandler.loadData("vehicles.json"));
    }

}
