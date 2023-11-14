package com.example.exerciseapp.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;

public class ContactFragment extends Fragment {

    private EditText subjectET;
    private EditText messageET;
    private Button sendBT;


    private String userEmail;
    private static final String APP_EMAIL = "marcin3009k@gmail.com";
    private static final String FRAGMENT_NAME = "/Contact";


    private TitleChangeListener titleChangeListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            titleChangeListener = (TitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement TitleChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleChangeListener != null) {
            titleChangeListener.title(FRAGMENT_NAME);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (titleChangeListener != null) {
            titleChangeListener.title("");
        }
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
        sendBT.setOnClickListener(view -> sendMail());
        return mView;
    }

    private void initView(View v) {
        subjectET = v.findViewById(R.id.fContact_subject);
        messageET = v.findViewById(R.id.fContact_message);
        sendBT = v.findViewById(R.id.fContact_button);
    }

    private void sendMail() {

        String subject = subjectET.getText().toString();
        String message = messageET.getText().toString();

        Intent userMessageIntent = new Intent(Intent.ACTION_SEND);
        userMessageIntent.setType("message/rfc822");
        userMessageIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{APP_EMAIL});
        userMessageIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        userMessageIntent.putExtra(Intent.EXTRA_TEXT, (CharSequence) message);
        userMessageIntent.putExtra(Intent.EXTRA_CC, new String[]{userEmail});

        try {
            startActivity(Intent.createChooser(userMessageIntent, "Choose an email client"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "Email client not found.", Toast.LENGTH_SHORT).show();
        }
    }
}