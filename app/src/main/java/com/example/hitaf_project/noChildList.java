package com.example.hitaf_project;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;


public class noChildList extends Dialog {

    public Activity c;

    public noChildList(Activity a) {
        super(a);
        this.c = a;
    }

    //To inform the user that there is no child to be registered yet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_child_dialog);
    }



}
