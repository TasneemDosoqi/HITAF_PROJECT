package com.example.hitaf_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;


public class setting extends AppCompatActivity {

    ArrayList<Child> model;
    AppCompatActivity self = this;
    static EditText childName;
    static EditText childAge;
    static RadioButton male;
    static RadioButton female;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    StorageReference mRefrence;
    StorageReference childImageRefrence;
    FirebaseUser user;
    int childGender = 0;
    public Uri imageurl;
    static ImageView childimage;
    Child child;
    static RadioGroup group;
    String childID = childId.getchildId();
    String pass, retrivedName, retrivedAge, retrivedGender, retrivedImage, imageid;
    static Button addImage;
    Button btnLogOut;
    static Button save;
    static Button cancel;
    static Button delete;
    DatabaseReference dataref;
    String childNamestring;
    String childIdstring;
    String userEmail;
    static TextView t1;
    static TextView t2;
    static TextView t3;
    static TextView t4;
    static TextView t5;
    TextView errorSetting;
    Boolean flag,changepic = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Declaration
        flag = false;
        Intent i = getIntent();
        pass = i.getStringExtra("userPass");

        mRefrence = FirebaseStorage.getInstance().getReference("Images");
        childName = findViewById(R.id.child_name);
        childAge = findViewById(R.id.child_age);
        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);
        group = findViewById(R.id.radioSex);
        childimage = findViewById(R.id.imageUpdated);
        addImage = findViewById(R.id.add_ChildPic);
        save =findViewById(R.id.editBtn);
        delete =findViewById(R.id.delete_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Child");
        child = new Child();

        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t4 = findViewById(R.id.textView4);
        t5 = findViewById(R.id.textView5);
         errorSetting = findViewById(R.id.errorS);
        errorSetting.setText(" ");

        //hidden elements of no action yet
        childName.setVisibility(View.INVISIBLE);
        childAge.setVisibility(View.INVISIBLE);
        male.setVisibility(View.INVISIBLE);
        female.setVisibility(View.INVISIBLE);
        group.setVisibility(View.INVISIBLE);
        childimage.setVisibility(View.INVISIBLE);
        addImage.setVisibility(View.INVISIBLE);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);
        t4.setVisibility(View.INVISIBLE);
        t5.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);

        //Sign out from user account button
        btnLogOut = findViewById(R.id.signout_button);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(setting.this, SignIn.class);
                startActivity(I);
            }
        });

        // If user do not have child yet
        if(childID.isEmpty()){
            childName.setText(" ");
            childAge.setText(" ");
            male.setChecked(false);
            female.setChecked(false);
        }
        else {

            //Retrieve exist child information from database
            reference.child(childId.getchildId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //unhidden elements if there is action
                    childName.setVisibility(View.VISIBLE);
                    childAge.setVisibility(View.VISIBLE);
                    male.setVisibility(View.VISIBLE);
                    female.setVisibility(View.VISIBLE);
                    group.setVisibility(View.VISIBLE);
                    childimage.setVisibility(View.VISIBLE);
                    addImage.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                    t5.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);

                    retrivedName = dataSnapshot.child("childName").getValue().toString();
                    retrivedAge = dataSnapshot.child("childAge").getValue().toString();
                    retrivedGender = dataSnapshot.child("childGender").getValue().toString();
                    retrivedImage = dataSnapshot.child("childImage").getValue().toString();
                    childName.setText(retrivedName);
                    childAge.setText(retrivedAge);
                    checkGender(Integer.parseInt(retrivedGender));
                    System.out.println("Image information" + retrivedName + retrivedAge + retrivedGender + retrivedImage);
                    System.out.println("Image Ref" + childImageRefrence);

                    //If user modify child information
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    final StorageReference gsReference = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/Images/"+retrivedImage);
                    gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            System.out.println(" Url : " + uri);
                            Glide.with(self)
                                    .load(uri)
                                    .into(childimage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Handling errors
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Retrieve children for the current user and fill it recycler view
        getChildrenSnapshot();
        model = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference("Child");
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    }

    // Function to go to page add child
    public void  toAddChild(View view){
        Intent i =new Intent(this, AddChild.class);
        startActivity(i);
    }

    //Function to update child information if any information changed
    public void onUpdateClicked(View view) {

        if (childName.getText().toString().trim().length() == 0 || childAge.getText().toString().trim().length() == 0){
            errorSetting.setText("فضلاً تأكد من تعبئة جميع الحقول");

            return;
        }
        int age = Integer.parseInt(childAge.getText().toString());
        if ( age < 0 || age >= 18 ){
            errorSetting.setText("عذراً هِتاف موجهة للأطفال من عمر ١ - ١٨ سنة");
            return;}

        fileUploader(view);


    }

    //Function to upload image if user update the child image
    public void onImageClicked(View view) {

        changepic = true;

        fileChooser();

    }

    //Function to get mime type for image
    public String getExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimetype = MimeTypeMap.getSingleton();
        return mimetype.getExtensionFromMimeType(cr.getType(uri));
    }

    //Function to update child information if any information changed
    public void fileUploader(View v) {
        if (changepic) {
            imageid = childID + "." + getExtention(imageurl);
            StorageReference ref = mRefrence.child(imageid);
            ref.putFile(imageurl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
        } else {
            imageid = retrivedImage;
        }
        String childname = childName.getText().toString();
        int childage = Integer.parseInt(childAge.getText().toString());

        reference.child(childId.getchildId()).child("childAge").setValue(childage);
        reference.child(childId.getchildId()).child("childGender").setValue(childGender);
        reference.child(childId.getchildId()).child("childName").setValue(childname);
        reference.child(childId.getchildId()).child("childImage").setValue(imageid);

        //update success dialog
        Dialog dialog=new Dialog(setting.this);
        dialog.setTitle(" ");
        dialog.setContentView(R.layout.update_child_success);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(setting.this,setting.class);
                startActivity(i);
            }
        }, 5000);

    }

    //Function to open device gallery
    public void fileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);

    }

    //Function to upload image from device gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageurl = data.getData();
            childimage.setImageURI(imageurl);
        }
    }

    //Function to retrieve child gender if male or female
    public void checkGender(int gender) {
        if (gender == 0) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
    }

    //Function to allow user to choose gender of the child
    public int checkedRadio(View view) {
        if (view == male) {
            childGender = 0;
        }
        if (view == female) {
            childGender = 1;
        }
        return childGender;
    }

    //Function to go to reset password page
    public void resetPassword(View view) {
        Intent I = new Intent(setting.this, Resetpassword.class);
        I.putExtra("userPass", pass);
        startActivity(I);
    }

    //Function to delete current child from that bass
    public void onClickDeleteBtn(View view) {
        Bundle b = new Bundle();
        b.putString("ChildId", childId.getchildId());
        b.putString("ChildّImage", imageid);
        Dialog dialog = new areYouSureToDeleDialog(setting.this, b);
        dialog.setTitle(" ");
        dialog.setContentView(R.layout.activity_are_you_sure_to_dele_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    //Function to retrieve child information based on parent current account
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
                        model.add( new Child(childNamestring,childIdstring));
                    }
                }
                ShowChildrenList(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //show the child list if registered of user or not
    private void ShowChildrenList(ArrayList<Child> child) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        if (child.size() != 0){
            ChildrenListAdapter adapter = new ChildrenListAdapter(this, child);
            recyclerView.setAdapter(adapter);
        }
        else {
            //To open dialogue to show the user is it there is no registered child yet
            final Dialog dialog=new Dialog(setting.this);
            dialog.setContentView(R.layout.no_child_dialog_setting);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();        }

    }

    //Back button to go to home page
    public void backToHome(View view) {
        Intent i = new Intent(setting.this, Home1.class);
        startActivity(i);
    }
}