package com.example.exerciseapp.mModels;

public class ExtensionExerciseModel {

    private final long id;
    private final int numberOfSeries;
    private final int exerciseTime, exerciseRepetitions;
    private final int breakLength;

    private ExtensionExerciseModel(UserExerciseModelBuilder userExerciseModelBuilder) {
        this.id = userExerciseModelBuilder.id;
        this.numberOfSeries = userExerciseModelBuilder.numberOfSeries;
        this.exerciseTime = userExerciseModelBuilder.exerciseTime;
        this.exerciseRepetitions = userExerciseModelBuilder.exerciseRepetitions;
        this.breakLength = userExerciseModelBuilder.breakLength;
    }

    public long getId() {
        return id;
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

    public int getBreakLength() {
        return breakLength;
    }

    public static class UserExerciseModelBuilder {
        private final long id;
        private final int numberOfSeries;
        private int exerciseTime, exerciseRepetitions;
        private final int breakLength;

        public UserExerciseModelBuilder(long id, int numberOfSeries, int breakLength) {
            this.id = id;
            this.numberOfSeries = numberOfSeries;
            this.breakLength = breakLength;
        }

        public UserExerciseModelBuilder setExerciseTime(int exerciseTime) {
            this.exerciseTime = exerciseTime;
            return this;
        }

        public UserExerciseModelBuilder setExerciseRepetitions(int exerciseRepetitions) {
            this.exerciseRepetitions = exerciseRepetitions;
            return this;
        }

        public ExtensionExerciseModel build() {
            if (exerciseRepetitions <= 0 && exerciseTime <= 0) {
                throw new IllegalStateException("Either the number of repetitions or duration " +
                        "of the exercise must be greater than zero");
            }
            return new ExtensionExerciseModel(this);
        }
    }
}
