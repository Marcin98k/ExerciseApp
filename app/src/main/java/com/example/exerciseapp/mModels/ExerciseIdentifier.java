package com.example.exerciseapp.mModels;

public class ExerciseIdentifier {

    private long id;
    private String name;
    private byte type;

    public ExerciseIdentifier(long id, String name, byte type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
