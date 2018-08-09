package com.ngocbich.polyspinv1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Ngoc Bich on 7/18/2018.
 */

public class PostData extends AsyncTask<String,Void,Integer> {
    private static final String USER_AGENT = "Mozilla/5.0";
    private String postPara;

    private JSONObject postDataParams;

    public PostData(String name1, String value1,String name2, String value2) {
       postDataParams=new JSONObject();
        try {
            postDataParams.put(name1,value1);
            postDataParams.put(name2,value2);
            Log.d("params",postDataParams.toString());

            postPara="?"+name1+"="+value1+"&"+name2+"="+value2;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer==400){
            Log.d("Post","Failed");
        }
        else if(integer==200)
        {
            Log.d("Post","Successful");
        }
        else {
            Log.d("Post","Diff");
        }
        super.onPostExecute(integer);
    }

    @Override
    protected Integer doInBackground(String... strings) {

        String urlString=strings[0]+postPara;
        Log.d("urlString-POSTDATA",urlString);
        URL url=null;
        HttpURLConnection urlConnection=null;
        OutputStream outputStream=null;
        int responseCode=400;
       // String authtoken="";

        try{
           url=new URL(urlString);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            urlConnection.setChunkedStreamingMode(0);

            /*urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.addRequestProperty("Authorization", authtoken);

            urlConnection.setRequestProperty("User-Agent", USER_AGENT);*/

            urlConnection.connect();

            outputStream=urlConnection.getOutputStream();

            outputStream.write(postPara.getBytes());
            outputStream.flush();
            outputStream.close();

           /* Authenticator.setDefault(new Authenticator() { protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray()); } });*/
            Log.d("outputStream",outputStream.toString());
            responseCode=urlConnection.getResponseCode();
            Log.d("responseCode",responseCode+"");
            Log.d("url",url +"");
            Log.d("urlString",urlString);
            Log.d("urlConnection_POST",urlConnection+"");

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return responseCode;
    }

}
