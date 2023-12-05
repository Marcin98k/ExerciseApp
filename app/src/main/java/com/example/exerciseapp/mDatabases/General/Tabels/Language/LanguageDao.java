package com.example.exerciseapp.mDatabases.General.Tabels.Language;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface LanguageDao {

    @Insert
    long createLanguage(LanguageModel languageModel);

    @Query("SELECT * FROM LANGUAGE WHERE ID = :id")
    LanguageModel getLanguageById(int id);

    @Query("UPDATE LANGUAGE SET STATUS = :status WHERE ID = :id")
    int updateLanguageStatus(long id, boolean status);

    @Transaction
    default boolean switchLanguage(int oldLanguage, int newLanguage) {
        getLanguageOrThrow(oldLanguage);
        getLanguageOrThrow(newLanguage);

        int updateOld = updateLanguageStatus(oldLanguage, false);
        int updateNew = updateLanguageStatus(newLanguage, true);
        return updateOld > 0 && updateNew > 0;
    }

    default LanguageModel getLanguageOrThrow(int id) {

        LanguageModel language = getLanguageById(id);

        if (language == null) {
            throw new IllegalArgumentException("No language with id " + id);
        }
        return language;
    }
}