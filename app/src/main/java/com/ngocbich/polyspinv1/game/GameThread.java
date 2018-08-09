package com.ngocbich.polyspinv1.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ngocbich.polyspinv1.Constants;

/**
 * Created by Ngoc Bich on 7/3/2018.
 */

public class GameThread extends Thread {
    public static final int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private static boolean running;
    public static Canvas canvas;

    private Bitmap gameImage;
    private Rect gameImageSrc;
    private Rect gameImageDst;

    private float delta;

    //pt khoi tao
    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

        gameImage=Bitmap.createBitmap(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT,Bitmap.Config.ARGB_8888);
        gameImageSrc= new Rect(0,0,gameImage.getWidth(),gameImage.getHeight());
        gameImageDst= new Rect();
        canvas=new Canvas(gameImage);
        delta=0;
    }

    public static void setRunning(boolean b) {
        running = b;
    }

    private void render(){
        if(canvas!=null){
            canvas.getClipBounds(gameImageDst);//lấy 1 hình chữ nhật bao quanh vungf cắt của gameImageDSt
            canvas.drawBitmap(gameImage,gameImageSrc,gameImageDst,null);
            try{
                surfaceHolder.unlockCanvasAndPost(canvas);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / FPS;

        while(running){
            startTime = System.nanoTime();

            canvas=null;

            try{
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    //update
                    this.gameView.update(delta);

                    //draw
                    this.gameView.draw(canvas);

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                render();
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
                Log.d("gamethread","ok");
            }
        }
    }
}
