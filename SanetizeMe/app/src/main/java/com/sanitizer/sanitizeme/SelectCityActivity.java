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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class SelectCityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    List<Details> showRoomDetails;
    List<String> cities;

    private ProgressBar pBar;
    private ProgressBar cityBar;
    private  Button nextButton;
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
       // showRoomDetails = new ArrayList<Details>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setTitle("SanitizeMe");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        spinner = (Spinner) findViewById(R.id.spinner2);
        nextButton=(Button)findViewById(R.id.button);
        nextButton.setEnabled(false);

        pBar=(ProgressBar)findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);

        cityBar = (ProgressBar)findViewById(R.id.cityprogressBar);


        getAllCitiesRequest();


        // Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("City Name");
//        categories.add("Khargone");
//        categories.add("Indore");
//        categories.add("Bhopal");
//        categories.add("Pune");
//        categories.add("Ujjain");
//        categories.add("Mumbai");
//        categories.add("Delhi");
//        categories.add("Jaipur");



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
            Intent intent= new Intent(SelectCityActivity.this,ContactUsActivity.class);
            startActivity(intent);
            return(true);
        case R.id.refer:
            shareApp();
            return(true);
        case R.id.homeDash:
            Intent intentHome = new Intent(SelectCityActivity.this,MainActivity.class);
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

    public void codeAfterCityRequest(List<String> cities) {
    // Creating adapter for spinner
    dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);

    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // Spinner click listener
    spinner.setOnItemSelectedListener(this);
    // attaching data adapter to spinner
    spinner.setAdapter(dataAdapter);

    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(SelectCityActivity.this,CategoryActivity.class);
            intent.putExtra("data",String.valueOf(spinner.getSelectedItem()));
            intent.putExtra("showroomdetail", (Serializable)showRoomDetails);

            startActivity(intent);
        }
    });
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item


//        if (position == spinner.getSelectedItemPosition())
//        {
            String item = parent.getItemAtPosition(position).toString();
            if (item != "City Name") {
                SelectedCity selectedCity = new SelectedCity((item));
                sendNetworkRequest(selectedCity);
                pBar.setVisibility(View.VISIBLE);
            } else {
                nextButton.setEnabled(false);
                Toast.makeText(SelectCityActivity.this, "Please select city",
                        Toast.LENGTH_SHORT).show();
            }
            //then at end of statement reset adapter like

           // spinner.setAdapter(dataAdapter);
      //  }


    }

    private void getAllCitiesRequest() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-3-6-17-6.ap-south-1.compute.amazonaws.com/api/").addConverterFactory(GsonConverterFactory.create());
        builder.client(okHttpClient);
        Retrofit retorfit = builder.build();

        UserDetialClient client = retorfit.create(UserDetialClient.class);
        Call<CityListModel> call = client.getAllCities();

        call.enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {
                if(response.body() != null) {
                    cities = response.body().getCityNames();
                    cities.add(0, "City Name");
                    codeAfterCityRequest(cities);

                    cityBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(SelectCityActivity.this, "No city",
                            Toast.LENGTH_LONG).show();
                    cityBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {
                Toast.makeText(SelectCityActivity.this, "Please check internet connection",
                        Toast.LENGTH_LONG).show();
                pBar.setVisibility(View.GONE);
               // spinner.setAdapter(dataAdapter);
            }
        });
    }


    private void sendNetworkRequest(SelectedCity selectedCity) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-3-6-17-6.ap-south-1.compute.amazonaws.com/api/").addConverterFactory(GsonConverterFactory.create());
        builder.client(okHttpClient);
        Retrofit retorfit = builder.build();

        UserDetialClient client = retorfit.create(UserDetialClient.class);
        Call<ShowRoomDetail> call = client.selectCity(selectedCity);

        call.enqueue(new Callback<ShowRoomDetail>() {
            @Override
            public void onResponse(Call<ShowRoomDetail> call, Response<ShowRoomDetail> response) {
                if(response.body() != null) {
                    showRoomDetails = response.body().getDetails();
                    // Toast.makeText(SelectCityActivity.this, "Success",
                    //  Toast.LENGTH_LONG).show();
                    nextButton.setEnabled(true);
                    pBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(SelectCityActivity.this, "No showroom for this city",
                            Toast.LENGTH_LONG).show();
                    pBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ShowRoomDetail> call, Throwable t) {
                Toast.makeText(SelectCityActivity.this, "Please check internet connection and select city again",
                        Toast.LENGTH_LONG).show();
                pBar.setVisibility(View.GONE);
                spinner.setAdapter(dataAdapter);
            }
        });
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        arg0.setSelection(0);
    }
    }

