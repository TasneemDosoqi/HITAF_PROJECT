package com.example.hitaf_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
   // FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    Vector<YoutubeVideos> youtubeVideos = new Vector<YoutubeVideos>();
    String newLink;
    String checkLinkFromDB;
    Boolean flag=false;
    Button addLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

       // firebaseDatabase = FirebaseDatabase.getInstance();
      //  myRef = firebaseDatabase.getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        video_link= (EditText)findViewById(R.id.link);
        addLink= (Button)findViewById(R.id.save);


        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/R4OdbmZebdA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/W4M4oHBGSkA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8_4_F8EunRI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/watch?v=bJvzjM0uLwI\" frameborder=\"0\" allowfullscreen></iframe>") );

        //youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uhQ7mh_o_cM\" frameborder=\"0\" allowfullscreen></iframe>") );


        videoAdapter videoAdapter = new videoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

        addLink.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {
                        newLink=video_link.getText().toString().trim();
                        chickIfLinkIsExist();
//                        if(chickIfLinkIsExist()){
//                        if(!checkLinkFromDB.equals(null)){
//                        if(flag){
//                            System.out.println("the link is exsist");
//                        }
//                        else
//                            System.out.println("the link is not exsist");
//
//                         }
//                    }}
                    }});

    }

    public Boolean chickIfLinkIsExist(){

        myRef = FirebaseDatabase.getInstance().getReference().child("Videos");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("this is nwe link:"+newLink);
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(childSnapshot);
                    System.out.println(childSnapshot.child("video").getValue());
                    if (childSnapshot.child("video").getValue().toString().equals(newLink)){

                        checkLinkFromDB = childSnapshot.child("video").getValue().toString();
                        System.out.println("checkLinkFromDB: " + checkLinkFromDB);
                        flag = true;
                        System.out.println("flag 1 :" + flag);
                        if(!checkLinkFromDB.equals(null)){


                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return flag;}



    }





