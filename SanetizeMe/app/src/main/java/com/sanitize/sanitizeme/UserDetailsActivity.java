package com.sanitize.sanitizeme;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private ProgressBar spinner;
    String allNumbers;
    String showroomAddress;
    String showroomNumber;
    private CheckBox yesCB;
    private CheckBox noCB;
    private CheckBox homeCB;
    private  CheckBox showroomCB;
    private String isMessageIsActive = "True";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        selectedCategories =  (ArrayList<String>)getIntent().getSerializableExtra("selectedCategories");
        showroomDetail =  (List<Details>)getIntent().getSerializableExtra("showroomdetail");
        Details showDetail = showroomDetail.get(0);
        showroomAddress = showDetail.getShowroomContact();
        showroomNumber = showDetail.getShowroomAddress();

        if(selectedCategories.contains("Two Wheeler") || selectedCategories.contains("Four Wheeler")) {
           isTwoOrFourWheeler = true;
           isTFW = "yes";
       } else {
           isTwoOrFourWheeler = false;
           isTFW = "no";
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

        yesCB = (CheckBox) findViewById(R.id.tfCheckBox);
        noCB = (CheckBox) findViewById(R.id.nocheckbox);
        homeCB = (CheckBox) findViewById(R.id.homecheckBox);
        showroomCB = (CheckBox) findViewById(R.id.showroomcheckBox);

        yesCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                 if (isChecked) {
                     if (noCB.isChecked()) {
                         noCB.setChecked(false);
                     }
                 }
            }
        }
        );

        noCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    if (yesCB.isChecked()) {
                        yesCB.setChecked(false);
                    }
                }
            }
        }
        );

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
                        UserDetail userDetail = new UserDetail(username.getText().toString(), mobnumber.getText().toString(), email.getText().toString(), address.getText().toString(), isTFW, atShowroom, serviceSelected);
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


    private void sendMessageOrNotRequest() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://www.sanitizeme.co.in/api/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retorfit = builder.build();
        UserDetialClient client = retorfit.create(UserDetialClient.class);
        Call<ActivateMessage> call = client.getSendMessageOrNot();

        call.enqueue(new Callback<ActivateMessage>() {
            @Override
            public void onResponse(Call<ActivateMessage> call, Response<ActivateMessage> response) {

                isMessageIsActive = response.body().getActivateMessage();
            }

            @Override
            public void onFailure(Call<ActivateMessage> call, Throwable t) {
                isMessageIsActive = "True";
            }
        });
    }

    private void sendNetworkRequest(UserDetail userDetail) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://www.sanitizeme.co.in/api/api/").addConverterFactory(GsonConverterFactory.create());
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
                        allNumbers = "91"+showroomNumber+","+"91"+mobnumber.getText().toString()+","+"919009355103,"+"919555091071";

                        new Thread( new Runnable() { @Override public void run() {
                            // Run whatever background code you want here.
                            SendSMS sendSMS = new SendSMS();
                            String smsResponse = sendSMS.sendSms(allNumbers,showroomAddress,showroomNumber,isTwoOrFourWheeler,serviceSelected);
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

//        if (isValidMail(email.getText().toString()) == false) {
//            email.setError("Enter valid email address");
//            wrong = false;
//        }
        return  wrong;
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

//    private boolean isValidMail(String email) {
//        return Pattern.compile("^(\"(91)?[7-9][0-9]{12}\")$").matcher(email).matches();
//    }


    private boolean isValidMobile(String phone) {
        boolean check;
        if(phone.length() < 10 )
        {
            check = false;
           // txtPhone.setError("Not Valid Number");
        }
        else
        {
            check = true;
        }
        return check;
    }
}
