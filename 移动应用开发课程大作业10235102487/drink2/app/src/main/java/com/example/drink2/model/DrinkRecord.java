package com.example.drink2.model;

import java.util.ArrayList;
import java.util.List;

public class DrinkRecord {

    public enum DrinkType {
        MILK_TEA,
        COFFEE
    }

    public enum SugarLevel {
        FULL,
        HALF,
        NONE,
        CUSTOM
    }

    private final String drinkName;
    private final String brandName;
    private final DrinkType type;
    private final String sizeLabel;
    private final SugarLevel sugarLevel;
    private final int caffeineMg;
    private final int calorieKcal;
    private final int sugarGram;
    private final float price;
    private final String temperature;
    private final List<String> toppings;
    private final String note;
    private final long timestampMillis;

    public DrinkRecord(String drinkName,
                       String brandName,
                       DrinkType type,
                       String sizeLabel,
                       SugarLevel sugarLevel,
                       int caffeineMg,
                       int calorieKcal,
                       int sugarGram,
                       float price,
                       String temperature,
                       List<String> toppings,
                       String note,
                       long timestampMillis) {
        this.drinkName = drinkName;
        this.brandName = brandName;
        this.type = type;
        this.sizeLabel = sizeLabel;
        this.sugarLevel = sugarLevel;
        this.caffeineMg = caffeineMg;
        this.calorieKcal = calorieKcal;
        this.sugarGram = sugarGram;
        this.price = price;
        this.temperature = temperature;
        this.toppings = toppings != null ? new ArrayList<>(toppings) : new ArrayList<>();
        this.note = note;
        this.timestampMillis = timestampMillis;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getBrandName() {
        return brandName;
    }

    public DrinkType getType() {
        return type;
    }

    public String getSizeLabel() {
        return sizeLabel;
    }

    public SugarLevel getSugarLevel() {
        return sugarLevel;
    }

    public int getCaffeineMg() {
        return caffeineMg;
    }

    public int getCalorieKcal() {
        return calorieKcal;
    }

    public int getSugarGram() {
        return sugarGram;
    }

    public float getPrice() {
        return price;
    }

    public String getTemperature() {
        return temperature;
    }

    public List<String> getToppings() {
        return new ArrayList<>(toppings);
    }

    public String getNote() {
        return note;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }

    @Deprecated
    public String getName() {
        return drinkName;
    }

    @Deprecated
    public int getCalorie() {
        return calorieKcal;
    }
}

