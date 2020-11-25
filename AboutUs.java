package com.example.arogyademo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AboutUs extends AppCompatActivity {

    ImageView imageViewDev1Call,imageViewDev2Call,imageViewDev3Call,imageViewDev1Mail,imageViewDev2Mail,imageViewDev3Mail,imageViewDev1SMS,imageViewDev2SMS,imageViewDev3SMS,imageViewDev1Whatsapp,imageViewDev2Whatsapp,imageViewDev3Whatsapp;
    String[] arrayPhone,arrayEmail;
    String phoneNumber,email;
    String developer_help_message;
    String developer_help_subject="AROGYA HELP";
    public static final int REQUEST_CALL=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //INITIALIZATION
        imageViewDev1Call=findViewById(R.id.ID_AboutUs_Dev1Call_IV);
        imageViewDev2Call=findViewById(R.id.ID_AboutUs_Dev2Call_IV);
        imageViewDev3Call=findViewById(R.id.ID_AboutUs_Dev3Call_IV);
        imageViewDev1Mail=findViewById(R.id.ID_AboutUs_Dev1Mail_IV);
        imageViewDev2Mail=findViewById(R.id.ID_AboutUs_Dev2Mail_IV);
        imageViewDev3Mail=findViewById(R.id.ID_AboutUs_Dev3Mail_IV);
        imageViewDev1SMS=findViewById(R.id.ID_AboutUs_Dev1SMS_IV);
        imageViewDev2SMS=findViewById(R.id.ID_AboutUs_Dev2SMS_IV);
        imageViewDev3SMS=findViewById(R.id.ID_AboutUs_Dev3SMS_IV);
        imageViewDev1Whatsapp=findViewById(R.id.ID_AboutUs_Dev1Whatsapp_IV);
        imageViewDev2Whatsapp=findViewById(R.id.ID_AboutUs_Dev2Whatsapp_IV);
        imageViewDev3Whatsapp=findViewById(R.id.ID_AboutUs_Dev3Whatsapp_IV);
        arrayPhone=getResources().getStringArray(R.array.developer_phone);
        arrayEmail=getResources().getStringArray(R.array.developer_email);
        developer_help_message=getResources().getString(R.string.developer_help_message);
        //CALL HANDLING
        imageViewDev1Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[0];
                makePhoneCall();
            }
        });
        imageViewDev2Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[1];
                makePhoneCall();
            }
        });
        imageViewDev3Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[2];
                makePhoneCall();
            }
        });
        imageViewDev1SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[0];
                sendSMS();
            }
        });
        imageViewDev2SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[1];
                sendSMS();
            }
        });
        imageViewDev3SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[2];
                sendSMS();
            }
        });
        imageViewDev1Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[0];
                sendWhatsappMessage();
            }
        });
        imageViewDev2Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[1];
                sendWhatsappMessage();
            }
        });
        imageViewDev3Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=arrayPhone[2];
                sendWhatsappMessage();
            }
        });
        imageViewDev1Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=arrayEmail[0];
                sendEmail();
            }
        });
        imageViewDev2Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=arrayEmail[1];
                sendEmail();
            }
        });
        imageViewDev3Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=arrayEmail[2];
                sendEmail();
            }
        });


    }
    private void sendEmail()
    {
        String rec_list=email;
        String recipient[]=rec_list.split(",");
        String subject=developer_help_subject;
        String content=developer_help_message;
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose app for sending Email"));
    }
    private void sendWhatsappMessage()
    {
        String toNumber=phoneNumber;
        toNumber=toNumber.replace("+","").replace(" ","").replace("-","");
        String msg=developer_help_message;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_VIEW);
        String url = "https://api.whatsapp.com/send?phone=" + toNumber + "&text=" + msg;
        sendIntent.setData(Uri.parse(url));
        startActivity(sendIntent);
    }
    private void sendSMS()
    {
        String smsNumber="smsto:"+phoneNumber;
        String sms=developer_help_message;
        Intent smsIntent=new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body",sms);
        startActivity(smsIntent);
    }
    private void makePhoneCall() {
        String call_num=phoneNumber;
        if(call_num.length()>0&&call_num.length()<15)
        {
            if(ContextCompat.checkSelfPermission(AboutUs.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(AboutUs.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else
            {
                String dial="tel:"+call_num;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(this,"PERMISSION DENIED",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall();
            }
            else
            {
                Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
