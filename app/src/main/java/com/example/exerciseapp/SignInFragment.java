package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.google.android.material.textfield.TextInputLayout;

public class SignInFragment extends Fragment implements FragmentRespond {

    private EditText username;
    private EditText email;
    private EditText password;

    private Button btn1;

    private TextInputLayout emailContainer;
    private TextInputLayout passwordContainer;

    private String emailStr;
//    Switch type variable <<!.!>>
    private String passwordStr;

    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_sign_in, container, false);


        email = mView.findViewById(R.id.fSignIn_ET_email);
        password = mView.findViewById(R.id.fSignIn_ET_password);

        emailContainer = mView.findViewById(R.id.fSignIn_container_email);
        passwordContainer = mView.findViewById(R.id.fSignIn_container_password);

        btn1 = mView.findViewById(R.id.fSignIn_btn);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        return mView;
    }

    @Override
    public void fragmentMessage() {

    }
}