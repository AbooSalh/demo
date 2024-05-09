package com.example;

import org.json.JSONObject;

class Trip {
    private String id;
    private String type; // "Internal", "External"
    private String source;
    private String destination;
    private boolean oneWay;
    private int numberOfStops;
    private int availableSeats;
    private double price;
    private Driver assignedDriver;
    private Vehicle vehicle;

    public Trip(String id,String type, String source, String destination, boolean oneWay, int numberOfStops, int availableSeats,
            double price, Driver d1, Vehicle vehicle) {
       this.id=id;
                this.type = type;
        this.source = source;
        this.destination = destination;
        this.oneWay = oneWay;
        this.numberOfStops = numberOfStops;
        this.availableSeats = availableSeats;
        this.price = price;
        this.assignedDriver = d1;
        this.vehicle = vehicle;
    }

    public Trip(String type, String source, String destination, boolean oneWay, int numberOfStops, int availableSeats,
            double price, Driver d1) {
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.oneWay = oneWay;
        this.numberOfStops = numberOfStops;
        this.availableSeats = availableSeats;
        this.price = price;
        this.assignedDriver = d1;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
       jsonObject.put("id", this.id);
        jsonObject.put("type", this.type);
        jsonObject.put("source", this.source);
        jsonObject.put("destination", this.destination);
        jsonObject.put("oneWay", this.oneWay);
        jsonObject.put("numberOfStops", this.numberOfStops);
        jsonObject.put("availableSeats", this.availableSeats);
        jsonObject.put("price", this.price);
        jsonObject.put("assignedDriver", this.assignedDriver.toJSON());
        if(this.vehicle !=null){
            jsonObject.put("vehicle", this.vehicle.toJSON());
        }else {
            jsonObject.put("vehicle", "");
        }
        return jsonObject;
    }

  
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(Driver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}