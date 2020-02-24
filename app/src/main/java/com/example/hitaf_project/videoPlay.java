package com.example.hitaf_project;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
//import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;


public class videoPlay extends AppCompatActivity {

    //StorageReference spaceRef = storageRef.child("images/space.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_videoplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        VideoView Avatar = findViewById(R.id.video_view);
        String Str = "https://firebasestorage.googleapis.com/v0/b/hitafdataset.appspot.com/o/اسد.mp4?alt=media&token=651539d0-597d-4d26-b0c3-1bf8bbcc9c10";
        Uri uri = Uri.parse(Str);

        Avatar.setVideoURI(uri);
        Avatar.requestFocus();
        Avatar.start();

    }



}
