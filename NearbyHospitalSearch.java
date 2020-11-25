package com.example.arogyademo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class NearbyHospitalSearch extends FragmentActivity implements OnMapReadyCallback {
    private static final int MY_LOCATION_REQUEST_CODE = 101;
    int PROXIMITY_RADIUS=10000;
    double latitude,longitude;
    private FusedLocationProviderClient client;
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospital_search);
        client = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        fetchLocation();//makes my location enabled in maps
        getUserLastKnownLocation();
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //LatLng UP=new LatLng(28.978185, 77.930725);
        LatLng lastKnownLocation=new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(lastKnownLocation).title("MY LOC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastKnownLocation));

    }
    public void onButtonClick(View view) {
        mMap.clear();
        String hospital="hospital";
        Toast.makeText(NearbyHospitalSearch.this,latitude+","+longitude,Toast.LENGTH_LONG).show();
        /*latitude=28.984463;
        longitude=77.706413;*/
        String url=getUrl(latitude,longitude,hospital);
        Object dataTransfer[]=new Object[2];
        dataTransfer[0]=mMap;
        dataTransfer[1]=url;
        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(NearbyHospitalSearch.this,"Showing Nearby Hospitals",Toast.LENGTH_LONG).show();
    }
    private String getUrl(Double latitude,Double longitude,String nearbyPlace)
    {
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyDXLOB7Xa3OCJq1SGx_18ihvBgfMslvulc");
        return googlePlaceUrl.toString();
    }

    private void getUserLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(NearbyHospitalSearch.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NearbyHospitalSearch.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        } else //if permission granted
        {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    } else {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        Toast.makeText(NearbyHospitalSearch.this, "Turn On Location!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    //fetch location makes maps my location enabled true
    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(NearbyHospitalSearch.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            } else //if permission not granted
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
