package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ngocbich.polyspinv1.BitmapImage;

/**
 * Created by Ngoc Bich on 7/4/2018.
 */

public class Enemy implements GameObject {

    private Rect rect;
    private int size;         //kich thuoc
    private int deltaY;
    private int xStart,yStart;       //vi tri x, y ban dau
    private int index;

    private int degress;

    private Bitmap image;

    //size co hai kich thuoc lon va nho
    //deltaY: toc do di chuyen <=> do di chuyen xuong cua enemy
    //xStart: vi tri toa do ban dau (va khong thay doi trong qua trinh di chuyen xuong)
    //yStart: vi tri toa do y cua ennemy ban dau, sau do tang len de di chuyen xuong
    //bitmapImage: doi tuong chua tat ca cac hinh anh gom ds cac nhan vat player, enemy, spinnie
    //index: chi so de lay hinh anh luu trong doi tuong bitmapImage
    public Enemy(int size, int deltaY,int xStart,int yStart,int index,BitmapImage bitmapImage) {
        this.size=size;
        this.deltaY=deltaY;
        this.xStart=xStart;
        this.yStart=yStart;
        this.index=index;
     //   image=bitmapImage.getEnemies()[index];

        rect=new Rect(xStart-size/2,yStart-size/2,xStart+size/2,yStart+size/2);

        degress=0;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void incrementY() {
        rect.top += deltaY;
        rect.bottom += deltaY;
        yStart+=deltaY;

        degress+=10;
        if(degress>=360){
            degress=0;
        }
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    public void incrementY(int speed){
        deltaY=deltaY*speed;
        rect.top +=deltaY;
        rect.bottom+=deltaY;

    }

    //xet va cham nhan vat player voi enemy
    public boolean collide(Player player) {
        return Rect.intersects(rect, player.getRect());
    }

    // xet va cham Spinnie voi enemy
    public boolean collide(Spinnie spinnie) {
        return Rect.intersects(rect, spinnie.getRect());
    }

    @Override
    public void update() {
        incrementY();
    }

    @Override
    public void draw(Canvas canvas) {
       // canvas.save();
      //  canvas.rotate(degress,xStart,yStart);
        canvas.drawBitmap(image, null, rect, new Paint());
      //  canvas.restore();
    }
}
