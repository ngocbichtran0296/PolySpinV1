package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ngocbich.polyspinv1.BitmapImage;

/**
 * Created by Ngoc Bich on 7/17/2018.
 */

public class StarItem implements GameObject {
    private Rect rect;
    private Bitmap image;

    private int x,y;
    private int vx,vy;
    private int width,height;
    private int delta;

    public StarItem(int x, int y, int vx, int vy, int width, int height, int delta,BitmapImage bitmapImage) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
        this.delta = delta;

        this.image=bitmapImage.getStar();
        rect=new Rect(x-width/2,y-height/2,x+width/2,y+height/2);
    }

    //colliding
    public boolean isColliding(Rect other){
        if(Rect.intersects(rect,other)){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        x+=vx*delta;
        y+=vy*delta;

        rect.top+=vy*delta;
        rect.bottom+=vy*delta;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,null,rect,new Paint());
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
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

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }
}
