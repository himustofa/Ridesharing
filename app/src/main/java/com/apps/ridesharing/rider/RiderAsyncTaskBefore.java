package com.apps.ridesharing.rider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.apps.ridesharing.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RiderAsyncTaskBefore extends AsyncTask<String,Void,String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    private static final String TAG = "RiderAsyncTaskBefore";
    private Context context;
    private ProgressDialog progress;
    private AsyncResponse asyncResponse = null;

    public RiderAsyncTaskBefore(Context ctx, AsyncResponse response) {
        this.context = ctx;
        this.asyncResponse = response;
    }

    @Override
    protected void onPreExecute() {
        this.progress = new ProgressDialog(context);
        //progress.setTitle("Retrieving data");
        this.progress.setMessage(context.getString(R.string.progress));
        this.progress.setIndeterminate(true);
        this.progress.setCancelable(false);
        this.progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if (type.equals("select_rider")) {
            String mobile = params[1];
            String url = "http://to-let.nhp-bd.com/mk_rider_select.php?riderMobileNumber="+mobile;
            try {
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();

                //Log.d(TAG, String.valueOf(result));
                return result;
            } catch (Exception e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        this.asyncResponse.processFinish(result);
        if(this.progress != null) {
            this.progress.dismiss(); //close the dialog if error occurs
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    //====================================================| Alert Message
    public void alertDialog(String msg) {
        new AlertDialog.Builder(context)
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
