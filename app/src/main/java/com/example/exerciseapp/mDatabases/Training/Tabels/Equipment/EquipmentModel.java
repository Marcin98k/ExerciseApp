package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.EquipmentType;
import com.example.exerciseapp.mEnums.VolumeType;

import java.util.Objects;

@Entity(tableName = "EQUIPMENT")
public class EquipmentModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "IMAGE")
    private String image;

    @ColumnInfo(name = "EQUIPMENT_TYPE")
    private EquipmentType equipmentType;

    @ColumnInfo(name="VOLUME_TYPE")
    private VolumeType volumeType;

    @ColumnInfo(name = "VOLUME")
    private int volume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VolumeType volumeType) {
        this.volumeType = volumeType;
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
        EquipmentModel that = (EquipmentModel) o;
        return id == that.id && volume == that.volume && Objects.equals(name, that.name)
                && Objects.equals(image, that.image) && equipmentType == that.equipmentType && volumeType == that.volumeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, equipmentType, volumeType, volume);
    }
}
