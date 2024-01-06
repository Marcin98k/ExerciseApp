package com.example.exerciseapp.Exercise.CreateExercise;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseViewModel extends ViewModel {

    private final Map<ExerciseIntegerFields, Integer> numericalExerciseMap = new HashMap<>();

    public final MutableLiveData<Map<ExerciseIntegerFields, Integer>> sharedNumericalValues
            = new MutableLiveData<>();

    public void setNumericalValues(ExerciseIntegerFields name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The number cannot be negative");
        }
        this.numericalExerciseMap.put(name, value);
        this.sharedNumericalValues.setValue(numericalExerciseMap);
    }

    public LiveData<Map<ExerciseIntegerFields, Integer>> getNumericalValues() {
        for (ExerciseIntegerFields field : ExerciseIntegerFields.values()) {
            if (!numericalExerciseMap.containsKey(field)) {
                numericalExerciseMap.put(field, 0);
            }
        }
        sharedNumericalValues.setValue(numericalExerciseMap);
        return sharedNumericalValues;
    }

    public enum ExerciseIntegerFields {
        KCAL,
        DURATION,
        USER_ID,
        NUMBER_OF_SERIES,
        VOLUME,
        BREAK_LENGTH
    }


    private final Map<ExerciseTextFields, String> exerciseTextMap = new HashMap<>();

    public final MutableLiveData<Map<ExerciseTextFields, String>> sharedTextValues
            = new MutableLiveData<>();

    public void setTextValues(ExerciseTextFields name, String value) {
        this.exerciseTextMap.put(name, value);
        this.sharedTextValues.setValue(exerciseTextMap);
    }

    public LiveData<Map<ExerciseTextFields, String>> getTextValues() {
        for (ExerciseTextFields field : ExerciseTextFields.values()) {
            if (!exerciseTextMap.containsKey(field)) {
                exerciseTextMap.put(field, null);
            }
        }
        sharedTextValues.setValue(exerciseTextMap);
        return sharedTextValues;
    }

    public String getImage() {
        return sharedTextValues.getValue() != null ?
                sharedTextValues.getValue().get(ExerciseTextFields.IMAGE) : null;
    }

    public String getName() {
        return sharedTextValues.getValue() != null ?
                sharedTextValues.getValue().get(ExerciseTextFields.NAME) : null;
    }

    public String getEquipment() {
        return sharedTextValues.getValue() != null ?
                sharedTextValues.getValue().get(ExerciseTextFields.EQUIPMENT) : null;
    }

    public String getDescription() {
        return sharedTextValues.getValue() != null ?
                sharedTextValues.getValue().get(ExerciseTextFields.DESCRIPTION) : null;
    }

    public enum ExerciseTextFields {

        IMAGE,
        NAME,
        EQUIPMENT,
        DESCRIPTION
    }

    private final Map<BodyParts, Boolean> bodyPartsMap = new HashMap<>();

    public final MutableLiveData<Map<BodyParts, Boolean>> sharedBodyParts
            = new MutableLiveData<>();

    public void setBodyPartsValues(BodyParts name, Boolean value) {
        this.bodyPartsMap.put(name, value);
        this.sharedBodyParts.setValue(bodyPartsMap);
    }

    public void setBodyPartsList(Map<BodyParts, Boolean> bodyParts) {
        this.bodyPartsMap.putAll(bodyParts);
        sharedBodyParts.setValue(bodyPartsMap);
    }

    public LiveData<Map<BodyParts, Boolean>> getBodyPartsValues() {
        for (BodyParts field : BodyParts.values()) {
            if (!bodyPartsMap.containsKey(field)) {
                bodyPartsMap.put(field, false);
            }
        }
        sharedBodyParts.setValue(bodyPartsMap);
        return sharedBodyParts;
    }


    private final MutableLiveData<ExerciseType> sharedExerciseType = new MutableLiveData<>();

    public void setExerciseType(ExerciseType exerciseType) {
        sharedExerciseType.setValue(exerciseType);
    }

    public LiveData<ExerciseType> getExerciseType() {
        return sharedExerciseType;
    }


    public final MutableLiveData<Level> sharedLevel = new MutableLiveData<>();

    public void setLevel(Level level) {
        sharedLevel.setValue(level);
    }

    public LiveData<Level> getLevel() {
        return sharedLevel;
    }


    public final MutableLiveData<FromWhere> sharedFromWhere = new MutableLiveData<>();

    public void setFromWhere(FromWhere fromWhere) {
        sharedFromWhere.setValue(fromWhere);
    }

    public LiveData<FromWhere> getFromWhere() {
        return sharedFromWhere;
    }

    public final MutableLiveData<Integer> sharedUserId = new MutableLiveData<>();

    public void setUserId(Integer userId) {
        sharedUserId.setValue(userId);
    }

    public LiveData<Integer> getUserId() {
        return sharedUserId;
    }

    public final MutableLiveData<List<Integer>> sharedEquipmentIds =
            new MutableLiveData<>(null);

    public void setEquipmentListIds(List<Integer> equipmentIds) {
        if (!equipmentIds.isEmpty()) {
            sharedEquipmentIds.setValue(equipmentIds);
        }
    }

    public LiveData<List<Integer>> getEquipmentIds() {
        return sharedEquipmentIds;
    }


    public final MutableLiveData<Map<Integer, Double>> sharedEquipmentVolume
            = new MutableLiveData<>();

    public void setEquipmentListVolume(Map<Integer, Double> listVolume) {
        for (Map.Entry<Integer, Double> entry : listVolume.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IllegalArgumentException("The number cannot be negative");
            }
        }
        sharedEquipmentVolume.setValue(listVolume);
    }

    public Map<Integer, Double> getEquipmentMapVolume() {
        return sharedEquipmentVolume.getValue() != null ?
                sharedEquipmentVolume.getValue() : new HashMap<>();
    }
}
