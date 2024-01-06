package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mInterfaces.FragmentRespond;

import java.util.ArrayList;
import java.util.List;

public class SelectEquipment extends Fragment {

    private GridView gridView;

    private GridAdapter gridAdapter;
    private TrainingRepository trainingRepository;
    private ExerciseViewModel exerciseViewModel;

    private FragmentRespond fragmentRespond;

    public SelectEquipment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentRespond = (FragmentRespond) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentRespond");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_equipment, container, false);
        initializeDatabase();
        initializeViewModel();
        initializeWidgets(mView);
        fillGridView(gridView);
        fragmentRespond.fragmentAnswer(FragmentErrors.NO_ERROR, "");
        return mView;
    }

    private void initializeDatabase() {
        trainingRepository = new TrainingRepository(requireActivity());
    }

    private void initializeViewModel() {
        exerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
    }

    private void initializeWidgets(View v) {
        gridView = v.findViewById(R.id.grid_view_equipment);
    }

    public void fillGridView(GridView gridView) {

        List<GridModel> gridList = new ArrayList<>();

        trainingRepository.getAllEquipment().observe(getViewLifecycleOwner(), equipmentModels -> {

            for (EquipmentModel equipment : equipmentModels) {
                GridModel gridModel = new GridModel();
                gridModel.setId(equipment.getId());
                gridModel.setImage(equipment.getImage());
                gridModel.setName(equipment.getName());
                gridList.add(gridModel);
            }

            List<Integer> selectedPositions = getSelectedPositions();

            gridAdapter = new GridAdapter(requireContext(), gridList, selectedPositions);
            gridView.setAdapter(gridAdapter);
            gridView.setNumColumns(3);
        });
    }

    private List<Integer> getSelectedPositions() {
        List<Integer> equipmentList = exerciseViewModel.getEquipmentIds().getValue();
        return equipmentList != null ? equipmentList : new ArrayList<>();
    }

    @Override
    public void onPause() {
        super.onPause();
        exerciseViewModel.setEquipmentListIds(gridAdapter.getIdsFromSelectedPositions());
    }
}