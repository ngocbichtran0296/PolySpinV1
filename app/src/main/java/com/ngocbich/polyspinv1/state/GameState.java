package com.ngocbich.polyspinv1.state;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;

/**
 * Created by Ngoc Bich on 6/26/2018.
 */

public abstract class GameState {
    protected GameStateManager gsm;
    private BitmapImage bitmapImage;
    long c;

    public GameState(GameStateManager gsm, BitmapImage bitmapImage){
        this.gsm=gsm;

        this.bitmapImage=bitmapImage;
        //stop all sound

      //  Log.d("do dai",bitmapImage.getBackground().length +"");
        init(bitmapImage);
    }

    public GameState(GameStateManager gsm, long c){
        this.gsm=gsm;
        this.c=c;
    }

    public abstract void init(BitmapImage bitmapImage);
    public abstract void update(float delta);
    public abstract void draw(Canvas canvas);
    public abstract void recieveTouch(MotionEvent event);
    public abstract void handleInput();
    public abstract void dispose();


}//end class
