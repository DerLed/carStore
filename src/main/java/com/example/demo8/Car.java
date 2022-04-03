package com.example.demo8;

public class Car extends Vehicle{
    private final String typeVehicle = TypeVehicle.CAR.getType();
    public Car(int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        super(id, brand, model, category, registrationNumber, year, hasTrailer);
    }
    public Car(){

    }

    @Override
    public String getTypeVehicle() {
        return typeVehicle;
    }

}
