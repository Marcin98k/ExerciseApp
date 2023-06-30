package com.example.exerciseapp.mModels;

public class TaskDateModel {

    private int id;
    private int userId;
    private String date;
    private int taskId;
    private int status;

    public TaskDateModel(int id, int userId, String date, int taskId, int status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.taskId = taskId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getStatus() {
        return status;
    }
}
