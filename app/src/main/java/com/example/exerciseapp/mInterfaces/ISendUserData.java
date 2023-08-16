package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.mEnums.RowNames;

public interface ISendUserData {
    void sendData(String listName, long id, RowNames rowNames, String value);
}
