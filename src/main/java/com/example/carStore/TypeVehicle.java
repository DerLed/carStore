package com.example.carStore;

/**
 * Перечисление для хранения катерорий ТС
 *
 */
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

    /**
     * Метод принимает строковое отображение категории и возвращает
     * соотвествующее поле перечисления
     */
    public static TypeVehicle getTypeByName(String code){
        for(TypeVehicle e : TypeVehicle.values()){
            if(e.getType().equals(code))
                return e;
        }
        return null;
    }
}
