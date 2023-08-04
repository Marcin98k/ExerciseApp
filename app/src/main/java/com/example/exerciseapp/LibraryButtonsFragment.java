package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;

public class LibraryButtonsFragment extends Fragment implements View.OnClickListener {

    public static final String NAME = "LibraryButtonFragment";

    private ISingleIntegerValue iSingleIntegerValue;

    public LibraryButtonsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iSingleIntegerValue = (ISingleIntegerValue) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement ISingleIntegerValue");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_library_buttons, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {

        Button firstBtn = v.findViewById(R.id.frag_library_button_first_btn);
        firstBtn.setOnClickListener(this);

        Button secondBtn = v.findViewById(R.id.frag_library_button_second_btn);
        secondBtn.setOnClickListener(this);

        Button thirdBtn = v.findViewById(R.id.frag_library_button_third_btn);
        thirdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.frag_library_button_first_btn):
                iSingleIntegerValue.singleIntValue(NAME, 1);
                break;
            case (R.id.frag_library_button_second_btn):
                iSingleIntegerValue.singleIntValue(NAME, 2);
                break;
            case (R.id.frag_library_button_third_btn):
                iSingleIntegerValue.singleIntValue(NAME, 3);
                break;
        }
    }
}