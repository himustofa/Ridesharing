package com.apps.ridesharing.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.apps.ridesharing.HomeActivity;
import com.apps.ridesharing.HomeRiderActivity;
import com.apps.ridesharing.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FIREBASE_SERVICE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Intent intent = new Intent(this, HomeRiderActivity.class);

        if (remoteMessage.getData().size() > 0) {
            String name = remoteMessage.getData().get("user_full_name");
            String mobile = remoteMessage.getData().get("user_mobile");
            String lat = remoteMessage.getData().get("user_latitude");
            String lng = remoteMessage.getData().get("user_longitude");
            Bundle bundle = new Bundle();
            bundle.putString("user_full_name", name);
            bundle.putString("user_mobile", mobile);
            bundle.putString("user_latitude", lat);
            bundle.putString("user_longitude", lng);
            intent.putExtras(bundle);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Notification");
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    //Another way FirebaseInstanceIdService
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, token);
        //sendRegistrationToServer(token);
    }


}
