package com.example.carStore;

public class Truck extends Vehicle implements Calculate{
    private final String typeVehicle = TypeVehicle.TRUCK.getType();
    public Truck(int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer) {
        super(id, brand, model, category, registrationNumber, year, hasTrailer);
    }
    public Truck(){

    }

    @Override
    public String getTypeVehicle() {
        return typeVehicle;
    }

    @Override
    public String getFullName() {
        return this.getModel().concat(" ").concat(this.getBrand());
    }
}
