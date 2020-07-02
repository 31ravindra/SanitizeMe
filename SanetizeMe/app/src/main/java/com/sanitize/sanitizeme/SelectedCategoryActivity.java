package com.sanitize.sanitizeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryActivity extends AppCompatActivity {
    ArrayList<String> selectedCategories;
    List<Details> showRoomDetails;

    ListView lView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        selectedCategories =  (ArrayList<String>)getIntent().getSerializableExtra("SelectedNamesList");
        showRoomDetails =  (List<Details>)getIntent().getSerializableExtra("showroomdetail");
        System.out.println(selectedCategories);

        Button button=(Button)findViewById(R.id.button2);



        lView = (ListView) findViewById(R.id.list);

        SelectedCategoryAdapter lAdapter = new SelectedCategoryAdapter(SelectedCategoryActivity.this, selectedCategories);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SelectedCategoryActivity.this,UserDetailsActivity.class);
                intent.putExtra("selectedCategories",selectedCategories);
                intent.putExtra("showroomdetail", (Serializable)showRoomDetails);
                startActivity(intent);
            }
        });


    }
}
