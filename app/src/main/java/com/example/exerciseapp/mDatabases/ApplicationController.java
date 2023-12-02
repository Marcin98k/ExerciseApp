package com.example.exerciseapp.mDatabases;

import com.example.exerciseapp.mModels.LanguageModel;

public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    public ApplicationController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public boolean createLanguage(LanguageModel languageModel) {
        return applicationRepository.createLanguage(languageModel);
    }

    public boolean switchLanguage(long oldLanguageId, long newLanguageId) {
        return applicationRepository.switchLanguage(oldLanguageId, newLanguageId);
    }
}
