package com.example.arogyademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AfterFrontPage extends AppCompatActivity {

    TextView textViewTitle,textViewSubTitle,textViewSignInSignUp;
    ImageView imageViewTopImage;
    View viewProgressBarStart,viewProgressBarStop;
    Animation animimgpage,bttone,bttwo,btthree,btfour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_front_page);

        textViewTitle=findViewById(R.id.ID_AFP_Title_TV);
        textViewSubTitle=findViewById(R.id.ID_AFP_SubTitle_TV);
        textViewSignInSignUp=findViewById(R.id.ID_AFP_SignInSignUp_TV);
        imageViewTopImage=findViewById(R.id.ID_AFP_Top_IV);
        viewProgressBarStart =findViewById(R.id.ID_AFP_ProgressBarStart_View);
        viewProgressBarStop=findViewById(R.id.ID_AFP_ProgressBarStop_View);

        //LOADING ANIMATIONS
        animimgpage = AnimationUtils.loadAnimation(this, R.anim.anim_appear_image_afp0);
        bttone = AnimationUtils.loadAnimation(this, R.anim.anim_appear_push_in_from_bottom_afp1);
        bttwo = AnimationUtils.loadAnimation(this, R.anim.anim_progressbar_start_afp);
        btthree = AnimationUtils.loadAnimation(this, R.anim.anim_appear_button_afp3);
        btfour = AnimationUtils.loadAnimation(this, R.anim.anim_progressbar_stop_afp);

        // export animate
        imageViewTopImage.startAnimation(animimgpage);
        textViewTitle.startAnimation(bttone);
        textViewSubTitle.startAnimation(bttone);

        textViewSignInSignUp.startAnimation(btthree);
        viewProgressBarStart.startAnimation(bttwo);
        viewProgressBarStop.startAnimation(btfour);

        //INTENT
        textViewSignInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterFrontPage.this,SignInSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
