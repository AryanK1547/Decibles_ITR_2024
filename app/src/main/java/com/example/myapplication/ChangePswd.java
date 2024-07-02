package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePswd extends AppCompatActivity {
    AppCompatButton changePswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pswd);
        changePswd=findViewById(R.id.etChangePswdBtn);
        changePswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpLogin();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.changePswdMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void jumpLogin() {
        Intent i = new Intent(ChangePswd.this, LoginActivity.class);
        startActivity(i);

    }
}