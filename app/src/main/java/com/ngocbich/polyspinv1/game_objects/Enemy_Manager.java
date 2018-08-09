package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ngoc Bich on 7/16/2018.
 */

public class Enemy_Manager {
    private ArrayList<Rects> rects;
    private ArrayList<Circle> circles;
    private BitmapImage bitmapImage;

    private Random random;

    private int xRandom;
    private float count;
    public static int score;

    public Enemy_Manager(BitmapImage bitmapImage) {
        this.bitmapImage = bitmapImage;

        rects = new ArrayList<>();
        circles = new ArrayList<>();

        random = new Random();
        xRandom = 0;
        score = 0;
        count = 1;

        populateEnemy();
    }

    public void populateEnemy() {
        int x, y, color;
        color = Color.WHITE;
        int vx, vy;
        int delta;
        int size = 30;
        int distanceX = Constants.SCREEN_WIDTH / 8;

        y = -100;

        while (y >= -Constants.SCREEN_HEIGHT - 2000) {
            x = random();

            vx = 0;
            vy = 1;

            delta = 1;

            rects.add(new Rects(x*distanceX, y, vx, vy,
                    (random.nextInt(2 - 1 + 1) + 1) * size,
                    (random.nextInt(2 - 1 + 1) + 1) * size, color, delta));

            y = y - 100;
        }
    }

    public int random() {
        int x1;
        while (true) {
            x1 = random.nextInt(7 - 1 + 1) + 1;
            if (x1 != xRandom && x1 != 0) {
                break;
            }
        }
        xRandom = x1;
        // Log.d("position x:", x1 + "");
        return x1;
    }

    public void update() {
        for (Rects rect : rects) {
            rect.update();
        }

        if (score >= 50 * count) {
            count+=0.05f;
            for (Rects rect : rects) {
                rect.setVy((int)(rect.getVy() * count));
            }
        }
        for(int i=0;i<rects.size();i++) {
            if (rects.get(i).getRect().top >= Constants.SCREEN_HEIGHT
                    || rects.get(i).getRect().left <= 0
                    || rects.get(i).getRect().right >= Constants.SCREEN_WIDTH) {
                rects.add(new Rects(random() * Constants.SCREEN_WIDTH / 8, rects.get(rects.size() - 1).getRect().top - 100,
                        0, (int)(1 * count), (random.nextInt(2 - 1 + 1) + 1) * 30, (random.nextInt(2 - 1 + 1) + 1) * 30,
                        Color.WHITE, (int)(25*count)));

                rects.remove(i);
            }
        }

    }

    public void draw(Canvas canvas){
        for(Rects rect:rects){
            rect.draw(canvas);
        }

        /*Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);*/
    }

    public boolean isPlayerColliding(Rect other){
        for(Rects rect:rects){
            if(rect.isColliding(other)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Rects> getRects() {
        return rects;
    }

    public void setRects(ArrayList<Rects> rects) {
        this.rects = rects;
    }
}
