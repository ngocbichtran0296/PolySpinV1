package com.ngocbich.polyspinv1.game_objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ngoc Bich on 7/4/2018.
 */

public class EnemyManager {
    private ArrayList<Enemy> enemyList;
    private BitmapImage bitmapImage;

    private int size1 = 40;

    private Random random;

    private long startTime, initTime;
    private int count;

    private int score;
    private int x;

    public EnemyManager(BitmapImage bitmapImage) {
        enemyList = new ArrayList<>();
        this.bitmapImage = bitmapImage;

        this.random = new Random();// 2 -> 5 x=random.nextInt((5-2+1)+2);

        startTime = initTime = System.currentTimeMillis();
        count = 1;

        score = 0;

        x = 0;
        populateEnemy();


    }

    //them cac enemy vao danh sach
    public void populateEnemy() {
        int x1;
        int type;
        int size;
        int deltaY = 20;
        int yStart = -100;
        int distanceX = Constants.SCREEN_WIDTH / 8;
        while (yStart >= -Constants.SCREEN_HEIGHT - 1000) {
            x1 = random();

            type = random.nextInt(4 - 0 + 1) + 0;
            size = (random.nextInt(2 - 1 + 1) + 1) * size1;
            // deltaY=random.nextInt((20-15+1)+15);

            enemyList.add(new Enemy(size, deltaY, x1 * distanceX, yStart, type, bitmapImage));
            yStart = yStart - 40;


        }
    }

    public int random() {
        int x1;
        while (true) {
            x1 = random.nextInt(7 - 1 + 1) + 1;
            if (x1 != x && x1 != 0) {
                break;
            }
        }
        x = x1;
       // Log.d("position x:", x1 + "");
        return x1;
    }

    //va cham voi player
    public boolean collide(Player player) {
        for (Enemy enemy : enemyList) {
            if (enemy.collide(player)) {
                return true;
            }
        }
        return false;
    }

    //va cham voi spinnie
    public boolean collide(Spinnie spinnie) {
        for (Enemy enemy : enemyList) {
            if (enemy.collide(spinnie)) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        int currentTime = (int) (System.currentTimeMillis() - startTime);
        if (currentTime >= 1000) {
            score++;
            startTime = System.currentTimeMillis();
        }
        for (Enemy enemy : enemyList) {
            enemy.incrementY();
        }
        if (score >= 50 * count) {
            for (Enemy enemy : enemyList) {
                enemy.setDeltaY(20 + count * 5);
            }
            count++;
        }
        if (enemyList.get(0).getRect().top >= Constants.SCREEN_HEIGHT) {
            enemyList.add(new Enemy((random.nextInt(2 - 1 + 1)+ 1) * size1,
                    20, random() * Constants.SCREEN_WIDTH / 8,
                    enemyList.get(enemyList.size() - 1).getRect().top,
                    random.nextInt((4 - 0 + 1) + 0), bitmapImage));

            enemyList.remove(0);
        }

            }

    public void draw(Canvas canvas) {
        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.parseColor("#032421"));
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(ArrayList<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
