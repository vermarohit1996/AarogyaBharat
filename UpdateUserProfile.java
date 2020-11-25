package com.example.arogyademo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateUserProfile extends AppCompatActivity {
    String sex;
    EditText editTextName,editTextDOB,editTextCity;
    TextView textViewCurrentUser;
    Spinner spinnerBloodGroup,spinnerState;
    SharedPreferences sharedPreferences;
    String currentUserId;
    int mYear,mMonth,mDay;
    String stringName,stringSex,stringDOB,stringBloodGroup,stringState,stringCity;
    String[] states,blood;
    DatePickerDialog.OnDateSetListener dobListener;
    //DATABASE PARAMETER INIT.
    char charArraycurrentUserId[];
    String pathUserId;
    DatabaseReference databaseReference;
    Data_User data_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        currentUserId=sharedPreferences.getString("id",null);
        //FIREBASE
        charArraycurrentUserId=currentUserId.toCharArray();
        for(int i=0;i<currentUserId.length();i++)
        {
            if(charArraycurrentUserId[i]=='.')
            {
                charArraycurrentUserId[i]=':';
            }
        }
        pathUserId=String.valueOf(charArraycurrentUserId);//
        editTextName=findViewById(R.id.ID_UpdateUserProfile_Name_ET);
        editTextDOB=findViewById(R.id.ID_UpdateUserProfile_Date_ET);
        editTextCity=findViewById(R.id.ID_UpdateUserProfile_City_ET);
        textViewCurrentUser=findViewById(R.id.ID_UpdateUserProfile_CurrentUser_TV);
        spinnerState=findViewById(R.id.ID_UpdateUserProfile_State_Spinner);
        spinnerBloodGroup=findViewById(R.id.ID_UpdateUserProfile_BloodGroup_Spinner);


        textViewCurrentUser.setText("Logged in as:"+currentUserId);

        //SPINNER
        blood = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, blood);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodAdapter);

        //SPINNER
        states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(statesAdapter);

        //Date Picker
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                Calendar caldob = Calendar.getInstance();
                int year = caldob.get(Calendar.YEAR);
                int month = caldob.get(Calendar.MONTH);
                int day = caldob.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateUserProfile.this, android.R.style.Theme_Holo_Light, dobListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dobListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editTextDOB.setText(date);

            }
        };
    }

    public void onUpdateProfile(View view) {
        stringName=editTextName.getText().toString().trim();
        stringSex=sex;
        stringDOB=editTextDOB.getText().toString().trim();
        stringBloodGroup=(String)spinnerBloodGroup.getSelectedItem();
        stringState=(String)spinnerState.getSelectedItem();
        stringCity=editTextCity.getText().toString().trim();
        /*
        databaseReferenceName.setValue(stringName);
        databaseReferenceGender.setValue(stringSex);
        databaseReferenceDOB.setValue(stringDOB);
        databaseReferenceBloodGroup.setValue(stringBloodGroup);
        databaseReferenceState.setValue(stringState);
        databaseReferenceCity.setValue(stringCity);*/
        data_user=new Data_User(currentUserId,stringName,stringSex,stringDOB,stringBloodGroup,stringState,stringCity);
        databaseReference.child("users").child(pathUserId).setValue(data_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateUserProfile.this,"Profile Updated...",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateUserProfile.this,"Try Again...",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onRadioButtonClicked(View view) {
        if(view.getId()==R.id.radioButtonMale)
        {
            sex="M";
        }
        else if(view.getId()==R.id.radioButtonFemale)
        {
            sex="F";
        }
        else if(view.getId()==R.id.radioButtonOther)
        {
            sex="O";
        }
    }
    private void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
