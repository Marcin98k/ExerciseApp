package com.example.exerciseapp.mDatabases;

import com.example.exerciseapp.mModels.GoalsModel;
import com.example.exerciseapp.mModels.PerformanceModel;
import com.example.exerciseapp.mModels.UnitsModel;
import com.example.exerciseapp.mModels.UserModel;

public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(UserModel userModel) {
        return userRepository.createUser(userModel);
    }

    public UserModel getUserById(long userId) {
        return userRepository.getUserById(userId);
    }

    public boolean createGoal(GoalsModel goalsModel) {
        return userRepository.createGoals(goalsModel);
    }

    public GoalsModel getGoalsById(long goalsId) {
        return userRepository.getGoalsById(goalsId);
    }

    public boolean updateGoals(GoalsModel goalsModel) {
        return userRepository.updateGoals(goalsModel);
    }

    public boolean createPerformance(PerformanceModel performanceModel) {
        return userRepository.createPerformance(performanceModel);
    }

    public PerformanceModel getPerformanceById(long performanceId) {
        return userRepository.getPerformanceById(performanceId);
    }

    public boolean changePerformance(PerformanceModel oldPerformance,
                                     PerformanceModel newPerformance) {
        return userRepository.changePerformance(oldPerformance, newPerformance);
    }

    public boolean createUnits(UnitsModel unitsModel) {
        return userRepository.createUnits(unitsModel);
    }

    public UnitsModel getUnitsById(long unitsId) {
        return userRepository.getUnitsById(unitsId);
    }

    public boolean changeUnits(UnitsModel oldUnits, UnitsModel newUnits) {
        return userRepository.changeUnits(oldUnits, newUnits);
    }
}
