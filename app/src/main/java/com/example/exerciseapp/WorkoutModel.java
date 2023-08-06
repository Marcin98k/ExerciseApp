package com.example.exerciseapp;

public class WorkoutModel {

    private int id;
    private long exerciseId;
    private byte type;
    private String name;

    public WorkoutModel(int id, long exerciseId, byte type) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.type = type;
    }

    public WorkoutModel(int id, long exerciseId, byte type, String name) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
