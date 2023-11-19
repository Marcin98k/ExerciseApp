package com.example.exerciseapp.mModels;

public class UnitModel {

    private final long id;
    private final int height;
    private final int weight;
    private final int unitHeight;
    private final int unitWeight;

    private UnitModel(UnitModelBuilder unitModelBuilder) {
        this.id = unitModelBuilder.id;
        this.height = unitModelBuilder.height;
        this.weight = unitModelBuilder.weight;
        this.unitHeight = unitModelBuilder.unitHeight;
        this.unitWeight = unitModelBuilder.unitWeight;
    }

    public long getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getUnitHeight() {
        return unitHeight;
    }

    public int getUnitWeight() {
        return unitWeight;
    }

    public static class UnitModelBuilder {

        private long id;
        private int height;
        private int weight;
        private int unitHeight;
        private int unitWeight;


        public UnitModelBuilder setHeight(long id, int height, int unitHeight) {
            this.id = id;
            this.height = height;
            this.unitHeight = unitHeight;
            return this;
        }

        public UnitModelBuilder setWeight(long id, int weight, int unitWeight) {
            this.id = id;
            this.weight = weight;
            this.unitWeight = unitWeight;
            return this;
        }

        public UnitModelBuilder setAll(long id, int height, int unitHeight, int weight,
                                       int unitWeight) {
            this.id = id;
            this.height = height;
            this.unitHeight = unitHeight;
            this.weight = weight;
            this.unitWeight = unitWeight;
            return this;
        }

        public UnitModel build() {
            return new UnitModel(this);
        }
    }
}
