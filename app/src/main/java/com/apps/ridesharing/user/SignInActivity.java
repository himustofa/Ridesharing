package com.apps.ridesharing.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
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
import android.widget.Toast;

import com.apps.ridesharing.HomeActivity;
import com.apps.ridesharing.R;
import com.apps.ridesharing.database.ConstantKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class SignInActivity extends AppCompatActivity implements IUserBackgroundWorker{

    private static final String TAG = "SignInActivity";
    private SharedPreferences preferences;

    private EditText mobileNumber, mobileCode;
    private TextInputLayout layoutMobileNumber;
    private Button btnSent, btnSubmit;
    private String tempCode;

    private UserModel model;
    private UserService userService;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.userService = new UserService(this);
        context = this;

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY,false);

        if (isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }

        mobileNumber = (EditText) findViewById(R.id.user_mobile_number);
        mobileNumber.addTextChangedListener(new MyTextWatcher(mobileNumber));
        layoutMobileNumber = (TextInputLayout) findViewById(R.id.layout_user_mobile_number);

        mobileCode = (EditText) findViewById(R.id.user_mobile_code);
        btnSent = (Button) findViewById(R.id.sent_button);
        btnSubmit = (Button) findViewById(R.id.submit_button);

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
                    String type = "insert_user";
                    UserBackgroundWorker worker = new UserBackgroundWorker(context);
                    worker.iWorker = (IUserBackgroundWorker) context;
                    worker.execute(type, mobileNumber.getText().toString().trim());
                } else {
                    alertDialog("Please check your code");
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

    @Override
    public void processFinish(String output) {
        if (output != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(output);
                //===============================================| Writes SharedPreferences
                SharedPreferences.Editor editor = this.preferences.edit();
                editor.putBoolean(ConstantKey.USER_IS_LOGGED_KEY, true);
                editor.putString(ConstantKey.USER_MOBILE_KEY, this.mobileNumber.getText().toString()); //user mobile
                editor.apply();
                editor.commit();

                this.model = this.userService.getDataByMobile(this.mobileNumber.getText().toString());
                if (this.model==null) {
                    //this.model = new UserModel(jsonObject.getString("id"), this.mobileNumber.getText().toString());
                    this.model = new UserModel( jsonObject.getString("id"), jsonObject.getString("user_mobile_number"), jsonObject.getString("user_full_name"), jsonObject.getString("user_email"), jsonObject.getString("user_birth_date"), jsonObject.getString("user_nid"), jsonObject.getString("user_gender"), jsonObject.getString("user_image_name"), jsonObject.getString("user_image_path"), jsonObject.getString("user_latitude"), jsonObject.getString("user_longitude"), jsonObject.getString("created_by_id"), jsonObject.getString("updated_by_id"), jsonObject.getString("created_at"));
                    long data = this.userService.addData(this.model);
                    if (data > 0) {
                        Log.d(TAG, String.valueOf("Insert successfully"));
                    }
                } else {
                    this.model = new UserModel( jsonObject.getString("id"), jsonObject.getString("user_mobile_number"), jsonObject.getString("user_full_name"), jsonObject.getString("user_email"), jsonObject.getString("user_birth_date"), jsonObject.getString("user_nid"), jsonObject.getString("user_gender"), jsonObject.getString("user_image_name"), jsonObject.getString("user_image_path"), jsonObject.getString("user_latitude"), jsonObject.getString("user_longitude"), jsonObject.getString("created_by_id"), jsonObject.getString("updated_by_id"), jsonObject.getString("created_at"));
                    long data = this.userService.updateByMobile(this.model, this.mobileNumber.getText().toString());
                    if (data > 0){
                        Log.d(TAG, String.valueOf("Update successfully"));
                    }
                }

                Intent intent = new Intent(this.context, SignUpActivity.class);
                this.context.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
                alertDialog(e.getMessage());
            }
        }
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
