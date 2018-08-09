package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

/**
 * Created by Ngoc Bich on 7/26/2018.
 */

public class Demon implements GameObject {
    private Bitmap image;
    private Point point;
    private Rect rect;
    private int width,height;
    private int deltaX;
    private int orien;

    public Demon(BitmapImage bitmapImage, Point point, int width, int height, int deltaX) {
        this.image = bitmapImage.getDemon();
        this.point = point;
        this.width=width;
        this.height=height;
        this.deltaX=deltaX;
        this.orien=1;

        rect=new Rect(point.x-width/2,point.y-height/2,point.x+width/2,point.y+height/2);
    }

    @Override
    public void update() {
        if(orien==1){
            point.x=point.x-deltaX;
            if(point.x<= Constants.SCREEN_WIDTH/9){
                orien=2;
                point.x=Constants.SCREEN_WIDTH/9;
            }
        }
        if(orien==2){
            point.x=point.x+deltaX;
            if(point.x>=Constants.SCREEN_WIDTH/9*8){
                orien=1;
                point.x=Constants.SCREEN_WIDTH/9*8;
            }
        }

        rect.left=point.x-width/2;
        rect.right=point.x+width/2;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,null,rect,new Paint());
       // canvas.drawRect(rect,new Paint());
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
