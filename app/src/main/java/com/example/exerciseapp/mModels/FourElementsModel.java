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
    private int icon;

    public FourElementsModel(int id, String strImage, String name, String type, int icon) {
        this.id = id;
        this.strImage = strImage;
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public FourElementsModel(int id, int intImage, String name, String type, int icon) {
        this.id = id;
        this.intImage = intImage;
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    protected FourElementsModel(Parcel in) {
        id = in.readInt();
        strImage = in.readString();
        intImage = in.readInt();
        name = in.readString();
        type = in.readString();
        icon = in.readInt();
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
        parcel.writeInt(icon);
    }
}
