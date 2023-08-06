package com.example.exerciseapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;

import java.util.List;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DetailsFragment";
    private Button nextBtnView;


    private long id;
    private String btnText;
    private List<ExerciseModel> exerciseDetail;

    private final byte POSITION = 0;


    private ContentBD contentBD;
    private UpdateIntegersDB updateIntegersDB;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement UpdateIntegerDB");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            btnText = getArguments().getString("btnText",
                    String.valueOf(R.string.next)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        contentBD = new ContentBD(requireActivity());
        initView(mView);
        nextBtnView.setOnClickListener(this);
        return mView;
    }

    private void initView(View v) {

        ImageView imageIMG = v.findViewById(R.id.frag_details_image);
        TextView nameTV = v.findViewById(R.id.frag_details_name);
        TextView levelTV = v.findViewById(R.id.frag_details_level);
        ImageView bodyPartsIMG = v.findViewById(R.id.frag_details_body_parts_image);
        TextView equipmentTV = v.findViewById(R.id.frag_details_equipment);
        TextView typeTV = v.findViewById(R.id.frag_details_type);
        TextView kcalTV = v.findViewById(R.id.frag_details_kcal);
        TextView durationTV = v.findViewById(R.id.frag_details_duration);
        TextView descriptionTV = v.findViewById(R.id.frag_details_description);
        nextBtnView = v.findViewById(R.id.frag_details_button);

        exerciseDetail = contentBD.showExerciseById(id);

//        imageIMG.setImageResource(exerciseDetail.get(0).getImage());

        nameTV.setText(exerciseDetail.get(POSITION).getName());
        levelTV.setText(String.valueOf(exerciseDetail.get(POSITION).getLevel()));
//        bodyPartsIMG.setImageBitmap(exerciseDetail.get(POSITION).getBodyParts());
        equipmentTV.setText(exerciseDetail.get(POSITION).getEquipment());
        typeTV.setText(String.valueOf(exerciseDetail.get(POSITION).getType()));
        kcalTV.setText(String.valueOf(exerciseDetail.get(POSITION).getKcal()));
        durationTV.setText(String.valueOf(exerciseDetail.get(POSITION).getDuration()));
        descriptionTV.setText(String.valueOf(exerciseDetail.get(POSITION).getDescription()));

        nextBtnView.setText(btnText);
    }

    @Override
    public void onClick(View view) {
        updateIntegersDB.values("detailsFragment", (int) id,
                exerciseDetail.get(POSITION).getType(),
                0);
    }
}