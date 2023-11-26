package com.example.exerciseapp.mModels;

public class WorkoutModelToChange {

    private int id;
    private long exerciseId;
    private long extensionId;
    private byte type;
    private String name;
    private int fromWhere;

    public WorkoutModelToChange(int id, long exerciseId, byte type) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.type = type;
    }

    public WorkoutModelToChange(int id, long exerciseId, long extensionId, byte type, String name,
                                int fromWhere) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.extensionId = extensionId;
        this.type = type;
        this.name = name;
        this.fromWhere = fromWhere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExtensionId(long extensionId) {
        this.extensionId = extensionId;
    }

    public long getExtensionId() {
        return extensionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
    }


    @Override
    public String toString() {
        return "WorkoutModelToChange{" +
                "id=" + id +
                ", exerciseId=" + exerciseId +
                ", extensionId=" + extensionId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", fromWhere=" + fromWhere +
                '}';
    }
}
