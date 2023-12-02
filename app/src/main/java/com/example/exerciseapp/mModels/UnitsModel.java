package com.example.exerciseapp.mModels;

public class UnitsModel {

    private long id, userId;
    private int height;
    private int weight;
    private int height_unit;
    private int weight_unit;

    public UnitsModel() {}

    public UnitsModel(long id, int height, int weight, int height_unit, int weight_unit) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.height_unit = height_unit;
        this.weight_unit = weight_unit;
    }

    public UnitsModel setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight_unit() {
        return height_unit;
    }

    public int getWeight_unit() {
        return weight_unit;
    }
}
