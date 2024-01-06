package com.example.exerciseapp.mDatabases.User;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository extends Application {

    private final BasicUserInformationDao basicUserInformationDao;
    private final GoalsDao goalsDao;
    private final PerformanceDao performanceDao;
    private final PerformanceHistoryDao performanceHistoryDao;
    private final UnitsDao unitsDao;
    private final UnitsHistoryDao unitsHistoryDao;
    private final ExecutorService executorService;

    public UserRepository(Context context) {

        UserDatabase db = Room.databaseBuilder(context, UserDatabase.class, "User.db").build();

        basicUserInformationDao = db.basicInformationDao();
        goalsDao = db.goalsDao();
        performanceDao = db.performanceDao();
        performanceHistoryDao = db.performanceHistoryDao();
        unitsDao = db.unitsDao();
        unitsHistoryDao = db.unitsHistoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    //    CREATE
    public void createBasicUserInformation(BasicUserInformationModel basicUserInformationModel) {
        executorService.execute(() -> basicUserInformationDao
                .createBasicInformation(basicUserInformationModel));
    }

    public void createUserGoals(GoalsModel goalsModel) {
        executorService.execute(() -> goalsDao.createGoals(goalsModel));
    }

    public void createUserPerformance(PerformanceModel performanceModel) {
        executorService.execute(() -> performanceDao.createPerformance(performanceModel));
    }

    public void createPerformanceHistory(PerformanceHistoryModel performanceHistoryModel) {
        executorService.execute(() -> performanceHistoryDao
                .createPerformanceHistory(performanceHistoryModel));
    }

    public void createUserUnits(UnitsModel unitsModel) {
        executorService.execute(() -> unitsDao.createUnits(unitsModel));
    }

    public void createUnitsHistory(UnitsHistoryModel unitsHistoryModel) {
        executorService.execute(() -> unitsHistoryDao.createUnitsHistory(unitsHistoryModel));
    }
}
