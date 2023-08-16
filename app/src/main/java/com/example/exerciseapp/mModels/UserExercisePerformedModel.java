package com.example.exerciseapp.mModels;

public class UserExercisePerformedModel {

    private long id;
    private String date;
    private long exerciseID;
    private long extensionID;
    private String sumTime;

    public UserExercisePerformedModel(long id, String date, long exerciseID, long extensionID, String sumTime) {
        this.id = id;
        this.date = date;
        this.exerciseID = exerciseID;
        this.extensionID = extensionID;
        this.sumTime = sumTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public long getExtensionID() {
        return extensionID;
    }

    public void setExtensionID(long extensionID) {
        this.extensionID = extensionID;
    }

    public String getSumTime() {
        return sumTime;
    }

    public void setSumTime(String sumTime) {
        this.sumTime = sumTime;
    }
}
