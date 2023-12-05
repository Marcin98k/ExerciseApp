package com.example.exerciseapp.mDatabases.User.Tabels.UnitsHistory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.exerciseapp.mDatabases.User.Tabels.UnitsModel;

import java.util.Objects;

@Entity(tableName = "UNITS_HISTORY")
public class UnitsHistoryModel extends UnitsModel {

    @ColumnInfo(name = "USER_ID")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UnitsHistoryModel that = (UnitsHistoryModel) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }
}
