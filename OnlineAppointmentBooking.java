package com.example.arogyademo;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class OnlineAppointmentBooking extends AppCompatActivity {
    Data_Appointment data_appointment;
    EditText editTextName,editTextPhone,editTextDate,editTextMessage;

    Spinner spinnerDocSpecialties,spinnerTimeSlot;
    DatePickerDialog.OnDateSetListener dobListener;
    String sex;
    int mYear, mMonth, mDay;
    String name,phone,date,message,speciality,timeslot;
    String[] doc_speciality;
    String[] time_slot;
    SharedPreferences sharedPreferences;
    String currentUserId;
    char charArraycurrentUserId[];
    String pathUserId;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_appointment_booking);

        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        currentUserId=sharedPreferences.getString("id","anonymous");
        charArraycurrentUserId=currentUserId.toCharArray();
        for(int i=0;i<currentUserId.length();i++)
        {
            if(charArraycurrentUserId[i]=='.')
            {
                charArraycurrentUserId[i]=':';
            }
        }
        pathUserId=String.valueOf(charArraycurrentUserId);
        //
        databaseReference=FirebaseDatabase.getInstance("https://arogyademo.firebaseio.com/").getReference();
        data_appointment=new Data_Appointment();
        editTextName=findViewById(R.id.ID_OAP_Name_ET);
        editTextPhone=findViewById(R.id.ID_OAP_Phone_ET);
        editTextDate=findViewById(R.id.ID_OAP_Date_ET);
        editTextMessage=findViewById(R.id.ID_OAP_Message_ET);
        spinnerDocSpecialties=findViewById(R.id.ID_OAP_DoctorSpecialtiesSpinner);
        spinnerTimeSlot=findViewById(R.id.ID_OAP_TimeSlotSpinner);

        //SPINNER Init. Task
        doc_speciality = getResources().getStringArray(R.array.doctor_specialties);
        ArrayAdapter<String> doctorSpecAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doc_speciality);
        doctorSpecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDocSpecialties.setAdapter(doctorSpecAdapter);

        //SPINNER Init. Task
        time_slot = getResources().getStringArray(R.array.time_slot);
        ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, time_slot);
        timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTimeSlot.setAdapter(timeSlotAdapter);

        //Date Picker
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar caldob = Calendar.getInstance();
                int year = caldob.get(Calendar.YEAR);
                int month = caldob.get(Calendar.MONTH);
                int day = caldob.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(OnlineAppointmentBooking.this, android.R.style.Theme_Holo_Light, dobListener, year, month, day);
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
                editTextDate.setText(date);

            }
        };
    }

    public void onBook(View view) {
        name=editTextName.getText().toString().trim();
        phone=editTextPhone.getText().toString().trim();
        date=editTextDate.getText().toString().trim();
        message=editTextMessage.getText().toString().trim();
        //SEX automatically initialised on radio button click
        int pos;
        pos=spinnerDocSpecialties.getSelectedItemPosition();
        speciality=doc_speciality[pos];
        pos=spinnerTimeSlot.getSelectedItemPosition();
        timeslot=time_slot[pos];
        //DATA_APPOINTMENT CLASS
        data_appointment.setUserId(currentUserId);
        data_appointment.setName(name);
        data_appointment.setGender(sex);
        data_appointment.setPhone(phone);
        data_appointment.setSpeciality(speciality);
        data_appointment.setDate(date);
        data_appointment.setTimeslot(timeslot);
        data_appointment.setMessage(message);
        databaseReference.child("appointment").child(pathUserId).setValue(data_appointment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OnlineAppointmentBooking.this,"Appointment Booked...",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OnlineAppointmentBooking.this,"Try Again...",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onRadioButtonClicked(View view) {
        if(view.getId()==R.id.ID_OAP_RadioButtonMale)
        {
            sex="M";
        }
        else if(view.getId()==R.id.ID_OAP_RadioButtonFemale)
        {
            sex="F";
        }
        else if(view.getId()==R.id.ID_OAP_RadioButtonOther)
        {
            sex="O";
        }

    }
}
