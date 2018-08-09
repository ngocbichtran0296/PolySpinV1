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
 * Created by Ngoc Bich on 7/17/2018.
 */

public class StarItem_Manager {
    private ArrayList<StarItem> starItems;
    private BitmapImage bitmapImage;

    private Random random;

    private int xRandom;
    private float count;


    public StarItem_Manager(BitmapImage bitmapImage) {
        this.bitmapImage = bitmapImage;

        starItems = new ArrayList<>();

        random = new Random();
        count = 1;
        xRandom = 0;

        populateItem();
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
        return x1;
    }

    public void populateItem() {
        int x, y;
        int vx, vy;
        int delta;
        int size = 30;

        int distanceX = Constants.SCREEN_WIDTH / 8;

        y = -100;

        while (y >= -Constants.SCREEN_HEIGHT - 5000) {
            x = random();

            vx = 0;
            vy = 1;

            delta = 20;

            starItems.add(new StarItem(x*distanceX, y, vx, vy,
                    size, size, delta,bitmapImage));

            y = y - 500;
        }
    }

    public void update(){
        for(StarItem item:starItems){
            item.update();
        }

        if(starItems.get(0).getRect().top >=Constants.SCREEN_HEIGHT){
            starItems.add(new StarItem(random()*Constants.SCREEN_WIDTH/8,starItems.get(starItems.size()-1).getRect().top-500,
                    0,(int)(1*count),30,30,20,bitmapImage));

            starItems.remove(0);
        }

       /* if (Circle_Manager.score >= 20 * count) {
            count++;
            for (StarItem item:starItems) {
                item.setVy((int)(item.getVy() + count));
            }
        }*/

    }

    public void draw(Canvas canvas){
        for(StarItem item:starItems){
            item.draw(canvas);
        }
        /*
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));
        canvas.drawBitmap(bitmapImage.getStar(),null,new Rect(Constants.SCREEN_WIDTH/8*6+50,100,Constants.SCREEN_WIDTH/8*6 +100,150),paint);
        canvas.drawText("" + Constants.star, Constants.SCREEN_WIDTH/8*7, 50 + paint.descent() - paint.ascent(), paint);
        */
    }

    public boolean isPlayerColliding(Rect other){
        for(StarItem item:starItems){
            if(item.isColliding(other)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<StarItem> getStarItems() {
        return starItems;
    }
}
