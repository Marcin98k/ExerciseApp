package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment implements FragmentRespond {

    private EditText username;
    private EditText emailET;
    private EditText passwordET;
    private Editable editable;

    private Button btn1;

    private TextInputLayout emailContainer;
    private TextInputLayout passwordContainer;

    private String emailStr;
    private char[] passwordChar;
    private long userId = -1;

    private DBHelper dbHelper;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        dbHelper = new DBHelper(requireActivity());
        emailET = mView.findViewById(R.id.fSignIn_ET_email);
        passwordET = mView.findViewById(R.id.fSignIn_ET_password);

        emailContainer = mView.findViewById(R.id.fSignIn_container_email);
        passwordContainer = mView.findViewById(R.id.fSignIn_container_password);

        btn1 = mView.findViewById(R.id.fSignIn_btn);

        btn1.setOnClickListener(v -> {

            emailStr = emailET.getText().toString().trim();
            editable = passwordET.getText();
            passwordChar = new char[editable.length()];
            for (int i = 0; i < editable.length(); i++) {
                passwordChar[i] = editable.charAt(i);
            }

            long id = logIn(emailStr, passwordChar);
            if (id != -1) {
                dbHelper.accountStatus(id, true);
                Intent intent = new Intent(requireActivity(), LibraryActivity.class);
                intent.putExtra("userID", id);
                startActivity(intent);
            }
        });

        return mView;
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

    private long logIn(String email, char[] pass) {

        String emailPatter = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailPatter);
        Matcher matcher = pattern.matcher(email);

        Log.i("TAG", "logIn: " + email + " pass: " + Arrays.toString(pass));

        if (!matcher.matches()) {
            Toast.makeText(requireActivity(), "Incorrect login or passwordET", Toast.LENGTH_SHORT).show();
            passwordET.setText("");
            return -1;
        }
        byte[] hashPassword = hashPassword(pass);

        InsertResult result = dbHelper.logIn(email, hashPassword);

        if (!result.isSuccess()) {
            Toast.makeText(requireActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
            Log.i("SignIn", "logIn: User does not exist");
            return -1;
        }
        Log.i("TAG", "logIn: getIndex");
        return result.getIndex();
    }

    @Override
    public void fragmentMessage() {

    }
}