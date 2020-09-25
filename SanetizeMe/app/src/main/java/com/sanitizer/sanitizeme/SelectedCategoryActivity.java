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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setTitle("SanitizeMe");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
            Intent intent= new Intent(SelectedCategoryActivity.this,ContactUsActivity.class);
            startActivity(intent);
            return(true);
        case R.id.refer:
            shareApp();
            return(true);
        case R.id.homeDash:
            Intent intentHome = new Intent(SelectedCategoryActivity.this,MainActivity.class);
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

}
