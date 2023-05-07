package com.example.exerciseapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IntegerModel implements Parcelable {

    private int id;
    private int firstValue;
    private int secondValue;
    private int thirdValue;
    private int forthValue;
    private int fifthValue;

    public IntegerModel() {
//        Empty constructor;
    }

    public IntegerModel(int id, int firstValue) {
        this.id = id;
        this.firstValue = firstValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue, int forthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue, int forthValue, int fifthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
        this.fifthValue = fifthValue;
    }

    protected IntegerModel(Parcel in) {
        id = in.readInt();
        firstValue = in.readInt();
        secondValue = in.readInt();
        thirdValue = in.readInt();
        forthValue = in.readInt();
        fifthValue = in.readInt();
    }

    public static final Creator<IntegerModel> CREATOR = new Creator<IntegerModel>() {
        @Override
        public IntegerModel createFromParcel(Parcel in) {
            return new IntegerModel(in);
        }

        @Override
        public IntegerModel[] newArray(int size) {
            return new IntegerModel[size];
        }
    };

    @Override
    public String toString() {
        return "IntegerModel{" +
                "id=" + id +
                ", firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                ", thirdValue=" + thirdValue +
                ", forthValue=" + forthValue +
                ", fifthValue=" + fifthValue +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(int firstValue) {
        this.firstValue = firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }

    public int getThirdValue() {
        return thirdValue;
    }

    public void setThirdValue(int thirdValue) {
        this.thirdValue = thirdValue;
    }

    public int getForthValue() {
        return forthValue;
    }

    public void setForthValue(int forthValue) {
        this.forthValue = forthValue;
    }

    public int getFifthValue() {
        return fifthValue;
    }

    public void setFifthValue(int fifthValue) {
        this.fifthValue = fifthValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(firstValue);
        parcel.writeInt(secondValue);
        parcel.writeInt(thirdValue);
        parcel.writeInt(forthValue);
        parcel.writeInt(fifthValue);
    }
}
