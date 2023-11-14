package com.example.exerciseapp.SignInANDSingUp;

import androidx.lifecycle.ViewModel;

import com.example.exerciseapp.mModels.UserInformationModel;

public class RegisterViewModel extends ViewModel {

    private UserInformationModel userInformationModel;

    public UserInformationModel getUserInformationModel() {
        return userInformationModel;
    }

    public void setUserInformationModel(UserInformationModel userInformationModel) {
        this.userInformationModel = userInformationModel;
    }
}
