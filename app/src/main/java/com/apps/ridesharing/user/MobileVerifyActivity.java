package com.apps.ridesharing.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.ridesharing.HomeActivity;
import com.apps.ridesharing.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MobileVerifyActivity extends AppCompatActivity {

    private static final String TAG = "MobileVerifyActivity";
    private EditText mobileNumber, mobileCode;
    private Button btnSent, btnSubmit;
    private String tempCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);

        mobileNumber = (EditText) findViewById(R.id.user_mobile_number);
        mobileCode = (EditText) findViewById(R.id.user_mobile_code);
        btnSent = (Button) findViewById(R.id.sent_button);
        btnSubmit = (Button) findViewById(R.id.submit_button);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mobileNumber.getText().toString().isEmpty()) {
                    //sendSms(mobile.getText().toString());
                    Random rand = new Random();
                    tempCode = String.valueOf(rand.nextInt(9999)+1);

                    mobileCode.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);

                    mobileNumber.setVisibility(View.GONE);
                    btnSent.setVisibility(View.GONE);

                    alertDialog(tempCode);
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileCode.getText().toString().equals(tempCode)) {
                    Intent intent = new Intent(MobileVerifyActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog("Please check your code");
                }
            }
        });
    }

    //https://www.textlocal.com/
    //http://api.txtlocal.com/docs/sendsms
    //https://control.txtlocal.co.uk/settings/apikeys/
    public String sendSms(String s) {
        try {
            // Construct data
            String apiKey = "apikey=" + "OFalNF409p0-zTYJLzU7Drnsdk695cgSXr7Fh5joEE";
            String message = "&message=" + "Your OTP code is: 2345";
            String sender = "&sender=" + "KAMAL";
            String numbers = "&numbers=" + "+88"+s;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
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
                Toast.makeText(this, ""+line, Toast.LENGTH_SHORT).show();
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            Log.d(TAG, "Error SMS "+e);
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            return "Error "+e;
        }
    }

    //====================================================| Alert Dialog
    public void alertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
