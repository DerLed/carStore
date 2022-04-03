package com.example.carStore;

public class Bike extends Vehicle{
    private final String typeVehicle = TypeVehicle.BIKE.getType();
    public Bike(int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        super(id, brand, model, category, registrationNumber, year, hasTrailer);
    }
    public Bike(){

    }

    @Override
    public String getTypeVehicle() {
        return typeVehicle;
    }

}
