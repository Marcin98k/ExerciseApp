package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EquipmentVolumeDao {

    @Insert
    long createEquipmentVolume(EquipmentVolumeModel equipmentVolumeModel);

    @Query("SELECT * FROM EQUIPMENT_VOLUME WHERE ID = :id")
    EquipmentVolumeModel getEquipmentVolumeById(int id);
}
