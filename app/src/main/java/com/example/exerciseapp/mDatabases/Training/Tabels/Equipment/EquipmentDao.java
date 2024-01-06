package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exerciseapp.mEnums.EquipmentType;
import com.example.exerciseapp.mEnums.VolumeType;

import java.util.List;

@Dao
public interface EquipmentDao {

    @Insert
    long createEquipment(EquipmentModel equipmentModel);

    @Query("SELECT * FROM EQUIPMENT")
    List<EquipmentModel> getAllEquipment();

    @Query("SELECT * FROM EQUIPMENT WHERE ID = :id")
    EquipmentModel getEquipmentById(int id);

    @Query("SELECT * FROM EQUIPMENT WHERE ID IN (:ids)")
    List<EquipmentModel> getListEquipmentByIds(int[] ids);

    @Query("SELECT * FROM EQUIPMENT WHERE EQUIPMENT_TYPE = :equipmentType")
    List<EquipmentModel> getListEquipmentByType(EquipmentType equipmentType);

    @Query("SELECT * FROM EQUIPMENT WHERE VOLUME_TYPE = :volumeType")
    List<EquipmentModel> getListEquipmentByVolumeType(VolumeType volumeType);
}
