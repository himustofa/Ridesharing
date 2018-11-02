package com.apps.ridesharing.firebase;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mySingleton;
    private static Context context;
    private RequestQueue requestQueue;

    public MySingleton(Context ctx) {
        this.context = ctx;
        this.requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getMyInstance(Context ctx) {
        if (mySingleton == null) {
            mySingleton = new MySingleton(ctx);
        }
        return mySingleton;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
