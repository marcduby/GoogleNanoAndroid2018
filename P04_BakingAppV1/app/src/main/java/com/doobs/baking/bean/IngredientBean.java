package com.doobs.baking.bean;

/**
 * Bean class to hold the ingredient for a recipe
 *
 * Created by mduby on 8/24/18.
 */

public class IngredientBean {
    // instance variable
    private String measurement;
    private String name;
    private Double amount;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
