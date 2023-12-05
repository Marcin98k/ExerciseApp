package com.example.exerciseapp.mDatabases.User.Tabels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "PERFORMANCE")
public class PerformanceModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "PUSH")
    private int push;

    @ColumnInfo(name = "PULL")
    private int pull;

    @ColumnInfo(name = "SQUAD")
    private int squad;

    @ColumnInfo(name = "DIP")
    private int dip;

    @ColumnInfo(name = "TIMESTAMP")
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    public int getPull() {
        return pull;
    }

    public void setPull(int pull) {
        this.pull = pull;
    }

    public int getSquad() {
        return squad;
    }

    public void setSquad(int squad) {
        this.squad = squad;
    }

    public int getDip() {
        return dip;
    }

    public void setDip(int dip) {
        this.dip = dip;
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
        PerformanceModel that = (PerformanceModel) o;
        return id == that.id && push == that.push && pull == that.pull
                && squad == that.squad && dip == that.dip && timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, push, pull, squad, dip, timestamp);
    }
}
