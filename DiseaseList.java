package com.example.arogyademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiseaseList extends AppCompatActivity {
    ListView listViewDiseases;
    String currentUserId;
    String[] diseases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);
        currentUserId=getIntent().getStringExtra("id");
        diseases=getResources().getStringArray(R.array.disease_list);
        listViewDiseases=findViewById(R.id.ID_DiseaseList_Diseases_ListView);
        ArrayAdapter<String> adapterDiseases=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,diseases);
        listViewDiseases.setAdapter(adapterDiseases);
        listViewDiseases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(DiseaseList.this,DiseaseListInformationViewer.class);
                intent.putExtra("id",currentUserId);
                intent.putExtra("disease_pos",position);
                startActivity(intent);
            }
        });


    }
}
