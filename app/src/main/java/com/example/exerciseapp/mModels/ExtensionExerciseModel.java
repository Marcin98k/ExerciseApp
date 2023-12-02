package com.example.exerciseapp.mModels;

import com.example.exerciseapp.mEnums.ExerciseType;

public class ExtensionExerciseModel {

    private long id, exerciseId;
    private ExerciseType exerciseType;
    private int numberOfSeries, volume, breakLength;

    public ExtensionExerciseModel() {}
    public ExtensionExerciseModel(long id, long exerciseId, ExerciseType exerciseType, int numberOfSeries,
                                  int volume, int breakLength) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.exerciseType = exerciseType;
        this.numberOfSeries = numberOfSeries;
        this.volume = volume;
        this.breakLength = breakLength;
    }

    public long getId() {
        return id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public int getNumberOfSeries() {
        return numberOfSeries;
    }

    public int getVolume() {
        return volume;
    }

    public int getBreakLength() {
        return breakLength;
    }
}
