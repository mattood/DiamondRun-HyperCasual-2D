package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Diamond {
    private int xLocation;
    public int yLocation;
    private int bitmapWidth;
    private int bitmapHeight;
    public int speed = 5;
    public Bitmap greenDiamondBitmap;
    public Bitmap blueDiamondBitmap;
    public Bitmap purpleDiamondBitmap;
    public Bitmap yellowDiamondBitmap;
    public Bitmap redDiamondBitmap;
    Bitmap[] bitmapColorsArr;
    Random rand;
    protected Bitmap bitmap;

    Diamond(Context context, int bmWidth, int bmHeight){
        bitmapWidth = bmWidth;
        bitmapHeight = bmHeight;
        rand = new Random();
        initBitmapDiamonds(context);
        bitmapColorsArr = new Bitmap[]{greenDiamondBitmap, blueDiamondBitmap, purpleDiamondBitmap, yellowDiamondBitmap, redDiamondBitmap};

    }

    public void initBitmapDiamonds(Context context){ //why is it giving context null
        greenDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.greendiamond);
        greenDiamondBitmap = Bitmap.createScaledBitmap(greenDiamondBitmap, bitmapWidth, bitmapHeight, false);

        blueDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluediamond);
        blueDiamondBitmap = Bitmap.createScaledBitmap(blueDiamondBitmap, (bitmapWidth), bitmapHeight, false);

        redDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.reddiamond);
        redDiamondBitmap= Bitmap.createScaledBitmap(redDiamondBitmap, (bitmapWidth), bitmapHeight, false);

        yellowDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowdiamond);
        yellowDiamondBitmap = Bitmap.createScaledBitmap(yellowDiamondBitmap, (bitmapWidth), bitmapHeight, false);

        purpleDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.purplediamond);
        purpleDiamondBitmap = Bitmap.createScaledBitmap(purpleDiamondBitmap, (bitmapWidth), bitmapHeight, false);
    }


    public Bitmap getRandomDiamondColorBitmap(){
        bitmap= bitmapColorsArr[rand.nextInt(bitmapColorsArr.length)];
        return bitmap; //random bitmap color
    }

    public Bitmap getDiamondBitmap(){
        return this.bitmap;
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
