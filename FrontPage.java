package com.example.arogyademo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FrontPage extends AppCompatActivity {
    ImageView imageView;
    String targetActivity;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        imageView=findViewById(R.id.ID_FP_Back_IV);
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        imageView.animate().scaleX(2).scaleY(2).setDuration(3500).start();
        if(sharedPreferences.contains("id"))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Create the intent that will start the activity
                    Toast.makeText(FrontPage.this,"Already Signed In",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(FrontPage.this,HomePage.class);
                    FrontPage.this.startActivity(intent);
                    FrontPage.this.finish();
                }
            },3500);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Create the intent that will start the activity
                    Intent intent=new Intent(FrontPage.this,AfterFrontPage.class);
                    FrontPage.this.startActivity(intent);
                    FrontPage.this.finish();
                }
            },3500);
        }
    }
}
