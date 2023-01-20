package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;

public class PlayerDiamond extends Diamond  {
    private Rect rect;

    private int launchSpeed = 20;
    private final int baseLine;
    Matrix matrix;
    private int playerDiamondColorIndex = -1;

    public PlayerDiamond(Context context, int bmWidth, int bmHeight, int screenWidth, int screenHeight)  {
        super (context, bmWidth, bmHeight);

        this.setXLocation(screenWidth/2 - bmWidth/2); //centered in screen
        this.setYLocation((screenHeight*4)/5);
        baseLine = this.getYLocation();

        playerDiamondColorIndex = randNum.nextInt(bitmapColorsArr.length);
        this.bitmap = this.bitmapColorsArr[playerDiamondColorIndex]; //ndomDiamondColorBitmap();
        matrix = new Matrix();
    }

    public Rect getRect(){
        rect = new Rect(this.getXLocation(), this.getYLocation(), this.getXLocation()+this.getBitmapWidth(), this.getYLocation()+this.getBitmapHeight());
        return rect;
    }


    public Bitmap getChangedColorBitmap(){ //ensures that color change occurs in a sequence
        ++playerDiamondColorIndex;
        Bitmap newColorBitmap = bitmapColorsArr[playerDiamondColorIndex % bitmapColorsArr.length]; //mod to make sure index is always inside array bounds, 0-4 for this array
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

    public void moveLeft(){
        this.setXLocation(this.getXLocation() - this.speed); //speed not set yet
    }

    public void moveDownWithFinger(MotionEvent e2){ //sets y location to wherever finger is
        this.setYLocation((int)e2.getY());
    }

    public void executeDiamondLaunch(){
       this.setYLocation(this.getYLocation() - launchSpeed);
    }

    public void fallDownScreen(){
        this.setYLocation(this.getYLocation() + launchSpeed);
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
