package com.example.demo8;

public class VehicleFactory {
    public Vehicle createVehicle(TypeVehicle typeVehicle, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer){
        return switch (typeVehicle) {
            case CAR -> new Car(brand, model, category, registrationNumber, year, hasTrailer);
            case TRUCK -> new Truck(brand, model, category, registrationNumber, year, hasTrailer);
            default -> throw new IllegalStateException("Unexpected value: " + typeVehicle);
        };
    }
}
