package com.example.essexcaremporium;

public class Car {

    // Car attributes
    private String make;
    private String model;
    private int year;
    private String vin;
    private String fuelType;
    private double price;
    private int mpg; // -1 if it's an EV (MPG not applicable)
    private String imageUrl;

    // Constants for tax credit calculations
    public static final int EV_CREDIT = 7500;
    public static final int PLUG_IN_HYBRID_CREDIT = 3500;
    public static final int HYBRID_CREDIT = 2500;
    public static final double GAS_CREDIT_PERCENT = 0.02;
    public static final int MPG_THRESHOLD = 35;

    // Constructor
    public Car(String make, String model, int year, String vin, String fuelType, double price, int mpg, String imageUrl) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.fuelType = fuelType;
        this.price = price;
        this.mpg = mpg;
        this.imageUrl = imageUrl;
    }

    // Getters for the Car attributes
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getVin() { return vin; }
    public String getFuelType() { return fuelType; }
    public double getPrice() { return price; }
    public int getMpg() { return mpg; }
    public String getImageUrl() { return imageUrl; }

    // Setters for the Car attributes
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setVin(String vin) { this.vin = vin; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setPrice(double price) { this.price = price; }
    public void setMpg(int mpg) { this.mpg = mpg; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Method to calculate the tax credit based on fuel type and MPG
    public double getTaxCredit() {
        switch (fuelType.toLowerCase()) {
            case "ev":
                return EV_CREDIT;
            case "plug-in-hybrid":
                return PLUG_IN_HYBRID_CREDIT;
            case "hybrid":
                return HYBRID_CREDIT;
            case "gas":
                return (mpg >= MPG_THRESHOLD) ? price * GAS_CREDIT_PERCENT : 0;
            default:
                return 0;
        }
    }

    // Override toString() to provide a string representation of the car
    @Override
    public String toString() {
        return make + ", " + model + ", " + vin + ", " + fuelType + ", " + price;
    }
}