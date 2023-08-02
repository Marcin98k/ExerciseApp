package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ThreeElementLinearListModel implements Parcelable {

    private int id;
    private int icon;
    private String name;
    private int action;
    private String strVal;

    private String image;

    public ThreeElementLinearListModel(int id, int icon, String name, int action) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.action = action;
    }

    public ThreeElementLinearListModel(int id, String image, String name, int action) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.action = action;
    }

    public ThreeElementLinearListModel(int id, String name, int action, String strVal, String image) {
        this.id = (int) id;
        this.name = name;
        this.action = action;
        this.strVal = strVal;
        this.image = image;
    }

    protected ThreeElementLinearListModel(Parcel in) {
        id = in.readInt();
        icon = in.readInt();
        name = in.readString();
        action = in.readInt();
        image = in.readString();
        strVal = in.readString();
    }

    public static final Creator<ThreeElementLinearListModel> CREATOR = new Creator<ThreeElementLinearListModel>() {
        @Override
        public ThreeElementLinearListModel createFromParcel(Parcel in) {
            return new ThreeElementLinearListModel(in);
        }

        @Override
        public ThreeElementLinearListModel[] newArray(int size) {
            return new ThreeElementLinearListModel[size];
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

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
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
        parcel.writeInt(action);
        parcel.writeString(image);
        parcel.writeString(strVal);
    }
}
