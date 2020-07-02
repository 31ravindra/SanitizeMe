package com.sanitize.sanitizeme;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendSMS {


    public String sendSms(String allnumbers, String showroomAdd, String showroomcontact, boolean isTOF) {
        try {
            // Construct data
             String SMSMessage;
             if(isTOF) {
                 SMSMessage = "Dear customer," + '\n' + "Thanks for the booking." + '\n' + "Sanitize Showroom Address - " + showroomAdd + '\n' + "Sanitize Showroom Contact - "+ showroomcontact+'\n' + "We will call you for time slot, Don't Worry!" + '\n' + "Stay Safe!" + '\n' + "feel free to contact us on" + '\n' + "customersupport@sanitizeme.co.in" + '\n' + "9555091071";
             } else {
                 SMSMessage = "Dear customer," + '\n' + "Thanks for the booking." + '\n'  + "Sanitize Showroom Contact - "+ showroomcontact+'\n' + "We will call you for time slot, Don't Worry!" + '\n' + "Stay Safe!" + '\n' + "feel free to contact us on" + '\n' + "customersupport@sanitizeme.co.in" + '\n' + "9555091071";
             }

            String apiKey = "apikey=" + "6DoCBMSMl4Q-HNv0ilM3hSMNkgS0FSZGgD9XH80ino";
            String message = "&message=" + SMSMessage;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + allnumbers;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}




