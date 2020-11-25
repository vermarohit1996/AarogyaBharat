package com.example.arogyademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HealthTips extends AppCompatActivity {
    ImageView imageViewBMI;
    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);
        currentUserId=getIntent().getStringExtra("id");
        imageViewBMI=findViewById(R.id.ID_HealthTips_BMI_IV);
        imageViewBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HealthTips.this,BMI.class);
                intent.putExtra("id",currentUserId);
                startActivity(intent);
            }
        });
    }
}
