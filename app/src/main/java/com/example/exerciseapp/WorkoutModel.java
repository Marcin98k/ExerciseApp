package com.example.exerciseapp;

public class WorkoutModel {

    private int id;
    private long exerciseId;
    private long extensionId;
    private byte type;
    private String name;
    private int fromWhere;

    public WorkoutModel(int id, long exerciseId, byte type) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.type = type;
    }

    public WorkoutModel(int id, long exerciseId, long extensionId, byte type, String name,
                        int fromWhere) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.extensionId = extensionId;
        this.type = type;
        this.name = name;
        this.fromWhere = fromWhere;
    }

    public int getId() {
        return id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public long getExtensionId() {
        return extensionId;
    }

    public byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getFromWhere() {
        return fromWhere;
    }
}
