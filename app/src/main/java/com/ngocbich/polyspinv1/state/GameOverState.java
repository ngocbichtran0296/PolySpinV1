package com.ngocbich.polyspinv1.state;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.ngocbich.polyspinv1.PutData;
import com.ngocbich.polyspinv1.game_objects.Circle_Manager;
import com.ngocbich.polyspinv1.game_objects.Enemy_Manager;
import com.ngocbich.polyspinv1.model.Scores;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ngoc Bich on 7/17/2018.
 */

public class GameOverState extends GameState {
    private Bitmap replayButton;
    private Rect rect;
    private Bitmap background;

    public GameOverState(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        replayButton = bitmapImage.getReplay();
        background = bitmapImage.getBackground()[PlayState.indexBg];

        rect = new Rect(Constants.SCREEN_WIDTH / 2 - 80, Constants.SCREEN_HEIGHT / 6 * 4 - 80,
                Constants.SCREEN_WIDTH / 2 + 80, Constants.SCREEN_HEIGHT / 6 * 4 + 80);
    }

    @Override
    public void update(float delta) {

        if (Constants.highScore < Circle_Manager.score) {

            Constants.highScore = Circle_Manager.score;
            SharedPreferences prefs = Constants.CURRENT_CONTEXT.getSharedPreferences("MyShare", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("URHighScore",Constants.highScore);
            editor.commit();

            for (Scores s : Constants.scores) {
                if (s.getIdAccount().equals(String.valueOf(Constants.idAccount))) {
                    Constants.idScore = Integer.parseInt(s.getId());
                    new PutData(Constants.idScore + "", "idScore", Constants.idScore + "", "s", Circle_Manager.score + "", "idAccount", Constants.idAccount + "")
                            .execute("http://192.168.1.7:6660/api/Scores");
                    break;
                }
            }
        }

        Log.d("HIGHSCORE_GAMEOVER",Constants.highScore+"");
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, null,
                new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), new Paint());

        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#032421"));

        paint.setTextSize((int)((float) Constants.blockSize*0.75));//90
        canvas.drawText("BEST", Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*0.8),//100
                (int)((double)Constants.blockSize*3.3) + paint.descent() - paint.ascent(), paint);//400

        paint.setTextSize((int)((double)Constants.blockSize*0.58));//70
        canvas.drawText("" + Constants.highScore, Constants.SCREEN_WIDTH / 6 * 3 - (int)((double)Constants.blockSize*0.3),//30
                (int)((double)Constants.blockSize*4.4) + paint.descent() - paint.ascent(), paint);//530


        paint.setTextSize((int)((double)Constants.blockSize*0.58));
        canvas.drawText("" + Circle_Manager.score, Constants.SCREEN_WIDTH / 6 * 3 - (int)((double)Constants.blockSize*0.25),
                (int)((double)Constants.blockSize*2.08) + paint.descent() - paint.ascent(), paint);//250
        paint.setTextSize((int)((double)Constants.blockSize*0.8));//100
        canvas.drawText("GAME OVER", Constants.SCREEN_WIDTH / 2 - (int)((double)Constants.blockSize*2.08),//250
                (int)((double)Constants.blockSize*7.08) + paint.descent() - paint.ascent(), paint);//850
        canvas.drawBitmap(replayButton, null, rect, new Paint());
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {

                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (rect.contains((int) event.getX(), (int) event.getY())) {
                    gsm.setState(gsm.MENU);
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
