package com.example.exerciseapp.mClasses;

public class InsertResult {

    private long index;
    private boolean success;

    public InsertResult(long index, boolean success) {
        this.index = index;
        this.success = success;
    }
    public InsertResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public long getIndex() {
        return index;
    }
}
