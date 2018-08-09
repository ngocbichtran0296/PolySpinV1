package com.ngocbich.polyspinv1.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.ngocbich.polyspinv1.BitmapImage;
import com.ngocbich.polyspinv1.Constants;
import com.ngocbich.polyspinv1.MainActivity;
import com.ngocbich.polyspinv1.game.Game;
import com.ngocbich.polyspinv1.game_objects.Challenge;
import com.ngocbich.polyspinv1.game_objects.Circle;
import com.ngocbich.polyspinv1.game_objects.Circle_Manager;
import com.ngocbich.polyspinv1.game_objects.Enemy_Manager;
import com.ngocbich.polyspinv1.game_objects.Player;
import com.ngocbich.polyspinv1.game_objects.Rects;
import com.ngocbich.polyspinv1.game_objects.Spinnie;
import com.ngocbich.polyspinv1.game_objects.StarItem;
import com.ngocbich.polyspinv1.game_objects.StarItem_Manager;

import java.util.Random;

/**
 * Created by Ngoc Bich on 7/25/2018.
 */

public class Challenge1 extends GameState{
    private Spinnie spinnie;
    private Player player;
    private Point pointPlayer;//vi tri trong tam cua player
    private Rect spinniePosition, playerPosition;

    private boolean movingPlayer = false;


    private Bitmap background;

 //   private Enemy_Manager enemy_manager;
    private Circle_Manager circle_manager;
    private StarItem_Manager starItem_manager;

    private Paint paint;

    private float xStart,yStart,orient;

    private Random random;
    private int cons_x;

    public Challenge1(GameStateManager gsm, BitmapImage bitmapImage) {
        super(gsm, bitmapImage);
    }

    @Override
    public void init(BitmapImage bitmapImage) {
        if(Game.mute==1)MainActivity.getPlayerPlay().start();
        else {
            if(MainActivity.getPlayerPlay().isPlaying())
            MainActivity.getPlayerPlay().pause();
        }

        paint= new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.parseColor("#032421"));

        int xPlayer = (Constants.SCREEN_WIDTH / 6) * 3;
        int yPlayer = (Constants.SCREEN_HEIGHT / 6) * 4 + 50;
        int xSpinnie = (Constants.SCREEN_WIDTH / 6) * 3;
        int ySpinnie = (Constants.SCREEN_HEIGHT / 6) * 5;

        spinniePosition = new Rect(xSpinnie - 40, ySpinnie - 40, xSpinnie + 40, ySpinnie + 40);
        spinnie = new Spinnie(spinniePosition, bitmapImage, 1, 0);

        playerPosition = new Rect(xPlayer - 50, yPlayer - 50, xPlayer + 50, yPlayer + 50);
        player = new Player(playerPosition, bitmapImage, 0, xPlayer, yPlayer);

        pointPlayer = new Point(xPlayer, yPlayer);

        ChallengeState.indexBg = new Random().nextInt(3 - 0 + 1) + 0;

        ChallengeState.failed=false;

        background = bitmapImage.getBackground()[ChallengeState.indexBg];
        //  bgstar=bitmapImage.getBgstar();

        // enemyManager=new EnemyManager(bitmapImage);
      //  enemy_manager = new Enemy_Manager(bitmapImage);
        starItem_manager = new StarItem_Manager(bitmapImage);
        circle_manager=new Circle_Manager(bitmapImage,xSpinnie,ySpinnie);

        xStart=Constants.SCREEN_WIDTH/27;
        yStart=-50;
        orient=1;

        random=new Random();
        cons_x=0;
    }

    public void updateXStart(){
        if(orient==1){
            xStart+=Constants.SCREEN_WIDTH/18;
            if(xStart>=Constants.SCREEN_WIDTH)orient=2;
        }
        if(orient==2){
            xStart-=Constants.SCREEN_WIDTH/18;
            if(xStart<=0)orient=1;
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
        //  Log.d("position x:", x1 + "");
        return x1;
    }

    @Override
    public void update(float delta) {
        float distanceX,distanceY,radiusSum,foverlap;
        double length;
        float cx,cy;
        float a,b;

        if (ChallengeState.failed==false) {
            if(Circle_Manager.score>=50){
                if(MainActivity.getPlayerPlay().isPlaying())
                MainActivity.getPlayerPlay().pause();
                gsm.setState(gsm.COMPLETED);
            }
            spinnie.update();
            player.update(pointPlayer);
            circle_manager.update2();
            starItem_manager.update();
            updateXStart();
            if(xStart==Constants.SCREEN_WIDTH/27*6 || xStart==Constants.SCREEN_WIDTH/27*12 ||
                    xStart==Constants.SCREEN_WIDTH/27*15 || xStart==Constants.SCREEN_WIDTH/27*21 ){

                if(Circle_Manager.score>=20){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,25,1,1));
                }
                if(Circle_Manager.score>=40){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,30,1,1));
                }
                if(Circle_Manager.score>=60){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,35,1,1));
                }
                if(Circle_Manager.score>=80){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,40,1,1));
                }
                if(Circle_Manager.score>=100){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,45,1,1));
                }
                if (Circle_Manager.score>=150)
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,50,1,1));

                if(Circle_Manager.score<20){
                    circle_manager.getCircles().add(new Circle(random()*Constants.SCREEN_WIDTH/9,yStart,0,60,60,60,Color.WHITE,20,1,1));
                }
            }

            for(int i=0;i<circle_manager.getCircles().size()-1;i++){
                if(circle_manager.getCircles().get(i).isColliding(player.getRect())){
                    circle_manager.getCircles().get(i).setCollide(true);

                  //  Circle_Manager.score++;

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
                    ChallengeState.failed=true;
                }
            }

            // kiem tra va cham giua player va star
            if (starItem_manager.isPlayerColliding(player.getRect())) {
                for (StarItem item : starItem_manager.getStarItems()) {
                    if (item.isColliding(player.getRect())) {
                        Circle_Manager.score += 2;

                        item.setRect(new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
                    }
                }
            }

        }//end

        //kiem tra game over
        if (ChallengeState.failed==true) {
            if(MainActivity.getPlayerPlay().isPlaying())
            MainActivity.getPlayerPlay().pause();
            gsm.setState(gsm.COMPLETED);
        }
    }

    @Override
    public void draw(Canvas canvas) {

        if (ChallengeState.failed==false) {
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
