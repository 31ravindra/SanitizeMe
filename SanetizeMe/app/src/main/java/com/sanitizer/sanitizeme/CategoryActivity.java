package com.sanitizer.sanitizeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    String[] categoryName = {"Two Wheeler", "Four Wheeler", "House", "Office", "Gym","Shop", "Restaurants","Society" };

    int[] categoryImage = {R.drawable.bike, R.drawable.car, R.drawable.home,R.drawable.office, R.drawable.gym, R.drawable.shops, R.drawable.restaurant,R.drawable.society};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setTitle("SanitizeMe");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {

        case R.id.contact:
            Intent intent= new Intent(CategoryActivity.this,ContactUsActivity.class);
            startActivity(intent);
            return(true);
        case R.id.refer:
            shareApp();
            return(true);
        case R.id.homeDash:
            Intent intentHome = new Intent(CategoryActivity.this,MainActivity.class);
            startActivity(intentHome);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    public void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SanitizeMe");
            String shareMessage= "\nWhy worry, when sanitization is at your door steps just through online booking. We Sanitize home,  office, shop, society, school, four wheeler, etc. We provide sanitization all over Pune, Indore and other cities with proper precautions taken by our employees.\n" +
                    "For any further queries please download this App given in the link\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    @Override
   public void getSelectedNameList(ArrayList<String> selectedname) {

        this.selectednames = selectedname;
        System.out.println(selectednames);

    }
}
