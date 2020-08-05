package com.example.hitaf_project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<Child> children = new ArrayList<>();
    String retrivedImage;

    //Constructor
    public RecyclerViewAdapter(Context context2, ArrayList<Child> child) {
        this.context = context2;
        this.children = child;
    }

    //Function to inflate the recycler view with customer layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    //Function to link every child and it's element in recycler view
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Child ch = children.get(position);
        retrivedImage = ch.getChildImage();

        //Bind every child image to every item in recycler view
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference gsReference = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/Images/"+retrivedImage);
        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                System.out.println(" Url : " + uri);
                Glide.with(context)
                        .load(uri)
                        .into(holder.image);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        holder.name.setText(ch.getChildName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), Home.class);
                i.putExtra("childName",ch.getChildName());
                context.startActivity(i);
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), Home.class);
                i.putExtra("childName",ch.getChildName());
                context.startActivity(i);
            }
        });

    }

    //Function override for RecyclerView extended
    @Override
    public int getItemCount() {
        return children.size();
    }

    //Class to hold the child elements
    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.child_image);
            name = itemView.findViewById(R.id.name);
        }
    }

}
