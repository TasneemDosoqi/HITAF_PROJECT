package com.example.hitaf_project;

public class Parent {

    // Class that have constructor getter and setter for parent
    private String ParentName;
    private String ParentLockKey;
    private String ParentEmail;
    private String ParentPassword;
    private String ParentPhone;

    public Parent() {

    }

    public Parent(String parentName, String parentLockKey, String parentEmail, String parentPassword, String parentPhone) {
        ParentName = parentName;
        ParentLockKey = parentLockKey;
        ParentEmail = parentEmail;
        ParentPassword = parentPassword;
        ParentPhone = parentPhone;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public void setParentLockKey(String parentLockKey) {
        ParentLockKey = parentLockKey;
    }

    public void setParentEmail(String parentEmail) {
        ParentEmail = parentEmail;
    }

    public void setParentPassword(String parentPassword) {
        ParentPassword = parentPassword;
    }

    public String getParentName() {
        return ParentName;
    }

    public String getParentLockKey() {
        return ParentLockKey;
    }

    public String getParentEmail() {
        return ParentEmail;
    }

    public String getParentPassword() {
        return ParentPassword;
    }

    public String getParentPhone() {
        return ParentPhone;
    }

    public void setParentPhone(String parentPhone) {
        ParentPhone = parentPhone;
    }
}

