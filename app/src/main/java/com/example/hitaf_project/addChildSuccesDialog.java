package com.example.hitaf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;



public class addChildSuccesDialog extends Dialog {

    //Class to show dialog if adding child done successfully
    public Activity c;

    public addChildSuccesDialog(Activity a) {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_succes_dialog);
    }
}
