package com.ngocbich.polyspinv1.state;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.LoginActivity;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.model.Scores;

import java.util.Random;

/**
 * Created by Ngoc Bich on 7/4/2018.
 */

public class MenuState extends GameState {
    private Bitmap background;
    private Random random;
    private Rect r;
    private Paint paint;
    private Rect playButtonPosition;    // vi tri cua play button
    private Rect rankingButtonPosition;
    private Rect chalengeButtonPosition;
    private Rect settingButtonPosition;
    private Rect skinButtonPosition;

    private int buttonSize; //kich thuoc cua moi button (size x size)
    private Bitmap playButton, rankingButton, challengeButton, skinButton, settingButton;

    private Bitmap muteButton, soundButton, logoutButton;
    private Rect muteSoundPosition, logoutPosition;

    private boolean settingPress;
    private int turnSettingButton = 0;


    public MenuState(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        if(Game.mute==0){
            if(MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
        }
        else if(Game.mute==1){

            MainActivity.getPlayerMenu().start();
        }
        if(MainActivity.getPlayerPlay().isPlaying())
        MainActivity.getPlayerPlay().pause();

        if (MainActivity.getPlayerBoss().isPlaying())
        MainActivity.getPlayerBoss().pause();

        random = new Random();
        background = bitmapImage.getBackground()[0];

        r = new Rect();
        paint = new Paint();

        settingPress = false;

        playButton = bitmapImage.getPlay();
        rankingButton = bitmapImage.getRanking();
        challengeButton = bitmapImage.getChallenge();
        settingButton = bitmapImage.getSetting();
        skinButton = bitmapImage.getSkin();

        //set position cho cac button
        playButtonPosition = new Rect(Constants.SCREEN_WIDTH / 2 - playButton.getWidth() / 2,
                Constants.SCREEN_HEIGHT / 2 - playButton.getHeight() / 2,
                Constants.SCREEN_WIDTH / 2 + playButton.getWidth() / 2,
                Constants.SCREEN_HEIGHT / 2 + playButton.getHeight() / 2);

        rankingButtonPosition = new Rect(Constants.SCREEN_WIDTH / 9,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 3,
                Constants.SCREEN_WIDTH / 9 * 2,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 2);

        chalengeButtonPosition = new Rect(Constants.SCREEN_WIDTH / 9 * 4,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 3,
                Constants.SCREEN_WIDTH / 9 * 5,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 2);

        skinButtonPosition = new Rect(Constants.SCREEN_WIDTH / 9 * 5,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 3,
                Constants.SCREEN_WIDTH / 9 * 6,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 2);

        settingButtonPosition = new Rect(Constants.SCREEN_WIDTH / 9 * 7,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 3,
                Constants.SCREEN_WIDTH / 9 * 8,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 2);

        muteButton = bitmapImage.getMute();
        soundButton = bitmapImage.getSound();
        logoutButton = bitmapImage.getLogout();

        muteSoundPosition = new Rect(Constants.SCREEN_WIDTH / 9 * 7,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 4 - 50,
                Constants.SCREEN_WIDTH / 9 * 8,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 3 - 50);

        logoutPosition = new Rect(Constants.SCREEN_WIDTH / 9 * 7,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 5 - 100,
                Constants.SCREEN_WIDTH / 9 * 8,
                Constants.SCREEN_HEIGHT - Constants.blockSize * 4 - 100);


    }

    @Override
    public void update(float delta) {
        //an button
        if (turnSettingButton == 0) {
            settingPress = false;
        }
        //hien cac button
        else if (turnSettingButton == 1) {
            settingPress = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //ve background
        canvas.drawBitmap(background, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), new Paint());

        //ve title of the game
        Constants.drawTittle(canvas, r, paint, "POLYSPIN");

        //ve high score tittle
        Constants.drawText(canvas, r, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 3.5f, paint, "BEST");

        //ve score
        //text of score se lay diem cao nhat cua nguoi choi
        Constants.drawText(canvas, r, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 3.5f + 80, paint, ""+Constants.highScore);

        //ve Play button
        canvas.drawBitmap(playButton, null, playButtonPosition, new Paint());
        //ve Ranking button
        canvas.drawBitmap(rankingButton, null, rankingButtonPosition, new Paint());
        //Challenge button
        canvas.drawBitmap(challengeButton, null, chalengeButtonPosition, new Paint());

        //Skin button
       // canvas.drawBitmap(skinButton, null, skinButtonPosition, new Paint());

        //Setting button
        canvas.drawBitmap(settingButton, null, settingButtonPosition, new Paint());

        if (settingPress) {
            if (Game.mute == 0) {
                canvas.drawBitmap(muteButton, null, muteSoundPosition, new Paint());
                if(MainActivity.getPlayerMenu().isPlaying())
                MainActivity.getPlayerMenu().pause();

            } else if (Game.mute == 1) {
                MainActivity.getPlayerMenu().start();

                canvas.drawBitmap(soundButton, null, muteSoundPosition, new Paint());
            }
            canvas.drawBitmap(logoutButton, null, logoutPosition, new Paint());
        }
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //nhan play button
                if (playButtonPosition.contains((int) event.getX(), (int) event.getY())) {
                    if(MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    Game.state=2;
                    gsm.setState(gsm.PLAY);
                    Log.d("playButton","pressed");
                }

                //nhan setting button
                if (settingButtonPosition.contains((int) event.getX(), (int) event.getY())) {
                    Log.d("settingButton","pressed");
                    if (turnSettingButton == 0)
                        turnSettingButton = 1;//nhan lan thu nhat => hien ra button soung/mute va logout
                    else if (turnSettingButton == 1) turnSettingButton = 0;//an cac button
                }

                //nhan ranking button
                if (rankingButtonPosition.contains((int) event.getX(), (int) event.getY())) {
                    Log.d("rankingButton","pressed");
                    if(MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    gsm.setState(gsm.RANKING);
                }

                //nhan challenges button
                if (chalengeButtonPosition.contains((int) event.getX(), (int) event.getY())) {
                    if(MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    Log.d("challengeButton","pressed");
                    gsm.setState(gsm.CHALLENGE);
                }

                //nhan mute/sound button
                if (settingPress && muteSoundPosition.contains((int) event.getX(), (int) event.getY())) {
                    if (Game.mute == 0) Game.mute = 1;//bat am thanh
                    else if (Game.mute == 1) Game.mute = 0;//tat am thanh
                }

                //nhan logout button
                if(settingPress && logoutPosition.contains((int)event.getX(),(int)event.getY())){
                    if(MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    //chuyen den man hinh dang nhap
                    Constants.logoutPress=true;
                    SharedPreferences prefs = Constants.CURRENT_CONTEXT.getSharedPreferences("MyShare", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putBoolean("URLogin",false);
                    editor.commit();

                    resetChallenges();

                    Constants.accounts.clear();
                    Constants.scores.clear();

                    Intent intent=new Intent(Constants.CURRENT_CONTEXT,LoginActivity.class);
                    Constants.CURRENT_CONTEXT.startActivity(intent);

                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
        }
    }

    public void resetChallenges(){
        for(int i=0;i<Game.challenges.size()-1;i++){
            if(Game.challenges.get(i).getPass()==1) {
                Game.challenges.get(i).setPass(0);
                Game.challengeDAO.update(Game.challenges.get(i));
            }
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
