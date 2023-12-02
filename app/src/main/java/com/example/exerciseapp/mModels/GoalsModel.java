package com.example.exerciseapp.mModels;

public class GoalsModel {

    private long id;
    private boolean strength;
    private boolean muscle;
    private boolean fatLose;
    private boolean technique;

    public GoalsModel() {}

    public GoalsModel(long id, boolean strength, boolean muscle, boolean fatLose, boolean technique) {
        this.id = id;
        this.strength = strength;
        this.muscle = muscle;
        this.fatLose = fatLose;
        this.technique = technique;
    }

    public long getId() {
        return id;
    }

    public boolean isStrength() {
        return strength;
    }

    public boolean isMuscle() {
        return muscle;
    }

    public boolean isFatLose() {
        return fatLose;
    }

    public boolean isTechnique() {
        return technique;
    }
}
