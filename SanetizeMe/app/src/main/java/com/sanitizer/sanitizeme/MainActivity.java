package com.sanitizer.sanitizeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Menu myMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        // Find the toolbar view inside the activity layout

      //  getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setTitle("SanitizeMe");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        Button btn = (Button)findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectCityActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        myMenu = menu;
        MenuItem item = menu.findItem(R.id.homeDash);
        if (item != null) {
            item.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {

        case R.id.contact:
            Intent intent= new Intent(MainActivity.this,ContactUsActivity.class);
            startActivity(intent);
            return(true);
        case R.id.refer:
            shareApp();
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

    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }
}
