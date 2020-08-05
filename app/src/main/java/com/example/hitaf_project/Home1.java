package com.example.hitaf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home1 extends AppCompatActivity {
    Button btnLogOut;
    String pass,userEmail;
    ArrayList<Child> children = new ArrayList<>();
    DatabaseReference dataref;
    String childNamestring;
    String childIdstring;
    String childImagestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

         Intent i = getIntent();
         pass= i.getStringExtra("userPass");
         userEmail= i.getStringExtra("userEmail");

         btnLogOut = findViewById(R.id.signout);
         btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(Home1.this, SignIn.class);
                startActivity(I);
            }
        });

         getChildrenSnapshot();
    }

    private void getChildrenSnapshot() {
        dataref = FirebaseDatabase.getInstance().getReference("Child");
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds);

                    if(ds.child("parentEmail").getValue().equals(userEmail)){

                        childNamestring = ds.child("childName").getValue().toString();
                        childIdstring = ds.child("childID").getValue().toString();
                        childImagestring = ds.child("childImage").getValue().toString();
                        children.add( new Child(childNamestring,childIdstring,childImagestring ));
                    }
                }
                ShowChildrenList(children);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ShowChildrenList(ArrayList<Child> child) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        if (child.size() != 0){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, child);
        recyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(this,"لا يوجد أطفال مسجلين حالياً، اذهب إلى الاعدادات للإضافة.",Toast.LENGTH_LONG).show();
        }

    }

    public void onSettingClick(View v){

        Bundle b = new Bundle();
        b.putString("password",pass);
        b.putString("userEmail",userEmail);
        keyDialog dial = new keyDialog(this, b);
        dial.setContentView(R.layout.activity_key_dialog);
        dial.setCanceledOnTouchOutside(true);
        dial.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dial.show();

// ---------   if the lock key is correct it should go to setting page using the intent below
       /* Intent intent = new Intent(this, setting.class);
        intent.putExtra("userPass",pass);
        startActivity(intent); */
    }
}
