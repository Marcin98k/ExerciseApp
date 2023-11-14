package com.example.exerciseapp.mInterfaces;

public interface TrainingSummaryHandler {
        void summaryMessage(String name, String exerciseName, String duration, long exerciseId,
                            long extensionId, int fromWhere, boolean conditionVal);
    }
