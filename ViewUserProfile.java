package com.example.arogyademo;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUserProfile extends AppCompatActivity {
    String stringID, stringName, stringGender, stringDOB, stringBloodGroup, stringState, stringCity;
    TextView textViewID, textViewName, textViewGender, textViewDOB, textViewBlood_group, textViewState, textViewCity;
    ImageView imageView;
    Cursor cursor;
    String currentUserId;
    char[] charArraycurrentUserId;
    String pathUserId;
    SharedPreferences sharedPreferences;
    String NA = "Data Not Available";
    DatabaseReference databaseReference;
    Data_User data_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("id", null);
        data_user=new Data_User();
        //FIREBASE
        charArraycurrentUserId = currentUserId.toCharArray();
        for (int i = 0; i < currentUserId.length(); i++) {
            if (charArraycurrentUserId[i] == '.') {
                charArraycurrentUserId[i] = ':';
            }
        }
        pathUserId = String.valueOf(charArraycurrentUserId);//
        textViewID = findViewById(R.id.ID_ViewUserProfile_ID_TV);
        textViewName = findViewById(R.id.ID_ViewUserProfile_Name_TV);
        textViewGender = findViewById(R.id.ID_ViewUserProfile_Gender_TV);
        textViewDOB = findViewById(R.id.ID_ViewUserProfile_DOB_TV);
        textViewBlood_group = findViewById(R.id.ID_ViewUserProfile_Blood_Group_TV);
        textViewState = findViewById(R.id.ID_ViewUserProfile_State_TV);
        textViewCity = findViewById(R.id.ID_ViewUserProfile_City_TV);
        imageView = findViewById(R.id.ID_VProfile_DP);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                data_user = dataSnapshot.getValue(Data_User.class);
                textViewID.setText(currentUserId);
                textViewName.setText(data_user.getName());
                textViewGender.setText(data_user.getGender());
                textViewDOB.setText(data_user.getDate());
                textViewBlood_group.setText(data_user.getBlood_group());
                textViewState.setText(data_user.getState());
                textViewCity.setText(data_user.getCity());
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                textViewID.setText(currentUserId);
                textViewName.setText(NA);
                textViewGender.setText(NA);
                textViewDOB.setText(NA);
                textViewBlood_group.setText(NA);
                textViewState.setText(NA);
                textViewCity.setText(NA);
                Toast.makeText(ViewUserProfile.this,"User Info. Not Available",Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference=FirebaseDatabase.getInstance().getReference("users/"+pathUserId);
        databaseReference.addValueEventListener(userListener);

        /*SQLITE FUNCTIONALITY
        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.searchUserByPhone(currentUserID);
        cursor.moveToFirst();
        textViewPhone.setText(currentUserID);
        if (cursor.getString(cursor.getColumnIndex("NAME")) != null)
            textViewName.setText(cursor.getString(cursor.getColumnIndex("NAME")));
        if (cursor.getString(cursor.getColumnIndex("GENDER")) != null)
            textViewGender.setText(cursor.getString(cursor.getColumnIndex("GENDER")));
        if (cursor.getString(cursor.getColumnIndex("DOB")) != null)
            textViewDOB.setText(cursor.getString(cursor.getColumnIndex("DOB")));
        if (cursor.getString(cursor.getColumnIndex("EMAIL")) != null)
            textViewEmail.setText(cursor.getString(cursor.getColumnIndex("EMAIL")));
        if (cursor.getString(cursor.getColumnIndex("BLOOD_GROUP")) != null)
            textViewBlood_group.setText(cursor.getString(cursor.getColumnIndex("BLOOD_GROUP")));
        if (cursor.getString(cursor.getColumnIndex("STATE")) != null)
            textViewState.setText(cursor.getString(cursor.getColumnIndex("STATE")));
        if (cursor.getString(cursor.getColumnIndex("CITY")) != null)
            textViewCity.setText(cursor.getString(cursor.getColumnIndex("CITY")));
        if (cursor.getString(cursor.getColumnIndex("IMAGE")) != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex("IMAGE")));
            imageView.setImageBitmap(bitmap);
        }*/
    }
}
