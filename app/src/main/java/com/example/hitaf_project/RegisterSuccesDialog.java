package com.example.hitaf_project;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;


public class RegisterSuccesDialog extends Dialog {

    public Activity c;

    //Class to show dialog if Register new user done successfully
    public RegisterSuccesDialog(Activity a) {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_succes_dialog);
    }
}
