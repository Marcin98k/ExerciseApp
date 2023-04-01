package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {

    SendByte sendByte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button signIn = mView.findViewById(R.id.fWelcome_Btn_signIn);
        Button signUp = mView.findViewById(R.id.fWelcome_Btn_signUp);

//        signIn.setOnClickListener(v -> sendByte.mByte((byte) 0));
//        signUp.setOnClickListener(v -> sendByte.mByte((byte) 1));

        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            sendByte = (SendByte) context;
        } catch(RuntimeException e) {
            throw new RuntimeException(context.toString()
                    + " must implement SendByte");
        }
    }
}