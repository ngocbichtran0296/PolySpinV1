package com.ngocbich.polyspinv1.state;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;

/**
 * Created by Ngoc Bich on 6/27/2018.
 */

public class GameStateManager {
    private GameState gameState;// luu lai state hien tai

    public final int SPLASH=0;
    public final int MENU=1;
    public final int PLAY=2;
    public final int GAMEOVER=3;
    public final int PAUSE=4;
    public final int CONTROL=5;
    public final int RANKING=6;
    public final int CHALLENGE=7;
    public final int SKIN=8;

    public final int CHALL1=11;
    public final int CHALL2=12;
    public final int CHALL3=13;
    public final int CHALL4=14;
    public final int CHALL5=15;

    public final int COMPLETED=10;

    private BitmapImage bitmapImage;

    public GameStateManager(BitmapImage bitmapImage){
        this.bitmapImage=bitmapImage;
        setState(MENU);

    }

    public void recieveTouch(MotionEvent event){
        gameState.recieveTouch(event);
    }

    public void setState(int state) {
        if(gameState!=null)
            gameState.dispose();
        if(state==MENU){
            gameState=new MenuState(this, bitmapImage);
        }
        if (state==PLAY){
            gameState=new PlayState(this,bitmapImage);
        }
        if(state==GAMEOVER){
            gameState=new GameOverState(this,bitmapImage);
        }
        if (state==RANKING){
            gameState=new Ranking(this,bitmapImage);
        }
        if(state==CHALLENGE){
            gameState=new ChallengeState(this,bitmapImage);
        }

        if(state==CHALL1){
            gameState=new Challenge1(this,bitmapImage);
        }
        if(state==CHALL2){
            gameState=new Challenge2(this,bitmapImage);
        }
        if(state==CHALL3){
            gameState=new Challenge3(this,bitmapImage);
        }
        if(state==CHALL4){
            gameState=new Challenge4(this,bitmapImage);
        }
        if(state==CHALL5){
            gameState=new Challenge5(this,bitmapImage);
        }

        if(state==COMPLETED){
            gameState=new CompletedState(this,bitmapImage);
        }
    }

    public void update(float dt){
        gameState.update(dt);
    }

    public void draw(Canvas canvas){
        gameState.draw(canvas);
    }

    public void GameOver(long c){

    }
}// end class GameStateManager
