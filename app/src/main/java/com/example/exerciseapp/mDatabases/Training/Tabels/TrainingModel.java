package com.example.exerciseapp.mDatabases.Training.Tabels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import java.util.Objects;

@Entity(tableName = "TRAINING")
public class TrainingModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "IMAGE")
    private String image;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "LEVEL")
    private Level level;
    @ColumnInfo(name = "EQUIPMENT")
    private String equipmentIds;
    @ColumnInfo(name = "KCAL")
    private int kcal;
    @ColumnInfo(name = "DURATION")
    private int duration;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;
    @ColumnInfo(name = "FROM_WHERE")
    private FromWhere fromWhere;
    @ColumnInfo(name = "USER")
    private Integer userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(String equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FromWhere getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(FromWhere fromWhere) {
        this.fromWhere = fromWhere;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingModel that = (TrainingModel) o;
        return id == that.id && kcal == that.kcal && duration == that.duration
                && Objects.equals(userId, that.userId) && Objects.equals(image, that.image) &&
                Objects.equals(name, that.name) && level == that.level &&
                Objects.equals(equipmentIds, that.equipmentIds) &&
                Objects.equals(description, that.description) && fromWhere == that.fromWhere;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name, level, equipmentIds, kcal, duration, description, fromWhere, userId);
    }
}
