package com.example.myapplication;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Method;

public class Home extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeBtn;



   boolean doubleTap =false;
    @Override
    public void onBackPressed() {
        if(doubleTap){
            finishAffinity();
        }
        else{
            Toast.makeText(Home.this,"Press Again to Exit.",Toast.LENGTH_SHORT).show();
            doubleTap=true;
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTap=false;
                }
            },2000);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        //show Overfolow menu Icons
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
        try {
            Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
            m.setAccessible(true);
            m.invoke(menu, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itMenuLogOut){
          showLogoutAlertBox();
        }
        else if(item.getItemId()==R.id.itMenuMyProfie){
            Intent i= new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.itMenuAboutUs){
             Intent i= new Intent(Home.this, AboutUsActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.itMenuSettings){

             Intent i= new Intent(Home.this, SettingsActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.itMenuFeedback){
             Intent i= new Intent(Home.this, FeedbackActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.itMenuNotify){
            Toast.makeText(this,"No Recent Notifications Yet!!",Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.itMenuMyLocation){
            Intent i= new Intent(Home.this, MyLocationActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(com.google.android.material.R.color.background_material_dark)));
        preferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        editor=preferences.edit();




        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.itHomeBottomNavSearch);
        bottomNavigationView.setBackground(null);


        homeBtn=findViewById(R.id.itfloatingbtnHome);
        homeBtn.setBackgroundColor(Color.TRANSPARENT);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome,homeFragment).commit();
            }
        });

        boolean firsttime = preferences.getBoolean("isFirst",true);
        if (firsttime){
             showWelcomeAlertBox();

            editor.putBoolean("isFirst",false).commit();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showWelcomeAlertBox() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.welcome_firstuser_alertbox, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setView(customView);

        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        Button positiveButton = customView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                alertDialog.dismiss();
            }
        });
    }
    public void showLogoutAlertBox(){
         LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.logout_alertbox, null);

        // Create the alert dialog with the custom theme
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setView(customView);

        // Create the alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the dialog before setting width
        alertDialog.show();

        // Set the width to match_parent
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // Set click listener for the positive button
        Button negativeButton = customView.findViewById(R.id.negativeButton);
        Button positiveButton = customView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(Home.this, LoginActivity.class);
            startActivity(i);
            finish();
                Toast.makeText(Home.this,"Logged-Out Succesfully!!",Toast.LENGTH_SHORT).show();
                editor.putBoolean("isLogin",false).commit();
                editor.putBoolean("isFirst",true).commit();

            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                alertDialog.dismiss();
            }
        });
    }
      HomeFragment homeFragment =new HomeFragment();
       SearchFragment searchFragment =new SearchFragment();
        BrowseFragment browseFragment =new BrowseFragment();
        RadioFragment radioFragment =new RadioFragment();
        YourLibraryFragment libraryFragment =new YourLibraryFragment();

            @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item){

            if(item.getItemId()== R.id.itHomeBottomNavSearch){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome,searchFragment).commit();
            }
            else if(item.getItemId()== R.id.itHomeBottomNavBrowse){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome,browseFragment).commit();
            }
             else if(item.getItemId()== R.id.itHomeBottomNavYrLibrary){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome,libraryFragment).commit();
            }
              else if(item.getItemId()== R.id.itHomeBottomNavRadio){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome,radioFragment).commit();
            }
            return true;}}







