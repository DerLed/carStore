package com.example.demo8;

import java.util.Objects;

public enum TypeVehicle {
    BIKE("Мотоцикл"),
    CAR("Легковая"),
    TRUCK("Грузовой"),
    BUS("Автобус");
    private String type;
    TypeVehicle(String type){
        this.type = type;
    }
    public String getType() {return type;}

    public static TypeVehicle getTypeByName(String code){
        for(TypeVehicle e : TypeVehicle.values()){
            String у = e.getType();
            if(e.getType().equals(code))
                return e;
        }
        return null;
    }
}
