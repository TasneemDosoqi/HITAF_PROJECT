package com.example.hitaf_project;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
        //StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");
        //String translate_word = word.getText().toString().trim();
        //String translate_word = "اسد";
        //gs://hitafdataset.appspot.com/
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        //final StorageReference videoRef = storageRef.child("https://firebasestorage.googleapis.com/v0/b/hitafdataset.appspot.com/o/"+translate_word+".mp4?alt=media&token=651539d0-597d-4d26-b0c3-1bf8bbcc9c10\n");
        final StorageReference videoRef = storageRef.child("gs://hitafdataset.appspot.com/تمساح.mp4");
        //final StorageReference videoRef = storageRef.child("gs://bucket/video/تمساح.mp4");
        final long ONE_MEGABYTE = 1024 * 1024;
        videoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                VideoView Avatar = findViewById(R.id.video_view_type);
                Uri uri = Uri.parse(String.valueOf(videoRef));
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
