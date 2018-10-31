package com.apps.ridesharing.user;

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

public class UserAsyncTask extends AsyncTask<String,Void,String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    private static final String TAG = "UserAsyncTask";
    private Context context;
    private ProgressDialog progress;
    private AsyncResponse asyncResponse = null;

    public UserAsyncTask(Context ctx, AsyncResponse response) {
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

        if(type.equals("insert_user")) {
            String insert_url = "http://to-let.nhp-bd.com/mk_user_insert.php";
            try {
                String userMobile = params[1];
                String userFullName = "";
                String userEmail = "";
                String userBirthDate = "";
                String userNid = "";
                String userGender = "";
                String userImageName = "";
                String userImagePath = "";
                String userLatitude = "";
                String userLongitude = "";
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

                String post_data = URLEncoder.encode("userMobile","UTF-8")+"="+URLEncoder.encode(userMobile,"UTF-8")+"&"
                        +URLEncoder.encode("userFullName","UTF-8")+"="+URLEncoder.encode(userFullName,"UTF-8")+"&"
                        +URLEncoder.encode("userEmail","UTF-8")+"="+URLEncoder.encode(userEmail,"UTF-8")+"&"
                        +URLEncoder.encode("userBirthDate","UTF-8")+"="+URLEncoder.encode(userBirthDate,"UTF-8")+"&"
                        +URLEncoder.encode("userNid","UTF-8")+"="+URLEncoder.encode(userNid,"UTF-8")+"&"
                        +URLEncoder.encode("userGender","UTF-8")+"="+URLEncoder.encode(userGender,"UTF-8")+"&"
                        +URLEncoder.encode("userImageName","UTF-8")+"="+URLEncoder.encode(userImageName,"UTF-8")+"&"
                        +URLEncoder.encode("userImagePath","UTF-8")+"="+URLEncoder.encode(userImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("userLatitude","UTF-8")+"="+URLEncoder.encode(userLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("userLongitude","UTF-8")+"="+URLEncoder.encode(userLongitude,"UTF-8")+"&"
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

        if(type.equals("update_user")) {
            String insert_url = "http://to-let.nhp-bd.com/mk_user_update.php";
            try {
                String userMobile = params[1];
                String userFullName = params[2];
                String userEmail = params[3];
                String userBirthDate = params[4];
                String userNid = params[5];
                String userGender = params[6];
                String userImageName = params[7];
                String userImagePath = params[8];
                String encodeImage = params[9];
                String userId = params[10];
                String userLatitude = "";
                String userLongitude = "";
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

                String post_data = URLEncoder.encode("userMobile","UTF-8")+"="+URLEncoder.encode(userMobile,"UTF-8")+"&"
                        +URLEncoder.encode("userFullName","UTF-8")+"="+URLEncoder.encode(userFullName,"UTF-8")+"&"
                        +URLEncoder.encode("userEmail","UTF-8")+"="+URLEncoder.encode(userEmail,"UTF-8")+"&"
                        +URLEncoder.encode("userBirthDate","UTF-8")+"="+URLEncoder.encode(userBirthDate,"UTF-8")+"&"
                        +URLEncoder.encode("userNid","UTF-8")+"="+URLEncoder.encode(userNid,"UTF-8")+"&"
                        +URLEncoder.encode("userGender","UTF-8")+"="+URLEncoder.encode(userGender,"UTF-8")+"&"
                        +URLEncoder.encode("userImageName","UTF-8")+"="+URLEncoder.encode(userImageName,"UTF-8")+"&"
                        +URLEncoder.encode("userImagePath","UTF-8")+"="+URLEncoder.encode(userImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("userLatitude","UTF-8")+"="+URLEncoder.encode(userLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("userLongitude","UTF-8")+"="+URLEncoder.encode(userLongitude,"UTF-8")+"&"
                        +URLEncoder.encode("encodeImage","UTF-8")+"="+URLEncoder.encode(encodeImage,"UTF-8")+"&"
                        +URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
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

        if(type.equals("select_user")) {
            String url = "http://to-let.nhp-bd.com/mk_user_select.php?userMobileNumber=" + params[1];
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
