package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

class Passenger extends User {
    private Trip trip;

    public Passenger(String name, String id, String password) {
        super(name, id, "passenger", password);
    }
    public void savePassengerToJson() {
        JSONObject passengerObj = new JSONObject();
        passengerObj.put("name", getName());
        passengerObj.put("id", getId());
        passengerObj.put("type", getType());
        passengerObj.put("password", getPassword());

        JSONArray passengersArray = new JSONArray();
        passengersArray.put(passengerObj);

        JSONObject data = new JSONObject();
        data.put("passengers", passengersArray);

        try (FileWriter file = new FileWriter("passenger.json")) {
            file.write(data.toString());
            System.out.println("Passenger data saved successfully to " + "passenger.json");
        } catch (IOException e) {
            System.out.println("An error occurred while saving passenger data: " + e.getMessage());
        }
    }
  
    public void viewAvailableTrips() {
        JSONObject tripsData = JSONFileHandler.loadData("trips.json");
   
        if (tripsData.length() == 0) {
            System.out.println("Currently, there are no available trips. Stay tuned for exciting journeys ahead!");
            return;
        }
   
        JSONArray tripsArray = tripsData.getJSONArray("trips");
        System.out.println("Available Trips:");
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripObj = tripsArray.getJSONObject(i);
            String tripId = tripObj.getString("id");
            String source = tripObj.getString("source");
            String destination = tripObj.getString("destination");
            int numberOfStops = tripObj.getInt("numberOfStops");
            int availableSeats = tripObj.getInt("availableSeats");
            double price = tripObj.getDouble("price");
            boolean oneWay = tripObj.getBoolean("oneWay");
   
            System.out.println("Trip ID: " + tripId);
            System.out.println("Source: " + source);
            System.out.println("Destination: " + destination);
            System.out.println("Number of Stops: " + numberOfStops);
            System.out.println("Available Seats: " + availableSeats);
            System.out.println("Price: " + price);
            System.out.println("One Way: " + oneWay);
        }
   
        // Allow the passenger to choose a trip
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the trip you want to choose: ");
        String chosenTripId = scanner.nextLine();
   
        // Find the chosen trip in the trips array
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripObj = tripsArray.getJSONObject(i);
            if (tripObj.getString("id").equals(chosenTripId)) {
                // Decrease the available seats for the chosen trip
                int availableSeats = tripObj.getInt("availableSeats");
                if (availableSeats > 0) {
                    tripObj.put("availableSeats", availableSeats - 1);
                    System.out.println("Trip chosen successfully!");
                } else {
                    System.out.println("No available seats for this trip.");
                }
                break;
            }
        }
   
        // Save the updated trips data back to the JSON file
        JSONFileHandler.saveData(tripsData, "trips.json");
    }
    public void viewBookedTrips() {
        JSONObject userData = JSONFileHandler.loadData("users.json");
        if (userData.has("bookedTrips")) {
            JSONArray bookedTripsArray = userData.getJSONArray("bookedTrips");
   
            if (bookedTripsArray.length() == 0) {
                System.out.println("You have no booked trips.");
                return;
            }
   
            System.out.println("Your Booked Trips:");
            for (int i = 0; i < bookedTripsArray.length(); i++) {
                JSONObject tripObj = bookedTripsArray.getJSONObject(i);
                String tripId = tripObj.getString("id");
                String source = tripObj.getString("source");
                String destination = tripObj.getString("destination");
                double price = tripObj.getDouble("price");
                boolean oneWay = tripObj.getBoolean("oneWay");
   
                System.out.println("Trip ID: " + tripId);
                System.out.println("Source: " + source);
                System.out.println("Destination: " + destination);
                System.out.println("Price: " + price);
                System.out.println("One Way: " + oneWay);
            }
   
            // Allow the user to cancel a booking
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ID of the trip you want to cancel, or type 'no' to keep your bookings: ");
            String input = scanner.nextLine();
   
            if (!input.equalsIgnoreCase("no")) {
                // Remove the chosen trip from the booked trips array
                for (int i = 0; i < bookedTripsArray.length(); i++) {
                    JSONObject tripObj = bookedTripsArray.getJSONObject(i);
                    if (tripObj.getString("id").equals(input)) {
                        bookedTripsArray.remove(i);
                        System.out.println("Trip canceled successfully.");
   
                        // Increment available seats for the canceled trip
                        JSONObject tripsData = JSONFileHandler.loadData("trips.json");
                        JSONArray tripsArray = tripsData.getJSONArray("trips");
                        for (int j = 0; j < tripsArray.length(); j++) {
                            JSONObject trip = tripsArray.getJSONObject(j);
                            if (trip.getString("id").equals(input)) {
                                int availableSeats = trip.getInt("availableSeats");
                                trip.put("availableSeats", availableSeats + 1);
                                JSONFileHandler.saveData(tripsData, "trips.json");
                                break;
                            }
                        }
   
                        break;
                    }
                }
   
                // Update the JSON data with the modified booked trips array
                userData.put("bookedTrips", bookedTripsArray);
                JSONFileHandler.saveData(userData, "users.json");
   
                // Ask if the user wants to book another trip
                System.out.print("Do you wish to book a different trip? (yes/no): ");
                String bookAnother = scanner.nextLine();
                if (bookAnother.equalsIgnoreCase("yes")) {
                    viewAvailableTrips(); // Run the method again to choose a different trip
                }
            } else {
                System.out.println("Enjoy your travel!");
            }
   
        } else {
            System.out.println("You have no booked trips.");
        }
    }
    
    }
    

  

