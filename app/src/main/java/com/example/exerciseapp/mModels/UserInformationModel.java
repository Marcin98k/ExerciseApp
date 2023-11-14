package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserInformationModel implements Parcelable {

    private int id;
    private String name;
    private String email;
    private String password;
    private int gender;
    private int units;
    private int performance;
    private int goals;
    private int level;
    private int notification;
    private int status;

    private String authorizationToken;
    private String newsToken;

    public UserInformationModel(int id, String name, String email, String password, int gender,
                                int units, int performance, int goals, int level, int notification,
                                int status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.units = units;
        this.performance = performance;
        this.goals = goals;
        this.level = level;
        this.notification = notification;
        this.status = status;
    }

    public UserInformationModel(int id, String name, String email, String password,
                                String authorizationToken, String newsToken, int gender,
                                int units, int performance, int goals, int level, int notification) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.units = units;
        this.performance = performance;
        this.goals = goals;
        this.level = level;
        this.notification = notification;
        this.authorizationToken = authorizationToken;
        this.newsToken = newsToken;
    }

    protected UserInformationModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        gender = in.readInt();
        units = in.readInt();
        performance = in.readInt();
        goals = in.readInt();
        level = in.readInt();
        notification = in.readInt();
        status = in.readInt();
        authorizationToken = in.readString();
        newsToken = in.readString();
    }

    public static final Creator<UserInformationModel> CREATOR = new Creator<UserInformationModel>() {
        @Override
        public UserInformationModel createFromParcel(Parcel in) {
            return new UserInformationModel(in);
        }

        @Override
        public UserInformationModel[] newArray(int size) {
            return new UserInformationModel[size];
        }
    };

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
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

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getNewsToken() {
        return newsToken;
    }

    public void setNewsToken(String newsToken) {
        this.newsToken = newsToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeInt(gender);
        parcel.writeInt(units);
        parcel.writeInt(performance);
        parcel.writeInt(goals);
        parcel.writeInt(level);
        parcel.writeInt(notification);
        parcel.writeInt(status);
        parcel.writeString(authorizationToken);
        parcel.writeString(newsToken);
    }
}
