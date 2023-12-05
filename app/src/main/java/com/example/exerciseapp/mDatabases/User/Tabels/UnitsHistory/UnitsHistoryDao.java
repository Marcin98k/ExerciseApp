package com.example.exerciseapp.mDatabases.User.Tabels.UnitsHistory;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UnitsHistoryDao {

    @Insert
    long createUnitsHistory(UnitsHistoryModel unitsHistoryModel);

    @Query("SELECT * FROM UNITS_HISTORY WHERE ID = :id")
    UnitsHistoryModel getUnitHistoryById(int id);

    @Query("SELECT * FROM UNITS_HISTORY WHERE USER_ID = :userId")
    List<UnitsHistoryModel> getAllUnitsHistoryByUserId(int userId);
}
