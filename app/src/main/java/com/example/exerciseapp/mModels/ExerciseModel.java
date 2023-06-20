package com.example.exerciseapp.mModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ExerciseModel implements Parcelable {

    private int id;
    private String name;
    private String image;
    private int level;
    private String bodyParts;
    private String equipment;
    private int type;
    private int kcal;
    private int duration;
    private String description;
    private int extension;


    private String exerciseId;

    public ExerciseModel(int id, String name, String image, int level,
                         String bodyParts, String equipment, int type,
                         int kcal, int duration, String description, int extension) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.level = level;
        this.bodyParts = bodyParts;
        this.equipment = equipment;
        this.type = type;
        this.kcal = kcal;
        this.duration = duration;
        this.description = description;
        this.extension = extension;
    }

    public ExerciseModel(int id, String name, String image, int level,
                         String bodyParts, String equipment, int kcal,
                         int duration, String description, String exerciseId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.level = level;
        this.bodyParts = bodyParts;
        this.equipment = equipment;
        this.kcal = kcal;
        this.duration = duration;
        this.description = description;
        this.exerciseId = exerciseId;
    }


    protected ExerciseModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        level = in.readInt();
        bodyParts = in.readString();
        equipment = in.readString();
        type = in.readInt();
        kcal = in.readInt();
        duration = in.readInt();
        description = in.readString();
        exerciseId = in.readString();
        extension = in.readInt();
    }

    public static final Creator<ExerciseModel> CREATOR = new Creator<ExerciseModel>() {
        @Override
        public ExerciseModel createFromParcel(Parcel in) {
            return new ExerciseModel(in);
        }

        @Override
        public ExerciseModel[] newArray(int size) {
            return new ExerciseModel[size];
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(String bodyParts) {
        this.bodyParts = bodyParts;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(level);
        parcel.writeString(bodyParts);
        parcel.writeString(equipment);
        parcel.writeInt(type);
        parcel.writeInt(kcal);
        parcel.writeInt(duration);
        parcel.writeString(description);
        parcel.writeString(exerciseId);
        parcel.writeInt(extension);
    }
}
