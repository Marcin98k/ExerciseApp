package com.example.exerciseapp.mInterfaces;

public interface TrainingSummaryHandler {
        void summaryMessage(String name, String exerciseName, String duration, long userId,
                            long extensionId, boolean conditionVal);
    }
