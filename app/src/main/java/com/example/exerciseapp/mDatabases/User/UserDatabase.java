package com.example.exerciseapp.mDatabases.User;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.exerciseapp.mDatabases.User.Tabels.BasicInformation.BasicUserInformationDao;
import com.example.exerciseapp.mDatabases.User.Tabels.BasicInformation.BasicUserInformationModel;
import com.example.exerciseapp.mDatabases.User.Tabels.Goals.GoalsDao;
import com.example.exerciseapp.mDatabases.User.Tabels.Goals.GoalsModel;
import com.example.exerciseapp.mDatabases.User.Tabels.Performance.PerformanceDao;
import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceHistory.PerformanceHistoryDao;
import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceHistory.PerformanceHistoryModel;
import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceModel;
import com.example.exerciseapp.mDatabases.User.Tabels.Units.UnitsDao;
import com.example.exerciseapp.mDatabases.User.Tabels.UnitsHistory.UnitsHistoryDao;
import com.example.exerciseapp.mDatabases.User.Tabels.UnitsHistory.UnitsHistoryModel;
import com.example.exerciseapp.mDatabases.User.Tabels.UnitsModel;

@Database(entities = {BasicUserInformationModel.class, GoalsModel.class, PerformanceModel.class,
        PerformanceHistoryModel.class, UnitsModel.class, UnitsHistoryModel.class}, version = 1,
        exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract BasicUserInformationDao basicInformationDao();

    public abstract GoalsDao goalsDao();

    public abstract PerformanceDao performanceDao();

    public abstract PerformanceHistoryDao performanceHistoryDao();

    public abstract UnitsDao unitsDao();

    public abstract UnitsHistoryDao unitsHistoryDao();
}
