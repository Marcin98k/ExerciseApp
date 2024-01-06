package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mInterfaces.FragmentRespond;

import java.util.List;
import java.util.Map;

public class EquipmentVolume extends Fragment {

    private RecyclerView equipmentView;

    private VolumeAdapter volumeAdapter;
    private ExerciseViewModel exerciseViewModel;

    private FragmentRespond fragmentRespond;

    public EquipmentVolume() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_equipment_volume, container, false);
        initializeViewModel();
        initializeWidgets(mView);
        return mView;
    }

    private void initializeViewModel() {
        exerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        exerciseViewModel.getEquipmentIds().observe(getViewLifecycleOwner(),
                this::getEquipmentFromDbAndSetAdapter);
    }

    private void initializeWidgets(View v) {
        equipmentView = v.findViewById(R.id.recycler_view_list_equipment_volume);
    }

    private void getEquipmentFromDbAndSetAdapter(List<Integer> equipmentIds) {
        TrainingRepository trainingRepository = new TrainingRepository(requireActivity());
        trainingRepository.getAllEquipmentByIds(equipmentIds).observe(getViewLifecycleOwner(),
                equipmentModels -> {
                    equipmentView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    equipmentView.setAdapter(setVolumeAdapter(equipmentModels));
                });
    }

    private VolumeAdapter setVolumeAdapter(List<EquipmentModel> equipmentList) {
        volumeAdapter = new VolumeAdapter(requireActivity(), equipmentList, getVolumeFromViewModel());
        fragmentRespond.fragmentAnswer(FragmentErrors.NO_ERROR, "");
        return volumeAdapter;
    }

    private Map<Integer, Double> getVolumeFromViewModel() {
        return exerciseViewModel.getEquipmentMapVolume();
    }

    @Override
    public void onPause() {
        super.onPause();
        exerciseViewModel.setEquipmentListVolume(volumeAdapter.getEquipmentVolume());
    }
}