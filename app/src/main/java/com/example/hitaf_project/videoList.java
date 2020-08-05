package com.example.hitaf_project;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class videoList extends AppCompatActivity {

    VideoView v1,v2,v3;
    TextView Ved1, Ved2, Ved3;
    VideoView[] vediolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        v1= findViewById(R.id.video1);
        v2= findViewById(R.id.video2);
        v3= findViewById(R.id.video3);
        Ved1= findViewById(R.id.TVideo1);
        Ved2= findViewById(R.id.TVideo2);
        Ved3= findViewById(R.id.TVideo3);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference1;
        StorageReference gsReference2;
        StorageReference gsReference3;

        //To get reference for the videos from the database
        gsReference1 = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/videos/ألوان.mp4");
        gsReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                v1.setVideoURI(uri);
                v1.start();
                v1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        v1.start();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        gsReference2 = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/videos/حيوان.mp4");
        gsReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                v2.setVideoURI(uri);
                v2.start();
                v2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        v2.start();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        gsReference3 = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/videos/أرقام.mp4");
        gsReference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                v3.setVideoURI(uri);
                v3.start();
                v3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        v3.start();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        //Sending intend to video play page to play the chosen video
        Ved1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), videoPlay.class);
                i.putExtra("vname","Colors");
                startActivity(i);
            }
        });
        Ved2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), videoPlay.class);
                i.putExtra("vname","Animals");
                startActivity(i);
            }
        });
        Ved3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), videoPlay.class);
                i.putExtra("vname","Numbers");
                startActivity(i);
            }
        });

    }

    //Function to go to home page
    public void Home(View view) {
        Intent i=new Intent(this, Home.class);
        startActivity(i);
    }

}



