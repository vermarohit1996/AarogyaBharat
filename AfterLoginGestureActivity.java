package com.example.arogyademo;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class AfterLoginGestureActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    GestureDetectorCompat gestureDetectorCompat;
    String currentUerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_gesture);

        gestureDetectorCompat=new GestureDetectorCompat(this,this);
        currentUerId=getIntent().getStringExtra("id");
    }
    //onTouchEvent need to be overridden for registering gestureDetectorCompat
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(this,"Kindly Scroll to Continue",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //return false;
        Toast.makeText(this,"WELCOME TO AROGYA",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AfterLoginGestureActivity.this,HomePage.class);
        intent.putExtra("id",currentUerId);
        startActivity(intent);
        //overridePendingTransition(R.anim.anim_push_out,R.anim.anim_push_out);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
