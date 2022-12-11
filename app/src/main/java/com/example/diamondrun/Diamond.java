package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Diamond {
    private int xLocation;
    private int yLocation;
    private int width;
    private int height;
    private int speed;
    private Bitmap greenDiamondBitmap;
    private Bitmap blueDiamondBitmap;
    private Bitmap purpleDiamondBitmap;
    private Bitmap yellowDiamondBitmap;
    private Bitmap redDiamondBitmap;
    Bitmap[] bitmapColorsArr;
    GameGrid gameGrid;
    Context context;

    Diamond(){
        gameGrid = new GameGrid();
        initBitmapDiamonds();
    }

    private void initBitmapDiamonds(){
        greenDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.greendiamond);
        greenDiamondBitmap = Bitmap.createScaledBitmap(greenDiamondBitmap, getBitmapWidth(), getBitmapHeight(), false);

        blueDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluediamond);
        blueDiamondBitmap = Bitmap.createScaledBitmap(blueDiamondBitmap, getBitmapWidth(), getBitmapHeight(), false);

        redDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.reddiamond);
        redDiamondBitmap= Bitmap.createScaledBitmap(redDiamondBitmap, getBitmapWidth(), getBitmapHeight(), false);

        yellowDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowdiamond);
        yellowDiamondBitmap = Bitmap.createScaledBitmap(yellowDiamondBitmap, getBitmapWidth(), getBitmapHeight(), false);

        purpleDiamondBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.purplediamond);
        purpleDiamondBitmap = Bitmap.createScaledBitmap(purpleDiamondBitmap, getBitmapWidth(), getBitmapHeight(), false);
    }


    public Bitmap getRandomDiamondBitmapColor(){
        bitmapColorsArr = new Bitmap[]{greenDiamondBitmap, blueDiamondBitmap, purpleDiamondBitmap, yellowDiamondBitmap, redDiamondBitmap};
        Random rand = new Random();
        return bitmapColorsArr[rand.nextInt(bitmapColorsArr.length)]; //random bitmap color
    }

    public int getBitmapWidth(){
        return gameGrid.getAxisLength(); //each bitmap diamond is one axis length so that they connect
    }

    public void setBitmapHeight(){
        this.height = 50;
    } //fix hardcode later

    public int getBitmapHeight(){
        return height;
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
