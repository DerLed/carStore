package com.example.demo8;

import java.util.ArrayList;
import java.util.List;

public class CarStore {
    private List<Vehicle> vehicleList = new ArrayList<>();

    CarStore(List<Vehicle> vehicleList){
        this.vehicleList = new ArrayList<>(vehicleList);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void addVehicle(Vehicle vehicle){
        this.vehicleList.add(vehicle);
    }
}
