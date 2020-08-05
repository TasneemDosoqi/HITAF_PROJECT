package com.example.hitaf_project;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

public class videoPlay extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    VideoView videoView1,videoView2;
    MediaController mediaController;
    MediaController mediaController1;
    String VideoName;
    String selectedVideo;
    Button play,pause,forward,backword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView1 = (VideoView)findViewById(R.id.AvatarVideo);
        videoView2=(VideoView)findViewById(R.id.realVideo);
        mediaController= new MediaController(this);
        mediaController1=new MediaController(this);
        play = findViewById(R.id.playVid);
        pause = findViewById(R.id.pauseVid);
        forward = findViewById(R.id.forword);
        backword = findViewById(R.id.backwordVid);

        Intent i=getIntent();
        selectedVideo=i.getStringExtra("vname");
        VideoName = selectedVideo;

        getAvatarVideo();
        getRealVideo();
    }

    //Retrieve avatar video from database based on the last intent
    public void getAvatarVideo(){
        storageReference = firebaseStorage.getInstance().getReferenceFromUrl("gs://hitaf-application-project.appspot.com/videosPlaylist/"+VideoName+".mp4");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView1.setVideoURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    //Retrieve Learning video from database based on the last intent
    public void getRealVideo(){
        storageReference = firebaseStorage.getInstance().getReferenceFromUrl("gs://hitaf-application-project.appspot.com/ZakriaVideo/"+VideoName+".mp4");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView2.setVideoURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    //Function to go to home page
    public void Home(View view) {
        Intent i=new Intent(this, videoList.class);
        startActivity(i);
    }

    //Function to forward 5 seconds
    public void forwordButton(View view) {
        int currentPosition = videoView1.getCurrentPosition();
        // check if seekForward time is lesser than video duration
        if (currentPosition + 5000 <= videoView1.getDuration()) {
            // forward to position
            videoView1.seekTo(currentPosition + 5000);
            videoView2.seekTo(currentPosition + 5000);

        } else {
            // forward to end position
            videoView1.seekTo(videoView1.getDuration());
            videoView2.seekTo(videoView1.getDuration());
        }
    }

    //Function to play both video
    public void playButton(View view) {

        videoView1.start();
        videoView2.start();

    }

    //Function to pause both video
    public void pauseButton(View view) {
        videoView1.pause();
        videoView2.pause();
    }

    //Function to backward 5 seconds
    public void BackwordButton(View view) {
        int currentPosition = videoView1.getCurrentPosition();
        // check if seek backward time is lesser than zero
        if (currentPosition - 5000 >= 0) {
            // backward to position
            videoView1.seekTo(currentPosition - 5000);
            videoView2.seekTo(currentPosition - 5000);

        } else {
            videoView1.seekTo(0);
            videoView2.seekTo(0);
        }
    }
}
