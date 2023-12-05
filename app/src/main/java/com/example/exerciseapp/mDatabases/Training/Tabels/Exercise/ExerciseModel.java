package com.example.exerciseapp.mDatabases.Training.Tabels.Exercise;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.exerciseapp.mDatabases.Training.Tabels.TrainingModel;

import java.util.Objects;

@Entity(tableName = "EXERCISE")
public class ExerciseModel extends TrainingModel {

    @ColumnInfo(name = "BODY_PARTS")
    private int bodyParts;

    public int getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseModel that = (ExerciseModel) o;
        return Objects.equals(bodyParts, that.bodyParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodyParts);
    }
}
