package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPasswd extends AppCompatActivity {

  AppCompatButton submit,sendOTP;
  EditText email,receivedOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pswd);

        sendOTP =findViewById(R.id.etForgotEmailBtn);
        submit = findViewById(R.id.etForgotSubmitOtpBtn);
        email = findViewById(R.id.etForgotPEmail);
        receivedOTP = findViewById(R.id.etForgotOTP);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    email.setError("Invalid Email!!");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpPassChange();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fgpMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void jumpPassChange() {
        Intent i = new Intent(ForgotPasswd.this, ChangePswd.class);
        startActivity(i);

    }
}
