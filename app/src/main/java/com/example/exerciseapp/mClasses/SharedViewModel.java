package com.example.exerciseapp.mClasses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    //    Integer;
    private final MutableLiveData<Integer> shareInt = new MutableLiveData<>();

    public void setShareInt(Integer shareInt) {
        this.shareInt.setValue(shareInt);
    }

    public LiveData<Integer> getSharedInt() {
        return shareInt;
    }

    private final MutableLiveData<String> shareStr = new MutableLiveData<>();

    public void setShareStr(String shareStr) {
        this.shareStr.setValue(shareStr);
    }

    public LiveData<String> getSharedStr() {
        return shareStr;
    }

//    Integer[];
    public final MutableLiveData<Integer[]> shareTabInt = new MutableLiveData<>();

    public void setShareTabInt(Integer[] shareTabInt) {
        this.shareTabInt.setValue(shareTabInt);
    }

    public LiveData<Integer[]> getSharedTabInt() {
        return shareTabInt;
    }

}
