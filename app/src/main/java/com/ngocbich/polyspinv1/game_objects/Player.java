package com.ngocbich.polyspinv1.game_objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

/**
 * Created by Ngoc Bich on 7/3/2018.
 */

public class Player implements GameObject {
    private Rect rect;// hinh chua player
    private Bitmap image;
    private int index;
    private RectF circle;

    private int x,y;


    public Player(Rect rect, BitmapImage bitmapImage, int index,int x,int y) {
        this.rect = rect;
        this.index = index;
        image = bitmapImage.getPlayer()[index];
        this.x=x;
        this.y=y;

        circle=new RectF((float) rect.left,(float)rect.top,(float)rect.right,(float)rect.bottom);
    }

    public Rect getRect() {
        return this.rect;

    }

    public RectF getCircle() {
        return circle;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, null, rect, new Paint());
    }

    //update tam cua player (diem trong tam cua hinh chua player)
    public void update(Point point) {
        float oldLeft = rect.left;

        int width = rect.width() / 2;
        int height = rect.height() / 2;

        if (point.y > (Constants.SCREEN_HEIGHT / 6) * 4 + 50) {
            point.set(point.x, (Constants.SCREEN_HEIGHT / 6) * 4 + 50);
        }
        if (point.x > (Constants.SCREEN_WIDTH - 40)) {
            point.set((Constants.SCREEN_WIDTH - 40), point.y);
        }
        if (point.x < 40) {
            point.set(40, point.y);
        }
        if(point.y<40){
            point.set(point.x,40);
        }

        //set vi tri cua player theo diem
        rect.set(point.x - width, point.y - height, point.x + width, point.y + height);
        x=point.x;
        y=point.y;

        circle.set((float) (point.x - width), (float)( point.y - height), (float) (point.x + width), (float)( point.y + height));

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
