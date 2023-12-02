package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

public class TrainingModel implements Parcelable {

    private String image, name;
    private Level level;
    private String equipmentIds;
    private int kcal, duration;
    private String description;
    private FromWhere fromWhere;
    private long userId;


    public TrainingModel() {}

    public TrainingModel(String image, String name, Level level, String equipmentIds, int kcal,
                         int duration, String description, FromWhere fromWhere, long userId) {
        this.image = image;
        this.name = name;
        this.level = level;
        this.equipmentIds = equipmentIds;
        this.kcal = kcal;
        this.duration = duration;
        this.description = description;
        this.fromWhere = fromWhere;
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public String getEquipmentIds() {
        return equipmentIds;
    }

    public int getKcal() {
        return kcal;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public FromWhere getFromWhere() {
        return fromWhere;
    }

    public long getUserId() {
        return userId;
    }

    protected TrainingModel(Parcel in) {
        image = in.readString();
        name = in.readString();
        level = Level.values()[in.readInt()];
        equipmentIds = in.readString();
        kcal = in.readInt();
        duration = in.readInt();
        description = in.readString();
        fromWhere = FromWhere.values()[in.readInt()];
        userId = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeInt(level.ordinal());
        parcel.writeString(equipmentIds);
        parcel.writeInt(kcal);
        parcel.writeInt(duration);
        parcel.writeString(description);
        parcel.writeInt(fromWhere.ordinal());
        parcel.writeLong(userId);
    }
}
