package com.example.exerciseapp.mModels;

public class CustomUserExerciseModel {

    private int id;
    private String name;
    private int exerciseID;
    private int exerciseExtensionID;

    public CustomUserExerciseModel(int id, String name, int exerciseID, int exerciseExtensionID) {
        this.id = id;
        this.name = name;
        this.exerciseID = exerciseID;
        this.exerciseExtensionID = exerciseExtensionID;
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

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getExerciseExtensionID() {
        return exerciseExtensionID;
    }

    public void setExerciseExtensionID(int exerciseExtensionID) {
        this.exerciseExtensionID = exerciseExtensionID;
    }
}
