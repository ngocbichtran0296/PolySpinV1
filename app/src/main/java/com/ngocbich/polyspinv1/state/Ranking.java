package com.ngocbich.polyspinv1.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.GetScores;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.game_objects.Enemy_Manager;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ngoc Bich on 7/20/2018.
 */

public class Ranking extends GameState {
    private Bitmap background;
    private Bitmap starscore;
    private Bitmap circle;
    private Rect rankbg, pos1, pos2, pos3, pos4, pos5;

    private Rect rect;

    private List<Scores> scores;

    private int width = Constants.SCREEN_WIDTH, height = Constants.SCREEN_HEIGHT / 20;
    private Rect circlePos, namePos, starPos;

    private int xRank, yRank, xName, yName, xScore, yScore;

    private int myPosition; //vi tri trong bang xep hang cua nguoi choi

    private int xR1, yR1, xN1, yN1, xS1, yS1;
    private int xR2, yR2, xN2, yN2, xS2, yS2;
    private int xR3, yR3, xN3, yN3, xS3, yS3;
    private int xR4, yR4, xN4, yN4, xS4, yS4;
    private int xR5, yR5, xN5, yN5, xS5, yS5;

    private int count;

    private boolean up, down;
    private Bitmap upButton, downButton;
    private Rect upPos, downPos;

    public Ranking(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);

        if(Game.mute==1){
            MainActivity.getPlayerMenu().start();
        }
        else {
            if(MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
        }

        sort();
        findPosition();
        background = bitmapImage.getBackground()[0];
        circle = bitmapImage.getRankcircle();
        starscore = bitmapImage.getStarscore();

        upButton = bitmapImage.getUp();
        downButton = bitmapImage.getDown();
        upPos = new Rect(Constants.SCREEN_WIDTH / 10, Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.17),
                Constants.SCREEN_WIDTH / 10 * 2, Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.17));
        downPos = new Rect(Constants.SCREEN_WIDTH / 10, Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.33),
                Constants.SCREEN_WIDTH / 10 * 2, Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.33));

        count = 0;
        up = false;
        down = false;

        xName = Constants.SCREEN_WIDTH / 2;
        yName = Constants.SCREEN_HEIGHT / 9 * 2;
        xRank = Constants.SCREEN_WIDTH / 6;
        yRank = Constants.SCREEN_HEIGHT / 9 * 2;
        xScore = Constants.SCREEN_WIDTH / 10 * 8;
        yScore = Constants.SCREEN_HEIGHT / 9 * 2;

        xR1 = Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*1.7);
        yR1 = ((Constants.SCREEN_HEIGHT / 20 * 5 + (int)((double)Constants.blockSize*0.08)) + (Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.08))) / 2;
        xN1 = Constants.SCREEN_WIDTH / 2;
        yN1 = ((Constants.SCREEN_HEIGHT / 20 * 5 + (int)((double)Constants.blockSize*0.08)) + (Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.08))) / 2;
        xS1 = Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.33);
        yS1 = ((Constants.SCREEN_HEIGHT / 20 * 5 + (int)((double)Constants.blockSize*0.08)) + (Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.08))) / 2;

        xR2 = Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*1.7);
        yR2 = ((Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.17)) + (Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.17))) / 2;
        xN2 = Constants.SCREEN_WIDTH / 2;
        yN2 = ((Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.17)) + (Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.17))) / 2;
        xS2 = Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.33);
        yS2 = ((Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.17)) + (Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.17))) / 2;

        xR3 = Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*1.7);
        yR3 = ((Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.25)) + (Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.25))) / 2;
        xN3 = Constants.SCREEN_WIDTH / 2;
        yN3 = ((Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.25)) + (Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.25))) / 2;
        xS3 = Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.33);
        yS3 = ((Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.25)) + (Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.25))) / 2;

        xR4 = Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*1.7);
        yR4 = ((Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.3)) + (Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.3))) / 2;
        xN4 = Constants.SCREEN_WIDTH / 2;
        yN4 = ((Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.3)) + (Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.3))) / 2;
        xS4 = Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.3);
        yS4 = ((Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.3)) + (Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.3))) / 2;

        xR5 = Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*1.6);
        yR5 = ((Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.41)) + (Constants.SCREEN_HEIGHT / 20 * 10 + (int)((double)Constants.blockSize*0.41))) / 2;
        xN5 = Constants.SCREEN_WIDTH / 2;
        yN5 = ((Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.41)) + (Constants.SCREEN_HEIGHT / 20 * 10 + (int)((double)Constants.blockSize*0.41))) / 2;
        xS5 = Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.3);
        yS5 = ((Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.41)) + (Constants.SCREEN_HEIGHT / 20 * 10 + (int)((double)Constants.blockSize*0.41))) / 2;


        namePos = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.5),
                Constants.SCREEN_HEIGHT / 9 * 2 - (int)((double)Constants.blockSize*0.41),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75),
                Constants.SCREEN_HEIGHT / 9 * 2 + (int)((double)Constants.blockSize*0.41));

        circlePos = new Rect(Constants.SCREEN_WIDTH / 6 - (int)((double)Constants.blockSize*0.8), Constants.SCREEN_HEIGHT / 9 * 2 - (int)((double)Constants.blockSize*0.8),
                Constants.SCREEN_WIDTH / 6 + (int)((double)Constants.blockSize*0.8), Constants.SCREEN_HEIGHT / 9 * 2 + (int)((double)Constants.blockSize*0.8));

        starPos = new Rect(Constants.SCREEN_WIDTH / 10 * 7 - (int)((double)Constants.blockSize*0.25), Constants.SCREEN_HEIGHT / 9 * 2 - (int)((double)Constants.blockSize*0.25),
                Constants.SCREEN_WIDTH / 10 * 7 + (int)((double)Constants.blockSize*0.25), Constants.SCREEN_HEIGHT / 9 * 2 + (int)((double)Constants.blockSize*0.25));


        //bg cua danh sach
        rankbg = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 9 * 2,
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*2.5), Constants.SCREEN_HEIGHT / 9 * 7);

        pos1 = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 20 * 5 + (int)((double)Constants.blockSize*0.08),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.08));

        pos2 = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 20 * 6 + (int)((double)Constants.blockSize*0.17),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.17));

        pos3 = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 20 * 7 + (int)((double)Constants.blockSize*0.25),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.25));

        pos4 = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 20 * 8 + (int)((double)Constants.blockSize*0.3),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.3));

        pos5 = new Rect(Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.3), Constants.SCREEN_HEIGHT / 20 * 9 + (int)((double)Constants.blockSize*0.41),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 10 + (int)((double)Constants.blockSize*0.41));

        rect = new Rect(Constants.SCREEN_WIDTH / 6 - (int)((double)Constants.blockSize*0.8), Constants.SCREEN_HEIGHT / 9 * 2 - (int)((double)Constants.blockSize*0.41),
                Constants.SCREEN_WIDTH / 2 + (int)((double)Constants.blockSize*3.75), Constants.SCREEN_HEIGHT / 20 * 10 + (int)((double)Constants.blockSize*0.41));
    }

    public void sort() {
       // Log.d("HIGHSCORE_RANKING",Constants.highScore+"");
        scores=Constants.scores;
        for(int i=0;i<scores.size()-1;i++) {
            if (Constants.idAccount == Integer.parseInt(scores.get(i).getIdAccount())) {
                scores.get(i).setScore(String.valueOf(Constants.highScore));
                break;
            }
        }
        Scores temp;
        for (int i = 0; i < scores.size(); i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (Integer.parseInt(scores.get(i).getScore()) < Integer.parseInt(scores.get(j).getScore())) {
                    temp = scores.get(j);
                    scores.remove(scores.get(j));

                    scores.add(i, temp);
                }
            }

        }
        Log.d("sortScore", scores.toString());
    }

    public void findPosition() {
        Log.d("idAccount", Constants.idAccount + "");
        for (int i = 0; i < scores.size(); i++) {
            if (Constants.idAccount == Integer.parseInt(scores.get(i).getIdAccount())) {
                myPosition = i + 1;
                Log.d("myposition", myPosition + "");
            }
        }
    }

    @Override
    public void init(BitmapImage bitmapImage) {
    }

    @Override
    public void update(float delta) {
        if (up) {
            count = count - 5;
            if (count < 0) count = 0;
            up=false;
        }

        if (down) {
            if (count + 5 <= scores.size()) {
                count = count + 5;
            }
            down=false;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        canvas.drawBitmap(background, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), new Paint());

        Constants.drawText(canvas, new Rect(), Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 9, new Paint(), "RANKING");

        paint.setColor(Color.parseColor("#F0F0F0"));
        // canvas.drawRect(rankbg,paint);

        paint.setColor(Color.parseColor("#FFDA7F"));
        canvas.drawRect(namePos, paint);

        paint.setColor(Color.parseColor("#40D3B0"));
        canvas.drawRect(pos1, paint);
        canvas.drawRect(pos2, paint);
        canvas.drawRect(pos3, paint);
        canvas.drawRect(pos4, paint);
        canvas.drawRect(pos5, paint);

        canvas.drawBitmap(circle, null, circlePos, new Paint());

        canvas.drawBitmap(starscore, null, starPos, new Paint());


        //thu hang cua nguoi choi
        paint.setColor(Color.BLACK);
        paint.setTextSize((int)((double)Constants.blockSize*0.41));
        Constants.drawText2(canvas, new Rect(), xName, yName, paint, Constants.AccountName);
        paint.setTextSize((int)((double)Constants.blockSize*0.58));
        Constants.drawText2(canvas, new Rect(), xRank, yRank, paint, myPosition + "");
        paint.setTextSize((int)((double)Constants.blockSize*0.41));
        Constants.drawText2(canvas, new Rect(), xScore, yScore, paint, Constants.highScore + "");

        //danh sach bang xep hang
        paint.setColor(Color.BLACK);
        paint.setTextSize((int)((double)Constants.blockSize*0.3));
        if (0 + count < scores.size()) {
            drawPos1(canvas, paint, "" + (1 + count), getName(scores.get(0 + count)), scores.get(0 + count).getScore());
        }
        if (1 + count < scores.size()) {
            drawPos2(canvas, paint, "" + (2 + count), getName(scores.get(1 + count)), scores.get(1 + count).getScore());
        }
        if (2 + count < scores.size()) {
            drawPos3(canvas, paint, "" + (3 + count), getName(scores.get(2 + count)), scores.get(2 + count).getScore());
        }
        if (3 + count < scores.size()) {
            drawPos4(canvas, paint, "" + (4 + count), getName(scores.get(3 + count)), scores.get(3 + count).getScore());
        }
        if (4 + count < scores.size()) {
            drawPos5(canvas, paint, "" + (5 + count), getName(scores.get(4 + count)), scores.get(4 + count).getScore());
        }
        //drawPos5(canvas,paint,""+(5+count),getName(scores.get(4+count)),scores.get(4+count).getScore());

        canvas.drawBitmap(upButton, null, upPos, new Paint());
        canvas.drawBitmap(downButton, null, downPos, new Paint());

    }

    public String getName(Scores s) {
        for (Accounts accounts : Constants.accounts) {
            if (accounts.getAccountId().equals(s.getIdAccount())) {
                return accounts.getName();
            }
        }
        return "";
    }

    public void drawPos1(Canvas canvas, Paint paint, String pos, String name, String score) {
        Constants.drawText2(canvas, new Rect(), xR1, yR1, paint, pos);
        Constants.drawText2(canvas, new Rect(), xN1, yN1, paint, name);
        Constants.drawText3(canvas, new Rect(), xS1, yS1, paint, score);
    }

    public void drawPos2(Canvas canvas, Paint paint, String pos, String name, String score) {
        Constants.drawText2(canvas, new Rect(), xR2, yR2, paint, pos);
        Constants.drawText2(canvas, new Rect(), xN2, yN2, paint, name);
        Constants.drawText3(canvas, new Rect(), xS2, yS2, paint, score);
    }

    public void drawPos3(Canvas canvas, Paint paint, String pos, String name, String score) {
        Constants.drawText2(canvas, new Rect(), xR3, yR3, paint, pos);
        Constants.drawText2(canvas, new Rect(), xN3, yN3, paint, name);
        Constants.drawText3(canvas, new Rect(), xS3, yS3, paint, score);
    }

    public void drawPos4(Canvas canvas, Paint paint, String pos, String name, String score) {
        Constants.drawText2(canvas, new Rect(), xR4, yR4, paint, pos);
        Constants.drawText2(canvas, new Rect(), xN4, yN4, paint, name);
        Constants.drawText3(canvas, new Rect(), xS4, yS4, paint, score);
    }

    public void drawPos5(Canvas canvas, Paint paint, String pos, String name, String score) {
        Constants.drawText2(canvas, new Rect(), xR5, yR5, paint, pos);
        Constants.drawText2(canvas, new Rect(), xN5, yN5, paint, name);
        Constants.drawText3(canvas, new Rect(), xS5, yS5, paint, score);
    }


    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {

                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (!rect.contains((int) event.getX(), (int) event.getY())) {
                    if(MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    gsm.setState(gsm.MENU);
                }

                if (upPos.contains((int) event.getX(), (int) event.getY())) {
                    up = true;
                    down = false;
                }
                if (downPos.contains((int) event.getX(), (int) event.getY())) {
                    up = false;
                    down = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                break;
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
