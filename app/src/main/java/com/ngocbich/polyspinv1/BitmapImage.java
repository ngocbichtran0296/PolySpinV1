package com.ngocbich.polyspinv1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Ngoc Bich on 7/3/2018.
 */

public class BitmapImage {
    private Bitmap[] player;
    private Bitmap[] spinnies;
    private Bitmap[] background;
    private Bitmap logo;
    private Bitmap star;
    private Bitmap bgstar,rankcircle,starscore;

    private Bitmap play,setting,challenge,ranking,sound,mute,account,replay,skin,logout;

    private int size; // kich thuoc mac dinh ban dau

    private Bitmap up,down;

    private Bitmap demon;

    public BitmapImage(Context context, int blockSize){
        this.size=blockSize;

        //background
        background=new Bitmap[4];
        background[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bgcolor4), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
        background[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bgcolor0), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
        background[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bgcolor2), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
        background[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bgcolor3), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);

        bgstar=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bgstar), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);

        //player
        player=new Bitmap[4];
        player[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player0), blockSize, blockSize, false);
        player[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player1), blockSize, blockSize, false);
        player[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player2), blockSize, blockSize, false);
        player[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player3), blockSize, blockSize, false);

        //spinnie
        spinnies=new Bitmap[4];
        spinnies[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.spinnie0),blockSize,blockSize,false);
        spinnies[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.spinnie1),blockSize,blockSize,false);
        spinnies[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.spinnie2),blockSize,blockSize,false);
        spinnies[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.spinnie3),blockSize,blockSize,false);

        logo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.logo),blockSize,blockSize,false);

        // cac Button
        play=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.playbutton),blockSize*3,blockSize*3,false);
        setting=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.settingbutton),blockSize,blockSize,false);
        ranking=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.rankingbutton),blockSize,blockSize,false);
        replay=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.replaybutton),blockSize,blockSize,false);
        challenge=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.challengebutton),blockSize,blockSize,false);
        mute=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.mutebutton),blockSize,blockSize,false);
        sound=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.soundbutton),blockSize,blockSize,false);
        account=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.accountbutton),blockSize,blockSize,false);
        skin=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.skinbutton),blockSize,blockSize,false);

        logout=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.logoutbutton),blockSize,blockSize,false);

        star=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.star),blockSize,blockSize,false);

        rankcircle=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.rankcircle),blockSize*3,blockSize*3,false);

        starscore=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.starscore),blockSize*3,blockSize*3,false);

        up=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.up),blockSize*3,blockSize*3,false);
        down=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.down),blockSize*3,blockSize*3,false);

        demon=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.demon),blockSize*3,blockSize*3,false);
    }

    public Bitmap getDemon() {
        return demon;
    }

    public Bitmap getUp() {
        return up;
    }

    public Bitmap getDown() {
        return down;
    }

    public Bitmap getStarscore() {
        return starscore;
    }

    public Bitmap getRankcircle() {
        return rankcircle;
    }

    public Bitmap getStar() {
        return star;
    }

    public Bitmap getLogout() {
        return logout;
    }

    public Bitmap[] getBackground() {
        return background;
    }

    public void setBackground(Bitmap[] background) {
        this.background = background;
    }

    public Bitmap[] getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Bitmap[] getSpinnies() {
        return spinnies;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public Bitmap getPlay() {
        return play;
    }

    public Bitmap getSetting() {
        return setting;
    }

    public Bitmap getChallenge() {
        return challenge;
    }

    public Bitmap getRanking() {
        return ranking;
    }

    public Bitmap getSound() {
        return sound;
    }

    public Bitmap getMute() {
        return mute;
    }

    public Bitmap getReplay() {
        return replay;
    }

    public Bitmap getSkin() {
        return skin;
    }
}
