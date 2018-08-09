package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ngoc Bich on 7/22/2018.
 */

public class Circle_Manager {
    private BitmapImage bitmapImage;
    private ArrayList<Circle> circles;

    public static int score;

    private int xTarget,yTarget;

    public Circle_Manager(BitmapImage bitmapImage,int xTarget,int yTarget) {
        this.bitmapImage = bitmapImage;

        circles = new ArrayList<>();

        score = 0;

        this.xTarget=xTarget;
        this.yTarget=yTarget;

      //  populateEnemy();
    }


    public void addCircle(float xPos, float yPos,int color) {
        float a,b;//he so cua phuong trinh duong thang
        color = Color.BLACK;
        float vx, vy;
        float delta=25;
        int size = 30;

        a=((yTarget-yPos)/(xTarget-xPos));
        b=(yPos-(a*xPos));

        vy=yPos;
        vx=((vy-b)/a);

        circles.add(new Circle(xPos, yPos, vx, vy, 2 * size, 2 * size, color, delta,a,b));
    }

    public void update2() {
        for (Circle circle : circles) {
            circle.update2();
        }

        for(int i=0;i<circles.size()-1;i++){
            if(circles.get(i).getRect().top <= -50 || circles.get(i).getRect().left <= 0
                    || circles.get(i).getRect().right >= Constants.SCREEN_WIDTH
                    || circles.get(i).getRect().bottom >= Constants.SCREEN_HEIGHT){
                circles.remove(i);
            }
        }
    }

    public void update() {
        for (Circle circle : circles) {
            circle.update();
        }

        for(int i=0;i<circles.size()-1;i++){
            if(circles.get(i).getRect().top <= -50 || circles.get(i).getRect().left <= 0
                    || circles.get(i).getRect().right >= Constants.SCREEN_WIDTH
                    || circles.get(i).getRect().bottom >= Constants.SCREEN_HEIGHT){
                circles.remove(i);
            }
        }
    }

    public void draw(Canvas canvas){
        for(Circle circle:circles){
            circle.draw(canvas);
        }
    }


    public ArrayList<Circle> getCircles() {
        return circles;
    }
}
