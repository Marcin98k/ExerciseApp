package com.example.exerciseapp.mModels;

import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

public class ExerciseModel extends TrainingModel{

    private long id, bodyParts;

    public ExerciseModel() {
        super();
    }
    public ExerciseModel(long id, String image, String name, Level level, String equipmentIds,
                         int kcal, int duration, String description, FromWhere fromWhere,
                         long userId, long bodyParts) {
        super(image, name, level, equipmentIds, kcal, duration, description, fromWhere, userId);
        this.id = id;
        this.bodyParts = bodyParts;
    }

    public long getId() {
        return id;
    }

    public long getBodyParts() {
        return bodyParts;
    }
}
