package com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BodyPartsDao {

    @Insert
    long createBodyPartsCombination(BodyPartsModel bodyPartsModel);

    @Query("SELECT * FROM BODY_PARTS WHERE ID = :id")
    BodyPartsModel getBodyPartsCombinationById(int id);
}
