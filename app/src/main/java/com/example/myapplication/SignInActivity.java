package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInActivity extends AppCompatActivity {
    private TextView AccBtn;
    private EditText FirstName,LastName,username,password,contact,email;
    private ImageButton google_btn,microsoft_btn;
    private CheckBox passwordSignIn;
    private int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        AppCompatButton signinBtn = findViewById(R.id.etSignInBtn);
        AccBtn = findViewById(R.id.tvSignInCheck);
        username = findViewById(R.id.etSignInUsername);
        password = findViewById(R.id.etSignInPswd);
        contact = findViewById(R.id.etSignInPhone);
        email = findViewById(R.id.etSignInEmail);
        passwordSignIn = findViewById(R.id.cbSignInPass);
        FirstName = findViewById(R.id.etSignInFirstName);
        LastName = findViewById(R.id.etSignInLastName);

         passwordSignIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked){
                     password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                 }
                 else password.setTransformationMethod(PasswordTransformationMethod.getInstance());
             }
         });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                if (FirstName.getText().toString().isEmpty()) {
                    FirstName.setError("Name Cannot Be Empty");
                    FirstName.requestFocus();
                    valid = false;
                } else if (FirstName.getText().toString().contains(" ")) {
                    FirstName.setError("No Space in Between is Allowed.");
                    FirstName.requestFocus();
                    valid = false;
                }

                if (valid && LastName.getText().toString().isEmpty()) {
                    LastName.setError("Name Cannot Be Empty");
                    LastName.requestFocus();
                    valid = false;
                } else if (valid && LastName.getText().toString().contains(" ")) {
                    LastName.setError("No Space in Between is Allowed.");
                    LastName.requestFocus();
                    valid = false;
                }

                if (valid && email.getText().toString().isEmpty()) {
                    email.setError("Email should not be Empty");
                    email.requestFocus();
                    valid = false;
                } else if (valid && (!email.getText().toString().contains("@") || !email.getText().toString().contains(".com"))) {
                    email.setError("Enter Valid Email Id.");
                    email.requestFocus();
                    valid = false;
                }

                String etcontact = contact.getText().toString();
                if (valid && etcontact.isEmpty()) {
                    contact.setError("Contact number cannot be empty.");
                    contact.requestFocus();
                    valid = false;
                } else if (valid && etcontact.length() != 10) {
                    contact.setError("Contact must contain 10 digits.");
                    contact.requestFocus();
                    valid = false;
                }

                if (valid && username.getText().toString().isEmpty()) {
                    username.setError("Username field should not be Empty.");
                    username.requestFocus();
                    valid = false;
                } else if (valid && username.getText().toString().length() < 8) {
                    username.setError("Username must be 8 characters or above");
                    username.requestFocus();
                    valid = false;
                }

                if (valid && password.getText().toString().isEmpty()) {
                    password.setError("Password field should not be Empty.");
                    password.requestFocus();
                    valid = false;
                } else if (valid && password.getText().toString().length() < 8) {
                    password.setError("Password must be 8 characters or above");
                    password.requestFocus();
                    valid = false;
                } else if (valid && !password.getText().toString().matches(".*[a-z].*")) {
                    password.setError("Password must contain a lowercase character");
                    password.requestFocus();
                    valid = false;
                } else if (valid && !password.getText().toString().matches(".*[A-Z].*")) {
                    password.setError("Password must contain an uppercase character");
                    password.requestFocus();
                    valid = false;
                } else if (valid && !password.getText().toString().matches(".*[0-9].*")) {
                    password.setError("Password must contain a numeric value");
                    password.requestFocus();
                    valid = false;
                } else if (valid && !password.getText().toString().matches(".*[@$_#].*")) {
                    password.setError("Password must contain a special symbol");
                    password.requestFocus();
                    valid = false;
                }

                if (valid) {
                    Toast.makeText(SignInActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                    jumpLogin();
                }
            }
                                     });

        AccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { jumpLogin();}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signIn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void jumpLogin() {
        Intent i = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(i);

    }
}