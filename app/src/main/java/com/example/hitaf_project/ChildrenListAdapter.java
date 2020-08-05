package com.example.hitaf_project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildrenListAdapter extends RecyclerView.Adapter<ChildrenListAdapter.ViewHolder> {
    Context context;
    ArrayList<Child> children;
    String childID ;

    //Constructor
    public ChildrenListAdapter(Context context2, ArrayList<Child> child) {
        this.context = context2;
        this.children = child;
    }

    //Function override for RecyclerView extended
    @Override
    public int getItemCount() {
        return children.size();
    }

    //Function to inflate the recycler view with customer layout
    @NonNull
    @Override
    public ChildrenListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_child_4list, parent, false);
        return new ChildrenListAdapter.ViewHolder(view);
    }

    //Function to link every child and it's element in recycler view
    @Override
    public void onBindViewHolder(final ChildrenListAdapter.ViewHolder holder, int position) {

        final Child ch = children.get(position);
        holder.childName_TextView.setText(ch.getChildName());

        //In case user hit setting button
        holder.childSetting_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.lin2.setBackgroundResource(R.drawable.button_listitem_green);
                childID = ch.getChildID();
                childId.setchildId(childID);
                Intent i = new Intent(context.getApplicationContext(), setting.class);
                i.putExtra("FLAG", true);
                context.startActivity(i);
            }
        });

        //In case user hit play list button
        holder.childPlaylist_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.lin3.setBackgroundResource(R.drawable.button_listitem_green);
                childID = ch.getChildID();
                childId.setchildId(childID);
                Intent i = new Intent(context.getApplicationContext(), addVideo.class );
                context.startActivity(i);
            }
        });
    }

    //Class to hold the child elements
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView childName_TextView;
        TextView childSetting_TextView;
        TextView childPlaylist_TextView;
        LinearLayout lin2;
        LinearLayout lin3;

        public ViewHolder(View itemView) {
            super(itemView);

            childName_TextView = itemView.findViewById(R.id.childName_textView);
            childSetting_TextView = itemView.findViewById(R.id.settingBtn);
            childPlaylist_TextView = itemView.findViewById(R.id.playListBtn);
            lin2 = itemView.findViewById(R.id.lin2);
            lin3 = itemView.findViewById(R.id.lin3);
        }
    }
}
