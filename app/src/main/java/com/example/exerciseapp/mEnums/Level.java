package com.example.exerciseapp.mEnums;

import androidx.annotation.NonNull;

public enum Level {

    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case BEGINNER: return "Beginner";
            case INTERMEDIATE: return "Intermediate";
            case ADVANCED: return "Advanced";
            default: throw new IllegalArgumentException();
        }
    }
}
