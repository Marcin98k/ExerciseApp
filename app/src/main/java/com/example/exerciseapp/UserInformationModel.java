package com.example.exerciseapp;

public class UserInformationModel {

    private int id;
    private String name;
    private int gender;
    private int height;
    private int unitHeight;
    private int weight;
    private int unitWeight;
    private int level;

    public UserInformationModel() {
//        Empty constructor;
    }

    public UserInformationModel(int id,String name, int gender,
                                int height, int unitHeight,
                                int weight, int unitWeight,
                                int level) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.unitHeight = unitHeight;
        this.weight = weight;
        this.unitWeight = unitWeight;
        this.level = level;
    }

    @Override
    public String toString() {
        return "UserInformationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", unitHeight=" + unitHeight +
                ", weight=" + weight +
                ", unitWeight=" + unitWeight +
                ", level=" + level +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getUnitHeight() {
        return unitHeight;
    }

    public void setUnitHeight(int unitHeight) {
        this.unitHeight = unitHeight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(int unitWeight) {
        this.unitWeight = unitWeight;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
