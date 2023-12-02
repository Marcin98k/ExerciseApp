package com.example.exerciseapp.mModels;

public class PerformanceModel {

    private long id, userId;
    private int push;
    private int pull;
    private int squad;
    private int dip;

    public PerformanceModel() {}

    public PerformanceModel(long id, int push, int pull, int squad, int dip) {
        this.id = id;
        this.push = push;
        this.pull = pull;
        this.squad = squad;
        this.dip = dip;
    }

    public PerformanceModel setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public int getPush() {
        return push;
    }

    public int getPull() {
        return pull;
    }

    public int getSquad() {
        return squad;
    }

    public int getDip() {
        return dip;
    }
}
