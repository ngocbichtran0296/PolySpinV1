package com.ngocbich.polyspinv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//chay dau tien
//set man hinh, lay kich thuoc thiet bi
//ket noi web appi, load account, score
//kiem tra da dang nhap hay chua, neu da dang nhap (co tai khoan) -> vao MenuState, neu chua thi hien thi man hinh dang nhap/dang ky

public class MainActivity extends AppCompatActivity {
    //list account
    public static List<Accounts> account=new ArrayList<>();
    public static List<Scores> score=new ArrayList<>();

    public static SharedPreferences share;
    public static SharedPreferences.Editor editor;

    private static MediaPlayer playerMenu,playerPlay,playerBoss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;

        Constants.blockSize = dm.widthPixels / 9;

        Log.d("blocksize",Constants.blockSize+"");

        playerBoss = MediaPlayer.create(this,R.raw.boss);
        playerBoss.setVolume(100, 100);
        playerBoss.setLooping(true);

        playerMenu=MediaPlayer.create(this,R.raw.menu);
        playerMenu.setVolume(70, 100);
        playerMenu.setLooping(true);

        playerPlay=MediaPlayer.create(this,R.raw.play);
        playerPlay.setVolume(100, 100);
        playerPlay.setLooping(true);


        if(playerMenu.isPlaying())playerMenu.pause();
        if(playerBoss.isPlaying())playerBoss.pause();
        if (playerPlay.isPlaying())playerPlay.pause();

        //get accounts
        new GetAccount().execute("http://192.168.1.7:6660/api/Accounts");
        new GetScore().execute("http://192.168.1.7:6660/api/Scores");


        //kiem tra user da co da dang nhap (tao tai khoan va dang nhap tu lan truoc) ???
        if (checkLogin() == true) {// da login
            Intent game = new Intent(this, Game.class);
            startActivity(game);
            Log.d("da dang nhap: ", " OK");
            finish();
        }
        //chua co tai khoan
        else {// chua login
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        // setContentView(R.layout.activity_main);
    }

    public static MediaPlayer getPlayerMenu() {
        return playerMenu;
    }

    public static MediaPlayer getPlayerPlay() {
        return playerPlay;
    }

    public static MediaPlayer getPlayerBoss() {
        return playerBoss;
    }

    public boolean checkLogin() {
        // khoi tao SharedPreferences co ten "MyShare"
        // Constants.share=getSharedPreferences("MyShare",MODE_PRIVATE);
        share = getSharedPreferences("MyShare", MODE_PRIVATE);
        editor = share.edit();

        // lay chuoi String trong file SharedPreferences thong qua ten URName, URPass va URLogin
        Constants.AccountName = share.getString("URName", "");
        Constants.AccountPass = share.getString("URPass", "");
        Constants.login = share.getBoolean("URLogin", false);
        Constants.highScore = share.getInt("URHighSocre", 0);
        Constants.idAccount = share.getInt("URIdAccount", 0);
        Constants.idScore = share.getInt("URIdScore", 0);

        Log.d("HIGHCSORE-MAIN",Constants.highScore+"");
        Log.d("LOGIN-MAIN",Constants.login+"");

        if (Constants.login == true) {
            return true;
        }
        return false;
    }


    public class GetAccount extends AsyncTask<String,Void,Integer> {
        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==400){
                Log.d("Test","Failed");
            }
            else if(integer==200)
            {
                Log.d("Test","Successful");
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

                //luong dod du lieu dau vao
                inputStream=urlConnection.getInputStream();
                Log.d("inputStream",inputStream.toString());

                //doc du lieu
                // endOfFile= -1
                while ((c=inputStream.read())!= -1){
                    result +=(char)c;
                }
                Log.d("result",result);

                //doc du lieu ra kieu json tu chuoi result
                JSONArray jsonArray=new JSONArray(result);
                JSONObject jsonObject;
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject=jsonArray.getJSONObject(i);

                    // dua du lieu vao list account
                    account.add(new Accounts(jsonObject.getString("AccountId"),
                            jsonObject.getString("Name"),
                            jsonObject.getString("Password")));
                }

                for(Accounts account:account){
                    Log.d("GETACCOUNT-MAIN",account.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
                return 400;
            }

            return 200;
        }// end method doInBackground
    }//end class GetAccounts


    public class GetScore extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==400){
                Log.d("Test","Failed");
            }
            else if(integer==200)
            {
                Log.d("Test","Successful");
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
                Log.d("result",result);

                //doc du lieu ra kieu json tu chuoi result
                JSONArray jsonArray=new JSONArray(result);
                JSONObject jsonObject;
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject=jsonArray.getJSONObject(i);

                    // dua du lieu vao list account
                    score.add(new Scores(jsonObject.getString("ScoreId"),
                            jsonObject.getString("Score1"),
                            jsonObject.getString("AccountId")));
                }

                for(Scores s:score){
                    Log.d("GETSCORE_MAIN",s.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
                return 400;
            }

            return 200;
        }
    }

}
