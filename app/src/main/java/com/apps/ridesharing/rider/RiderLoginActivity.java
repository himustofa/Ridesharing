package com.apps.ridesharing.rider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.ridesharing.R;
import com.apps.ridesharing.database.ConstantKey;

public class RiderLoginActivity extends AppCompatActivity {

    private static final String TAG = "RiderLoginActivity";
    private SharedPreferences preferences;

    private EditText mobileNumber, password;
    private TextInputLayout layoutMobileNumber, layoutPassword;
    private Button btnLogin, btnCreate;

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_login);

        context = this;

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.RIDER_LOGIN_KEY, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.RIDER_IS_LOGGED_KEY,false);

        if (isLoggedIn) {
            //Intent intent = new Intent(getApplicationContext(), HomeRiderActivity.class);
            //startActivity(intent);
        }

        //===============================================| findViewById
        mobileNumber = (EditText) findViewById(R.id.rider_mobile_number);
        layoutMobileNumber = (TextInputLayout) findViewById(R.id.layout_rider_mobile_number);

        password = (EditText) findViewById(R.id.rider_password);
        layoutPassword = (TextInputLayout) findViewById(R.id.layout_rider_password);

        btnLogin = (Button) findViewById(R.id.login_button);
        btnCreate = (Button) findViewById(R.id.create_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mobileNumber.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    String type = "login_rider";
                    new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Log.d(TAG, ""+output);
                            if (output.equals("Login successfully")) {
                                //===============================================| Writes SharedPreferences
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(ConstantKey.RIDER_IS_LOGGED_KEY, true);
                                editor.putString(ConstantKey.RIDER_MOBILE_KEY, mobileNumber.getText().toString()); //user mobile
                                editor.apply();
                                editor.commit();

                                Intent intent = new Intent(context, RiderOnlineActivity.class);
                                startActivity(intent);
                            } else {
                                alertDialog("Create a new account");
                            }
                        }
                    }).execute(type, mobileNumber.getText().toString().trim(), password.getText().toString().trim());
                } else {
                    alertDialog("Something wrong!");
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RiderMobileActivity.class);
                startActivity(intent);
            }
        });
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
