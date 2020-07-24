package com.sanitizer.sanitizeme;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {


    public String sendSms(String allnumbers, String showroomAdd, String showroomcontact, boolean isTOF, String categorySelected, String userNumber) {
        try {
            // Construct data
             String SMSMessage;
             if(isTOF) {
                 SMSMessage = "Dear customer("+userNumber+")," + '\n' + "Thanks for the booking." + '\n' + "Category Selected -"+categorySelected + '\n'+ "Sanitize Showroom Address - " + showroomAdd + '\n' + "Sanitize Showroom Contact - "+ showroomcontact+'\n' + "We will call you for time slot, Don't Worry!" + '\n' + "Stay Safe!" + '\n' + "feel free to contact us for service and cancellation issue on" + '\n' + "customersupport@sanitizeme.co.in" + '\n' + "9555091071";
             } else {
                 SMSMessage = "Dear customer("+userNumber+")," + '\n' + "Thanks for the booking." + '\n' + "Category Selected -"+categorySelected + '\n' + "Sanitize Showroom Contact - "+ showroomcontact+'\n' + "We will call you for time slot, Don't Worry!" + '\n' + "Stay Safe!" + '\n' + "feel free to contact us for service and cancellation issue on" + '\n' + "customersupport@sanitizeme.co.in" + '\n' + "9555091071";
             }

            String apiKey = "apikey=" + "BKniB2oGHHU-HYhhYC7QRkBvmJy2XrlVuHbLSRx2x6";
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




