package com.example.arogyademo;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewPassword extends AppCompatActivity {
    EditText editTextPassword,editTextCPassword;
    String currentUserId,password,cpassword;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        currentUserId=getIntent().getStringExtra("id");
        editTextPassword=findViewById(R.id.ID_CreateNewPassword_Password_ET);
        editTextCPassword=findViewById(R.id.ID_CreateNewPassword_CPassword_ET);
        databaseHelper=new DatabaseHelper(this);

    }

    public void onResetPassword(View view) {
        password=editTextPassword.getText().toString().trim();
        cpassword=editTextCPassword.getText().toString().trim();
        if(!(password.equals(cpassword)))
        {
            Toast.makeText(CreateNewPassword.this,"Password and Confirm Password are Not Same",Toast.LENGTH_LONG).show();
        }
        else
        {
            long res=databaseHelper.updatePassword(password,currentUserId);
            if(res>0)
            {
                Toast.makeText(CreateNewPassword.this,"Password Updated Successfully",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CreateNewPassword.this,SignInSignUpActivity.class);
                intent.putExtra("id",currentUserId);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(CreateNewPassword.this,"Password Can't Be Updated!!!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
