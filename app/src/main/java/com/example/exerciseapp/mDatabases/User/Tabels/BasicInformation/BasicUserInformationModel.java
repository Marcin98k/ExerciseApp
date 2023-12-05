package com.example.exerciseapp.mDatabases.User.Tabels.BasicInformation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.exerciseapp.mEnums.AccountStatus;
import com.example.exerciseapp.mEnums.Gender;
import com.example.exerciseapp.mEnums.Level;

import java.util.Objects;

@Entity(tableName = "BASIC_USER_INFORMATION")
public class BasicUserInformationModel {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "IMAGE")
    private String image;

    @ColumnInfo(name = "EMAIL")
    private String email;

    @ColumnInfo(name = "PASSWORD")
    private String password;

    @ColumnInfo(name = "TOKEN")
    private String token;

    @ColumnInfo(name = "GENDER")
    private Gender gender;

    @ColumnInfo(name = "UNITS")
    private int units;

    @ColumnInfo(name = "PERFORMANCE")
    private int performance;

    @ColumnInfo(name = "LEVEL")
    private Level level;

    @ColumnInfo(name = "NOTIFICATION")
    private boolean notification;

    @ColumnInfo(name = "ACCOUNT_STATUS")
    private AccountStatus accountStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicUserInformationModel that = (BasicUserInformationModel) o;
        return id == that.id && units == that.units && performance == that.performance
                && notification == that.notification && Objects.equals(name, that.name)
                && Objects.equals(image, that.image) && Objects.equals(email, that.email)
                && Objects.equals(password, that.password) && Objects.equals(token, that.token)
                && gender == that.gender && level == that.level && accountStatus == that.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, email, password, token, gender, units, performance,
                level, notification, accountStatus);
    }
}
