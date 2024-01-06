package com.example.exerciseapp.SignInANDSingUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.LibraryActivity;
import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mInterfaces.FragmentRespondToChange;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragmentToChange extends Fragment implements FragmentRespondToChange {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Editable editable;

    private Button signInBtn;

    private TextInputLayout loginContainer;
    private TextInputLayout passwordContainer;

    private String login;
    private char[] password;

    private DBHelper dbHelper;

    public SignInFragmentToChange() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeViews(mView);

        dbHelper = new DBHelper(requireActivity());

        setupSignInButton(signInBtn);
        return mView;
    }

    private void initializeViews(View v) {
        loginEditText = v.findViewById(R.id.fSignIn_ET_email);
        passwordEditText = v.findViewById(R.id.fSignIn_ET_password);

        loginContainer = v.findViewById(R.id.fSignIn_container_email);
        passwordContainer = v.findViewById(R.id.fSignIn_container_password);

        signInBtn = v.findViewById(R.id.fSignIn_btn);
    }

    @VisibleForTesting
    private void setupSignInButton(Button btn) {

        btn.setOnClickListener(v -> {
            login = loginEditText.getText().toString().trim();
            editable = passwordEditText.getText();
            password = new char[editable.length()];

            for (int i = 0; i < editable.length(); i++) {
                password[i] = editable.charAt(i);
            }

            long id = signIn(login, password);
            if (id != -1) {
                dbHelper.accountStatus(id, true);
                Intent intent = new Intent(requireActivity(), LibraryActivity.class);
                intent.putExtra("userID", id);
                startActivity(intent);
            }
        });
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
            throw new IllegalStateException(e);
        }
    }

    @VisibleForTesting
    private long signIn(String email, char[] pass) {

        String emailPattern = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            showToast("Incorrect login or password");
            passwordEditText.setText("");
            return -1;
        }
        byte[] hashPassword = hashPassword(pass);

        InsertResult result = dbHelper.logIn(email, hashPassword);

        if (!result.isSuccess()) {
            showToast("User does not exist");
            return -1;
        }
        return result.getIndex();
    }

    private void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fragmentMessage() {

    }
}