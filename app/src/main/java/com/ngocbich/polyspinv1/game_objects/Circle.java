package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Ngoc Bich on 7/16/2018.
 */

public class Circle implements GameObject {
    private float x;
    private float y;
    private float vx,vy;
    private int width,height;
    private Rect rect;

    private int color;
    private float delta;
    private float a,b;

    private boolean collide=false;

    private int posColliding=0;

    public Circle(float x, float y, float vx, float vy, int width, int height, int color, float delta,float a,float b) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
        this.color=color;
        this.delta=delta;
        this.a=a;this.b=b;


       rect=new Rect((int)(x-width/2),(int)(y-height/2),(int)(x+width/2),(int)(y-height/2));
    }

    public void update2(){
        if(collide==false) {
            y += delta;
        }
        else {
            if(posColliding==1){
                y-=(delta);
                x = (y - b) / a;
            }else if(posColliding==2){
               /* x+=(delta);
                y=a*x+b;*/
                y-=(delta);
                x = (y - b) / a;
            }
            else if(posColliding==3){
                y+=(delta);
                x = (y - b) / a;
            }
            else if(posColliding==4){
                /*x+=(delta);
                y=a*x+b;*/
                y+=(delta);
                x = (y - b) / a;
            }

        }

        rect.top=(int)(y-height/2);
        rect.left=(int)(x-width/2);
        rect.right=(int)(x+width/2);
        rect.bottom=(int)(y+height/2);

    }

    @Override
    public void update() {
        if(collide==false) {
            y += delta;
            x = (y - b) / a;
        }
        else {
            if(posColliding==1){
                y-=(delta);
                x = (y - b) / a;
            }else if(posColliding==2){
                x+=(delta);
                y=a*x+b;
            }
            else if(posColliding==3){
                y+=(delta);
                x = (y - b) / a;
            }
            else if(posColliding==4){
                x+=(delta);
                y=a*x+b;
            }
        }

        rect.top=(int)(y-height/2);
        rect.left=(int)(x-width/2);
        rect.right=(int)(x+width/2);
        rect.bottom=(int)(y+height/2);


    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawOval(x-width/2,y-height/2,x+width/2,y+height/2,paint);
    }

    public boolean isColliding(Rect other){
        if(Rect.intersects(rect,other)){
            return true;
        }
        return false;
    }

    public int getPosColliding() {
        return posColliding;
    }

    public void setPosColliding(int posColliding) {
        this.posColliding = posColliding;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public boolean isCollide() {
        return collide;
    }

}
