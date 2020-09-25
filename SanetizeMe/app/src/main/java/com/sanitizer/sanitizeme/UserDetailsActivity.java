package com.sanitizer.sanitizeme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailsActivity extends AppCompatActivity {

    EditText username;
    EditText mobnumber;
    EditText email;
    EditText address;
    ArrayList<String> selectedCategories;
    List<Details> showroomDetail;
    Boolean isTwoOrFourWheeler = false;
    String isTFW = "no";
    String atShowroom = "no";
    String serviceSelected = "";
    String userNumber;
    private ProgressBar spinner;
    String allNumbers;
    String showroomAddress;
    String showroomNumber;
    private CheckBox yesCB;
    private CheckBox noCB;
    private CheckBox homeCB;
    private  CheckBox showroomCB;
    private TextView sanitizeText;
    private String isMessageIsActive = "True";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setTitle("SanitizeMe");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        selectedCategories =  (ArrayList<String>)getIntent().getSerializableExtra("selectedCategories");
        showroomDetail =  (List<Details>)getIntent().getSerializableExtra("showroomdetail");
        Details showDetail = showroomDetail.get(0);
        showroomAddress = showDetail.getShowroomContact();
        showroomNumber = showDetail.getShowroomAddress();

        homeCB = (CheckBox) findViewById(R.id.homecheckBox);
        showroomCB = (CheckBox) findViewById(R.id.showroomcheckBox);
        sanitizeText = (TextView)findViewById(R.id.textlabel);



        if(selectedCategories.contains("Two Wheeler") || selectedCategories.contains("Four Wheeler")) {
           isTwoOrFourWheeler = true;
           isTFW = "yes";
           homeCB.setVisibility(View.VISIBLE);
           showroomCB.setVisibility(View.VISIBLE);
           sanitizeText.setVisibility(View.VISIBLE);
       } else {
           isTwoOrFourWheeler = false;
           isTFW = "no";
            homeCB.setVisibility(View.INVISIBLE);
            showroomCB.setVisibility(View.INVISIBLE);
            sanitizeText.setVisibility(View.INVISIBLE);
       }

        serviceSelected = selectedCategories.toString();

        serviceSelected = serviceSelected.replace("[", "")
                .replace("]", "")
                .replace(" ", "");


         username = (EditText) findViewById(R.id.usernametext);

         mobnumber = (EditText) findViewById(R.id.mobtext);

         email = (EditText) findViewById(R.id.emailtext);

         address = (EditText) findViewById(R.id.addtext);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);







        homeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                 if (isChecked) {
                                                     if (showroomCB.isChecked()) {
                                                         showroomCB.setChecked(false);
                                                     }
                                                 }
                                             }
                                         }
        );

        showroomCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if (isChecked) {
                                                      if (homeCB.isChecked()) {
                                                          homeCB.setChecked(false);
                                                      }
                                                  }
                                              }
                                          }
        );


        //cb.setChecked(isTwoOrFourWheeler);



       // final ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);

        Button bookButton = (Button) findViewById(R.id.bookbtn);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isMessageIsActive.equals("True")) {
                    showNotAbleToSendMessage();
                } else {

                    if (showroomCB.isChecked()) {
                        atShowroom = "yes";
                    } else {
                        atShowroom = "no";
                    }

                    if (checkDataEntered() == true) {
                        UserDetail userDetail = new UserDetail(username.getText().toString(), mobnumber.getText().toString(), email.getText().toString(), address.getText().toString(), "yes", atShowroom, serviceSelected);
                        sendNetworkRequest(userDetail);
                        spinner.setVisibility(View.VISIBLE);
                    }
                }



                //Intent intent= new Intent(SelectCityActivity.this,CategoryActivity.class);
//                intent.putExtra("data",String.valueOf(spinner.getSelectedItem()));
//                startActivity(intent);
            }
        });

        sendMessageOrNotRequest();

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
            Intent intent= new Intent(UserDetailsActivity.this,ContactUsActivity.class);
            startActivity(intent);
            return(true);
        case R.id.refer:
            shareApp();
            return(true);
        case R.id.homeDash:
            Intent intentHome = new Intent(UserDetailsActivity.this,MainActivity.class);
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

    private void sendMessageOrNotRequest() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-3-6-17-6.ap-south-1.compute.amazonaws.com/api/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retorfit = builder.build();
        UserDetialClient client = retorfit.create(UserDetialClient.class);
        Call<ActivateMessage> call = client.getSendMessageOrNot();

        call.enqueue(new Callback<ActivateMessage>() {
            @Override
            public void onResponse(Call<ActivateMessage> call, Response<ActivateMessage> response) {

                assert response.body() != null;
                isMessageIsActive = response.body().getActivateMessage();
            }

            @Override
            public void onFailure(Call<ActivateMessage> call, Throwable t) {
                isMessageIsActive = "True";
            }
        });
    }

    private void sendNetworkRequest(UserDetail userDetail) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-3-6-17-6.ap-south-1.compute.amazonaws.com/api/api/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retorfit = builder.build();
        UserDetialClient client = retorfit.create(UserDetialClient.class);
        Call<String> call = client.Book(userDetail);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               // Toast.makeText(UserDetailsActivity.this, "Success",
                  //      Toast.LENGTH_LONG).show();
                showConfirmAlert();
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserDetailsActivity.this, "Something went wrong",
                        Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
            }
        });
    }


    public void showNotAbleToSendMessage() {
        AlertDialog alertDialog1 = new AlertDialog.Builder(
                UserDetailsActivity.this).create();

        // Setting Dialog Title
        alertDialog1.setTitle("Maintenance in progress!");

        // Setting Dialog Message
        alertDialog1.setMessage("Service disabled, maintenance in progress");

        // Setting Icon to Dialog
        // alertDialog1.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent= new Intent(UserDetailsActivity.this,ContactUsActivity.class);
                        startActivity(intent);
                    }
                });

        // Showing Alert Message
        alertDialog1.show();
    }

    public  void showConfirmAlert() {
        AlertDialog alertDialog1 = new AlertDialog.Builder(
                UserDetailsActivity.this).create();

        // Setting Dialog Title
        alertDialog1.setTitle("Thanks for booking!");

        // Setting Dialog Message
        alertDialog1.setMessage("You will soon get a notification on registered phone and email address.Call us for cancellation!");

        // Setting Icon to Dialog
       // alertDialog1.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent= new Intent(UserDetailsActivity.this,ContactUsActivity.class);

                        startActivity(intent);
                        userNumber = mobnumber.getText().toString();
                        allNumbers = "91"+showroomNumber+","+"91"+mobnumber.getText().toString()+","+"919009355103,"+"919555091071";

                        new Thread( new Runnable() { @Override public void run() {
                            // Run whatever background code you want here.
                            SendSMS sendSMS = new SendSMS();
                            String smsResponse = sendSMS.sendSms(allNumbers,showroomAddress,showroomNumber,isTwoOrFourWheeler,serviceSelected,userNumber);
                        } } ).start();






                    }
                });



        // Showing Alert Message
        alertDialog1.show();
    }
    Boolean checkDataEntered() {

        Boolean wrong = true;


        if (isValidMobile(mobnumber.getText().toString()) == false) {
            mobnumber.setError("Enter Valid Mobile Number");
            wrong = false;
        }

        if (isEmpty(username)) {
           username.setError("Enter name");
            wrong = false;
        }

            if (isEmpty(address)) {
                address.setError("Enter Address");
                wrong = false;
            }

//        if(isEmpty(email)) {
//            email.setError("Enter email address");
//            wrong = false;
//        }

        if(!isEmpty(email)) {
            if (isValidMail(email.getText().toString().trim()) == false) {
                email.setError("Enter valid email address");
                wrong = false;
            }
        }
        return  wrong;
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isValidMail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }



    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }
}
