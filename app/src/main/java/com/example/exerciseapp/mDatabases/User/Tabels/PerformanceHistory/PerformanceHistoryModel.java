package com.example.exerciseapp.mDatabases.User.Tabels.PerformanceHistory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceModel;

import java.util.Objects;

@Entity(tableName = "PERFORMANCE_HISTORY")
public class PerformanceHistoryModel extends PerformanceModel {


    @ColumnInfo(name = "USER_ID")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PerformanceHistoryModel that = (PerformanceHistoryModel) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }
}
