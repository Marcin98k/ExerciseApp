package com.example.exerciseapp.mModels;

public class LanguageModel {

    private long id, userId;
    private String name;
    private String image;
    private String prefix;
    private boolean status;

    public LanguageModel() {}

    public LanguageModel(long id, long userId, String name, String image, String prefix,
                         boolean status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.prefix = prefix;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isStatus() {
        return status;
    }
}
