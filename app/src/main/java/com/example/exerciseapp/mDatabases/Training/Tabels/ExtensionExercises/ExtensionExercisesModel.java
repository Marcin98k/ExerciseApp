package com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.ExerciseType;

import java.util.Objects;

@Entity(tableName = "EXTENSION_EXERCISE")
public class ExtensionExercisesModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "EXERCISE_ID")
    private int exerciseId;

    @ColumnInfo(name = "EXERCISE_TYPE")
    private ExerciseType exerciseType;

    @ColumnInfo(name = "NUMBER_OF_SERIES")
    private int numberOfSeries;

    @ColumnInfo(name = "VOLUME")
    private int volume;

    @ColumnInfo(name = "BREAK")
    private int breakLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getNumberOfSeries() {
        return numberOfSeries;
    }

    public void setNumberOfSeries(int numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBreakLength() {
        return breakLength;
    }

    public void setBreakLength(int breakLength) {
        this.breakLength = breakLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtensionExercisesModel that = (ExtensionExercisesModel) o;
        return id == that.id && exerciseId == that.exerciseId
                && numberOfSeries == that.numberOfSeries && volume == that.volume
                && breakLength == that.breakLength && exerciseType == that.exerciseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exerciseId, exerciseType, numberOfSeries, volume, breakLength);
    }
}
