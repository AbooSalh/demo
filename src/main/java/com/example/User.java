package com.example;

import org.json.JSONObject;

public class User {
    private String name;
    private String id;
    private String password;
    private String type; // "Passenger", "Driver", "Manager"

    public User(String name, String id, String type , String password) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.password = password;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        jsonObject.put("id", this.id);
        jsonObject.put("type", this.type);
        jsonObject.put("password", this.password);
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
