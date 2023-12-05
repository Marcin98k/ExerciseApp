package com.example.exerciseapp.mDatabases.General;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.exerciseapp.mDatabases.General.Tabels.Language.LanguageDao;
import com.example.exerciseapp.mDatabases.General.Tabels.Language.LanguageModel;
import com.example.exerciseapp.mDatabases.General.Tabels.Units.AppUnitsDao;
import com.example.exerciseapp.mDatabases.General.Tabels.Units.AppUnitsModel;

@Database(entities = {LanguageModel.class, AppUnitsModel.class}, version = 1, exportSchema = false)
public abstract class GeneralDatabase extends RoomDatabase {

    public abstract LanguageDao languageDao();

    public abstract AppUnitsDao appUnitsDao();
}
