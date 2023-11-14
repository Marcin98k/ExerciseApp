package com.example.exerciseapp.mModels;

public class ExerciseModel {

    private final int id, userId, extensionId;
    private final int numberOfSeries;
    private final int exerciseTime, exerciseRepetitions;
    private final int volume;
    private final int breakLength;

    private ExerciseModel(UserExerciseModelBuilder userExerciseModelBuilder) {
        this.id = userExerciseModelBuilder.id;
        this.userId = userExerciseModelBuilder.userId;
        this.extensionId = userExerciseModelBuilder.extensionId;
        this.numberOfSeries = userExerciseModelBuilder.numberOfSeries;
        this.exerciseTime = userExerciseModelBuilder.exerciseTime;
        this.exerciseRepetitions = userExerciseModelBuilder.exerciseRepetitions;
        this.volume = userExerciseModelBuilder.volume;
        this.breakLength = userExerciseModelBuilder.breakLength;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public int getNumberOfSeries() {
        return numberOfSeries;
    }

    public int getExerciseTime() {
        return exerciseTime;
    }

    public int getExerciseRepetitions() {
        return exerciseRepetitions;
    }

    public int getVolume() {
        return volume;
    }

    public int getBreakLength() {
        return breakLength;
    }

    public static class UserExerciseModelBuilder {
        private final int id;
        private int userId, extensionId;
        private final int numberOfSeries;
        private int exerciseTime, exerciseRepetitions;
        private final int volume;
        private final int breakLength;

        public UserExerciseModelBuilder(int id, int numberOfSeries, int volume,
                                        int breakLength) {
            this.id = id;
            this.numberOfSeries = numberOfSeries;
            this.volume = volume;
            this.breakLength = breakLength;
        }

        public UserExerciseModelBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserExerciseModelBuilder setExtensionId(int extensionId) {
            this.extensionId = extensionId;
            return this;
        }

        public UserExerciseModelBuilder setExerciseTime(int exerciseTime) {
            this.exerciseTime = exerciseTime;
            return this;
        }

        public UserExerciseModelBuilder setExerciseRepetitions(int exerciseRepetitions) {
            this.exerciseRepetitions = exerciseRepetitions;
            return this;
        }

        public ExerciseModel build() {
            return new ExerciseModel(this);
        }
    }
}
