package com.example.arogyademo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;
import static com.example.arogyademo.R.color.bright_foreground_inverse_material_dark;
import static com.example.arogyademo.R.color.colorAccent;
import static com.example.arogyademo.R.color.colorPrimary;


public class SignInFragment extends Fragment {
    private FirebaseAuth mAuth;
    Button signUp;
    DatabaseHelper databaseHelper;
    Button login;
    EditText idEditText, passwordEditText;
    String id, password,email;
    String currentUserId;
    SharedPreferences sharedPreferences;
    RadioButton radioButtonEmail,radioButtonPhone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);
        login = view.findViewById(R.id.ID_SI_Login_BT);
        login.setEnabled(false);
        login.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        idEditText = view.findViewById(R.id.ID_SI_userID_ET);
        passwordEditText = view.findViewById(R.id.ID_SI_Password_ET);
        idEditText.addTextChangedListener(loginTextWatcher);
        passwordEditText.addTextChangedListener(loginTextWatcher);
        radioButtonEmail=view.findViewById(R.id.ID_SI_RadioEmail_RB);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK AUTHENTICATE MODE VIA RADIO BUTTON AND THEN PROCEED

                if(radioButtonEmail.isChecked())
                {
                    //SIGN IN WITH EMAIL
                    email = idEditText.getText().toString().trim();
                    password = passwordEditText.getText().toString().trim();
                    mAuth = FirebaseAuth.getInstance();
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
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(),"SUCCESS",Toast.LENGTH_LONG).show();
                                        //SHARED PREFERENCE WRITRING
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString("id",email);
                                        editor.apply();
                                        Intent intent = new Intent(getContext(), AfterLoginGestureActivity.class);
                                        intent.putExtra("id", email);
                                        startActivity(intent);
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                    } else {

                                        //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        //updateUI(null);
                                        Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
                                    }

                                    // ...
                                }
                            });

                }
            }
        });
        databaseHelper.closeDb();
        return view;
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameinput = idEditText.getText().toString().trim();
            String passwordinput = passwordEditText.getText().toString().trim();
            if (!(usernameinput.length() == 0) && !(passwordinput.length() == 0)) {
                login.setEnabled(true);
                login.setBackgroundColor(R.color.colorPrimary);
            } else {
                login.setEnabled(false);
                login.setBackgroundColor(android.R.color.holo_red_dark);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
