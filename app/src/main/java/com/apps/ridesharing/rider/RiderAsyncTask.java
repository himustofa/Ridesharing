package com.apps.ridesharing.rider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.apps.ridesharing.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;

public class RiderAsyncTask extends AsyncTask<String,Void,String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    private static final String TAG = "RiderAsyncTask";
    private Context context;
    private ProgressDialog progress;
    private AsyncResponse asyncResponse = null;

    public RiderAsyncTask(Context ctx, AsyncResponse response) {
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
        //String server_url = "http://192.185.81.151/user.php";

        if(type.equals("insert_rider")) {
            String insert_url = "http://to-let.nhp-bd.com/mk_rider_insert.php";
            try {
                String riderMobileNumber = params[1];
                String riderPassword = params[2];
                String riderFullName = "";
                String riderEmail = "";
                String riderBirthDate = "";
                String riderNid = "";
                String riderGender = "";
                String riderDistrict = "";
                String riderVehicle = "";
                String riderLicense = "";
                String riderVehicleNo = "";
                String riderImageName = "";
                String riderImagePath = "";
                String riderLatitude = "";
                String riderLongitude = "";
                String createdById = "";
                String updatedById = "";
                String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("riderMobileNumber","UTF-8")+"="+URLEncoder.encode(riderMobileNumber,"UTF-8")+"&"
                        +URLEncoder.encode("riderPassword","UTF-8")+"="+URLEncoder.encode(riderPassword,"UTF-8")+"&"
                        +URLEncoder.encode("riderFullName","UTF-8")+"="+URLEncoder.encode(riderFullName,"UTF-8")+"&"
                        +URLEncoder.encode("riderEmail","UTF-8")+"="+URLEncoder.encode(riderEmail,"UTF-8")+"&"
                        +URLEncoder.encode("riderBirthDate","UTF-8")+"="+URLEncoder.encode(riderBirthDate,"UTF-8")+"&"
                        +URLEncoder.encode("riderNid","UTF-8")+"="+URLEncoder.encode(riderNid,"UTF-8")+"&"
                        +URLEncoder.encode("riderGender","UTF-8")+"="+URLEncoder.encode(riderGender,"UTF-8")+"&"
                        +URLEncoder.encode("riderDistrict","UTF-8")+"="+URLEncoder.encode(riderDistrict,"UTF-8")+"&"
                        +URLEncoder.encode("riderVehicle","UTF-8")+"="+URLEncoder.encode(riderVehicle,"UTF-8")+"&"
                        +URLEncoder.encode("riderLicense","UTF-8")+"="+URLEncoder.encode(riderLicense,"UTF-8")+"&"
                        +URLEncoder.encode("riderVehicleNo","UTF-8")+"="+URLEncoder.encode(riderVehicleNo,"UTF-8")+"&"
                        +URLEncoder.encode("riderImageName","UTF-8")+"="+URLEncoder.encode(riderImageName,"UTF-8")+"&"
                        +URLEncoder.encode("riderImagePath","UTF-8")+"="+URLEncoder.encode(riderImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("riderLatitude","UTF-8")+"="+URLEncoder.encode(riderLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("riderLongitude","UTF-8")+"="+URLEncoder.encode(riderLongitude,"UTF-8")+"&"
                        +URLEncoder.encode("createdById","UTF-8")+"="+URLEncoder.encode(createdById,"UTF-8")+"&"
                        +URLEncoder.encode("updatedById","UTF-8")+"="+URLEncoder.encode(updatedById,"UTF-8")+"&"
                        +URLEncoder.encode("createdAt","UTF-8")+"="+URLEncoder.encode(createdAt,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            } catch (IOException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            }
        }

        if(type.equals("update_rider")) {
            String insert_url = "http://to-let.nhp-bd.com/mk_rider_update.php";
            try {
                String riderMobileNumber = params[1];
                String riderFullName = params[2];
                String riderEmail = params[3];
                String riderBirthDate = params[4];
                String riderNid = params[5];
                String riderGender = params[6];
                String riderDistrict = params[7];
                String riderVehicle = params[8];
                String riderLicense = params[9];
                String riderVehicleNo = params[10];
                String riderImageName = params[11];
                String riderImagePath = params[12];
                String riderLatitude = "";
                String riderLongitude = "";
                String createdById = "";
                String updatedById = String.valueOf(new Timestamp(System.currentTimeMillis()));
                String createdAt = params[13];
                String riderId = params[14];
                String encodeImage = params[15];

                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("riderMobileNumber","UTF-8")+"="+URLEncoder.encode(riderMobileNumber,"UTF-8")+"&"
                        +URLEncoder.encode("riderFullName","UTF-8")+"="+URLEncoder.encode(riderFullName,"UTF-8")+"&"
                        +URLEncoder.encode("riderEmail","UTF-8")+"="+URLEncoder.encode(riderEmail,"UTF-8")+"&"
                        +URLEncoder.encode("riderBirthDate","UTF-8")+"="+URLEncoder.encode(riderBirthDate,"UTF-8")+"&"
                        +URLEncoder.encode("riderNid","UTF-8")+"="+URLEncoder.encode(riderNid,"UTF-8")+"&"
                        +URLEncoder.encode("riderGender","UTF-8")+"="+URLEncoder.encode(riderGender,"UTF-8")+"&"
                        +URLEncoder.encode("riderDistrict","UTF-8")+"="+URLEncoder.encode(riderDistrict,"UTF-8")+"&"
                        +URLEncoder.encode("riderVehicle","UTF-8")+"="+URLEncoder.encode(riderVehicle,"UTF-8")+"&"
                        +URLEncoder.encode("riderLicense","UTF-8")+"="+URLEncoder.encode(riderLicense,"UTF-8")+"&"
                        +URLEncoder.encode("riderVehicleNo","UTF-8")+"="+URLEncoder.encode(riderVehicleNo,"UTF-8")+"&"
                        +URLEncoder.encode("riderImageName","UTF-8")+"="+URLEncoder.encode(riderImageName,"UTF-8")+"&"
                        +URLEncoder.encode("riderImagePath","UTF-8")+"="+URLEncoder.encode(riderImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("riderLatitude","UTF-8")+"="+URLEncoder.encode(riderLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("riderLongitude","UTF-8")+"="+URLEncoder.encode(riderLongitude,"UTF-8")+"&"
                        +URLEncoder.encode("riderId","UTF-8")+"="+URLEncoder.encode(riderId,"UTF-8")+"&"
                        +URLEncoder.encode("encodeImage","UTF-8")+"="+URLEncoder.encode(encodeImage,"UTF-8")+"&"
                        +URLEncoder.encode("createdById","UTF-8")+"="+URLEncoder.encode(createdById,"UTF-8")+"&"
                        +URLEncoder.encode("updatedById","UTF-8")+"="+URLEncoder.encode(updatedById,"UTF-8")+"&"
                        +URLEncoder.encode("createdAt","UTF-8")+"="+URLEncoder.encode(createdAt,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            } catch (IOException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            }
        }

        if(type.equals("select_rider")) {
            String url = "http://to-let.nhp-bd.com/mk_rider_select.php?riderMobileNumber=" + params[1];
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

                Log.d(TAG, String.valueOf(result));
                return result;
            } catch (Exception e) {
                if(this.progress != null) {
                    this.progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
                alertDialog(e.getMessage());
                return null;
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
