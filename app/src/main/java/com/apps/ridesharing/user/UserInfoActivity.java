package com.apps.ridesharing.user;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.apps.ridesharing.HomeActivity;
import com.apps.ridesharing.R;
import com.apps.ridesharing.database.ConstantKey;
import com.apps.ridesharing.utility.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String userMobile;
    private String userId;

    private static final int RESULT_LOAD_IMAGE = 1;
    private OutputStream output;
    private String userImageName;
    private String userImagePath;

    private ImageView userPhoto;
    private EditText userFullName, userEmail, userBirthDate, userNid;
    private RadioGroup userGenderGroup;
    private RadioButton userGender;
    private Button button;
    private TextInputLayout layoutUserFullName, layoutUserEmail, layoutUserBirthDate;

    private UserModel userModel;
    private UserService userService;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");

        this.userService = new UserService(this);
        this.userModel = this.userService.getDataByMobile(userMobile);

        context = this;

        //===============================================| Initial AsyncTask
        if (userMobile != null && !userMobile.isEmpty()) {
            new UserAsyncTask(context, new UserAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null && !output.isEmpty()) {
                        //Load image from gallery
                        userPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                            }
                        });

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(output);

                            userId = jsonObject.getString("id");
                            userFullName.setText(jsonObject.getString("user_full_name"));
                            userEmail.setText(jsonObject.getString("user_email"));
                            userBirthDate.setText(jsonObject.getString("user_birth_date"));
                            userNid.setText(jsonObject.getString("user_nid"));

                            if (jsonObject.getString("user_gender").equals("Male")) {
                                RadioButton male =(RadioButton)findViewById(R.id.user_gender_male);
                                male.setChecked(true);
                            } else if (jsonObject.getString("user_gender").equals("Female")) {
                                RadioButton female =(RadioButton)findViewById(R.id.user_gender_female);
                                female.setChecked(true);
                            } else if (jsonObject.getString("user_gender").equals("Other")) {
                                RadioButton other =(RadioButton)findViewById(R.id.user_gender_other);
                                other.setChecked(true);
                            }

                            userImageName = jsonObject.getString("user_image_name");
                            if (userImageName != null && !userImageName.isEmpty()) {
                                String img = jsonObject.getString("user_image_path")+jsonObject.getString("user_image_name");
                                new DownloadImageTask(context, userPhoto).execute(img);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).execute("select_user", userMobile);
        }

        //===============================================| findViewById
        userPhoto = (ImageView) findViewById(R.id.user_passport);

        userFullName = (EditText) findViewById(R.id.user_full_name);
        userFullName.addTextChangedListener(new MyTextWatcher(userFullName));
        layoutUserFullName = (TextInputLayout) findViewById(R.id.layout_user_full_name);

        userEmail = (EditText) findViewById(R.id.user_email);
        userEmail.addTextChangedListener(new MyTextWatcher(userEmail));
        layoutUserEmail = (TextInputLayout) findViewById(R.id.layout_user_email);

        userBirthDate = (EditText) findViewById(R.id.user_birth_date);
        userBirthDate.addTextChangedListener(new MyTextWatcher(userBirthDate));
        layoutUserBirthDate = (TextInputLayout) findViewById(R.id.layout_user_birth_date);

        userNid = (EditText) findViewById(R.id.user_nid);

        userGenderGroup = (RadioGroup)findViewById(R.id.user_gender_group);

        button = (Button) findViewById(R.id.user_submit_button);

        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDate();
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        //===============================================| AsyncTask
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = userGenderGroup.getCheckedRadioButtonId();
                userGender =(RadioButton)findViewById(selectedId);

                if (userFullName.getText().toString().trim().isEmpty() && userEmail.getText().toString().trim().isEmpty() && userBirthDate.getText().toString().trim().isEmpty()) {
                    layoutUserFullName.setError("required!");
                    layoutUserEmail.setError("required!");
                    layoutUserBirthDate.setError("required!");
                } else {
                    if (userModel != null) {
                        UserModel model = new UserModel(userId,userMobile,userFullName.getText().toString(),userEmail.getText().toString(),userBirthDate.getText().toString(),userNid.getText().toString(),userGender.getText().toString(),userImageName,"http://to-let.nhp-bd.com/UserPhoto/","","");
                        long data = userService.updateByMobile(model, userMobile);
                        if (data > 0){

                            String type = "update_user";
                            String encodeImage = imageToServer();
                            new UserAsyncTaskBefore(context, new UserAsyncTaskBefore.AsyncResponse() {
                                @Override
                                public void processFinish(String output) {
                                    if (output.equals("Updated successfully")) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).execute(type,userMobile,userFullName.getText().toString(),userEmail.getText().toString(),userBirthDate.getText().toString(),userNid.getText().toString(),userGender.getText().toString(),userImageName,"http://to-let.nhp-bd.com/UserPhoto/",encodeImage,userId);

                            Log.d(TAG, String.valueOf("Update successfully"));
                        }
                    }
                }
            }
        });
    }

    //===============================================| Birthday date-picker
    private void birthDate() {
        DatePicker picker = new DatePicker(this);
        int curYear = picker.getYear();
        int curMonth = picker.getMonth()+1;
        int curDayOfMonth = picker.getDayOfMonth();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                userBirthDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, curYear, curMonth, curDayOfMonth);
        pickerDialog.show();
    }

    //===============================================| For Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectImage = data.getData();
            userImageName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".png";
            userPhoto.setImageURI(selectImage);
        }
    }

    //Encoding from passport image
    public String imageToServer(){
        Bitmap bitmap = ((BitmapDrawable)userPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String encode = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        return encode;
    }

    //===============================================| TextWatcher
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
                case R.id.user_full_name:
                    layoutUserFullName.setErrorEnabled(false);
                    break;
                case R.id.user_email:
                    layoutUserEmail.setErrorEnabled(false);
                    break;
                case R.id.user_birth_date:
                    layoutUserBirthDate.setErrorEnabled(false);
                    break;
            }
        }
    }

}
