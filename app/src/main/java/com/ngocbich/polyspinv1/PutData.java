package com.ngocbich.polyspinv1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ngoc Bich on 7/22/2018.
 */

public class PutData extends AsyncTask<String,Void,Integer> {
    private String postPara;

    private JSONObject postDataParams;

    public PutData(String id,String name,String value,String name1, String value1,String name2, String value2) {
        postDataParams=new JSONObject();
        try {
            postDataParams.put(name,value);
            postDataParams.put(name1,value1);
            postDataParams.put(name2,value2);
            Log.d("params",postDataParams.toString());

            postPara="/"+id+"?"+name+"="+value+"&"+name1+"="+value1+"&"+name2+"="+value2;
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
        URL url=null;
        HttpURLConnection urlConnection=null;
        OutputStream outputStream=null;
        int responseCode=400;

        try{
            url=new URL(urlString);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
          //  urlConnection.setDoInput(true);

          //  urlConnection.setChunkedStreamingMode(0);

           // urlConnection.connect();

            outputStream=urlConnection.getOutputStream();

            outputStream.write(postPara.getBytes());
            outputStream.flush();
            outputStream.close();

            Log.d("outputStream_PUT",outputStream.toString());
            responseCode=urlConnection.getResponseCode();
            Log.d("responseCode",responseCode+"");
            Log.d("url",url +"");
            Log.d("urlString",urlString);
            Log.d("urlConnection_PUT",urlConnection+"");

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return responseCode;
    }
}
