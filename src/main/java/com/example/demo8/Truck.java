package com.example.demo8;

public class Truck extends Vehicle{
    private final String typeVehicle = TypeVehicle.TRUCK.getType();
    public Truck(String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        super(brand, model, category, registrationNumber, year, hasTrailer);
    }
    public Truck(){

    }

    @Override
    public String getTypeVehicle() {
        return typeVehicle;
    }
}
