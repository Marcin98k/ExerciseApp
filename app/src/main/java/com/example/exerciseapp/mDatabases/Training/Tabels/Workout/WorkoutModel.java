package com.example.exerciseapp.mDatabases.Training.Tabels.Workout;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.exerciseapp.mDatabases.Training.Tabels.TrainingModel;

import java.util.Objects;

@Entity(tableName = "WORKOUT")
public class WorkoutModel extends TrainingModel {

    @ColumnInfo(name = "EXERCISE_IDs")
    private String exercisesIDs;

    @ColumnInfo(name = "BODY_PARTS_IDs")
    private String bodyPartsIDs;

    public String getExercisesIDs() {
        return exercisesIDs;
    }

    public void setExercisesIDs(String exercisesIDs) {
        this.exercisesIDs = exercisesIDs;
    }

    public String getBodyPartsIDs() {
        return bodyPartsIDs;
    }

    public void setBodyPartsIDs(String bodyPartsIDs) {
        this.bodyPartsIDs = bodyPartsIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutModel that = (WorkoutModel) o;
        return Objects.equals(exercisesIDs, that.exercisesIDs)
                && Objects.equals(bodyPartsIDs, that.bodyPartsIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercisesIDs, bodyPartsIDs);
    }
}
