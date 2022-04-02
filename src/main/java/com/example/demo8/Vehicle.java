package com.example.demo8;

public abstract class Vehicle {
    private String brand;
    private String model;
    private String category;
    private String registrationNumber;
    private String typeVehicle;
    private int year;
    private boolean hasTrailer;

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", typeVehicle='" + typeVehicle + '\'' +
                ", year=" + year +
                ", hasTrailer=" + hasTrailer +
                '}';
    }

    public Vehicle(String brand, String model, String category, String registrationNumber, String typeVehicle, int year, boolean hasTrailer) {
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.registrationNumber = registrationNumber;
        this.typeVehicle = typeVehicle;
        this.year = year;
        this.hasTrailer = hasTrailer;
    }

    public Vehicle(){

    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public int getYear() {
        return year;
    }

    public boolean isHasTrailer() {
        return hasTrailer;
    }
}
