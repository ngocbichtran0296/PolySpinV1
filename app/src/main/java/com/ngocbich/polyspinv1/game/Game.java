package com.ngocbich.polyspinv1.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.ngocbich.polyspinv1.ChallengeDAO;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.DBHelper;
import com.ngocbich.polyspinv1.GetAccounts;
import com.ngocbich.polyspinv1.GetScores;
import com.ngocbich.polyspinv1.LoginActivity;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.PostData;
import com.ngocbich.polyspinv1.SignUpActivity;
import com.ngocbich.polyspinv1.game_objects.Challenge;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;

import java.util.ArrayList;
import java.util.List;

public class Game extends Activity {
    private GameView gameView;

    public static List<Challenge> challenges;

    public static ChallengeDAO challengeDAO;
    private DBHelper dbHelper;

    public static int mute = 1;
    public static int state=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView=new GameView(this);
        setContentView(gameView);

        if(SignUpActivity.signup) {
            String id = "";
            for (Accounts account : SignUpActivity.list) {
                if (account.getName().equals(SignUpActivity.NAME)
                        && account.getPassword().equals(SignUpActivity.PASS)) {
                    id = account.getAccountId();
                    MainActivity.editor.putInt("URIdAccount",Integer.parseInt(id));
                    MainActivity.editor.commit();
                    Constants.idAccount = Integer.parseInt(id);

                    Constants.highScore = 0;

                    //post score
                    new PostData("score1", 0 + "", "account", id + "").execute("http://192.168.1.7:6660/api/Scores");

                    break;
                }
            }
        }

        Constants.CURRENT_CONTEXT=this;
        Constants.logoutPress=false;

        new GetScores().execute("http://192.168.1.7:6660/api/Scores");
        new GetAccounts().execute("http://192.168.1.7:6660/api/Accounts");

        for(Scores s: MainActivity.score){
            if(Constants.idAccount == Integer.parseInt(s.getIdAccount())){
                Constants.highScore=Integer.parseInt(s.getScore());
            }
        }


        dbHelper=new DBHelper(this,"gamePolySpin",null,1);
        challengeDAO=ChallengeDAO.getInstence(this);

        Log.d("sqlite insert",insert()+"");
        Log.d("IDACCOUNT",Constants.idAccount+"");
        Log.d("HIGHSCORE",Constants.highScore+"");

      //  challenges=challengeDAO.query();
    }
    public long insert(){
        int count=0;
        List<Challenge> list=new ArrayList<>();
        list.add(new Challenge(1,"Do more 50 point!",50,0));
        list.add(new Challenge(2,"Survive 30 seconds!",30,0));
        list.add(new Challenge(3,"Small circle!",1,0));//so 1 la man small circle
        list.add(new Challenge(4,"Collect more 30 stars!",40,0));
        list.add(new Challenge(5,"Beat the demon!",0,0));//so 0 la man demon
        count+=challengeDAO.insert(new Challenge(1,"Do more 50 point!",50,0));
        count+=challengeDAO.insert(new Challenge(2,"Survive 30 seconds!",30,0));
        count+=challengeDAO.insert(new Challenge(3,"Small circle!",1,0));
        count+=challengeDAO.insert(new Challenge(4,"Collect more 40 stars!",40,0));
        count+=challengeDAO.insert(new Challenge(5,"Beat the demon!",0,0));
       return count;
    }


    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        MainActivity.getPlayerBoss().pause();
        MainActivity.getPlayerPlay().pause();
        MainActivity.getPlayerMenu().pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        if(Game.mute==1){
            if(state==1)MainActivity.getPlayerMenu().start();
            if(state==2)MainActivity.getPlayerPlay().start();
            if (state==3)MainActivity.getPlayerBoss().start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            gameView.pause();
            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        while (true){
            gameView.pause();
            break;
        }
        finish();
    }



   /* @Override
    protected void onRestart() {
        if(gameView!=null){
            //initialize game
            gameView.init();
        }
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    */
}
