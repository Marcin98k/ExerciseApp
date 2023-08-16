package com.example.exerciseapp.mModels;

public class CustomExerciseModel {

    private long id;
    private String name;
    private int type;
    private int userId;
    private int mainExerciseId;
    private int exerciseExtensionId;

    public CustomExerciseModel(long id, String name, int type, int userId, int mainExerciseId,
                               int exerciseExtensionId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.mainExerciseId = mainExerciseId;
        this.exerciseExtensionId = exerciseExtensionId;
    }

    @Override
    public String toString() {
        return "CustomExerciseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", mainExerciseId=" + mainExerciseId +
                ", exerciseExtensionId=" + exerciseExtensionId +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getMainExerciseId() {
        return mainExerciseId;
    }

    public void setMainExerciseId(int mainExerciseId) {
        this.mainExerciseId = mainExerciseId;
    }

    public long getExerciseExtensionId() {
        return exerciseExtensionId;
    }

    public void setExerciseExtensionId(int exerciseExtensionId) {
        this.exerciseExtensionId = exerciseExtensionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
