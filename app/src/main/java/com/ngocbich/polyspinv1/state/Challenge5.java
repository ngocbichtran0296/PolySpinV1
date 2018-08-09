package com.ngocbich.polyspinv1.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.game_objects.Challenge;
import com.ngocbich.polyspinv1.game_objects.Circle;
import com.ngocbich.polyspinv1.game_objects.Circle_Manager;
import com.ngocbich.polyspinv1.game_objects.Demon;
import com.ngocbich.polyspinv1.game_objects.Player;
import com.ngocbich.polyspinv1.game_objects.Spinnie;

import java.util.Random;

/**
 * Created by Ngoc Bich on 7/25/2018.
 */

public class Challenge5 extends GameState {
    private Spinnie spinnie;
    private Player player;
    private Point pointPlayer;//vi tri trong tam cua player
    private Rect spinniePosition, playerPosition;

    private boolean movingPlayer = false;

    private int countHit=5;
    private Demon demon;
    private Rect posDemon;

    private Bitmap background;

    public static int indexBg;

    private Paint paint;

    private Circle_Manager circle_manager;

    private int distance;

    public Challenge5(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        if(Game.mute==1) MainActivity.getPlayerBoss().start();
        else {
            if(MainActivity.getPlayerBoss().isPlaying())
            MainActivity.getPlayerBoss().pause();
        }

        paint= new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));

        int xPlayer = (Constants.SCREEN_WIDTH / 6) * 3;
        int yPlayer = (Constants.SCREEN_HEIGHT / 6) * 4 + 70;
        int xSpinnie = (Constants.SCREEN_WIDTH / 6) * 3;
        int ySpinnie = (Constants.SCREEN_HEIGHT / 6) * 5;


        spinniePosition = new Rect(xSpinnie - 40, ySpinnie - 40, xSpinnie + 40, ySpinnie + 40);
        spinnie = new Spinnie(spinniePosition, bitmapImage, 1, 0);

        playerPosition = new Rect(xPlayer - 50, yPlayer - 50, xPlayer + 50, yPlayer + 50);
        player = new Player(playerPosition, bitmapImage, 0, xPlayer, yPlayer);

        pointPlayer = new Point(xPlayer, yPlayer);

        indexBg = new Random().nextInt(3 - 0 + 1) + 0;

        ChallengeState.failed=false;

        background = bitmapImage.getBackground()[indexBg];

        circle_manager=new Circle_Manager(bitmapImage,xSpinnie,ySpinnie);

        Point point=new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/9);
        demon=new Demon(bitmapImage,point,100,100,10);

        distance=Constants.SCREEN_WIDTH/9;

    }

    @Override
    public void update(float delta) {
        float distanceX,distanceY,radiusSum,foverlap;
        double length;
        float cx,cy;

        float a,b;

        if(ChallengeState.failed==false){
            if(countHit<=0){
                if(MainActivity.getPlayerBoss().isPlaying())
                MainActivity.getPlayerBoss().pause();
                gsm.setState(gsm.COMPLETED);
            }
            demon.update();
            player.update(pointPlayer);
            circle_manager.update();
            spinnie.update();

            //add enemy
            if(demon.getPoint().x==distance || demon.getPoint().x==distance*3 || demon.getPoint().x==distance*5 || demon.getPoint().x==distance*8) {
                circle_manager.addCircle(demon.getPoint().x, demon.getPoint().y+50,Color.BLACK);
            }

            //check collision between player and enemy
            for (int i=0;i<circle_manager.getCircles().size();i++){
                if(circle_manager.getCircles().get(i).isColliding(player.getRect())){
                    circle_manager.getCircles().get(i).setColor(Color.WHITE);
                    circle_manager.getCircles().get(i).setCollide(true);

                    a=(pointPlayer.y-circle_manager.getCircles().get(i).getY())/(pointPlayer.x-circle_manager.getCircles().get(i).getX());
                    b=circle_manager.getCircles().get(i).getY()-(a*circle_manager.getCircles().get(i).getX());

                    if(circle_manager.getCircles().get(i).getY()<=pointPlayer.y){
                        if(circle_manager.getCircles().get(i).getX()<=pointPlayer.x)circle_manager.getCircles().get(i).setPosColliding(1);//top left
                        else circle_manager.getCircles().get(i).setPosColliding(2);//top right
                    }else{
                        if(circle_manager.getCircles().get(i).getX()<=pointPlayer.x)circle_manager.getCircles().get(i).setPosColliding(3);//bottom left
                        else circle_manager.getCircles().get(i).setPosColliding(4);//bottom right
                    }

                    circle_manager.getCircles().get(i).setA(a);
                    circle_manager.getCircles().get(i).setB(b);
                }
            }

            //check collision between enemy and spinnie
            for (int i=0;i<circle_manager.getCircles().size();i++){
                if(circle_manager.getCircles().get(i).isColliding(spinnie.getRect())){
                    ChallengeState.failed=true;
                }
            }

            //check collision between circle and demon
            for (int i=0;i<circle_manager.getCircles().size();i++){
                if(circle_manager.getCircles().get(i).isColliding(demon.getRect())
                        && circle_manager.getCircles().get(i).isCollide()==true){

                    circle_manager.getCircles().remove(i);
                    countHit--;
                }
            }
        }

        if(ChallengeState.failed==true){
            if(MainActivity.getPlayerBoss().isPlaying())
            MainActivity.getPlayerBoss().pause();
            gsm.setState(gsm.COMPLETED);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background,null,new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT),new Paint());
        demon.draw(canvas);
        circle_manager.draw(canvas);
        player.draw(canvas);
        spinnie.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));
        canvas.drawText("" + countHit, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                movingPlayer = false;
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (ChallengeState.failed==false && player.getRect().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (ChallengeState.failed==false && movingPlayer) {
                    pointPlayer.set((int) event.getX(), (int) event.getY());
                }
                break;
            }
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
