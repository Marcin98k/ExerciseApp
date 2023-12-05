package com.example.exerciseapp.mDatabases.User.Tabels.Goals;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GoalsDao {

    @Insert
    long createGoals(GoalsModel goalsModel);

    @Query("SELECT * FROM GOALS WHERE ID = :id")
    GoalsModel getGoalsById(int id);
}
