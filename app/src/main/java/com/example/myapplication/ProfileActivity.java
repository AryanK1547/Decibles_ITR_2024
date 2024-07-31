package com.example.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_CHOOSER = 999;

    TextView tvToken, tvFname, tvLname, tvUsername, tvPassword, tvEmail, tvContact;
    ImageView ivProfile;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Uri imagePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        AppCompatButton profileChangeBtn = findViewById(R.id.btnLoginEditProfile);
        ivProfile = findViewById(R.id.ivprofile_image);
        tvToken = findViewById(R.id.tvProfileToken);
        tvPassword = findViewById(R.id.tvProfilePswd);
        tvFname = findViewById(R.id.tvProfileFirstName);
        tvLname = findViewById(R.id.tvProfileLastName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvContact = findViewById(R.id.tvProfilePhone);
        tvUsername = findViewById(R.id.tvProfileUsername);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        editor = sharedPreferences.edit(); // Initialize editor

         boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", true);
            if (!isLoggedIn) {
                clearProfileImage();
            }
        // Retrieve user data
        String strFname = sharedPreferences.getString("First_Name", "");
        String strLname = sharedPreferences.getString("Last_Name", "");
        String strUsername = sharedPreferences.getString("Username", "");
        String strEmail = sharedPreferences.getString("Email", "");
        String strPassword = sharedPreferences.getString("Password", "");
        String strContact = sharedPreferences.getString("Contact", "");
        String strImagePath = sharedPreferences.getString("Image_Path", "");

        // Set user data to views
        tvFname.setText(strFname);
        tvLname.setText(strLname);
        tvUsername.setText(strUsername);
        tvPassword.setText(strPassword);
        tvEmail.setText(strEmail);
        tvContact.setText(strContact);

        // Load profile image if available
        if (strImagePath != null && !strImagePath.isEmpty()) {
            File imageFile = new File(getFilesDir(), strImagePath);
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ivProfile.setImageBitmap(bitmap);
            }
        }

        // Set onClickListener for profileChangeBtn
        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Get FCM token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "FCM token Not Received", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Get new FCM registration token
                String token = task.getResult();
                tvToken.setText(token);
                System.out.println("Token: " + token);
                // Log and toast
                Toast.makeText(ProfileActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });

        // Handle window insets for edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_CHOOSER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ivProfile.setImageBitmap(bitmap);
                        saveImageToInternalStorage(bitmap);
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CHOOSER);
    }
    private void clearProfileImage() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("profileDir", Context.MODE_PRIVATE);
        File path = new File(directory, "profile.jpg");
        if (path.exists()) {
            path.delete();
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String filename = "profile_image.png";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            editor.putString("Image_Path", filename).apply();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
}
