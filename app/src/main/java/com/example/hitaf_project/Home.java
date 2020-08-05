package com.example.hitaf_project;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Video with you with you to show welcoming video for child
        final VideoView introVideo = findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.introvideo;
        Uri uri = Uri.parse(videoPath);
        introVideo.setVideoURI(uri);
        introVideo.start();
        introVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                introVideo.start();
            }
        });
    }

    //Function to go to sitting page
    public void sittingButton(View view){
        Intent i=new Intent(this, setting.class);
        startActivity(i);
    }

    //Function to go to typing page
    public void typingButton(View view){
        Intent i=new Intent(this, type.class);
        startActivity(i);
    }

    //Function to go to saying page
    public void talkButton(View view){
        Intent i=new Intent(this, Saying.class);
        startActivity(i);
    }

    //Function to go to watching page
    public void videoButton(View view){
        Intent i=new Intent(this, videoList.class);
        startActivity(i);
    }

    //Function to go to child list page
    public void childList(View view) {
        Intent i=new Intent(this, Home1.class);
        startActivity(i);
    }
}
