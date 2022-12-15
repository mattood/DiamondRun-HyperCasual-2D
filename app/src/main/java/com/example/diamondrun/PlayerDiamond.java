package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PlayerDiamond extends Diamond  {
    private Rect rect;
    private Bitmap bitmap;

    public PlayerDiamond(Context context, int bmWidth, int bmHeight, int screenWidth, int screenHeight)  {
        super (context, bmWidth, bmHeight);

        this.setXLocation(screenWidth/2); //centered in screen
        this.setYLocation((screenHeight*4)/5);
        bitmap = this.getRandomDiamondColorBitmap();
    }

    public Rect getRect(){
        rect = new Rect(this.getXLocation(), this.getYLocation(), this.getXLocation()+this.getBitmapWidth(), this.getYLocation()+this.getBitmapHeight());
        return rect;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, getXLocation(), getYLocation(), null);
    }

    public void moveRight(){
        this.setXLocation(this.getXLocation() + this.speed);//speed not set yet
    }

    public void moveLeft(){
        this.setXLocation(this.getXLocation() - this.speed); //speed not set yet
    }

    public void moveDown(int numCoordinates){
        this.setYLocation(this.getYLocation() + numCoordinates);
    }

    public void executeDiamondSlingshot(){
        moveDown(5); //first diamond gets dragged down preparing for launch
    }








}
