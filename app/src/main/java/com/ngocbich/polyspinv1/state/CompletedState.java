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

/**
 * Created by Ngoc Bich on 7/25/2018.
 */

public class CompletedState extends GameState {
    private Bitmap replayButton;
    private Rect rect;
    private Bitmap background;

    private String message;
    private Paint paint;

    public CompletedState(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        background=bitmapImage.getBackground()[ChallengeState.indexBg];
        rect=new Rect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        message="";

        paint=new Paint();

        paint.setColor(Color.parseColor("#1c4c51"));
    }

    @Override
    public void update(float delta) {

        if(ChallengeState.failed==true){
            message="FAILED";
            paint.setTextSize(100);
        }
        else {
            ChallengeState.failed=false;
            paint.setTextSize(50);
            message="CHALLENGE COMPLETED!";
            Challenge c=Game.challenges.get(ChallengeState.index);
            c.setPass(1);
            Log.d("update challenges",Game.challengeDAO.update(c)+"");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background,null,rect,new Paint());
        Constants.drawText2(canvas,new Rect(),Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,paint,message);
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {

                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if(rect.contains((int)event.getX(),(int)event.getY())){
                    ChallengeState.index=-1;
                    gsm.setState(gsm.CHALLENGE);
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
