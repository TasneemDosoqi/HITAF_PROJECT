package com.example.hitaf_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class addVideo extends AppCompatActivity {

    EditText video_link;
    RecyclerView recyclerView;
    Vector<YoutubeVideos> youtubeVideos = new Vector<YoutubeVideos>();
    String newLink;
    Button addLink;
    TextView errorAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        video_link= (EditText)findViewById(R.id.link);
        addLink= (Button)findViewById(R.id.save);
        errorAdd = findViewById(R.id.error_mess);
        errorAdd.setText(" ");

        //Adding the videos in recycler view
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/R4OdbmZebdA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/W4M4oHBGSkA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8_4_F8EunRI\" frameborder=\"0\" allowfullscreen></iframe>") );

        videoAdapter videoAdapter = new videoAdapter(youtubeVideos);
        recyclerView.setAdapter(videoAdapter);

        addLink.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {
                        newLink=video_link.getText().toString().trim();
                        chickIfLinkIsExist(newLink);

                    }
                });

    }

    //Function to The check of the link provided by the user is already exist in the database
    public void chickIfLinkIsExist(String newLink){

        if(newLink.equals("")){
            errorAdd.setText("فضلاً أدخل رابط الفيديو !");
            errorAdd.setTextColor(Color.parseColor("#E91E63"));
        }else
        if (newLink.equals("https://www.youtube.com/embed/R4OdbmZebdA") || newLink.equals("https://www.youtube.com/embed/W4M4oHBGSkA") || newLink.equals("https://www.youtube.com/embed/8_4_F8EunR")){
            errorAdd.setText("يمكنك الإستمتاع بمشاهدة هذا الفيديو عن طريق الدخول لحساب الطفل");
            errorAdd.setTextColor(Color.parseColor("#9C27B0"));
        }else {
            errorAdd.setText("رابط الفيديو المدخل أعلاه غير مدعوم من هِتاف حالياً !");
            errorAdd.setTextColor(Color.parseColor("#E91E63"));

        }
    }

    //Function to cancel adding video and go to setting page
    public void cancelButton1(View view){
        Intent i = new Intent(addVideo.this, setting.class);
        startActivity(i);
    }

}





