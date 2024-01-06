package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "EQUIPMENT_VOLUME",
        foreignKeys = {
                @ForeignKey(entity = EquipmentModel.class,
                        parentColumns = "ID",
                        childColumns = "EQUIPMENT_ID"),
                @ForeignKey(entity = VolumeModel.class,
                        parentColumns = "ID",
                        childColumns = "VOLUME_ID")
        })
public class EquipmentVolumeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "EQUIPMENT_ID")
    private int equipmentId;

    @ColumnInfo(name = "VOLUME_ID")
    private int volumeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentVolumeModel that = (EquipmentVolumeModel) o;
        return id == that.id && equipmentId == that.equipmentId && volumeId == that.volumeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equipmentId, volumeId);
    }
}
