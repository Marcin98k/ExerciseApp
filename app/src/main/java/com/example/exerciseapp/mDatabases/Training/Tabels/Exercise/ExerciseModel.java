package com.example.exerciseapp.mDatabases.Training.Tabels.Exercise;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.exerciseapp.mDatabases.Training.Tabels.TrainingModel;

import java.util.Objects;

@Entity(tableName = "EXERCISE")
public class ExerciseModel extends TrainingModel {

    @ColumnInfo(name = "BODY_PARTS")
    private long bodyParts;

    public long getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(long bodyParts) {
        this.bodyParts = bodyParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExerciseModel that = (ExerciseModel) o;
        return bodyParts == that.bodyParts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bodyParts);
    }
}
