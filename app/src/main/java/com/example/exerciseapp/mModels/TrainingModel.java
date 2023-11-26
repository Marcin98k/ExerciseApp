package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;

public class TrainingModel implements Parcelable {

    private final long id;
    private final String image;
    private final String name;
    private final int trainingType;
    private final long trainingId;
    private final long levelId;
    private final String equipmentIds;
    private final int kcal;
    private final int duration;
    private final String description;
    private final FromWhere fromWhere;

    public TrainingModel(long id, String image, String name, int trainingType, long trainingId,
                         long levelId, String equipmentIds, int kcal, int duration,
                         String description, FromWhere fromWhere) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingId = trainingId;
        this.levelId = levelId;
        this.equipmentIds = equipmentIds;
        this.kcal = kcal;
        this.duration = duration;
        this.description = description;
        this.fromWhere = fromWhere;
    }

    protected TrainingModel(Parcel in) {
        id = in.readLong();
        image = in.readString();
        name = in.readString();
        levelId = in.readLong();
        trainingType = in.readInt();
        trainingId = in.readLong();
        equipmentIds = in.readString();
        kcal = in.readInt();
        duration = in.readInt();
        description = in.readString();
        fromWhere = FromWhere.values()[in.readInt()];
    }

    public static final Creator<TrainingModel> CREATOR = new Creator<TrainingModel>() {
        @Override
        public TrainingModel createFromParcel(Parcel in) {
            return new TrainingModel(in);
        }

        @Override
        public TrainingModel[] newArray(int size) {
            return new TrainingModel[size];
        }
    };

    public long getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeLong(levelId);
        parcel.writeInt(trainingType);
        parcel.writeLong(trainingId);
        parcel.writeString(equipmentIds);
        parcel.writeInt(kcal);
        parcel.writeInt(duration);
        parcel.writeString(description);
        parcel.writeInt(fromWhere.ordinal());
    }
}
