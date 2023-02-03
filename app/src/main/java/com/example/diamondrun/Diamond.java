package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import java.util.Random;

public class Diamond {
    public int xLocation;
    public int yLocation;
    private int bitmapWidth;
    private int bitmapHeight;
    public int speed = 5;
    public Bitmap greenDiamondBitmap;
    public Bitmap blueDiamondBitmap;
    public Bitmap purpleDiamondBitmap;
    public Bitmap yellowDiamondBitmap;
    public Bitmap redDiamondBitmap;
    public Bitmap transparentdiamond;
    Bitmap[] bitmapColorsArr;
    protected Bitmap bitmap;
    Random randNum;
    public int multiplier = 1;
    private boolean multiplierActive = false;
    public int diamondScore;
    Paint paintDiamond;

    Diamond(Context context, int bmWidth, int bmHeight){

        bitmapWidth = bmWidth;
        bitmapHeight = bmHeight;
        initBitmapDiamonds(context);
        bitmapColorsArr = new Bitmap[]{greenDiamondBitmap, blueDiamondBitmap, purpleDiamondBitmap, yellowDiamondBitmap, redDiamondBitmap};
        randNum = new Random(System.currentTimeMillis());
        getRandDiamondScore(); //random score for each diamond 0-20
        //resetMultiplier();
    }

    /*public void resetMultiplier(){
        if(randNum.nextInt(100) == 1){ //probability of occurring
            multiplier = randNum.nextInt(2)+2;
        }
    }*/

    public int getRandDiamondScore(){

        diamondScore = randNum.nextInt(5) + 1; //so that its never 0
        return diamondScore;
    }

    public void initBitmapDiamonds(Context context){

        //regular sized diamonds
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

        transparentdiamond  = BitmapFactory.decodeResource(context.getResources(), R.drawable.transparentdiamond);
    }

    public boolean hasMultiplier(){
        if(multiplier!=1){
            return true;
        }
        else{
            return false;
        }
    }
    public Bitmap getRandomDiamondColorBitmap(){
        bitmap= bitmapColorsArr[randNum.nextInt(bitmapColorsArr.length)];
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
