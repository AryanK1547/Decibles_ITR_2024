package com.example.myapplication;

import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    ImageView ivMainAnim;
    TextView tvMainCmpAnim,tvMainSlgAnim;
    Animation fadeInAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        ivMainAnim = findViewById(R.id.ivMainLogo);
        tvMainCmpAnim = findViewById(R.id.tvMainCmpName);
        tvMainSlgAnim = findViewById(R.id.tvMainCmpslg);

        fadeInAnim= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fadein);

        ivMainAnim.setAnimation(fadeInAnim);
        tvMainCmpAnim.setAnimation(fadeInAnim);
        tvMainSlgAnim.setAnimation(fadeInAnim);

        Handler handler = new Handler();
        handler.postDelayed( new Runnable(){
        @Override
        public void run(){
            Intent i =new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }}  ,3000);

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}