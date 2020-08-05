package com.example.hitaf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Resetpassword extends AppCompatActivity {
    FirebaseAuth auth1;
    FirebaseUser user;
    EditText password1;
    EditText password2;
    EditText oldPassword;
    String pass1;
    String pass2;
    String oldpass;
    Button resetpassBtn;
    String UserPassword;
    Button backSettingBtn;
    TextView Err;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        password1 = (EditText) findViewById(R.id.Password1);
        password2 = (EditText) findViewById(R.id.Password2);
        oldPassword = (EditText) findViewById(R.id.oldpassword);
        resetpassBtn = (Button) findViewById(R.id.Confirm_button);
        Err = findViewById(R.id.errorText);
        Err.setText(" ");
        auth1 = FirebaseAuth.getInstance();
        Intent i=getIntent();
        UserPassword=i.getStringExtra("userPass");
        backSettingBtn = (Button)findViewById(R.id.back_home);

        //Back to setting page button
        backSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), setting.class);
                startActivity(i);
            }
        });

        //Reset password button


        resetpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass1 = password1.getText().toString();
                pass2 = password2.getText().toString();
                oldpass = oldPassword.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();

                //Checking if all entered information right
                if (pass1.isEmpty() || pass2.isEmpty() || oldpass.isEmpty()) {
                    Err.setText( "تأكد من تعبئة جميع الحقول");
                }
                 else if (!isPassword(pass1) || !isPassword(oldpass)) {
                    Err.setText( "يجب أن تحتوي كلمة السر على حروف، أرقام ورموز بعدد ٨ خانات");
                }
                else if (!pass1.equals(pass2)) {
                    Err.setText("تأكد من تطابق كلمة المرور");
                }
                else if(!isPassMatch(oldpass,UserPassword)){
                    Err.setText("كلمة المرور الحالية لا تطابق كلمة المرور المدخلة");
                }
                else if (user !=null)  {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldpass);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                //if reset password done successfully
                                user.updatePassword(pass1).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Dialog dialog = new Dialog(Resetpassword.this);
                                            dialog.setTitle(" ");
                                            dialog.setContentView(R.layout.resetpass_success_dialog);
                                            dialog.setCanceledOnTouchOutside(true);
                                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            dialog.show();

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Intent i=new Intent(getApplicationContext(),setting.class);
                                                    startActivity(i);
                                                }
                                            }, 5000);

                                        }
                                    }


                                });
                            }
                        }
                    });

                }
            }
        });
    }

    //Function to check if the password entered and confirm password matches
    public boolean isPassMatch(String s1, String s2){
        if(s1.equals(s2)){
            return true;
        }
        return false;
    }

    //Function to check the strength of the password
    public boolean isPassword(String s) {
        return s.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*.-]).{8,}$");
    }


}
