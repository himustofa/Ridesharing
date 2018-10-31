package com.apps.ridesharing.rider;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

public class RiderInfoActivity extends AppCompatActivity {

    private static final String TAG = "RiderInfoActivity";

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String riderMobile;
    private String riderId;

    private static final int RESULT_LOAD_IMAGE = 1;
    private OutputStream output;
    private String riderImageName;
    private String riderImagePath;

    private ImageView riderPhoto;
    private EditText riderFullName, riderEmail, riderBirthDate, riderNid, riderLicense, riderVehicleNo;
    private RadioGroup riderGenderGroup;
    private RadioButton riderGender;
    private Spinner riderDistrict, riderVehicle;
    private ArrayAdapter adapterDistrict, adapterVehicle;
    private Button button;
    private TextInputLayout layoutRiderFullName, layoutRiderEmail, layoutRiderBirthDate, layoutRiderNid, layoutRiderLicense, layoutVehicleNo;

    private RiderModel model;
    private RiderService riderService;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_info);

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.RIDER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.RIDER_IS_LOGGED_KEY, false);
        riderMobile = preferences.getString(ConstantKey.RIDER_MOBILE_KEY, "Data not found");

        this.riderService = new RiderService(this);
        this.model = this.riderService.getDataByMobile(riderMobile);

        context = this;

        //===============================================| Initial AsyncTask
        if (riderMobile != null && !riderMobile.isEmpty()) {
            new RiderAsyncTaskBefore(context, new RiderAsyncTaskBefore.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null && !output.isEmpty()) {
                        //Load image from gallery
                        riderPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                            }
                        });

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(output);

                            riderId = jsonObject.getString("id");
                            riderFullName.setText(jsonObject.getString("rider_full_name"));
                            riderEmail.setText(jsonObject.getString("rider_email"));
                            riderBirthDate.setText(jsonObject.getString("rider_birth_date"));
                            riderNid.setText(jsonObject.getString("rider_nid"));

                            if (jsonObject.getString("rider_gender").equals("Male")) {
                                RadioButton male =(RadioButton)findViewById(R.id.rider_gender_male);
                                male.setChecked(true);
                            } else if (jsonObject.getString("rider_gender").equals("Female")) {
                                RadioButton female =(RadioButton)findViewById(R.id.rider_gender_female);
                                female.setChecked(true);
                            } else if (jsonObject.getString("rider_gender").equals("Other")) {
                                RadioButton other =(RadioButton)findViewById(R.id.rider_gender_other);
                                other.setChecked(true);
                            }

                            riderDistrict.setSelection(adapterDistrict.getPosition(jsonObject.getString("rider_district")));
                            riderVehicle.setSelection(adapterVehicle.getPosition(jsonObject.getString("rider_vehicle")));
                            riderLicense.setText(jsonObject.getString("rider_license"));
                            riderVehicleNo.setText(jsonObject.getString("rider_vehicle_no"));

                            riderImageName = jsonObject.getString("rider_image_name");
                            if (riderImageName != null && !riderImageName.isEmpty()) {
                                String img = jsonObject.getString("rider_image_path")+jsonObject.getString("rider_image_name");
                                new DownloadImageTask(context, riderPhoto).execute(img);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).execute("select_rider", riderMobile);
        }

        //===============================================| findViewById
        riderPhoto = (ImageView) findViewById(R.id.rider_passport);

        riderFullName = (EditText) findViewById(R.id.rider_full_name);
        riderFullName.addTextChangedListener(new MyTextWatcher(riderFullName));
        layoutRiderFullName = (TextInputLayout) findViewById(R.id.layout_rider_full_name);

        riderEmail = (EditText) findViewById(R.id.rider_email);
        riderEmail.addTextChangedListener(new MyTextWatcher(riderEmail));
        layoutRiderEmail = (TextInputLayout) findViewById(R.id.layout_rider_email);

        riderBirthDate = (EditText) findViewById(R.id.rider_birth_date);
        riderBirthDate.addTextChangedListener(new MyTextWatcher(riderBirthDate));
        layoutRiderBirthDate = (TextInputLayout) findViewById(R.id.layout_rider_birth_date);

        riderNid = (EditText) findViewById(R.id.rider_nid);
        riderNid.addTextChangedListener(new MyTextWatcher(riderNid));
        layoutRiderNid = (TextInputLayout) findViewById(R.id.layout_rider_nid);

        riderGenderGroup = (RadioGroup)findViewById(R.id.rider_gender_group);

        riderDistrict = (Spinner) findViewById(R.id.rider_district);
        adapterDistrict = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.district_array));
        riderDistrict.setAdapter(adapterDistrict);

        riderVehicle = (Spinner) findViewById(R.id.rider_vehicle);
        adapterVehicle = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vehicle_array));
        riderVehicle.setAdapter(adapterVehicle);

        riderLicense = (EditText) findViewById(R.id.rider_license);
        riderLicense.addTextChangedListener(new MyTextWatcher(riderLicense));
        layoutRiderLicense = (TextInputLayout) findViewById(R.id.layout_rider_license);

        riderVehicleNo = (EditText) findViewById(R.id.rider_vehicle_no);
        riderVehicleNo.addTextChangedListener(new MyTextWatcher(riderVehicleNo));
        layoutVehicleNo = (TextInputLayout) findViewById(R.id.layout_rider_vehicle_no);

        button = (Button) findViewById(R.id.rider_submit_button);

        riderBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDate();
            }
        });

        riderPhoto.setOnClickListener(new View.OnClickListener() {
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
                int selectedId = riderGenderGroup.getCheckedRadioButtonId();
                riderGender =(RadioButton)findViewById(selectedId);

                if (riderFullName.getText().toString().trim().isEmpty() && riderEmail.getText().toString().trim().isEmpty() && riderBirthDate.getText().toString().trim().isEmpty() && riderLicense.getText().toString().trim().isEmpty() && riderVehicleNo.getText().toString().trim().isEmpty()) {
                    layoutRiderFullName.setError("required!");
                    layoutRiderEmail.setError("required!");
                    layoutRiderBirthDate.setError("required!");
                    layoutRiderLicense.setError("required!");
                    layoutVehicleNo.setError("required!");
                } else {
                    if (model != null) {
                        RiderModel riderModel = new RiderModel(riderId,riderMobile,riderFullName.getText().toString(),riderEmail.getText().toString(),riderBirthDate.getText().toString(),riderNid.getText().toString(),riderGender.getText().toString(),riderDistrict.getSelectedItem().toString(),riderVehicle.getSelectedItem().toString(),riderLicense.getText().toString(),riderVehicleNo.getText().toString(),riderImageName,"http://to-let.nhp-bd.com/RiderPhoto/","","");
                        long data = riderService.updateByMobile(riderModel, riderMobile);
                        if (data > 0){

                            String type = "update_rider";
                            String encodeImage = imageToServer();
                            new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                                @Override
                                public void processFinish(String output) {
                                    if (output.equals("Updated successfully")) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).execute(type,riderMobile,riderFullName.getText().toString(),riderEmail.getText().toString(),riderBirthDate.getText().toString(),riderNid.getText().toString(),riderGender.getText().toString(),riderDistrict.getSelectedItem().toString(),riderVehicle.getSelectedItem().toString(),riderLicense.getText().toString(),riderVehicleNo.getText().toString(),riderImageName,"http://to-let.nhp-bd.com/RiderPhoto/",model.getCreatedAt(),encodeImage,riderId);

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
                riderBirthDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
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
            riderImageName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".png";
            riderPhoto.setImageURI(selectImage);
        }
    }

    //Encoding from passport image
    public String imageToServer(){
        Bitmap bitmap = ((BitmapDrawable)riderPhoto.getDrawable()).getBitmap();
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
                case R.id.rider_full_name:
                    layoutRiderFullName.setErrorEnabled(false);
                    break;
                case R.id.rider_email:
                    layoutRiderEmail.setErrorEnabled(false);
                    break;
                case R.id.rider_birth_date:
                    layoutRiderBirthDate.setErrorEnabled(false);
                    break;
                case R.id.rider_license:
                    layoutRiderLicense.setErrorEnabled(false);
                    break;
                case R.id.rider_vehicle_no:
                    layoutVehicleNo.setErrorEnabled(false);
                    break;
            }
        }
    }


}
