package com.example.exerciseapp.mModels;

public class BodyPartsModel {

    private long id;
    private boolean chestStatus;
    private boolean backStatus;
    private boolean shouldersStatus;
    private boolean armsStatus;
    private boolean ABSStatus;
    private boolean legsStatus;

    public BodyPartsModel() {}

    public BodyPartsModel(long id, boolean chestStatus, boolean backStatus, boolean shouldersStatus,
                          boolean armsStatus, boolean ABSStatus, boolean legsStatus) {
        this.id = id;
        this.chestStatus = chestStatus;
        this.backStatus = backStatus;
        this.shouldersStatus = shouldersStatus;
        this.armsStatus = armsStatus;
        this.ABSStatus = ABSStatus;
        this.legsStatus = legsStatus;
    }

    public long getId() {
        return id;
    }

    public boolean isChestStatus() {
        return chestStatus;
    }

    public boolean isBackStatus() {
        return backStatus;
    }

    public boolean isShouldersStatus() {
        return shouldersStatus;
    }

    public boolean isArmsStatus() {
        return armsStatus;
    }

    public boolean isABSStatus() {
        return ABSStatus;
    }

    public boolean isLegsStatus() {
        return legsStatus;
    }
}
