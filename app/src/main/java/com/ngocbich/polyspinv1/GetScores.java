package com.ngocbich.polyspinv1;

import android.os.AsyncTask;
import android.util.Log;

import com.ngocbich.polyspinv1.model.Scores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngoc Bich on 7/18/2018.
 */

public class GetScores extends AsyncTask<String,Void,Integer> {

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer==400){
            Log.d("GetScore","Failed");
        }
        else if(integer==200)
        {
            Log.d("GetScore","Successful");
        }
        super.onPostExecute(integer);
    }

    @Override
    protected Integer doInBackground(String... strings) {

        String urlString=strings[0];
        URL url=null;
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        String result="";
        int c;

        try{
            url=new URL(urlString);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            //dua du lieu len server
            urlConnection.connect();

            //luong du lieu dau vao
            inputStream=urlConnection.getInputStream();
            Log.d("inputStream",inputStream.toString());

            //doc du lieu
            // endOfFile= -1
            while ((c=inputStream.read())!= -1){
                result +=(char)c;
            }
         //   Log.d("result",result);

            //doc du lieu ra kieu json tu chuoi result
            JSONArray jsonArray=new JSONArray(result);
            JSONObject jsonObject;
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);


                // dua du lieu vao list account

                Constants.scores.add(new Scores(jsonObject.getString("ScoreId"),
                        jsonObject.getString("Score1"),
                        jsonObject.getString("AccountId")));
            }

            for (Scores s:Constants.scores){
                Log.d("GETSCORE-game",s.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            return 400;
        }

        return 200;
    }

}
