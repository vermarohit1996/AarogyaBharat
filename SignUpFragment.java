package com.example.arogyademo;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.arogyademo.App.CHANNEL_1_ID;


public class SignUpFragment extends Fragment {
    private NotificationManagerCompat notificationManager;
    private FirebaseAuth mAuth;
    EditText editTextEmailPass;
    TextView textViewHeading;
    Button buttonContinue;
    String stringEmail,stringPassword;
    int buttonContinueClickCount=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        editTextEmailPass=view.findViewById(R.id.ID_SU_EmailPass_ET);
        textViewHeading=view.findViewById(R.id.ID_SU_Heading_TV);
        buttonContinue=view.findViewById(R.id.ID_SU_Continue_BT);
        buttonContinueClickCount=1;
        editTextEmailPass.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        //NOTIFICATION MANAGER
        notificationManager=NotificationManagerCompat.from(getContext());
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonContinueClickCount==1)
                {
                    stringEmail=editTextEmailPass.getText().toString().trim();
                    if(stringEmail.length()>0)
                    {
                        buttonContinueClickCount=2;
                        editTextEmailPass.setHint("Enter Password");
                        textViewHeading.setText("Enter Your\n Desired Password");
                        editTextEmailPass.setInputType(InputType.TYPE_CLASS_TEXT);
                        editTextEmailPass.setText(null);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Kindly Enter Email ID",Toast.LENGTH_LONG).show();
                    }
                }
                else if(buttonContinueClickCount==2)
                {
                    stringPassword=editTextEmailPass.getText().toString().trim();
                    if(stringPassword.length()>0)
                    {
                        //ACCESS FIREBASE
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 750);
                        mAuth.createUserWithEmailAndPassword(stringEmail, stringPassword)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            //Log.d(TAG, "createUserWithEmail:success");
                                            Toast.makeText(getContext(),"USER Signed Up Success. Kindly Login",Toast.LENGTH_LONG).show();
                                            sendNotification();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            //updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(getContext(),"USER Signed Up FAILED",Toast.LENGTH_LONG).show();

                                            //updateUI(null);
                                        }

                                        // ...
                                    }
                                });
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Kindly Enter Password",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void sendNotification() {
        String title="AROGYA";
        String message="Signed Up Success with Email ID:"+stringEmail;
        Notification notification=new NotificationCompat.Builder(getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.arogya_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

}
