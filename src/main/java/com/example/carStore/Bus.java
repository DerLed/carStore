package com.example.carStore;

public class Bus extends Vehicle{
    private final String typeVehicle = TypeVehicle.BUS.getType();
    public Bus(int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        super(id, brand, model, category, registrationNumber, year, hasTrailer);
    }
    public Bus(){
    }

    @Override
    public String getTypeVehicle() {
        return typeVehicle;
    }

}
