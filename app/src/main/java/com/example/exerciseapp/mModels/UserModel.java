package com.example.exerciseapp.mModels;

import com.example.exerciseapp.mEnums.AccountStatus;
import com.example.exerciseapp.mEnums.Gender;
import com.example.exerciseapp.mEnums.Level;

public class UserModel {

    private long id;
    private String name, image, email, password, token;
    private Gender gender;
    private long units, performance;
    private Level level;
    private boolean notification;
    private AccountStatus accountStatus;

    public UserModel() {}

    public UserModel(long id, String name, String image, String email, String password,
                     String token, Gender gender, long units, long performance, Level level,
                     boolean notification, AccountStatus accountStatus) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.email = email;
        this.password = password;
        this.token = token;
        this.gender = gender;
        this.units = units;
        this.performance = performance;
        this.level = level;
        this.notification = notification;
        this.accountStatus = accountStatus;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Gender getGender() {
        return gender;
    }

    public long getUnits() {
        return units;
    }

    public long getPerformance() {
        return performance;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isNotification() {
        return notification;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
