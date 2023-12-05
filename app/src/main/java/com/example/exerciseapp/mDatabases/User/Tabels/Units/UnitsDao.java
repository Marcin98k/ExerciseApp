package com.example.exerciseapp.mDatabases.User.Tabels.Units;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exerciseapp.mDatabases.User.Tabels.UnitsModel;

@Dao
public interface UnitsDao {

    @Insert
    long createUnits(UnitsModel unitsModel);

    @Query("SELECT * FROM UNITS WHERE ID = :id")
    UnitsModel getUnitsByID(int id);
}
