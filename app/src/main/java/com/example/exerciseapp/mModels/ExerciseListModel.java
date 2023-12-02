package com.example.exerciseapp.mModels;

public class ExerciseListModel {

    private long id;
    private long exerciseId;
    private long extensionId;

    public ExerciseListModel(long id, long exerciseId, long extensionId) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.extensionId = extensionId;
    }
}
