package com.example.exerciseapp.mModels;

public class StringModel {

    private int id;
    private String name;
    private int intValue;

    public StringModel() {
    }

    public StringModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StringModel(int id, String name, int intValue) {
        this.id = id;
        this.name = name;
        this.intValue = intValue;
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

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}
