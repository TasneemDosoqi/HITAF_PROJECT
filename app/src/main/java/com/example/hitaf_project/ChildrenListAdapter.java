package com.example.hitaf_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ChildrenListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> childernNames;


    public ChildrenListAdapter(Context context2, ArrayList<String> childernNames) {

        this.context = context2;
        this.childernNames = childernNames;
    }

    public int getCount() { return childernNames.size(); }
    public Object getItem(int position) { return null; }
    public long getItemId(int position) { return 0; }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.single_child_4list, parent, false);


            holder = new Holder();
            holder.childName_TextView = child.findViewById(R.id.childName_textView);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.childName_TextView.setText(childernNames.get(position));


        final ImageButton smileyFace =  child.findViewById(R.id.smileyFace_button);
        smileyFace.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                      smileyFace.setImageResource(R.drawable.smiley_face);
                }
            });

          return child;
    }


    public class Holder {
        TextView childName_TextView;
    }
}
