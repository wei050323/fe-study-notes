package com.example.drinktracker.model;

import java.io.Serializable;

public class DrinkRecord implements Serializable {
    private String name;
    private String time;
    private int caffeine;
    private String sugarLevel;
    private String type; // "coffee" or "milk_tea"

    public DrinkRecord(String name, String time, int caffeine, String sugarLevel, String type) {
        this.name = name;
        this.time = time;
        this.caffeine = caffeine;
        this.sugarLevel = sugarLevel;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getCaffeine() {
        return caffeine;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public String getType() {
        return type;
    }
}
