package com.example.exerciseapp.mModels;

import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.TrainingType;

public class TrainingDTO {

    private final long id, userId, exerciseId, extensionId;
    private final String exerciseIds;
    private final TrainingType trainingType;
    private final FromWhere fromWhere;

    private TrainingDTO(TrainingDTOBuilder trainingDTOBuilder) {
        this.id = trainingDTOBuilder.id;
        this.userId = trainingDTOBuilder.userId;
        this.exerciseId = trainingDTOBuilder.exerciseId;
        this.extensionId = trainingDTOBuilder.extensionId;
        this.exerciseIds = trainingDTOBuilder.exerciseIds;
        this.trainingType = trainingDTOBuilder.trainingType;
        this.fromWhere = trainingDTOBuilder.fromWhere;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public long getExtensionId() {
        return extensionId;
    }

    public String getExerciseIds() {
        return exerciseIds;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public FromWhere getFromWhere() {
        return fromWhere;
    }

    public static class TrainingDTOBuilder {
        private final long id, userId;
        private long exerciseId, extensionId;
        ;
        private String exerciseIds;
        private final TrainingType trainingType;
        private final FromWhere fromWhere;

        public TrainingDTOBuilder(long id, long userId, TrainingType trainingType, FromWhere fromWhere) {
            this.id = id;
            this.userId = userId;
            this.trainingType = trainingType;
            this.fromWhere = fromWhere;
        }

        public TrainingDTOBuilder setExerciseId(long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public TrainingDTOBuilder setExtensionId(long extensionId) {
            this.extensionId = extensionId;
            return this;
        }

        public TrainingDTOBuilder setExerciseIds(String exerciseIds) {
            this.exerciseIds = exerciseIds;
            return this;
        }

        public TrainingDTO build() {

            switch (trainingType) {
                case EXERCISE:
                    if (exerciseId <= 0 || extensionId <= 0) {
                        throw new IllegalStateException("Exercise id and/or extension id is incorrect");
                    }
                    break;
                case WORKOUT:
                    if (exerciseIds.isEmpty()) {
                        throw new IllegalStateException("Exercise list is empty");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid training equipmentType");
            }
            return new TrainingDTO(this);
        }
    }
}
