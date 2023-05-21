package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


// !@!@! TO-DO-JAVA Rename this class;
public class ThreeElementModel implements Parcelable {

    private int id;
    private String name;

//    !@!@! TO-DO-JAVA Rename this variable;
    private int status;

    private String strValue;


    public ThreeElementModel() {

    }

    public ThreeElementModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ThreeElementModel(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public ThreeElementModel(int id, int status) {
        this.id = id;
        this.status = status;
    }

    protected ThreeElementModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readInt();
    }

    public static final Creator<ThreeElementModel> CREATOR = new Creator<ThreeElementModel>() {
        @Override
        public ThreeElementModel createFromParcel(Parcel in) {
            return new ThreeElementModel(in);
        }

        @Override
        public ThreeElementModel[] newArray(int size) {
            return new ThreeElementModel[size];
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
        parcel.writeInt(status);
    }
}
