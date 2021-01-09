package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.enums.DishTypes;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish extends Entity {
    private final String name;
    private final DishTypes type;
    private final BigDecimal cost;
    private final String imgUrl;



    public Dish(Long id, String name, DishTypes type, BigDecimal cost,String imgUrl) {
        super(id);
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public DishTypes getType() {
        return type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Dish dish = (Dish) o;
        return Objects.equals(name, dish.name) &&
                type == dish.type &&
                Objects.equals(cost, dish.cost) &&
                Objects.equals(imgUrl, dish.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, cost, imgUrl);
    }

    @Override
    public String toString() {
        return String.format("Dish id: %d, name: %s, type: %s, cost: %f", getId(), name, type.toString(), cost);
    }
}
