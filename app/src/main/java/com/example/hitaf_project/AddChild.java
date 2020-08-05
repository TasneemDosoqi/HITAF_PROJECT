package com.example.hitaf_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddChild extends AppCompatActivity {
    EditText childname, childage;
    Button savebtn, cancelbtn;
    String childName;
    int childAge = 0, childGender = 0;
    StorageReference mRefrence;
    DatabaseReference dataref;
    public Uri imageurl;
    ImageView childimage;
    Child child;
    String childEmail;
    String childid;
    String imageid;
    RadioButton male;
    RadioButton female;
    Button backSettingBtn;
    MotionEvent event;
    TextView errorMa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        mRefrence = FirebaseStorage.getInstance().getReference("Images");
        dataref = FirebaseDatabase.getInstance().getReference().child("Child");
        childname = findViewById(R.id.child_name);
        childage = findViewById(R.id.child_age);
        savebtn = findViewById(R.id.save);
        cancelbtn = findViewById(R.id.cancle_button);
        childimage = findViewById(R.id.imageUpdated);
        errorMa = findViewById(R.id.error);
        child = new Child();
        errorMa.setText(" ");
    }

    //Function to call file uploder
    public void onSaveClicked(View view) {
        fileUploader(view);
    }

    //Function to call file chooser if clicking image
    public void onImageClicked(View view) {
        fileChooser();
    }

    //Function to get mime type for image
    public String getExtention(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimetype = MimeTypeMap.getSingleton();
        return mimetype.getExtensionFromMimeType(cr.getType(uri));

    }

    //Function add child to database
    public void fileUploader(final View view){
        if (childname.getText().toString().trim().length() == 0 || childage.getText().toString().trim().length() == 0){
            errorMa.setText("فضلاً تأكد من تعبئة جميع الحقول");

            return;
        }
        int age = Integer.parseInt(childage.getText().toString());
        if ( age < 0 || age >= 18 ){
            errorMa.setText("عذراً هِتاف موجهة للأطفال من عمر ١ - ١٨ سنة");
            return;
        }
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            childEmail = user.getEmail();
            childid = dataref.push().getKey();
            imageid = childid+"."+getExtention(imageurl);
            StorageReference ref = mRefrence.child(imageid);
            child.setParentEmail(childEmail);
            child.setChildName(childname.getText().toString().trim());
            childAge = Integer.parseInt(childage.getText().toString().trim());
            child.setChildAge(childAge);
            child.setChildImage(imageid);
            child.setChildGender(childGender);
            child.setChildID(childid);
            dataref.child(childid).setValue(child);
            ref.putFile(imageurl)

                    //In case of adding done successfully
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            clickSaveOpenDialog(view);

                        }
                    })
                    //In case something went wrong in adding image
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            errorMa.setText("لم يتم تحميل الصورة بنجاح ! ");

                        }
                    });
        } catch (Exception e) {
            //In case if you there did not add an image
            e.printStackTrace();
            errorMa.setText("فضلاً تأكد من أختيار صورة");
        }


    }

    //Function to open device gallery
    public void fileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }

    //Function to get the image from device gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null ){
            imageurl = data.getData();
            childimage.setImageURI(imageurl);
        }
    }

    //Function to define child gender
    public void onRadioButtongenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioMale:
                if (checked){
                    childGender = 0;
                    break;}
            case R.id.radioFemale:
                if (checked){
                    childGender = 1;
                    break;}
        }
    }

    //Function to show add child success dialog
    public void clickSaveOpenDialog(View view){
        final Dialog dialog=new Dialog(AddChild.this);
        dialog.setTitle(" ");
        dialog.setContentView(R.layout.activity_add_child_succes_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(AddChild.this,setting.class);
                startActivity(i);
            }
        }, 5000);
    }

    //Function to cancel adding child and go to setting page
    public void cancelButton1(View view){
        childname.setFocusable(false);
        childage.setFocusable(false);
        Intent i = new Intent(AddChild.this, setting.class);
        startActivity(i);
    }
}
