package com.sanitize.sanitizeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelectCityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    List<Details> showRoomDetails;
    private ProgressBar pBar;
    private  Button nextButton;
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
       // showRoomDetails = new ArrayList<Details>();
        spinner = (Spinner) findViewById(R.id.spinner2);
        nextButton=(Button)findViewById(R.id.button);
        nextButton.setEnabled(false);

        pBar=(ProgressBar)findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);




        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("City Name");
        categories.add("Khargone");
        categories.add("Indore");
        categories.add("Bhopal");
        categories.add("Pune");
//        categories.add("Mumbai");
//        categories.add("Delhi");
//        categories.add("Jaipur");


        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

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
                Toast.makeText(SelectCityActivity.this, "Please select city",
                        Toast.LENGTH_SHORT).show();
            }
            //then at end of statement reset adapter like

           // spinner.setAdapter(dataAdapter);
      //  }


    }



    private void sendNetworkRequest(SelectedCity selectedCity) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://www.sanitizeme.co.in/api/").addConverterFactory(GsonConverterFactory.create());
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

