package com.example;

import org.json.JSONObject;

// Vehicle class representing vehicles
class Vehicle {
    private String type; // "Bus", "Minibus", "Limousine"
    private int capacity;
    private String licensePlate;

    public Vehicle(String type, int capacity, String licensePlate) {
        this.type = type;
        this.capacity = capacity;
        this.licensePlate = licensePlate;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("capacity", this.capacity);
        jsonObject.put("licensePlate", this.licensePlate);
        return jsonObject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}