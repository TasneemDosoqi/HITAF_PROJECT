package com.example.hitaf_project;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class type extends AppCompatActivity {

    FirebaseAuth auth;
    EditText word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        auth = FirebaseAuth.getInstance();
        word = findViewById(R.id.word);


    }


    public void translate_word(View view) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference gsReference = storage.getReferenceFromUrl("gs://hitafdataset.appspot.com/تمساح.mp4");
        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                MediaController mc = new MediaController(type.this);
                VideoView Avatar = findViewById(R.id.video_view_type);

                Avatar.setMediaController(mc);
                Avatar.setVideoURI(uri);
                Avatar.requestFocus();
                Avatar.start();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }
}
