package com.example.arogyademo;

import android.Manifest;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.nio.channels.Channel;

public class App extends Application {
    public static final String CHANNEL_1_ID="Channel 1";
    public static final int REQUEST_SMS=101;
    public static final int REQUEST_PHONE_STATE=102;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel1=new NotificationChannel(CHANNEL_1_ID,"General Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("THIS CHANNEL HANDLES GENERAL APP NOTIFICATIONS");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
