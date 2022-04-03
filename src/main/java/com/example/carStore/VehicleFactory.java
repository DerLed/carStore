package com.example.carStore;

/**
 * Класс для фабрики объектов
 * возвращает экземпляр соовтетсвующего класса исходя из категории ТС
 */
public class VehicleFactory {
    public Vehicle createVehicle(TypeVehicle typeVehicle,int id, String brand, String model, String category, String registrationNumber, int year, boolean hasTrailer){
        return switch (typeVehicle) {
            case CAR -> new Car(id,brand, model, category, registrationNumber, year, hasTrailer);
            case TRUCK -> new Truck(id, brand, model, category, registrationNumber, year, hasTrailer);
            case BIKE -> new Bike(id, brand, model, category, registrationNumber, year, hasTrailer);
            case BUS -> new Bus(id, brand, model, category, registrationNumber, year, hasTrailer);
            default -> throw new IllegalStateException("Unexpected value: " + typeVehicle);
        };
    }
}
