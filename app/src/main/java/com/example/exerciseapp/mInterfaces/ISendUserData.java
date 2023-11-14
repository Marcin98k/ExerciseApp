package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.mEnums.UserDatabaseColumns;

public interface ISendUserData {
    void sendData(String listName, long id, UserDatabaseColumns userDatabaseColumns, String value);
}
