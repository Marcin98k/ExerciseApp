package com.example.exerciseapp.mDatabases.User.Tabels.PerformanceHistory;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PerformanceHistoryDao {

    @Insert
    long createPerformanceHistory(PerformanceHistoryModel performanceHistoryModel);

    @Query("SELECT * FROM PERFORMANCE_HISTORY WHERE ID = :id")
    PerformanceHistoryModel getPerformanceHistoryById(int id);

    @Query("SELECT * FROM PERFORMANCE_HISTORY WHERE USER_ID = :userId")
    List<PerformanceHistoryModel> getAllPerformanceHistoryByUserId(int userId);
}
