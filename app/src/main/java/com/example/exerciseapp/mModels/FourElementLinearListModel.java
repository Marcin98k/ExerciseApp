package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FourElementLinearListModel implements Parcelable{

    private int id;
    private int icon;
    private String name;
    private String firstValue;
    private String secondValue;

    private int secondId;

    public FourElementLinearListModel() {

    }

    @Override
    public String toString() {
        return "FourElementLinearListModel{" +
                "id=" + id +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                ", firstValue='" + firstValue + '\'' +
                ", secondValue='" + secondValue + '\'' +
                '}';
    }

    public FourElementLinearListModel(int id, int icon,
                                      String name, String firstValue, String secondValue) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public FourElementLinearListModel(int id, int icon,
                                      String name, String firstValue, String secondValue,
                                      int secondId) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.secondId = secondId;
    }

    protected FourElementLinearListModel(Parcel in) {
        id = in.readInt();
        icon = in.readInt();
        name = in.readString();
        firstValue = in.readString();
        secondValue = in.readString();
        secondId = in.readInt();
    }

    public static final Creator<FourElementLinearListModel> CREATOR = new Creator<FourElementLinearListModel>() {
        @Override
        public FourElementLinearListModel createFromParcel(Parcel in) {
            return new FourElementLinearListModel(in);
        }

        @Override
        public FourElementLinearListModel[] newArray(int size) {
            return new FourElementLinearListModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue = firstValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(icon);
        parcel.writeString(name);
        parcel.writeString(firstValue);
        parcel.writeString(secondValue);
        parcel.writeInt(secondId);
    }
}
