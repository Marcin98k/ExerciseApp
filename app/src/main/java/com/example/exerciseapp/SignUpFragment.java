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
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mInterfaces.IUserData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button signUpBtn;

    private Editable editable;

    private String username, email;

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
            throw new ClassCastException(context +
                    " must implement IUserData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initializeViews(mView);
        setupSignUpBtn(signUpBtn);

        return mView;
    }

    private void initializeViews(View v) {
        usernameEditText = v.findViewById(R.id.fSignUp_username);
        emailEditText = v.findViewById(R.id.fSignUp_email);
        passwordEditText = v.findViewById(R.id.fSignUp_password);
        signUpBtn = v.findViewById(R.id.fSignUp_sign_in);
    }

    private void setupSignUpBtn(Button signUpBtn) {
        signUpBtn.setOnClickListener(v -> {
            username = usernameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            editable = passwordEditText.getText();
            password = new char[editable.length()];

            for (int i = 0; i < editable.length(); i++) {
                password[i] = editable.charAt(i);
            }

            if (validateUser(username, email)) {
                iUserData.data(username, email, hashPassword(password));
            }
        });
    }

    @VisibleForTesting
    private boolean validateUser(String username, String email) {

        String usernamePatternStr = "^[a-zA-Z0-9]+([a-zA-Z0-9][a-zA-Z0-9])*[a-zA-Z0-9]+$";
        String emailPatternStr = "^[\\w!#$%&amp;'*+/=?'{|}~^-]+(?:\\.[\\w!#$%&'*+/=?'{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern usernamePattern = Pattern.compile(usernamePatternStr);
        Matcher usernameMatcher = usernamePattern.matcher(username);

        Pattern emailPattern = Pattern.compile(emailPatternStr);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!usernameMatcher.matches()) {
            showToast("Incorrect username");
            return false;
        }

        if (!emailMatcher.matches()) {
            showToast("Incorrect email");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @VisibleForTesting
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