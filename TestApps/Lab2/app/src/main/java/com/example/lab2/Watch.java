package com.example.lab2;

public class Watch {
    public String make;
    public String model;
    public String powerSource;
    public int waterResistance;
    public String bandContent;
    public double price;

    // Constructor
    public Watch(String make, String model, String powerSource, int waterResistance, String bandContent, double price)
    {
        this.make = make;
        this.model = model;
        this.powerSource = powerSource;
        this.waterResistance = waterResistance;
        this.bandContent = bandContent;
        this.price = price;
    }

    // Getters and Setters for all fields
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPowerSource() {
        return this.powerSource;
    }

    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }

    public int getWaterResistance() {
        return this.waterResistance;
    }

    public void setWaterResistance(int waterResistance) {
        this.waterResistance = waterResistance;
    }

    public String getBandContent() {
        return this.bandContent;
    }

    public void setBandContent(String bandContent) {
        this.bandContent = bandContent;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
