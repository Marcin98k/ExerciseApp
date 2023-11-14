package com.example.exerciseapp.mClasses;

import android.util.Log;

public class InsertResult {

    private static final String TAG = "InsertResult";
    private long index;
    private boolean success;

    public InsertResult(long index, boolean success) {
        this.index = index;
        this.success = success;
        alert(success);
    }

    @Override
    public String toString() {
        return "InsertResult{" +
                "index=" + index +
                ", success=" + success +
                '}';
    }

    private void alert(boolean param) {
        if (param) {
            Log.i(TAG, "(Success)");
        } else {
            Log.i(TAG, "(Fail)");
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public long getIndex() {
        return index;
    }
}
