package com.example.exerciseapp.SignInANDSingUp;

import androidx.lifecycle.ViewModel;

import com.example.exerciseapp.mModels.UserInformationModelToChange;

public class RegisterViewModel extends ViewModel {

    private UserInformationModelToChange userInformationModelToChange;

    public UserInformationModelToChange getUserInformationModel() {
        return userInformationModelToChange;
    }

    public void setUserInformationModel(UserInformationModelToChange userInformationModelToChange) {
        this.userInformationModelToChange = userInformationModelToChange;
    }
}
