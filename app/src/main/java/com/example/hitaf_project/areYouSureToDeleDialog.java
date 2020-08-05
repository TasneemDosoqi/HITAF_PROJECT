package com.example.hitaf_project;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class areYouSureToDeleDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Button Delete, Cancel;
    DatabaseReference dataref;
    String childid;
    String childImage;
    FirebaseStorage mFirebaseStorage;

    //Class to show dialog if user click delete child account button
    public areYouSureToDeleDialog(Activity a,Bundle m) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.childid = m.getString("ChildId");
        this.childImage = m.getString("ChildّImage");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_sure_to_dele_dialog);
        dataref = FirebaseDatabase.getInstance().getReference().child("Child");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference().getStorage();
        Delete = (Button) findViewById(R.id.yesdeleteit);
        Cancel = (Button) findViewById(R.id.cancel);
        Delete.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }

    //Function to define if user click delete or cancel
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesdeleteit:
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+childImage);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //In case deleting done Successfully
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // //In case deleting error
                    }
                });
                dataref.child(childid).removeValue(null);
                c.finish();
                childId.setchildId("");

                //open setting page

                Toast.makeText(getContext().getApplicationContext(),"تم حذف حساب الطفل بنجاح",Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 3000);



                Intent i = new Intent(getContext().getApplicationContext(), setting.class);
                c.startActivity(i);

                break;
            case R.id.Cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
