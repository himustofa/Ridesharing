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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.ridesharing.HomeActivity;
import com.apps.ridesharing.R;
import com.apps.ridesharing.database.ConstantKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class RiderMobileActivity extends AppCompatActivity {

    private static final String TAG = "RiderMobileActivity";
    private SharedPreferences preferences;

    private EditText mobileNumber, mobileCode, password, confirmPassword;
    private TextInputLayout layoutMobileNumber, layoutMobileCode, layoutPassword, layoutConfirmPassword;
    private Button btnSent, btnCode, btnSubmit;
    private String tempCode;

    private RiderModel model;
    private RiderService riderService;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_mobile);

        this.riderService = new RiderService(this);
        context = this;

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.RIDER_LOGIN_KEY, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.RIDER_IS_LOGGED_KEY,false);

        if (isLoggedIn) {
            //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            //startActivity(intent);
        }

        //===============================================| findViewById
        mobileNumber = (EditText) findViewById(R.id.rider_mobile_number);
        mobileNumber.addTextChangedListener(new MyTextWatcher(mobileNumber));
        layoutMobileNumber = (TextInputLayout) findViewById(R.id.layout_rider_mobile_number);

        mobileCode = (EditText) findViewById(R.id.rider_mobile_code);
        layoutMobileCode = (TextInputLayout) findViewById(R.id.layout_rider_mobile_code);

        password = (EditText) findViewById(R.id.rider_password);
        layoutPassword = (TextInputLayout) findViewById(R.id.layout_rider_password);

        confirmPassword = (EditText) findViewById(R.id.rider_confirm_password);
        layoutConfirmPassword = (TextInputLayout) findViewById(R.id.layout_rider_confirm_password);

        btnSent = (Button) findViewById(R.id.sent);
        btnCode = (Button) findViewById(R.id.submit_code);
        btnSubmit = (Button) findViewById(R.id.submit);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNumber.getText().toString().trim().isEmpty()) {
                    layoutMobileNumber.setError("required!");
                } else if (mobileNumber.getText().toString().trim().length() != 11) {
                    layoutMobileNumber.setError("length must be 11 digits");
                }
                if (!mobileNumber.getText().toString().isEmpty()) {
                    //sendSms(mobile.getText().toString());
                    Random rand = new Random();
                    tempCode = String.valueOf(rand.nextInt(9999)+1);
                    Log.d(TAG, String.valueOf(tempCode));

                    layoutMobileCode.setVisibility(View.VISIBLE);
                    btnCode.setVisibility(View.VISIBLE);
                    layoutMobileNumber.setVisibility(View.GONE);
                    btnSent.setVisibility(View.GONE);

                    alertDialog(tempCode);
                }
            }
        });
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileCode.getText().toString().equals(tempCode)) {
                    layoutPassword.setVisibility(View.VISIBLE);
                    layoutConfirmPassword.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    layoutMobileCode.setVisibility(View.GONE);
                    btnCode.setVisibility(View.GONE);
                } else {
                    alertDialog("Please check your code");
                }
            }
        });

        //===============================================| AsyncTask
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mobileNumber.getText().toString().trim().isEmpty() && password.getText().toString().equals(confirmPassword.getText().toString())) {

                    String type = "insert_rider";
                    new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            if (output != null) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(output);
                                    //===============================================| Writes SharedPreferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean(ConstantKey.RIDER_IS_LOGGED_KEY, true);
                                    editor.putString(ConstantKey.RIDER_MOBILE_KEY, mobileNumber.getText().toString()); //user mobile
                                    editor.apply();
                                    editor.commit();

                                    model = riderService.getDataByMobile(mobileNumber.getText().toString());
                                    if (model==null) {
                                        model = new RiderModel( jsonObject.getString("id"), jsonObject.getString("rider_mobile_number"), jsonObject.getString("rider_password"), jsonObject.getString("rider_full_name"), jsonObject.getString("rider_email"), jsonObject.getString("rider_birth_date"), jsonObject.getString("rider_nid"), jsonObject.getString("rider_gender"), jsonObject.getString("rider_district"), jsonObject.getString("rider_vehicle"), jsonObject.getString("rider_license"), jsonObject.getString("rider_vehicle_no"), jsonObject.getString("rider_image_name"), jsonObject.getString("rider_image_path"), jsonObject.getString("rider_latitude"), jsonObject.getString("rider_longitude"), jsonObject.getString("created_by_id"), jsonObject.getString("updated_by_id"), jsonObject.getString("created_at"));
                                        long data = riderService.addData(model);
                                        if (data > 0) {
                                            Log.d(TAG, String.valueOf("Insert successfully"));
                                        }
                                    } else {
                                        model = new RiderModel( jsonObject.getString("id"), jsonObject.getString("rider_mobile_number"), jsonObject.getString("rider_password"), jsonObject.getString("rider_full_name"), jsonObject.getString("rider_email"), jsonObject.getString("rider_birth_date"), jsonObject.getString("rider_nid"), jsonObject.getString("rider_gender"), jsonObject.getString("rider_district"), jsonObject.getString("rider_vehicle"), jsonObject.getString("rider_license"), jsonObject.getString("rider_vehicle_no"), jsonObject.getString("rider_image_name"), jsonObject.getString("rider_image_path"), jsonObject.getString("rider_latitude"), jsonObject.getString("rider_longitude"), jsonObject.getString("created_by_id"), jsonObject.getString("updated_by_id"), jsonObject.getString("created_at"));
                                        long data = riderService.updateByMobile(model, mobileNumber.getText().toString());
                                        if (data > 0){
                                            Log.d(TAG, String.valueOf("Update successfully"));
                                        }
                                    }
                                    Intent intent = new Intent(context, RiderInfoActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    alertDialog(e.getMessage());
                                }
                            }
                        }
                    }).execute(type, mobileNumber.getText().toString().trim(), password.getText().toString().trim());
                } else {
                    alertDialog("Something wrong!");
                }
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

    //====================================================| TextWatcher
    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.user_mobile_number:
                    layoutMobileNumber.setErrorEnabled(false);
                    break;
            }
        }
    }


}
