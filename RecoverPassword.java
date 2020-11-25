package com.example.arogyademo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class RecoverPassword extends AppCompatActivity {
    public static final int REQUEST_PHONE_STATE=101;
    public static final int REQUEST_SMS=102;
    EditText email,id,phone,editTextOTP;
    Button recover;
    String password;//password used for recovery
    DatabaseHelper databaseHelper;
    String sphone;
    String otp="123";
    String currentUserId;
    LinearLayout linearLayoutOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        editTextOTP=findViewById(R.id.ID_RP_OTP_ET);
        linearLayoutOTP=findViewById(R.id.ID_RP_OTP_LinearLayout);
        linearLayoutOTP.setVisibility(LinearLayout.INVISIBLE);
        phone=findViewById(R.id.ID_RP_Phone_ET);
        recover=findViewById(R.id.ID_RP_RecoverPass_BT);
        databaseHelper=new DatabaseHelper(this);
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sphone=phone.getText().toString().trim();
                currentUserId=sphone;
                Cursor cursorPhone=databaseHelper.searchUserByPhone(sphone);
                if(cursorPhone.getCount()==0)
                {
                    Toast.makeText(RecoverPassword.this, "User Not Found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cursorPhone.moveToFirst();
                    //password=cursorPhone.getString(cursorPhone.getColumnIndex("PASSWORD"));
                    //GENERATE OTP
                    otpGenerator();
                    sendSms();
                    linearLayoutOTP.setVisibility(LinearLayout.VISIBLE);
                }


            }
        });

    }

    private void otpGenerator() {
        Random rand=new Random();
        int max=9999;
        int min=1000;
        int temp= rand.nextInt((max-min)+1)+min;
        otp=Integer.toString(temp);
    }

    public void onSubmitOTP(View view) {
        String otp_submitted=editTextOTP.getText().toString().trim();
        if(otp_submitted.equals(otp))
        {
            Toast.makeText(RecoverPassword.this,"OTP VALIDATED...",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RecoverPassword.this,CreateNewPassword.class);
            intent.putExtra("id",currentUserId);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(RecoverPassword.this,"Please Enter OTP Again!!!",Toast.LENGTH_LONG).show();
        }

    }
    private void sendSms() {
        String smsNumber=sphone;
        String smsMessage="Team Arogya:OTP to Recover Account:"+sphone+" is:"+otp;
        if(ContextCompat.checkSelfPermission(RecoverPassword.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RecoverPassword.this,new String[] {Manifest.permission.READ_PHONE_STATE},REQUEST_PHONE_STATE);
        }
        else if(ContextCompat.checkSelfPermission(RecoverPassword.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RecoverPassword.this,new String[] {Manifest.permission.SEND_SMS},REQUEST_SMS);
        }
        else//if permission granted
        {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumber,null,smsMessage,null,null);
            Toast.makeText(RecoverPassword.this,"OTP to Registered Mobile Number",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_SMS:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    sendSms();
                }
                else
                {
                    //IF PERMISSION DENIED
                    Toast.makeText(RecoverPassword.this,"SEND SMS PERMISSION NOT GRANTED",Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_PHONE_STATE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    sendSms();
                }
                else
                {
                    Toast.makeText(RecoverPassword.this,"REQUEST PHONE STATE NOT GRANTED",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}
