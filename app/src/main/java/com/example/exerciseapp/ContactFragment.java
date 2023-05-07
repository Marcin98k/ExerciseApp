package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ContactFragment extends Fragment {
    private EditText subjectET;
    private EditText messageET;
    private Button sendBT;
    private String userEmail;
    private final String APP_EMAIL = "marcin3009k@gmail.com";

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userEmail = getArguments().getString("userEmail");
        } else {
            userEmail = "TestEmail@mail.com";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(mView);


        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        return mView;
    }

    private void sendMail() {

        String subject = subjectET.getText().toString();
        String message = messageET.getText().toString();

        Intent userMessageIntent = new Intent(Intent.ACTION_SEND);
        userMessageIntent.putExtra(Intent.EXTRA_EMAIL, APP_EMAIL);
        userMessageIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        userMessageIntent.putExtra(Intent.EXTRA_TEXT, (CharSequence) message);

        userMessageIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(userMessageIntent, "Choose an email client"));
    }

    private void initView(View v) {
        subjectET = v.findViewById(R.id.fContact_subject);
        messageET = v.findViewById(R.id.fContact_message);
        sendBT = v.findViewById(R.id.fContact_button);
    }
}