package com.example.exerciseapp.mDatabases.User.Tabels.Performance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceModel;

@Dao
public interface PerformanceDao {

    @Insert
    long createPerformance(PerformanceModel performanceModel);

    @Query("SELECT * FROM PERFORMANCE WHERE ID = :id")
    PerformanceModel getPerformanceById(int id);
}
