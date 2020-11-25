package com.example.arogyademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BMI extends AppCompatActivity {
    EditText editTextWeight, editTextHeight;
    TextView textViewResult, textViewTips;
    String currentUserId;
    LinearLayout linearLayoutBMIResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        currentUserId = getIntent().getStringExtra("id");
        editTextHeight = findViewById(R.id.ID_BMI_height_ET);
        editTextWeight = findViewById(R.id.ID_BMI_weight_ET);
        linearLayoutBMIResult = findViewById(R.id.ID_BMI_LinearAfterCalculate_Layout);
        linearLayoutBMIResult.setVisibility(LinearLayout.INVISIBLE);
        textViewResult = findViewById(R.id.ID_BMI_BMIResult_TV);
        textViewTips = findViewById(R.id.ID_BMI_TIPS_TV);


    }

    public void calculateBMI(View view) {
        double weight = Double.parseDouble(editTextWeight.getText().toString().trim());
        double height = Double.parseDouble(editTextHeight.getText().toString().trim());
        double bmi = weight * 10000 / (height * height);
        DecimalFormat bmi_format=new DecimalFormat("#.##");
        String[] bmi_tips = getResources().getStringArray(R.array.bmi);
        String bmi_category;
        if (bmi < 18.5) {
            bmi_category = "Under-Weight";
            textViewTips.setText(bmi_tips[0]);
            textViewResult.setBackgroundColor(R.color.yellow);
        } else if (bmi >= 18.5 && bmi < 25) {
            bmi_category = "Healthy";
            textViewTips.setText(bmi_tips[1]);
            textViewResult.setBackgroundColor(R.color.green);
        } else if (bmi >= 25 && bmi < 30) {
            bmi_category = "Over-Weight";
            textViewTips.setText(bmi_tips[2]);
            textViewResult.setBackgroundColor(R.color.fuchsia);
        } else {
            bmi_category = "Obese";
            textViewTips.setText(bmi_tips[3]);
            textViewResult.setBackgroundColor(R.color.red);
        }
        textViewResult.setText("BMI=" + bmi_format.format(bmi) + " ," + bmi_category);
        linearLayoutBMIResult.setVisibility(LinearLayout.VISIBLE);
    }
}
