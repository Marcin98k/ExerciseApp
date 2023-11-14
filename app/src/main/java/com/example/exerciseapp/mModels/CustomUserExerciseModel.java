package com.example.exerciseapp.mModels;

public class CustomUserExerciseModel {

    private long id;
    private long userId;
    private String name;
    private int type;
    private int exerciseID;
    private int exerciseExtensionID;

    public CustomUserExerciseModel(long id, long userId, String name, int type, int exerciseID,
                                   int exerciseExtensionID) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.exerciseID = exerciseID;
        this.exerciseExtensionID = exerciseExtensionID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
