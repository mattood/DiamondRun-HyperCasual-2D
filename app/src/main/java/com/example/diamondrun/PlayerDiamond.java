package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PlayerDiamond extends Diamond  {

    private Bitmap bitmap;

    public PlayerDiamond(){
        this.setXLocation(Constants.SCREEN_HEIGHT/2); //centered in screen
        this.setYLocation((Constants.SCREEN_HEIGHT*8)/9);
    }

    public void draw(Canvas canvas){
            bitmap = this.getRandomDiamondBitmapColor();
            canvas.drawBitmap(bitmap, getXLocation(), getYLocation(), null);
    }







}
