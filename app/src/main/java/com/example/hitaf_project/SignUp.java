package com.example.hitaf_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;


public class SignUp extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    EditText username, email, password, confirmPassword, phone, usernalockKeyme;
    Button signup;
    String emailID, paswd, pho, conf, lock, usenam;
    TextView alreadyHave, errorMSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //set up fire base
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        errorMSG = findViewById(R.id.error_messages);
        errorMSG.setText(" ");
        // link input fields
        alreadyHave =findViewById(R.id.login_view);
        username = findViewById(R.id.edit_Username_signup);
        email = findViewById(R.id.edit_email_signup);
        password = findViewById(R.id.edit_password_signup);
        confirmPassword = findViewById(R.id.edit_confirm_signup);
        phone = findViewById(R.id.edit_phone_signup);
        usernalockKeyme = findViewById(R.id.edit_lock_signup);
        signup = findViewById(R.id.signup);


        // add sign up listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // read values from input fields
                emailID = email.getText().toString();
                paswd = password.getText().toString();
                pho = phone.getText().toString();
                usenam = username.getText().toString();
                lock = usernalockKeyme.getText().toString();
                conf = confirmPassword.getText().toString();

                /*
                 * 1. validate user input
                 * 2. if valid check if same user name exist
                 * 3. if not exist create user
                 */

                if (validateUserInput()) {
                    ref = database.getReference("Parent");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(usenam).exists()) {
                                errorMSG.setText("عفواً إسم المستخدم مسجل مسبقاً");
                                username.requestFocus();
                            } else {
                                submitUserFirebase();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }
            }
        });

        alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(SignUp.this, SignIn.class);
                startActivity(I);
            }
        });

    }

    //Showing information about HIFAF project team and email
    public void aboutUs(View view) {
        final Dialog dialog=new Dialog(SignUp.this);
        dialog.setContentView(R.layout.about_us_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    // Creates User Object from user inputs
    private Parent createParentObjectFromInputFields() {
        Parent tempParent = new Parent();
        tempParent.setParentName(username.getText().toString().trim());
        tempParent.setParentEmail(email.getText().toString().trim());
        tempParent.setParentLockKey(usernalockKeyme.getText().toString().trim());
        tempParent.setParentPassword(password.getText().toString().trim());
        tempParent.setParentPhone(phone.getText().toString().trim());
        return tempParent;
    }

    //Function to check the validation and the strength of the password
    public boolean isPassword(String s) {
        return s.matches("(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*.-]).{8,}$");
    }

    //Function to check the validation of the phone number
    public boolean isPhoneNumber(String s) {
        return s.matches("[0-9]{10}");
    }

    //Function to check the validation of the Email
    public boolean isEmail(String s) {
        return s.matches("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+");
    }

    //Function to check the lock number is exactly five digit
    public boolean islock(String s) {
        return s.matches("[0-9]{5}");
    }

    //Function to check if the password fields matches or not
    public boolean isPassMatch(String s1, String s2){
        if(s1.equals(s2)){
            return true;
        }
        return false;
    }

    //Function to add new user to database
    public void submitUserFirebase() {

        firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(SignUp.this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    errorMSG.setText("عذراً لم يتم تسجيلك كمستخدم جديد حاول مجدداً في وقت لاحق");
                } else {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ref.child(username.getText().toString().trim()).setValue(createParentObjectFromInputFields());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // show dialog Successfully registered
                    final Dialog dialog=new Dialog(SignUp.this);
                    dialog.setTitle(" ");
                    dialog.setContentView(R.layout.activity_register_succes_dialog);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(SignUp.this,SignIn.class);
                            startActivity(i);
                        }
                    }, 5000);
                }
            }
        });
    }

    //function to validate the user inputs
    public boolean validateUserInput() {
        if (usenam.isEmpty()) {
            errorMSG.setText( "قم بإدخال إسم المستخدم ");
            username.requestFocus();
        } else if (emailID.isEmpty()) {
            errorMSG.setText("قم بإدخال البريد الإلكتروني");
            email.requestFocus();
        } else if (!isEmail(emailID)) {
            errorMSG.setText("تأكد من إدخال بريد إلكتروني صحيح ");
            email.requestFocus();
        }else if (paswd.isEmpty()) {
            errorMSG.setText("قم بإدخال الرقم السري");
            password.requestFocus();
        } else if (conf.isEmpty()) {
            errorMSG.setText("قم بتأكيد الرقم السري");
            confirmPassword.requestFocus();
        } else if(!isPassMatch(paswd,conf)){
            errorMSG.setText("كلمة المرور غير متطابقة");
        } else if (pho.isEmpty()) {
            errorMSG.setText( "قم بإدخال رقم الجوال");
            phone.requestFocus();
        } else if (lock.isEmpty()) {
            errorMSG.setText( "قم بإدخال رمز القفل");
            password.requestFocus();
        } else if (!isPassword(paswd)) {
            errorMSG.setText( "يجب أن تحتوي كلمة السر على حروف، أرقام ورموز بعدد ٨ خانات");
            password.requestFocus();
        } else if (!isPhoneNumber(pho)) {
            errorMSG.setText("تأكد من إدخالك لرقم جوال صحيح");
            phone.requestFocus();
        } else if (!islock(lock)) {
            errorMSG.setText( "تأكد من إدخالك لرمز قفل مكون من ٥ أرقام");
            usernalockKeyme.requestFocus();}

        else {
            return true;
        }
        return false;
    }
}

