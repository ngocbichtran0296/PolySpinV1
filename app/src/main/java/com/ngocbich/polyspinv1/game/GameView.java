package com.ngocbich.polyspinv1.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.R;
import com.ngocbich.polyspinv1.SignUpActivity;
import com.ngocbich.polyspinv1.game_objects.Spinnie;
import com.ngocbich.polyspinv1.model.Scores;
import com.ngocbich.polyspinv1.state.GameStateManager;

/**
 * Created by Ngoc Bich on 7/3/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameStateManager gameStateManager;
    private GameThread gameThread;
    private BitmapImage bitmapImage;

    private volatile boolean running = false;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT=context;
        gameThread=new GameThread(getHolder(),this);

        bitmapImage=new BitmapImage(Constants.CURRENT_CONTEXT,Constants.blockSize);

        gameStateManager=new GameStateManager(bitmapImage);


       // spinnie=new Spinnie(new Rect(100,Constants.SCREEN_HEIGHT-280,180,Constants.SCREEN_HEIGHT-200),bitmapImage,1,0);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running=false;
        while (gameThread.isAlive()){
            try {
                gameThread.setRunning(running);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //update
    public void update(float dt){
        gameStateManager.update(dt);
    }

    //draw
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameStateManager.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameStateManager.recieveTouch(event);
        return true;
    }

    public void pause(){
        running=false;
        while (gameThread.isAlive()){
            try {
                gameThread.setRunning(running);
                gameThread.join();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume(){
        running=true;
      //  gameThread=new GameThread(getHolder(),this);
        gameThread.setRunning(running);
       // gameThread.start();
        if(gameThread==null){
            gameThread=new GameThread(getHolder(),this);
            gameThread.start();
        }
    }

    //initialize game
    public void init(){
        running=true;
        gameThread.setRunning(running);
        gameThread.start();
    }
}
