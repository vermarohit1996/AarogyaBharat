package com.example.arogyademo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateUserOld extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String mCurrentPhotoPath = null;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView imageViewDP;
    Spinner statesSpinner, bloodSpinner;
    TextView textView;
    String currentUserId;
    TextView textViewSubmit;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGender;
    DatePickerDialog.OnDateSetListener dobListener;
    SharedPreferences sharedPreferences;
    private TextInputEditText editTextName, editTextDOB, editTextAge, editTextEmail, editTextCity;

    String id,phone,password,name,gender="NA",dob,email,blood_group,state,city,image;

    int mYear, mMonth, mDay;
    //GENDER NA-0,MALE-1,FEMALE-2,OTHER-3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuserold);
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        //currentUserId=getIntent().getStringExtra("id");
        currentUserId=sharedPreferences.getString("id",null);
        databaseHelper=new DatabaseHelper(UpdateUserOld.this);
        imageViewDP = findViewById(R.id.ID_CProfile_DP_IV);
        textViewSubmit=findViewById(R.id.ID_CProfile_SubmitBT);
        editTextName = findViewById(R.id.ID_CProfile_NameET);
        editTextDOB = findViewById(R.id.ID_CProfile_DOBET);
        editTextDOB.addTextChangedListener(DOBTextWatcher);
        editTextAge = findViewById(R.id.ID_CProfile_AgeET);
        editTextEmail = findViewById(R.id.ID_CProfile_EmailET);
        editTextCity = findViewById(R.id.ID_CProfile_CityET);
        statesSpinner = findViewById(R.id.ID_CProfile_StateSpinner);
        bloodSpinner = findViewById(R.id.ID_CProfile_BloodSpinner);
        //SPINNER
        final String[] blood = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, blood);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodAdapter);

        final String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statesSpinner.setAdapter(statesAdapter);

        //SQL TASK
        Cursor oldInfo=databaseHelper.searchUserByPhone(currentUserId);
        oldInfo.moveToFirst();
        if(oldInfo.getString(oldInfo.getColumnIndex("NAME"))!=null)
        {
            editTextName.setText(oldInfo.getString(oldInfo.getColumnIndex("NAME")));
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("DOB"))!=null)
        {
            editTextDOB.setText(oldInfo.getString(oldInfo.getColumnIndex("DOB")));
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("EMAIL"))!=null)
        {
            editTextEmail.setText(oldInfo.getString(oldInfo.getColumnIndex("EMAIL")));
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("CITY"))!=null)
        {
            editTextCity.setText(oldInfo.getString(oldInfo.getColumnIndex("CITY")));
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("GENDER"))!=null)
        {
            String gen=oldInfo.getString(oldInfo.getColumnIndex("GENDER"));
            if(gen.equals("MALE"))
            {
                radioButtonGender=findViewById(R.id.radioButtonMale);
                radioButtonGender.setChecked(true);
            }
            else if(gen.equals("FEMALE"))
            {
                radioButtonGender=findViewById(R.id.radioButtonFemale);
                radioButtonGender.setChecked(true);
            }
            else if(gen.equals("OTHER"))
            {
                radioButtonGender=findViewById(R.id.radioButtonOther);
                radioButtonGender.setChecked(true);
            }
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("BLOOD_GROUP"))!=null)
        {
            int pos=0;
            for(int i=0;i<blood.length;i++)
            {
                if(blood[i].equals(oldInfo.getString(oldInfo.getColumnIndex("BLOOD_GROUP"))))
                {
                    break;
                }
            }
            bloodSpinner.setSelection(pos);

        }
        if(oldInfo.getString(oldInfo.getColumnIndex("STATE"))!=null)
        {
            int pos=0;
            for(int i=0;i<states.length;i++)
            {
                if(oldInfo.getString(oldInfo.getColumnIndex("STATE")).equals(states[i]))
                {
                    break;
                }
            }
            statesSpinner.setSelection(10);
        }
        if(oldInfo.getString(oldInfo.getColumnIndex("IMAGE"))!=null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(oldInfo.getString(oldInfo.getColumnIndex("IMAGE")));
            imageViewDP.setImageBitmap(bitmap);
        }

        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar caldob = Calendar.getInstance();
                int year = caldob.get(Calendar.YEAR);
                int month = caldob.get(Calendar.MONTH);
                int day = caldob.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateUserOld.this, android.R.style.Theme_Holo_Light, dobListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dobListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editTextDOB.setText(date);

            }
        };

        textView = findViewById(R.id.ID_CProfile_CurrentUser_TV);




        textView.setText("Current User:" + currentUserId);

        imageViewDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(UpdateUserOld.this, "com.example.arogyademo.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
        textViewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=currentUserId;
                name=editTextName.getText().toString().trim();
                //gender already known via onRadioButtonClicked
                dob=editTextDOB.getText().toString().trim();
                email=editTextEmail.getText().toString().trim();
                blood_group= (String) bloodSpinner.getSelectedItem();
                state= (String) statesSpinner.getSelectedItem();
                city=editTextCity.getText().toString().trim();
                image=mCurrentPhotoPath;
                User user=new User(name,gender,dob,email,blood_group,state,city,image);
                long result=databaseHelper.updateUser(user,phone);
                if(result>0)
                {
                    Snackbar.make(v,"User Information Updated Successfully",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    Snackbar.make(v,"Error!!! Information Can't Be Updated",Snackbar.LENGTH_LONG).show();
                }
                databaseHelper.closeDb();


            }
        });


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonMale:
                if (checked) {
                    gender = "MALE";
                }
                break;
            case R.id.radioButtonFemale:
                if (checked) {
                    gender = "FEMALE";
                }
                break;
            case R.id.radioButtonOther:
                if (checked) {
                    gender = "OTHER";
                }
                break;
        }
    }

    //TEXT WATCHER
    private TextWatcher DOBTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
/*
            LocalDate today = LocalDate.now();                          //Today's date
            LocalDate birthday = LocalDate.of(mYear, mMonth + 1, mDay);  //Birth date
            Period p = Period.between(birthday, today);

            editTextAge.setText(p.getYears() + " Years " + p.getMonths() + " Month " + p.getDays() + " Days");*/
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        imageViewDP.setImageBitmap(bitmap);
    }
}
