package com.example.exerciseapp.mDatabases.General.Tabels.Units;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AppUnitsDao {

    @Insert
    long createAppUnits(AppUnitsModel appUnitsModel);

    @Query("SELECT * FROM APP_UNITS WHERE USER_ID = :userId")
    AppUnitsModel getAppUnitsByUserId(int userId);
}
