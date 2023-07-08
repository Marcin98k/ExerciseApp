package com.example.exerciseapp.mModels;

public class DialogModel {

    private int id;
    private String name;
    private int score;
    private boolean status;

    public DialogModel(int id, String name, int score, boolean status) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.status = status;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
