package com.example.arogyademo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomePage extends AppCompatActivity {
    String currentUserId;//PHONE NUMBER
    ViewFlipper viewFlipperTop;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        currentUserId = getIntent().getStringExtra("id");
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        viewFlipperTop = findViewById(R.id.ID_Home_ViewFlipperTop);
        //Carousel image array
        int[] images = {R.drawable.viewflipper_1, R.drawable.viewflipper_2, R.drawable.viewflipper_3, R.drawable.viewflipper_4, R.drawable.viewflipper_5};
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            viewFlipperTop.addView(imageView);
            viewFlipperTop.setFlipInterval(2000);
            viewFlipperTop.setAutoStart(true);
            viewFlipperTop.setInAnimation(this, android.R.anim.slide_in_left);
            viewFlipperTop.setOutAnimation(this, android.R.anim.slide_out_right);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ID_Options_CompleteProfile) {
            Intent intent = new Intent(HomePage.this, UpdateUserProfile.class);
            intent.putExtra("id", currentUserId);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.ID_Options_ViewProfile) {
            Intent intent = new Intent(HomePage.this, ViewUserProfile.class);
            intent.putExtra("id", currentUserId);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.ID_Options_LogOut) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HomePage.this, SignInSignUpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onOnlineAppointmentBooking(View view) {
        Intent intent = new Intent(HomePage.this, OnlineAppointmentBooking.class);
        startActivity(intent);
    }

    public void onDiseaseEncyclopedia(View view) {
        Intent intent = new Intent(HomePage.this, DiseaseList.class);
        startActivity(intent);
    }

    public void onEmergencyCare(View view) {
        Intent intent = new Intent(HomePage.this, EmergencyCare.class);
        startActivity(intent);
    }

    public void onHealthTipsnTricks(View view) {
        Intent intent = new Intent(HomePage.this, HealthTips.class);
        startActivity(intent);
    }

    public void onNearbyHospitalSearch(View view) {
        Intent intent=new Intent(HomePage.this,NearbyHospitalSearch.class);
        startActivity(intent);
    }
}
