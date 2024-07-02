
package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private ImageButton google_btn,microsoft_btn;
    private TextView NoAccBtn,forgotPswd;
    private URL uri;
    private EditText etUsername,etPassword,SignInUsername,SignInPassword;

    private CheckBox chkBox;
     private int flag =1;

     SharedPreferences preferences; //temp data Storage
    SharedPreferences.Editor editor; //edits the temp stored data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        if(preferences.getBoolean("isLogin",false)){ //(Not Logged in By Default) if this condition is true then jump
            jumpHome();
        }


        loginBtn = findViewById(R.id.etLoginBtn);
        NoAccBtn = findViewById(R.id.tvLoginCheck);
        google_btn= findViewById(R.id.ibLoginGoogle);
        microsoft_btn= findViewById(R.id.ibLoginMicro);
        forgotPswd = findViewById(R.id.etLoginFgtPwd);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPswd);
        chkBox = findViewById(R.id.cbPass);



        forgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpForgotPswd();
            }
        });
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username field should not be Empty.");
                } else if (etUsername.getText().toString().length() < 8) {
                    etUsername.setError("Username must be 8 character or above");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password field should not be Empty.");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password must be 8 character or above");
                } else if (!etPassword.getText().toString().matches(".*[a-z].*")) {
                    etPassword.setError("Password must contain a lowercase character");
                } else if (!etPassword.getText().toString().matches(".*[A-Z].*")) {
                    etPassword.setError("Password must contain a uppercase character");
                } else if (!etPassword.getText().toString().matches(".*[0-9].*")) {
                    etPassword.setError("Password must contain a Numeric value");
                } else if (!etPassword.getText().toString().matches(".*[@$_#].*")) {
                    etPassword.setError("Password must contain a Special Symbol");
                }
                else {
                    flag = 0;
                }
                if (flag == 0){
                    editor.putBoolean("isLogin",true).commit(); //Updated Key after Login

                    Toast.makeText(LoginActivity.this, "LoggedIn Succesfully!!", Toast.LENGTH_SHORT).show();

                    jumpHome();

                }

            }



        });
        NoAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateSignIn();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void jumpHome() {
        Intent i = new Intent(LoginActivity.this, Home.class);

        startActivity(i);

    }
    private void jumpForgotPswd() {
        Intent i = new Intent(LoginActivity.this, ForgotPasswd.class);
        startActivity(i);

    }
    private void navigateSignIn(){
        Intent i = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(i);
    }

}

