package com.example.carStore;

import java.util.Objects;

public abstract class Vehicle {
    private int id = -1;
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

    public Vehicle(int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.registrationNumber = registrationNumber;
        this.year = year;
        this.hasTrailer = hasTrailer;
    }

    public Vehicle(){

    }

    public int getId() {return id;}

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

    public abstract String getTypeVehicle();

    public int getYear() {
        return year;
    }

    public boolean isHasTrailer() {
        return hasTrailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return year == vehicle.year && hasTrailer == vehicle.hasTrailer && Objects.equals(brand, vehicle.brand) && Objects.equals(model, vehicle.model) && Objects.equals(category, vehicle.category) && Objects.equals(registrationNumber, vehicle.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, category, registrationNumber, year, hasTrailer);
    }
}
