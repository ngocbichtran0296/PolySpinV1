package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Ngoc Bich on 7/16/2018.
 */

public class Rects implements GameObject{
    private int x,y;
    private int vx,vy;
    private int width,height;
    private Rect rect;
    private int color;
    private int delta;

    public Rects(int x, int y, int vx, int vy, int width, int height,int color,int delta){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        this.width=width;
        this.height=height;
        this.color=color;
        this.delta=delta;

        rect=new Rect(x-width/2,y-height/2,x+width/2,y+height/2);

      //  Log.d("l-t-r-b",""+rect.left+"="+rect.top+"="+rect.right+"="+rect.bottom);
    }
    @Override
    public void update() {

        x+=vx*delta;
        y+=vy*delta;

        rect.top+=vy*delta;
        rect.bottom+=vy*delta;
        rect.left+=vx*delta;
        rect.right+=vx*delta;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawRect(rect,paint);
    }

    public boolean isColliding(Rect other){
        if(Rect.intersects(rect,other)){
            return true;
        }
        return false;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
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

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
