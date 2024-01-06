package com.example.exerciseapp.mEnums;

import androidx.annotation.NonNull;

public enum ExerciseType {
    REPETITION,
    TIME;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case REPETITION: return "Repetition";
            case TIME: return "Time";
            default: throw new IllegalArgumentException();
        }
    }
}
