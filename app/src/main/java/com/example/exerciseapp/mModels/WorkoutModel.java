package com.example.exerciseapp.mModels;

import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

public class WorkoutModel extends TrainingModel{

    private long id;
    private String exercisesIDs;
    private String bodyPartsIDs;

    public WorkoutModel() {}

    public WorkoutModel(long id, String image, String name, Level level, String equipmentIds, int kcal,
                        int duration, String description, FromWhere fromWhere, long userId,
                        String exercisesIDs, String bodyPartsIDs) {
        super(image, name, level, equipmentIds, kcal, duration, description, fromWhere, userId);
        this.id = id;
        this.exercisesIDs = exercisesIDs;
        this.bodyPartsIDs = bodyPartsIDs;
    }

    public long getId() {
        return id;
    }

    public String getExercisesIDs() {
        return exercisesIDs;
    }

    public String getBodyPartsIDs() {
        return bodyPartsIDs;
    }
}
