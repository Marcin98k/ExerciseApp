package com.example.exerciseapp.mModels;

public class AppearanceBlockModel {

    private int id;
    private int icon;
    private String image;
    private String name;
    private int action;

    private int value;

    private String description;
    private String tag;

    int appearance;

    @Override
    public String toString() {
        return "AppearanceBlockModel{" +
                "id=" + id +
                ", icon=" + icon +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", action=" + action +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", appearance=" + appearance +
                '}';
    }

    public AppearanceBlockModel() {

    }

    public AppearanceBlockModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AppearanceBlockModel(int id, String name, int appearance) {
        this.id = id;
        this.name = name;
        this.appearance = appearance;
    }

    public AppearanceBlockModel(int id, int value, int appearance) {
        this.id = id;
        this.value = value;
        this.appearance = appearance;
    }
    public AppearanceBlockModel(int id, int icon, String image, String name, int action, String description, String tag) {
        this.id = id;
        this.icon = icon;
        this.image = image;
        this.name = name;
        this.action = action;
        this.description = description;
        this.tag = tag;
    }

    public AppearanceBlockModel(int id, int icon, String name, String description, String tag) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAppearance() {
        return appearance;
    }

    public void setAppearance(int appearance) {
        this.appearance = appearance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
