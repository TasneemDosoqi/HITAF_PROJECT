package com.example.hitaf_project;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class SpalshPage extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=7000;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_page);

        final VideoView introVideo = findViewById(R.id.video_view1);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.splashvideo;
        Uri uri = Uri.parse(videoPath);
        introVideo.setVideoURI(uri);
        introVideo.start();
        introVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                introVideo.start();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SpalshPage.this,
                        SignUp.class);

                startActivity(i);

                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
