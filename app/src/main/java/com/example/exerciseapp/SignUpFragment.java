package com.example.exerciseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.exerciseapp.Interfaces.FragmentRespond;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements FragmentRespond {


    private EditText username;
    private EditText email;
    private EditText password;

    private String usernameStr, emailStr, passwordStr;

    private String regexUsername = "^[a-zA-Z0-9_!#$%&'*+/=?'{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String regexEmail = "^[\\w!#$%&amp;'*+/=?'{|}~^-]+(?:\\.[\\w!#$%&'*+/=?'{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    String regexPassword = "";

    Pattern patternUsername;
    Matcher matcherUsername;
    Pattern patternEmail;
    Matcher matcherEmail;
    Pattern patternPassword;
    Matcher matcherPassword;

    SharedViewModel sharedViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        username = mView.findViewById(R.id.fSignUp_ET_username);
        email = mView.findViewById(R.id.fSignUp_ET_email);
        password = mView.findViewById(R.id.fSignUp_ET_password);

        usernameStr = username.getText().toString();
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        patternUsername = Pattern.compile(regexUsername);
        matcherUsername = patternUsername.matcher(usernameStr);

        patternEmail = Pattern.compile(regexEmail);
        matcherEmail = patternEmail.matcher(emailStr);

        patternPassword = Pattern.compile(regexPassword);
        matcherPassword = patternPassword.matcher(passwordStr);

        return mView;
    }

    @Override
    public void fragmentMessage() {

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(0);
//        if (matcherUsername.matches()) {
//            sharedViewModel.setShareStr(usernameStr);
//        } else {
//            stringBuilder.append(1);
//        }
//        if (matcherEmail.matches()) {
//            sharedViewModel.setShareStr(emailStr);
//        } else {
//            stringBuilder.append(2);
//        }
//        if (matcherPassword.matches()) {
//            sharedViewModel.setShareStr(passwordStr);
//        } else {
//            stringBuilder.append(3);
//        }
    }
}