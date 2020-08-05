package com.example.hitaf_project;

public class Child {

    private String ChildName, ParentEmail;
    private int ChildAge, ChildGender;
    private String ChildImage;
    private String ChildID;
    private String ParentEmailForChild;

    // Class that have constructor getter and setter for child
    public Child() {

    }
    public Child(String childName, String childID) {
        ChildName = childName;
        ChildID = childID;
    }

    public Child(String childName, String childID, String childImage) {
        ChildName = childName;
        ChildID = childID;
        ChildImage= childImage;
    }
    public Child(String childName, String parentEmail, int childAge, int childGender, String childImage) {
        ChildName = childName;
        ParentEmail = parentEmail;
        ChildAge = childAge;
        ChildGender = childGender;
        ChildImage = childImage;
    }

    public Child(String childName, String parentEmail, int childAge, int childGender) {
        ChildName = childName;
        ParentEmail = parentEmail;
        ChildAge = childAge;
        ChildGender = childGender;
    }

    public String getParentEmailForChild() {
        return ParentEmailForChild;
    }

    public void setParentEmailForChild(String parentEmailForChild) {
        ParentEmailForChild = parentEmailForChild;
    }

    public String getChildID() {
        return ChildID;
    }

    public void setChildID(String childID) {
        ChildID = childID;
    }

    public String getChildName() {return ChildName;}

    public void setChildName(String childName) {ChildName = childName; }

    public String getParentEmail() {return ParentEmail; }

    public void setParentEmail(String parentEmail) {ParentEmail = parentEmail; }

    public int getChildAge() {return ChildAge; }

    public void setChildAge(int childAge) {ChildAge = childAge; }

    public int getChildGender() {return ChildGender; }

    public void setChildGender(int childGender) {ChildGender = childGender; }

    public String getChildImage() {return ChildImage; }

    public void setChildImage(String childImage) {ChildImage = childImage; }
}
