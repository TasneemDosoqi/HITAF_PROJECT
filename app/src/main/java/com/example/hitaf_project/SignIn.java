package com.example.hitaf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView forgotBtn,newUser, errorMSG;
    Button signintoaccount;
    EditText email, password;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotBtn = (TextView) findViewById(R.id.forgot_password);
        email = (EditText) findViewById(R.id.edit_username);
        password = (EditText) findViewById(R.id.edit_password);
        signintoaccount=(Button) findViewById(R.id.signinBtn);
        newUser = findViewById(R.id.NewUser);
        errorMSG = findViewById(R.id.error_messages);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = null;

                if (user != null) {
                    Intent I = new Intent(SignIn.this, Home1.class);
                    startActivity(I);
                } else {
                    errorMSG.setText(" ");
                }
            }
        };
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(SignIn.this, SignUp.class);
                startActivity(I);
            }
        });
        signintoaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail =  email.getText().toString();

                final String userPaswd = password.getText().toString();

                if (userEmail.isEmpty()) {

                    errorMSG.setText("فضلاً أدخل بريدك الإلكتروني");
                    email.requestFocus();
                } else if (!isEmail(email.getText().toString())) {
                    errorMSG.setText("تأكد من إدخال بريد إلكتروني صحيح ");
                    email.requestFocus();
                }else if (userPaswd.isEmpty()) {
                    errorMSG.setText("فضلاً أدخل كلمة المرور الخاصة بحسابك");
                    password.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    errorMSG.setText("فضلاً أدخل البيانات أعلاه");
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(SignIn.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                errorMSG.setText("فضلاً تأكد من البيانات المدخلة !");

                            } else {
                                Intent i = new Intent(SignIn.this, Home1.class);
                                i.putExtra("userPass",userPaswd);
                                i.putExtra("userEmail",userEmail);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart () {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    //Function to check the validation of the Email
    public boolean isEmail(String s) {
        return s.matches("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+");
    }


    // Function for new user to go to sign up page
    public void newUser(View view){
        Intent intent=new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //function to reset user password
    public void reset(View view) {
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();
                if (emailAddress.length() == 0) {
                    Dialog dialog = new Dialog(SignIn.this);
                    dialog.setTitle(" ");
                    dialog.setContentView(R.layout.layout_dialog2);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                }
                else {
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Dialog dialog = new Dialog(SignIn.this);
                                        dialog.setTitle(" ");
                                        dialog.setContentView(R.layout.layout_dialog1);
                                        dialog.setCanceledOnTouchOutside(true);
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        dialog.show();
                                    }
                                }
                            });
                }
            }
        });
    }

    //Functions to show reset password success dialog
    public void clickSend(View view) {
        Dialog dialog=new Dialog(SignIn.this);
        dialog.setTitle(" ");
        dialog.setContentView(R.layout.layout_dialog1);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }


    //Showing information about HIFAF project team and email
    public void aboutUs(View view) {
        final Dialog dialog=new Dialog(SignIn.this);
        dialog.setContentView(R.layout.about_us_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
}




