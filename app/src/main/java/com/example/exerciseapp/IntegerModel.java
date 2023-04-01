package com.example.exerciseapp;

public class IntegerModel {

    private int id;
    private int firstValue;
    private int secondValue;
    private int thirdValue;
    private int forthValue;

    public IntegerModel() {
//        Empty constructor;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue, int forthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
    }

    @Override
    public String toString() {
        return "IntegerModel{" +
                "id=" + id +
                ", firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                ", thirdValue=" + thirdValue +
                ", forthValue=" + forthValue +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(int firstValue) {
        this.firstValue = firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }

    public int getThirdValue() {
        return thirdValue;
    }

    public void setThirdValue(int thirdValue) {
        this.thirdValue = thirdValue;
    }

    public int getForthValue() {
        return forthValue;
    }

    public void setForthValue(int forthValue) {
        this.forthValue = forthValue;
    }
}
