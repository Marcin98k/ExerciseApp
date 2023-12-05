package com.example.exerciseapp.mDatabases.User.Tabels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.HeightUnits;
import com.example.exerciseapp.mEnums.WeightUnits;

import java.util.Objects;

@Entity(tableName = "UNITS")
public class UnitsModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "HEIGHT")
    private int height;

    @ColumnInfo(name = "WEIGHT")
    private int weight;

    @ColumnInfo(name = "UNIT_HEIGHT")
    private HeightUnits unitHeight;

    @ColumnInfo(name = "UNIT_WEIGHT")
    private WeightUnits unitWeight;

    @ColumnInfo(name = "TIMESTAMP")
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HeightUnits getUnitHeight() {
        return unitHeight;
    }

    public void setUnitHeight(HeightUnits unitHeight) {
        this.unitHeight = unitHeight;
    }

    public WeightUnits getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(WeightUnits unitWeight) {
        this.unitWeight = unitWeight;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitsModel that = (UnitsModel) o;
        return id == that.id && height == that.height && weight == that.weight
                && timestamp == that.timestamp && unitHeight == that.unitHeight
                && unitWeight == that.unitWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, height, weight, unitHeight, unitWeight, timestamp);
    }
}
