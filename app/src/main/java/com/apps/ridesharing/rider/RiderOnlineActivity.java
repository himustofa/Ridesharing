package com.apps.ridesharing.rider;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apps.ridesharing.HomeRiderActivity;
import com.apps.ridesharing.R;
import com.apps.ridesharing.database.ConstantKey;
import com.apps.ridesharing.firebase.MySingleton;
import com.apps.ridesharing.firebase.SharedPrefManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RiderOnlineActivity extends AppCompatActivity {

    private static final String TAG = "RiderOnlineActivity";
    private Activity context;
    private SharedPreferences preferences, preferencesToken;
    private boolean isLoggedIn;
    private String riderMobile, token;

    private EditText startTime, price;
    private Button btnStart;

    private LatLng destination = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_online);

        context = this;

        startTime = (EditText) findViewById(R.id.rider_start_time);
        price = (EditText) findViewById(R.id.rider_price);
        btnStart = (Button) findViewById(R.id.start_button);

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.RIDER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.RIDER_IS_LOGGED_KEY, false);
        riderMobile = preferences.getString(ConstantKey.RIDER_MOBILE_KEY, "Data not found");

        //preferencesToken = getSharedPreferences(ConstantKey.FCM_PREFERENCES_KEY, MODE_PRIVATE);
        //token = preferences.getString(ConstantKey.FCM_TOKEN_KEY, "Data not found");
        token = SharedPrefManager.getInstance(RiderOnlineActivity.this).getDeviceToken();

        //====================================| PlaceAutocompleteFragment
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.rider_destination_place_autocomplete);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d(TAG, "Place selected: " + place.getName());
                destination = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                Log.d(TAG, "An error occurred: " + status);
            }
        });

        //====================================| Start Time
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RiderOnlineActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //====================================| Start Time
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "online_rider";
                new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.d(TAG, "Online rider response: "+output);
                        if (output.equals("Insert successfully")) {
                            Intent intent = new Intent(context, HomeRiderActivity.class);
                            startActivity(intent);
                        }
                    }
                }).execute(type, riderMobile, String.valueOf(destination.latitude), String.valueOf(destination.longitude), startTime.getText().toString(), price.getText().toString());


                //====================================| Insert Token
                /*String url = "http://to-let.nhp-bd.com/mk_insert_token.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Token response: "+response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError response: "+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("riderMobileNumber",riderMobile);
                        map.put("fcmToken",token);
                        map.put("createdById","");
                        map.put("updatedById","");
                        map.put("createdAt",String.valueOf(new Timestamp(System.currentTimeMillis())));
                        Log.d(TAG, "Map: "+riderMobile+" "+token);
                        return map;
                    }
                };
                MySingleton.getMyInstance(RiderOnlineActivity.this).addToRequestQueue(request);*/

                new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.d(TAG, "Token response: "+output);
                        if (output.equals("Insert successfully")) {
                            Log.d(TAG, "Token response: "+riderMobile+" : "+token);
                        }
                    }
                }).execute("insert_token", riderMobile, token);

            }
        });
    }
}
