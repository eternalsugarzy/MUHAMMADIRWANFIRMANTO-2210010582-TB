package models;

public class Class {
    private int classId;
    private String className;

    // Constructor
    public Class(int classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    // Getters and Setters
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
