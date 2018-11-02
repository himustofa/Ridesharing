package com.apps.ridesharing.firebase;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.apps.ridesharing.database.ConstantKey;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, ""+token);

        /*SharedPreferences preferences = getApplicationContext().getSharedPreferences(ConstantKey.FCM_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConstantKey.FCM_TOKEN_KEY, token);
        editor.apply();
        editor.commit();*/

        storeToken(token);
    }

    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
