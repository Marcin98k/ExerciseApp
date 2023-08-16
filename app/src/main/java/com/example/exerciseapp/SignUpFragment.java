package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.IUserData;
import com.google.android.material.textfield.TextInputEditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {


    private EditText usernameTV;
    private EditText emailTV;
    private EditText passwordTV;

    private Button confirmBtn;

    private Editable editable;

    private String usernameStr, emailStr;

    private char[] password;


    private IUserData iUserData;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iUserData = (IUserData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement IUserData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        usernameTV = mView.findViewById(R.id.fSignUp_ET_username);
        emailTV = mView.findViewById(R.id.fSignUp_ET_email);
        passwordTV = mView.findViewById(R.id.fSignUp_ET_password);


        Log.i("TAG", "onCreateView: (username1) " + usernameTV.getText().toString().trim());
        confirmBtn = mView.findViewById(R.id.frag_sign_up_confirm_btn);
        confirmBtn.setOnClickListener(v -> {
            usernameStr = usernameTV.getText().toString().trim();
            emailStr = emailTV.getText().toString().trim();
            editable = passwordTV.getText();
            password = new char[editable.length()];
            Log.i("TAG", "onCreateView {pass}: " + passwordTV.getText().toString());
            for (int i = 0; i < editable.length(); i++) {
                password[i] = editable.charAt(i);
            }

            Log.i("TAG", "onCreateView: username: " + usernameStr);
            if (validateUser(usernameStr, emailStr)) {
                iUserData.data(usernameStr, emailStr, hashPassword(password));
            }
        });

        return mView;
    }

    private boolean validateUser(String username, String email) {

        String usernameFormula = "^[a-zA-Z0-9]+([a-zA-Z0-9][a-zA-Z0-9])*[a-zA-Z0-9]+$";
        String emailFormula = "^[\\w!#$%&amp;'*+/=?'{|}~^-]+(?:\\.[\\w!#$%&'*+/=?'{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern usernamePattern = Pattern.compile(usernameFormula);
        Matcher usernameMatcher = usernamePattern.matcher(username);

        Pattern emailPattern = Pattern.compile(emailFormula);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!usernameMatcher.matches()) {
            Toast.makeText(requireContext(), "Incorrect username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!emailMatcher.matches()) {
            Toast.makeText(requireActivity(), "Incorrect email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static byte[] hashPassword(char[] password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            for (char c : password) {
                messageDigest.update((byte) c);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}