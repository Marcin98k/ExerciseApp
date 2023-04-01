package com.example.exerciseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class SignInFragment extends Fragment implements FragmentRespond {

    private EditText username;
    private EditText email;
    private EditText password;

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



        return mView;
    }

    @Override
    public void fragmentMessage() {

    }
}