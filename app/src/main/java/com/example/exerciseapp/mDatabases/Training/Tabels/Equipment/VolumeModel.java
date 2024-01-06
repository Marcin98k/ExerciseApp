package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "VOLUME_TABLE")
public class VolumeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "VOLUME")
    private int volume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolumeModel that = (VolumeModel) o;
        return id == that.id && volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume);
    }
}
