package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class PlayerDiamond extends Diamond  {
    private Rect rect;
    private Bitmap bitmap;
    private int launchSpeed = 20;
    private int pullDownSpeed = 2;
    private final int baseLine;
    Matrix matrix;

    public PlayerDiamond(Context context, int bmWidth, int bmHeight, int screenWidth, int screenHeight)  {
        super (context, bmWidth, bmHeight);

        this.setXLocation(screenWidth/2); //centered in screen
        this.setYLocation((screenHeight*4)/5);
        baseLine = this.getYLocation();
        bitmap = this.getRandomDiamondColorBitmap();
        matrix = new Matrix();
    }

    public Rect getRect(){
        rect = new Rect(this.getXLocation(), this.getYLocation(), this.getXLocation()+this.getBitmapWidth(), this.getYLocation()+this.getBitmapHeight());
        return rect;
    }


    public Bitmap getChangedColorBitmap(){
        Bitmap currentColorBitmap = this.bitmap;
        Bitmap newColorBitmap = this.getRandomDiamondColorBitmap();
        while(newColorBitmap.sameAs(currentColorBitmap)){
            newColorBitmap= this.getRandomDiamondColorBitmap();
        }
        this.bitmap = newColorBitmap;
        return this.bitmap;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, getXLocation(), getYLocation(), null);
    }

    public void resetYLocation(){
        this.setYLocation(getBaseStartLine());
    }

    public int getBaseStartLine(){
        return this.baseLine;
    } //just his initial starting position as set in constructor

    public boolean belowBaseline(int eventGetX){
        if(eventGetX > getBaseStartLine()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean aboveBaseline(int eventGetY){
        if(eventGetY < getBaseStartLine()){
            return true;
        }
        else{
            return false;
        }
    }

    /*private boolean playerDiamondMoved(){
        int initPlayerLocation;
        initPlayerLocation = this.getXLocation();
        if(i)
    }*/

    public void moveLeft(){
        this.setXLocation(this.getXLocation() - this.speed); //speed not set yet
    }

    public void moveDownWithFinger(int eventGetY){
        this.setYLocation(eventGetY);
    }

    public void executeDiamondLaunch(){
       this.setYLocation(this.getYLocation() - launchSpeed);
    }

    public Bitmap rotateBitmap(int angle, Bitmap bitmap){
        matrix.reset();
        matrix.setRotate(angle);
        this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return this.bitmap;
    }

    public Bitmap resetMatrix(){
        matrix.setRotate(0);
        this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return this.bitmap;
    }










}
