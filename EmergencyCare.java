package com.example.arogyademo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmergencyCare extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String currentUserId;
    //DATABASE PARAMETER INIT.
    char charArraycurrentUserId[];
    String pathUserId;
    public static final int REQUEST_LOCATION = 101;
    private FusedLocationProviderClient client;
    DatabaseReference databaseReference;
    Data_Emergency data_emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_care);
        client = LocationServices.getFusedLocationProviderClient(this);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("id", null);
        data_emergency=new Data_Emergency();
        //FIREBASE
        charArraycurrentUserId = currentUserId.toCharArray();
        for (int i = 0; i < currentUserId.length(); i++) {
            if (charArraycurrentUserId[i] == '.') {
                charArraycurrentUserId[i] = ':';
            }
        }
        pathUserId = String.valueOf(charArraycurrentUserId);//
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }

    public void onHelpButtonPressed(View view) {
        getUserLocation();
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(EmergencyCare.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmergencyCare.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else //if permission granted
        {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String lat_long=latitude+","+longitude;
                        data_emergency.setUserId(currentUserId);
                        data_emergency.setLocation(lat_long);
                        databaseReference.child("emergency").child(pathUserId).setValue(data_emergency)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(EmergencyCare.this,"Help is on the Way...",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EmergencyCare.this,"Try Again...",Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        Toast.makeText(EmergencyCare.this, "Turn On Location!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else //if permission not granted
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
