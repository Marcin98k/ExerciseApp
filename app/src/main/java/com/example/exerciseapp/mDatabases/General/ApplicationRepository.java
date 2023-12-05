package com.example.exerciseapp.mDatabases.General;

import android.content.Context;

import androidx.room.Room;

import com.example.exerciseapp.mDatabases.General.Tabels.Language.LanguageDao;
import com.example.exerciseapp.mDatabases.General.Tabels.Language.LanguageModel;
import com.example.exerciseapp.mDatabases.General.Tabels.Units.AppUnitsDao;
import com.example.exerciseapp.mDatabases.General.Tabels.Units.AppUnitsModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationRepository {

    private final LanguageDao languageDao;
    private final AppUnitsDao appUnitsDao;
    private final ExecutorService executorService;

    public ApplicationRepository(Context context) {

        GeneralDatabase db = Room.databaseBuilder(context, GeneralDatabase.class,
                "GeneralProperties.db").build();
        languageDao = db.languageDao();
        appUnitsDao = db.appUnitsDao();
        executorService = Executors.newSingleThreadExecutor();
    }

//    CREATE
    public void createLanguage(LanguageModel languageModel) {
        executorService.execute(() -> languageDao.createLanguage(languageModel));
    }

    public void createAppUnits(AppUnitsModel appUnitsModel) {
        executorService.execute(() -> appUnitsDao.createAppUnits(appUnitsModel));
    }

//    UPDATE
    public void switchLanguage(int oldLanguage, int newLanguage) {

        executorService.execute(() -> languageDao.switchLanguage(oldLanguage, newLanguage));
    }
}
