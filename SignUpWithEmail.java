package com.example.arogyademo;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpWithEmail extends AppCompatActivity {

    EditText editTextEmail,editTextPassword,editTextCPassword;
    Button buttonSignUp,buttonReset;
    String email,password,cpassword;
    private FirebaseAuth mAuth;
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);

        //NOTIFICATION MANAGER
        notificationManager= NotificationManagerCompat.from(this);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail=findViewById(R.id.ID_SUWithEmail_Email_ET);
        editTextPassword=findViewById(R.id.ID_SUWithEmail_Password_ET);
        editTextCPassword=findViewById(R.id.ID_SUWithEmail_CPassword_ET);
        buttonSignUp=findViewById(R.id.ID_SUWithEmail_SignUp_BT);
        buttonReset=findViewById(R.id.ID_SUWithEmail_Reset_BT);
    }

    public void onReset(View view) {
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextCPassword.setText("");
    }

    public void onSignUp(View view) {
        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        cpassword=editTextCPassword.getText().toString().trim();
        if(!(password.equals(cpassword)))
        {
            Snackbar.make(view,"Password and Confirm Password Should be Same", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(SignUpWithEmail.this,"USER Signed Up Success",Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpWithEmail.this,"USER Signed Up FAILED",Toast.LENGTH_LONG).show();

                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
