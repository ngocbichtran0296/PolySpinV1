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
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.game_objects.Challenge;

import java.util.List;

/**
 * Created by Ngoc Bich on 7/24/2018.
 */

public class ChallengeState extends GameState {
    private Bitmap bg,play;
//    public static List<Challenge> challenges;
    private Rect pos1,pos2,pos3,pos4,pos5;

    private String colorBg="#B8D7CF";
    private String colorPress="#dbffff";
    private String colorPass="#fdf2df";

    public static boolean failed=false;
    public static int index=-1;
    private int posPress=0;
    private boolean press=false;

    public static int indexBg;

    private Rect textPos,button,exit;
    public ChallengeState(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);

    }

    @Override
    public void init(BitmapImage bitmapImage) {
        if(Game.mute==1)MainActivity.getPlayerMenu().start();
        else {
            if(MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
        }

        bg=bitmapImage.getBackground()[0];
        play=bitmapImage.getPlay();
        Game.challenges=Game.challengeDAO.query();

        pos1=new Rect(Constants.SCREEN_WIDTH/3*0+50,
                Constants.SCREEN_HEIGHT/9*2,
                Constants.SCREEN_WIDTH/3-25,
                Constants.SCREEN_HEIGHT/9*3);

        pos2=new Rect(Constants.SCREEN_WIDTH/3+25,
                Constants.SCREEN_HEIGHT/9*2,
                Constants.SCREEN_WIDTH/3*2-25,
                Constants.SCREEN_HEIGHT/9*3);

        pos3=new Rect(Constants.SCREEN_WIDTH/3*2+25,
                Constants.SCREEN_HEIGHT/9*2,
                Constants.SCREEN_WIDTH/3*3-50,
                Constants.SCREEN_HEIGHT/9*3);

        pos4=new Rect(Constants.SCREEN_WIDTH/3*0+50,
                Constants.SCREEN_HEIGHT/9*3+20,
                Constants.SCREEN_WIDTH/3-25,
                Constants.SCREEN_HEIGHT/9*4+20);

        pos5=new Rect(Constants.SCREEN_WIDTH/3+25,
                Constants.SCREEN_HEIGHT/9*3+20,
                Constants.SCREEN_WIDTH/3*2-25,
                Constants.SCREEN_HEIGHT/9*4+20);


        button=new Rect(Constants.SCREEN_WIDTH/2 -100,
                Constants.SCREEN_HEIGHT/9*6-100,
                Constants.SCREEN_WIDTH/2+100,
                Constants.SCREEN_HEIGHT/9*6+100);

        exit=new Rect(0,Constants.SCREEN_HEIGHT/9*7,
                Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
    }

    @Override
    public void update(float delta) {

        if(index==0){
            if (MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
            gsm.setState(gsm.CHALL1);
        }
        else if(index==1){
            if (MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
            gsm.setState(gsm.CHALL2);
        }
        else if(index==2){
            if (MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
            Log.d("Challenge",""+index);
            gsm.setState(gsm.CHALL3);
        }
        else if(index==3){
            if (MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
            gsm.setState(gsm.CHALL4);
        }
        else if(index==4){
            if (MainActivity.getPlayerMenu().isPlaying())
            MainActivity.getPlayerMenu().pause();
            Game.state=3;
            gsm.setState(gsm.CHALL5);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        Paint text=new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(70);


        canvas.drawBitmap(bg, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), new Paint());

        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        Constants.drawText2(canvas, new Rect(), Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 9, paint, "CHALLENGES");

        //da pass
        if(Game.challenges.get(0).getPass()==1){
            paint.setColor(Color.parseColor(colorPass));
        }else paint.setColor(Color.parseColor(colorBg));//mau nen
        canvas.drawRect(pos1,paint);

        if(Game.challenges.get(1).getPass()==1){
            paint.setColor(Color.parseColor(colorPass));
        }else paint.setColor(Color.parseColor(colorBg));//mau nen
        canvas.drawRect(pos2,paint);


        if(Game.challenges.get(2).getPass()==1){
            paint.setColor(Color.parseColor(colorPass));
        }else paint.setColor(Color.parseColor(colorBg));//mau nen
        canvas.drawRect(pos3,paint);


        if(Game.challenges.get(3).getPass()==1){
            paint.setColor(Color.parseColor(colorPass));
        }else paint.setColor(Color.parseColor(colorBg));//mau nen
        canvas.drawRect(pos4,paint);


        if(Game.challenges.get(4).getPass()==1){
            paint.setColor(Color.parseColor(colorPass));
        }else paint.setColor(Color.parseColor(colorBg));//mau nen
        canvas.drawRect(pos5,paint);

        if(press){
            paint.setColor(Color.parseColor(colorPress));
            if(posPress==1){
                canvas.drawRect(pos1,paint);

            }
            else if(posPress==2){
                canvas.drawRect(pos2,paint);

            }
            else if(posPress==3){
                canvas.drawRect(pos3,paint);

            }
            else if(posPress==4){
                canvas.drawRect(pos4,paint);

            }
            else if (posPress==5){
                canvas.drawRect(pos5,paint);

            }
            paint.setColor(Color.WHITE);
            paint.setTextSize((int)(Constants.blockSize*0.8));
            Constants.drawText2(canvas,new Rect(),
                    Constants.SCREEN_WIDTH/2,
                    Constants.SCREEN_HEIGHT/9*5,
                    paint,Game.challenges.get(posPress-1).getDescription());

            canvas.drawBitmap(play,null,button,paint);
        }
        else {
            paint.setColor(Color.WHITE);
            paint.setTextSize((int)(Constants.blockSize*0.8));
            Constants.drawText2(canvas,new Rect(),
                    Constants.SCREEN_WIDTH/2,
                    Constants.SCREEN_HEIGHT/9*5,
                    paint,"Choose a challenge!");
        }

        Constants.drawText2(canvas,new Rect(),
                ((Constants.SCREEN_WIDTH/3*0+50)+(Constants.SCREEN_WIDTH/3-25))/2,
                ((Constants.SCREEN_HEIGHT/9*2)+(Constants.SCREEN_HEIGHT/9*3))/2,text,"1");

        Constants.drawText2(canvas,new Rect(),
                ((Constants.SCREEN_WIDTH/3+25)+(Constants.SCREEN_WIDTH/3*2-25))/2,
                ((Constants.SCREEN_HEIGHT/9*2)+(Constants.SCREEN_HEIGHT/9*3))/2,text,"2");

        Constants.drawText2(canvas,new Rect(),
                ((Constants.SCREEN_WIDTH/3*2+25)+(Constants.SCREEN_WIDTH/3*3-50))/2,
                ((Constants.SCREEN_HEIGHT/9*2)+(Constants.SCREEN_HEIGHT/9*3))/2,text,"3");

        Constants.drawText2(canvas,new Rect(),
                ((Constants.SCREEN_WIDTH/3*0+50)+(Constants.SCREEN_WIDTH/3-25))/2,
                ((Constants.SCREEN_HEIGHT/9*3+20)+(Constants.SCREEN_HEIGHT/9*4+20))/2,text,"4");

        Constants.drawText2(canvas,new Rect(),
                ((Constants.SCREEN_WIDTH/3+25)+(Constants.SCREEN_WIDTH/3*2-25))/2,
                ((Constants.SCREEN_HEIGHT/9*3+20)+(Constants.SCREEN_HEIGHT/9*4+20))/2,text,"5");

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {

                break;
            }
            case MotionEvent.ACTION_DOWN: {
               if(pos1.contains((int)event.getX(),(int)event.getY())){
                   posPress=1;
                   press=true;
               }
               if(pos2.contains((int)event.getX(),(int)event.getY())){
                   posPress=2;
                   press=true;
               }
                if(pos3.contains((int)event.getX(),(int)event.getY())){
                    posPress=3;
                    press=true;
                }
                if(pos4.contains((int)event.getX(),(int)event.getY())){
                    posPress=4;
                    press=true;
                }
                if(pos5.contains((int)event.getX(),(int)event.getY())){
                    posPress=5;
                    press=true;
                }

                if (exit.contains((int)event.getX(),(int)event.getY())){
                    if (MainActivity.getPlayerMenu().isPlaying())
                    MainActivity.getPlayerMenu().pause();
                    Game.state=1;
                    gsm.setState(gsm.MENU);
                }

                if(button.contains((int)event.getX(),(int)event.getY())){
                    index=posPress-1;
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
