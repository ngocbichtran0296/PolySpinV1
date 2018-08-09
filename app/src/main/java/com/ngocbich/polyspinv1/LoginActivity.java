package com.ngocbich.polyspinv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;

public class LoginActivity extends AppCompatActivity {
    private Button loginNow;
    private EditText userName, password;
    private TextView createNewAccount;

    private  String UserName;
    private  String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginNow = findViewById(R.id.LoginNow);
        userName = findViewById(R.id.Username);
        password = findViewById((R.id.Password));
        createNewAccount=findViewById(R.id.CreateNewAccount);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Lỗi đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else {
                    Constants.share=getSharedPreferences("MyShare",MODE_PRIVATE);
                    SharedPreferences.Editor editor=Constants.share.edit();
                    //SharedPreferences sharedPreferences = getSharedPreferences("MyShare",MODE_PRIVATE);

                    //kiem tra thong tin
                    if(checkAccount(userName.getText().toString(),password.getText().toString())){
                        //luu cac du lieu
                        editor.putString("URName",userName.getText().toString());
                        editor.putString("URPass",password.getText().toString());
                        editor.putInt("URHighScore",Constants.highScore);
                        editor.putInt("URIdAccount",Constants.idAccount);


                        editor.putBoolean("URLogin",true);

                        editor.commit();

                        //chuyen sang GameActivity
                        Intent game=new Intent(getApplicationContext(),Game.class);
                        startActivity(game);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Thông tin đăng nhập sai",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singup=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(singup);
                finish();
            }
        });
    }

    public boolean checkAccount(String name,String pass){
        for(Accounts account:MainActivity.account){
            if(account.getName().equals(name) && account.getPassword().equals(pass)){
                String id=account.getAccountId();
                for(Scores score:MainActivity.score){
                    if(score.getIdAccount().equals(id)){
                        Constants.highScore= Integer.parseInt(score.getScore());
                        Constants.idAccount=Integer.parseInt(account.getAccountId());
                        Constants.idScore=Integer.parseInt(score.getId());
                        Constants.AccountName=account.getName();
                        Constants.AccountPass=account.getPassword();

                        Log.d("highScore",Constants.highScore+"");
                        Log.d("accountID",Constants.idAccount+"");
                    }
                }
                return true;
            }
        }
        return false;
    }
}
