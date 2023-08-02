package com.example.exerciseapp.mModels;

public class LanguageModel {

    private long id;
    private String name;
    private boolean status;
    private String prefix;
    private String image;

    public LanguageModel(long id, String name, boolean status, String prefix, String image) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.prefix = prefix;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setActive(boolean status) {
        this.status = status;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
