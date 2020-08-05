package com.example.hitaf_project;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;


public class deleteChildSuccesDialog extends Dialog {

    public Activity c;

    //Class to show dialog if delete child account done successfully
    public deleteChildSuccesDialog(Activity a) {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_succes_dialog);
    }
}
