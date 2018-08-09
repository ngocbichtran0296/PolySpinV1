package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

import java.util.Random;

/**
 * Created by Ngoc Bich on 7/4/2018.
 */

public class Spinnie implements GameObject {
    private Rect rect;
    private Bitmap image;
    private int orientation;    // 1: trai  2: phai
    private int index;

    public Spinnie(Rect rect, BitmapImage bitmapImage, int orien, int index) {
        this.rect = rect;
        this.orientation = orien;
        this.index = index;
        this.image = bitmapImage.getSpinnies()[index];
    }

    public void update(int orientation, int x) {
        if (orientation == 1) {// qua tai
            this.orientation = 1;

            rect.left -= x;
            rect.right -= x;
        } else if (orientation == 2) {// qua phai
            this.orientation = 2;

            rect.left += x;
            rect.right += x;
        }
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public void update() {
        update(orientation, 3);
        if (rect.right > Constants.SCREEN_WIDTH/2+50) {
            orientation = 1;
        } else if (rect.left < Constants.SCREEN_WIDTH/2-50) {
            orientation = 2;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null, rect, new Paint());
    }
}
