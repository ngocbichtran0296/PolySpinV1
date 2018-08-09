package com.ngocbich.polyspinv1.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.game_objects.Circle;
import com.ngocbich.polyspinv1.game_objects.Circle_Manager;
import com.ngocbich.polyspinv1.game_objects.Enemy;
import com.ngocbich.polyspinv1.game_objects.EnemyManager;
import com.ngocbich.polyspinv1.game_objects.Enemy_Manager;
import com.ngocbich.polyspinv1.game_objects.Player;
import com.ngocbich.polyspinv1.game_objects.Rects;
import com.ngocbich.polyspinv1.game_objects.Spinnie;
import com.ngocbich.polyspinv1.game_objects.StarItem;
import com.ngocbich.polyspinv1.game_objects.StarItem_Manager;

import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by Ngoc Bich on 7/11/2018.
 */

public class PlayState extends GameState {
    private Spinnie spinnie;
    private Player player;
    private Point pointPlayer;//vi tri trong tam cua player
    private Rect spinniePosition, playerPosition;
    private int indexSpinnie, indexPlayer;


    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private Bitmap background;
   // private Bitmap bgstar;

    public static int indexBg;

    private  int sc=20;
    private Random random;


    // private EnemyManager enemyManager;
    private Circle_Manager circle_manager;
    private StarItem_Manager starItem_manager;

    private float xStart1,yStart1,xStart2,yStart2,orient1,orient2;
    private int cons_x;

    public PlayState(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        indexSpinnie = 0;
        indexPlayer = 0;

        int xPlayer = (Constants.SCREEN_WIDTH / 6) * 3;
        int yPlayer = (Constants.SCREEN_HEIGHT / 6) * 4 + 50;
        int xSpinnie = (Constants.SCREEN_WIDTH / 6) * 3;
        int ySpinnie = (Constants.SCREEN_HEIGHT / 6) * 5;

        // 80 x 80
        spinniePosition = new Rect(xSpinnie - 40, ySpinnie - 40, xSpinnie + 40, ySpinnie + 40);
        spinnie = new Spinnie(spinniePosition, bitmapImage, 1, indexSpinnie);

        //size: 80x80
        playerPosition = new Rect(xPlayer - 50, yPlayer - 50, xPlayer + 50, yPlayer + 50);
        player = new Player(playerPosition, bitmapImage, indexPlayer, xPlayer, yPlayer);

        pointPlayer = new Point(xPlayer, yPlayer);

        indexBg = new Random().nextInt(3 - 0 + 1) + 0;

        background = bitmapImage.getBackground()[indexBg];

       circle_manager=new Circle_Manager(bitmapImage,xSpinnie,ySpinnie);
        starItem_manager = new StarItem_Manager(bitmapImage);

        xStart1=Constants.SCREEN_WIDTH/27;
        yStart1=-50;
        xStart2=Constants.SCREEN_WIDTH/9*2;
        yStart2=-30;
        orient1=1;
        orient2=2;

        random=new Random();
        cons_x=0;

        if(Game.mute==1){
            MainActivity.getPlayerPlay().start();
        }
        else {
            if(MainActivity.getPlayerPlay().isPlaying());
            MainActivity.getPlayerPlay().pause();
        }
    }

    public int random() {
        int x1;
        while (true) {
            x1 = random.nextInt(8 - 1 + 1) + 1;
            if (x1 != cons_x && x1 != 0) {
                break;
            }
        }
        cons_x = x1;
         Log.d("position x:", x1 + "");
        return x1;
    }

    public void updateXStart(){

        if(orient1==1){
            xStart1+=Constants.SCREEN_WIDTH/27;
            if(xStart1>=Constants.SCREEN_WIDTH)orient1=2;
        }
        if(orient1==2){
            xStart1-=Constants.SCREEN_WIDTH/27;
            if(xStart1<=0)orient1=1;
        }

        if(orient2==1){
            xStart2+=Constants.SCREEN_WIDTH/27;
            if(xStart2>=Constants.SCREEN_WIDTH)orient2=2;
        }
        if(orient2==2){
            xStart2-=Constants.SCREEN_WIDTH/27;
            if(xStart2<=0)orient2=1;
        }
    }

    @Override
    public void update(float delta) {
        float a,b;

        int deltaY=20;

        if (!gameOver) {
            spinnie.update();
            player.update(pointPlayer);
            circle_manager.update2();
            starItem_manager.update();

            updateXStart();
            if(xStart1==Constants.SCREEN_WIDTH/27*6 || xStart1==Constants.SCREEN_WIDTH/27*12 ||
                    xStart1==Constants.SCREEN_WIDTH/27*15 || xStart1==Constants.SCREEN_WIDTH/27*21 ){

                if(Circle_Manager.score>=20){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,25,1,1));
                }
                if(Circle_Manager.score>=40){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,30,1,1));
                }
                if(Circle_Manager.score>=60){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,35,1,1));
                }
                if(Circle_Manager.score>=80){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,40,1,1));
                }
                if(Circle_Manager.score>=100){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,45,1,1));
                }
                if (Circle_Manager.score>=150)
                circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,50,1,1));

                if(Circle_Manager.score<20){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart1,0,60,60,60,Color.WHITE,20,1,1));
                }
            }


            for(int i=0;i<circle_manager.getCircles().size()-1;i++){
                if(circle_manager.getCircles().get(i).isColliding(player.getRect())){
                    circle_manager.getCircles().get(i).setCollide(true);


                    //
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

            // neu spinnie va cham voi enemy
            for (int i=0;i<circle_manager.getCircles().size();i++){
                if(circle_manager.getCircles().get(i).isColliding(spinnie.getRect())){
                    gameOver=true;
                }
            }




            if (starItem_manager.isPlayerColliding(player.getRect())) {
                for (StarItem item : starItem_manager.getStarItems()) {
                    if (item.isColliding(player.getRect())) {
                        Circle_Manager.score += 2;

                        item.setRect(new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
                    }
                }
            }

        }
        if (gameOver) {
            if(MainActivity.getPlayerPlay().isPlaying())
            MainActivity.getPlayerPlay().pause();
            gsm.setState(gsm.GAMEOVER);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));
        if (!gameOver) {
            canvas.drawBitmap(background, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), new Paint());

            spinnie.draw(canvas);
            player.draw(canvas);
            circle_manager.draw(canvas);
            starItem_manager.draw(canvas);
            canvas.drawText("" + Circle_Manager.score, 50, 50 + paint.descent() - paint.ascent(), paint);
        }
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                movingPlayer = false;
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (!gameOver && player.getRect().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!gameOver && movingPlayer) {
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
