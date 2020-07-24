package com.sanitizer.sanitizeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.AdapterCallback {
    GridView gridView;
    ArrayList<String> selectednames;
    List<Details>showRoomDetails;
    Button continueButton;

    String[] categoryName = {"Two Wheeler", "Four Wheeler", "House", "Office", "School","Shop", "Garden","Society" };

    int[] categoryImage = {R.drawable.bike, R.drawable.car, R.drawable.home,R.drawable.office, R.drawable.school, R.drawable.shops, R.drawable.garden,R.drawable.society};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        selectednames = new ArrayList<String>();
        showRoomDetails =  (List<Details>)getIntent().getSerializableExtra("showroomdetail");

        gridView = findViewById(R.id.grid_view);

        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, categoryName,categoryImage, CategoryActivity.this);
        gridView.setAdapter(adapter);

        continueButton = (Button)findViewById(R.id.proceed_button);
       // continueButton.setEnabled(false);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectednames.size() > 0) {
                    Intent intent = new Intent(CategoryActivity.this, SelectedCategoryActivity.class);
                    intent.putExtra("SelectedNamesList", selectednames);
                    intent.putExtra("showroomdetail", (Serializable) showRoomDetails);
                    startActivity(intent);
                } else {
                    Toast.makeText(CategoryActivity.this, "Please select at least one category",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
   public void getSelectedNameList(ArrayList<String> selectedname) {

        this.selectednames = selectedname;
        System.out.println(selectednames);

    }
}
