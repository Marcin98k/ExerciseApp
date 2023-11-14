package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FourElementsModel implements Parcelable {

    private int id;
    private String strImage;
    private int intImage;
    private String name;
    private String type;
    private int level;
    private int duration;
    private int fromWhere;

    public FourElementsModel(int id, String strImage, String name, String type, int level) {
        this.id = id;
        this.strImage = strImage;
        this.name = name;
        this.type = type;
        this.level = level;
    }

    public FourElementsModel(int id, String strImage, String name, String type, int level,
                             int duration , int fromWhere) {
        this.id = id;
        this.strImage = strImage;
        this.name = name;
        this.type = type;
        this.level = level;
        this.duration = duration;
        this.fromWhere = fromWhere;
    }

    protected FourElementsModel(Parcel in) {
        id = in.readInt();
        strImage = in.readString();
        intImage = in.readInt();
        name = in.readString();
        type = in.readString();
        level = in.readInt();
        duration = in.readInt();
        fromWhere = in.readInt();
    }

    public static final Creator<FourElementsModel> CREATOR = new Creator<FourElementsModel>() {
        @Override
        public FourElementsModel createFromParcel(Parcel in) {
            return new FourElementsModel(in);
        }

        @Override
        public FourElementsModel[] newArray(int size) {
            return new FourElementsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrImage() {
        return strImage;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }

    public int getIntImage() {
        return intImage;
    }

    public void setIntImage(int intImage) {
        this.intImage = intImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(strImage);
        parcel.writeInt(intImage);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeInt(level);
        parcel.writeInt(duration);
        parcel.writeInt(fromWhere);
    }
}
