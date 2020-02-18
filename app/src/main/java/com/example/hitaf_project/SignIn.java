package com.example.hitaf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    TextView forgotBtn;
  Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        forgotBtn = (TextView) findViewById(R.id.forgot_password);
        send=(Button) findViewById(R.id.sendEmail);
    }

    public void forgotPassword(View view) {
forgotBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       //startActivity(new Intent(this,));
        Dialog dialog=new Dialog(SignIn.this);
        dialog.setTitle(" ");
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
});
    }

       /* EmailDialog dialog=new EmailDialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();*/


       public void clickSend(View view) {
           Dialog dialog=new Dialog(SignIn.this);
           dialog.setTitle(" ");
           dialog.setContentView(R.layout.layout_dialog1);
           dialog.setCanceledOnTouchOutside(true);
           dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
           dialog.show();
       }

       public void newUser(View view){
           Intent intent=new Intent(this, SignUp.class);
           startActivity(intent);
       }
}




