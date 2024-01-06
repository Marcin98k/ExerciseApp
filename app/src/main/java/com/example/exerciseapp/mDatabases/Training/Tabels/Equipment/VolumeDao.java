package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface VolumeDao {

    @Insert
    long createVolume(VolumeModel volumeModel);

    @Query("SELECT * FROM VOLUME_TABLE WHERE ID = :id")
    VolumeModel getVolumeById(int id);
}
