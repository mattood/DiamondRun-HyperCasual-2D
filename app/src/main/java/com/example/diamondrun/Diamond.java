package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Diamond {
    private int xLocation;
    private int yLocation;
    private int bitmapWidth;
    private int bitmapHeight;
    private int width;
    private int height;
    private int speed;
    private Bitmap greenDiamondBitmap;
    private Bitmap blueDiamondBitmap;
    private Bitmap purpleDiamondBitmap;
    private Bitmap yellowDiamondBitmap;
    private Bitmap redDiamondBitmap;
    Bitmap[] bitmapColorsArr;

    Diamond() {
    }

    Diamond(Context context, int bmWidth, int bmHeight){
        bitmapWidth = bmWidth;
        bitmapHeight = bmHeight;
        initBitmapDiamonds(context);
    }

    Diamond(Context context){
        bitmapWidth = 10;
        bitmapHeight = 10;
        initBitmapDiamonds(context);
    }

    public void initBitmapDiamonds(Context context){ //why is it giving context null
        greenDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.greendiamond);
        greenDiamondBitmap = Bitmap.createScaledBitmap(greenDiamondBitmap, bitmapWidth, bitmapHeight, false);

        blueDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluediamond);
        blueDiamondBitmap = Bitmap.createScaledBitmap(blueDiamondBitmap, bitmapWidth, bitmapHeight, false);

        redDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.reddiamond);
        redDiamondBitmap= Bitmap.createScaledBitmap(redDiamondBitmap, bitmapWidth, bitmapHeight, false);

        yellowDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowdiamond);
        yellowDiamondBitmap = Bitmap.createScaledBitmap(yellowDiamondBitmap, bitmapWidth, bitmapHeight, false);

        purpleDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.purplediamond);
        purpleDiamondBitmap = Bitmap.createScaledBitmap(purpleDiamondBitmap, bitmapWidth, bitmapHeight, false);
    }


    public Bitmap getRandomDiamondColorBitmap(){ //why is it giving all elements to be null
        bitmapColorsArr = new Bitmap[]{greenDiamondBitmap, blueDiamondBitmap, purpleDiamondBitmap, yellowDiamondBitmap, redDiamondBitmap};
        Random rand = new Random();
        return bitmapColorsArr[rand.nextInt(bitmapColorsArr.length)]; //random bitmap color
    }


    public int getBitmapHeight(){
        return bitmapHeight;
    }

    public int getBitmapWidth(){
        return bitmapWidth;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setXLocation(int xLocation){
        this.xLocation = xLocation;
    }

    public int getXLocation(){
        return xLocation;
    }

    public void setYLocation(int yLocation){
        this.yLocation = yLocation;
    }

    public int getYLocation(){
        return yLocation;
    }

    public void moveX(){
        this.xLocation += speed;
    }

    public void moveY(){
        this.yLocation += speed;
    }


}
