package com.ngocbich.polyspinv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, password, repassword;
    private Button signUp;
    public static   List<Accounts> list=new ArrayList<>();
    private  List<Scores >scores=new ArrayList<>();
    private  String idscore="";
    public static boolean signup=false;
    public static String NAME="",PASS="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        repassword = findViewById(R.id.RePassword);

        signUp = findViewById(R.id.SignUpNow);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || password.getText().toString().equals("")
                        || repassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(repassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password không đúng", Toast.LENGTH_SHORT).show();
                } else {
                   /* SharedPreferences sharedPreferences = getSharedPreferences("MyShare",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
*/
                    //kiem tra ten dang nhap da co hay chua
                    if (!checkInfor(name.getText().toString())) {
                        //luu cac du lieu
                        MainActivity.editor.putString("URName", name.getText().toString());
                        MainActivity.editor.putString("URPass", password.getText().toString());
                        MainActivity.editor.putBoolean("URLogin", true);
                        MainActivity.editor.putInt("URHighScore",0);
                       // editor.putInt("URIdAccount",Constants.idAccount);


                        MainActivity.editor.commit();

                        //them tai khoan moi len web
                        new PostData("name", name.getText().toString(), "pass", password.getText().toString())
                                .execute("http://192.168.1.7:6660/api/Accounts");

                       //get accounts
                        new Gets().execute("http://192.168.1.7:6660/api/Accounts");

                        signup=true;
                        NAME=name.getText().toString();
                        PASS=password.getText().toString();


                        Constants.AccountName=name.getText().toString();
                        Constants.AccountPass=password.getText().toString();
                        /*Log.d("IDSCORE",idscore);
                        for (Accounts account : list) {
                            Log.d("account-list-signup",account.getName()+"-"+account.getPassword()+"-"+account.getAccountId());
                            if (account.getName().equals(name.getText().toString())
                                    && account.getPassword().equals(password.getText().toString())) {
                                id = account.getAccountId();
                                Constants.idAccount = Integer.parseInt(id);
                                Log.d("accountID-SIGNUP", id + "");
                                Constants.highScore = 0;

                                //post score
                                new PostData("score1", 0 + "", "account", id + "").execute("http://192.168.1.6:6660/api/Scores");

                                break;
                            }
                        }

                        //get scores
                        new Getscore().execute("http://192.168.1.6:6660/api/Scores");

                        for(Scores s:scores){
                            if(Constants.idAccount==Integer.parseInt(s.getIdAccount())){
                                Constants.idScore=Integer.parseInt(s.getId());
                                Log.d("scoreID", id + "");
                                break;
                            }
                        }*/

                        //chuyen sang GameActivity
                        Intent game = new Intent(getApplicationContext(), Game.class);
                        startActivity(game);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tên tài khoản bị trùng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean checkInfor(String name) {
        for (Accounts account : MainActivity.account) {
            if (account.getName().equals(name)) {
                return true;//trung ten tai khoan
            }
        }
        return false;
    }

    public class Gets extends AsyncTask<String,Void,Integer> {
        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==400){
                Log.d("GetAccount-new","Failed");
            }
            else if(integer==200)
            {
                Log.d("GetAccount-new","Successful");
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
                // Log.d("result",result);

                //doc du lieu ra kieu json tu chuoi result
                JSONArray jsonArray=new JSONArray(result);
                JSONObject jsonObject;
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject=jsonArray.getJSONObject(i);

                    // dua du lieu vao list account
                    list.add(new Accounts(jsonObject.getString("AccountId"),
                            jsonObject.getString("Name"),
                            jsonObject.getString("Password")));
                }

                for(Accounts a:list){
                    Log.d("GETACCOUNT-SIGNUP",a.toString());
                    if (a.getName().equals(name.getText().toString())
                            && a.getPassword().equals(password.getText().toString())){
                        idscore=a.getAccountId();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                return 400;
            }

            return 200;
        }// end method doInBackground
    }

    public class Getscore extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==400){
                Log.d("GetScore-new","Failed");
            }
            else if(integer==200)
            {
                Log.d("GetScore-new","Successful");
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
                    scores.add(new Scores(jsonObject.getString("ScoreId"),
                            jsonObject.getString("Score1"),
                            jsonObject.getString("AccountId")));

                }

                for(Scores s:scores){
                    Log.d("GETSCORE-SIGNUP",s.toString());
                }


            }catch (Exception e){
                e.printStackTrace();
                return 400;
            }

            return 200;
        }
    }
}
