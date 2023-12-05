package com.example.exerciseapp.SignInANDSingUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;

public class WelcomeFragment extends Fragment {

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
}