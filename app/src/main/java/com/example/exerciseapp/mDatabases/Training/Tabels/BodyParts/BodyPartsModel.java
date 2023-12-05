package com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "BODY_PARTS")
public class BodyPartsModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "CHEST")
    private boolean chestStatus;

    @ColumnInfo(name = "BACK")
    private boolean backStatus;

    @ColumnInfo(name = "SHOULDERS")
    private boolean shouldersStatus;

    @ColumnInfo(name = "ARMS")
    private boolean armsStatus;

    @ColumnInfo(name = "ABS")
    private boolean ABSStatus;

    @ColumnInfo(name = "LEGS")
    private boolean legsStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChestStatus() {
        return chestStatus;
    }

    public void setChestStatus(boolean chestStatus) {
        this.chestStatus = chestStatus;
    }

    public boolean isBackStatus() {
        return backStatus;
    }

    public void setBackStatus(boolean backStatus) {
        this.backStatus = backStatus;
    }

    public boolean isShouldersStatus() {
        return shouldersStatus;
    }

    public void setShouldersStatus(boolean shouldersStatus) {
        this.shouldersStatus = shouldersStatus;
    }

    public boolean isArmsStatus() {
        return armsStatus;
    }

    public void setArmsStatus(boolean armsStatus) {
        this.armsStatus = armsStatus;
    }

    public boolean isABSStatus() {
        return ABSStatus;
    }

    public void setABSStatus(boolean ABSStatus) {
        this.ABSStatus = ABSStatus;
    }

    public boolean isLegsStatus() {
        return legsStatus;
    }

    public void setLegsStatus(boolean legsStatus) {
        this.legsStatus = legsStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyPartsModel that = (BodyPartsModel) o;
        return id == that.id && chestStatus == that.chestStatus && backStatus == that.backStatus
                && shouldersStatus == that.shouldersStatus && armsStatus == that.armsStatus
                && ABSStatus == that.ABSStatus && legsStatus == that.legsStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chestStatus, backStatus, shouldersStatus, armsStatus, ABSStatus,
                legsStatus);
    }
}
