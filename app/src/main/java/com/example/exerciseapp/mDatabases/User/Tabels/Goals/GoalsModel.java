package com.example.exerciseapp.mDatabases.User.Tabels.Goals;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "GOALS")
public class GoalsModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "STRENGTH")
    private boolean strength;

    @ColumnInfo(name = "MUSCLE")
    private boolean muscle;

    @ColumnInfo(name = "FAT_LOSE")
    private boolean fatLose;

    @ColumnInfo(name = "TECHNIQUE")
    private boolean technique;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStrength() {
        return strength;
    }

    public void setStrength(boolean strength) {
        this.strength = strength;
    }

    public boolean isMuscle() {
        return muscle;
    }

    public void setMuscle(boolean muscle) {
        this.muscle = muscle;
    }

    public boolean isFatLose() {
        return fatLose;
    }

    public void setFatLose(boolean fatLose) {
        this.fatLose = fatLose;
    }

    public boolean isTechnique() {
        return technique;
    }

    public void setTechnique(boolean technique) {
        this.technique = technique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalsModel that = (GoalsModel) o;
        return id == that.id && strength == that.strength && muscle == that.muscle
                && fatLose == that.fatLose && technique == that.technique;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strength, muscle, fatLose, technique);
    }
}
