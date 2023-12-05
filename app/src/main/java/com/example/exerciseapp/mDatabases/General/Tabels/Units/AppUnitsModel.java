package com.example.exerciseapp.mDatabases.General.Tabels.Units;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.HeightUnits;
import com.example.exerciseapp.mEnums.WeightUnits;

import java.util.Objects;

@Entity(tableName = "APP_UNITS")
public class AppUnitsModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "USER_ID")
    private int userId;

    @ColumnInfo(name = "HEIGHT")
    private HeightUnits height;

    @ColumnInfo(name = "WEIGHT")
    private WeightUnits weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HeightUnits getHeight() {
        return height;
    }

    public void setHeight(HeightUnits height) {
        this.height = height;
    }

    public WeightUnits getWeight() {
        return weight;
    }

    public void setWeight(WeightUnits weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUnitsModel that = (AppUnitsModel) o;
        return id == that.id && userId == that.userId && height == that.height && weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, height, weight);
    }
}
