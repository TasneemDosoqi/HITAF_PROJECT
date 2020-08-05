package com.example.hitaf_project;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class aboutUsDialog extends Dialog {

    public Activity c;

    public aboutUsDialog(Activity a) {
        super(a);
        this.c = a;
    }

    //Showing information about HIFAF project team and email
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_dialog);
    }



}
