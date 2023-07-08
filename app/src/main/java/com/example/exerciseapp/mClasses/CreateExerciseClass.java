package com.example.exerciseapp.mClasses;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Objects;

public class CreateExerciseClass extends ViewModel {

    public final String TYPE = "type";
    public final String SETS = "sets";
    public final String VOLUME = "volume";
    public final String REST = "rest";
    public final String EXERCISE = "exercise";

    public HashMap<String, MutableLiveData<Integer>> map = new HashMap<String, MutableLiveData<Integer>>() {{
        put(TYPE, new MutableLiveData<>());
        put(SETS, new MutableLiveData<>());
        put(VOLUME, new MutableLiveData<>());
        put(REST, new MutableLiveData<>());
        put(EXERCISE, new MutableLiveData<>());
    }};

    public MutableLiveData<Integer> getValue(String key) {
        return map.get(key);
    }

    public void setValue(String key, Integer value) {
        if (map.get(key) != null) {
            Objects.requireNonNull(map.get(key)).setValue(value);
        }
    }
}
