package com.example.exerciseapp.mModels;

public class CustomExerciseModel {

    private long id, userId, exerciseId, extensionId;
    private String name;
    private int type;

    public CustomExerciseModel(long id, String name, int type, long userId, long exerciseId,
                               long extensionId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.extensionId = extensionId;
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

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(long extensionId) {
        this.extensionId = extensionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
