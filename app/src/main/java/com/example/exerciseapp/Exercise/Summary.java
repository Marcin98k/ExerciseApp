package com.example.exerciseapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.TrainingTimer;
import com.example.exerciseapp.mInterfaces.FragmentRespondToChange;
import com.example.exerciseapp.mInterfaces.TrainingSummaryHandler;

public class Summary extends Fragment implements FragmentRespondToChange {

    private static final String ARG_FRAGMENT_TYPE = "fragmentType";
    private static final String ARG_USER_ID = "userID";
    private static final String ARG_NAME = "name";
    private static final String ARG_EXTENSION_ID = "extensionId";
    private static final String ARG_DURATION = "duration";

    private TextView titleTXT, nameTXT;
    private TextView timeTitleTXT, timeTXT;

    private FragmentType fragmentType;
    private long userId, extensionId;
    private double duration;
    private String name;

    private TrainingSummaryHandler trainingSummaryHandler;

    public static Summary newInstanceForCreating(FragmentType fragmentType, String name) {
        Summary fragment = new Summary();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAGMENT_TYPE, fragmentType);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public static Summary newInstanceForExercise(FragmentType fragmentType, int userId, int extensionId,
                                                 int duration, String name) {
        Summary fragment = new Summary();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAGMENT_TYPE, fragmentType);
        args.putInt(ARG_USER_ID, userId);
        args.putInt(ARG_EXTENSION_ID, extensionId);
        args.putInt(ARG_DURATION, duration);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public Summary() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            trainingSummaryHandler = (TrainingSummaryHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement TrainingSummaryHandler");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = (FragmentType) getArguments().getSerializable(ARG_FRAGMENT_TYPE);
            name = getArguments().getString(ARG_NAME);
            userId = getArguments().getLong(ARG_USER_ID);
            extensionId = getArguments().getLong(ARG_EXTENSION_ID);
            duration = getArguments().getDouble(ARG_DURATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        initializeWidgets(mView);
        fillWidgets();
        return mView;
    }

    private void initializeWidgets(View v) {
        titleTXT = v.findViewById(R.id.text_title_summary);
        nameTXT = v.findViewById(R.id.text_name_summary);
        timeTitleTXT = v.findViewById(R.id.text_title_time_summary);
        timeTXT = v.findViewById(R.id.text_time_summary);
    }

    private void fillWidgets() {

        if (fragmentType == FragmentType.CREATE_SUMMARY) {
            titleTXT.setText(R.string.exercise_created);
            nameTXT.setText(name);
            timeTitleTXT.setVisibility(View.GONE);
            timeTXT.setText(R.string.exercise_has_been_added);
        } else {
            titleTXT.setText(R.string.completed);
            nameTXT.setText(name);
            new TrainingTimer.TrainingTimerBuilder(requireActivity())
                    .setSecond((int) duration)
                    .build()
                    .dynamicIncreaseTime(timeTXT);
        }
    }

    @Override
    public void fragmentMessage() {
        if (name.isEmpty() || userId < 0 || extensionId < 0 || duration < 0) {
            throw new IllegalStateException(getContext() + " -> Incorrect userData");
        }
        trainingSummaryHandler.summaryMessage(getTag(), name, String.valueOf(duration), userId,
                extensionId, true);
    }
}