package com.example.exerciseapp.mModels;

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
    private int sixthValue;
    private int seventhValue;
    private int eighthValue;

    public IntegerModel() {}

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

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue, int forthValue,
                        int fifthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
        this.fifthValue = fifthValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue, int forthValue,
                        int fifthValue, int sixthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
        this.fifthValue = fifthValue;
        this.sixthValue = sixthValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue,
                        int forthValue, int fifthValue, int sixthValue, int seventhValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
        this.fifthValue = fifthValue;
        this.sixthValue = sixthValue;
        this.seventhValue = seventhValue;
    }

    public IntegerModel(int id, int firstValue, int secondValue, int thirdValue,
                        int forthValue, int fifthValue, int sixthValue, int seventhValue,
                        int eighthValue) {
        this.id = id;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.forthValue = forthValue;
        this.fifthValue = fifthValue;
        this.sixthValue = sixthValue;
        this.seventhValue = seventhValue;
        this.eighthValue = eighthValue;
    }

    protected IntegerModel(Parcel in) {
        id = in.readInt();
        firstValue = in.readInt();
        secondValue = in.readInt();
        thirdValue = in.readInt();
        forthValue = in.readInt();
        fifthValue = in.readInt();
        sixthValue = in.readInt();
        seventhValue = in.readInt();
        eighthValue = in.readInt();
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

    public int getSixthValue() {
        return sixthValue;
    }

    public void setSixthValue(int sixthValue) {
        this.sixthValue = sixthValue;
    }

    public int getSeventhValue() {
        return seventhValue;
    }

    public void setSeventhValue(int seventhValue) {
        this.seventhValue = seventhValue;
    }

    public int getEighthValue() {
        return eighthValue;
    }

    public void setEighthValue(int eighthValue) {
        this.eighthValue = eighthValue;
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
        parcel.writeInt(sixthValue);
        parcel.writeInt(seventhValue);
        parcel.writeInt(eighthValue);
    }
}
