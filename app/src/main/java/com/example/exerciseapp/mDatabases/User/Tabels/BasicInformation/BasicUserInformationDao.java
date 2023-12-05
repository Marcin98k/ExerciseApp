package com.example.exerciseapp.mDatabases.User.Tabels.BasicInformation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BasicUserInformationDao {

    @Insert
    long createBasicInformation(BasicUserInformationModel basicUserInformationModel);

    @Query("SELECT * FROM BASIC_USER_INFORMATION WHERE ID = :id")
    BasicUserInformationModel getBasicUserInformation(int id);
}
