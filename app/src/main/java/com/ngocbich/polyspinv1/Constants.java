package com.ngocbich.polyspinv1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.ngocbich.polyspinv1.model.Accounts;
import com.ngocbich.polyspinv1.model.Scores;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ngoc Bich on 7/1/2018.
 */

//luu kich thuoc man hinh thiet bi
//load hinh, am thanh


public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static Context CURRENT_CONTEXT;

    public static int blockSize;

    public static SharedPreferences share;
  //  public static SharedPreferences.Editor editor;

    public static int indexChall;

    public  static int id;
    public static String AccountName;
    public static String AccountPass;
    public static boolean login;
    public static boolean logoutPress;
    public static int highScore;
    public static int idAccount;//in Score table
    public static int idScore;//in Score table

    public static List<Accounts> accounts=new ArrayList();
    public static List<Scores> scores=new ArrayList<>();

    public static void drawCenterText(Canvas canvas, Rect r, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        int cHeight = r.height();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    public static void drawTittle(Canvas canvas, Rect r, Paint paint, String text) {
        paint.setTextSize(100);
        paint.setColor(Color.rgb(28,76,81));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        int cHeight = r.height();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 6f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    public static void drawText(Canvas canvas,Rect r,float x,float y,Paint paint,String text){
        paint.setTextSize(70);
        paint.setColor(Color.parseColor("#032421"));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        int cHeight = r.height();
        paint.getTextBounds(text, 0, text.length(), r);
        float xPos = x - r.width() / 2f - r.left;
        float yPos = y + r.height() / 2f - r.bottom;
        canvas.drawText(text, xPos, yPos, paint);
    }

    public static void drawText2(Canvas canvas,Rect r,float x,float y,Paint paint,String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        int cHeight = r.height();
        paint.getTextBounds(text, 0, text.length(), r);
        float xPos = x - r.width() / 2f - r.left;
        float yPos = y + r.height() / 2f - r.bottom;
        canvas.drawText(text, xPos, yPos, paint);
    }

    public static void drawText3(Canvas canvas,Rect r,float x,float y,Paint paint,String text){
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        int cHeight = r.height();
        paint.getTextBounds(text, 0, text.length(), r);
        float xPos = x - r.width() / 2f - r.left;
        float yPos = y + r.height() / 2f - r.bottom;
        canvas.drawText(text, xPos, yPos, paint);
    }

}
