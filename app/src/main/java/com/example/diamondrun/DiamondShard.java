package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class DiamondShard {
    public int yLocation;
    public int xLocation;
    public Bitmap bitmap;
    public int bitmapWidth;
    public int bitmapHeight;

    DiamondShard(int xLocation, int yLocation, Bitmap bitmap){
        this.yLocation = yLocation;
        this.xLocation = xLocation;
        this.bitmap = bitmap;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        scaleCurrentBitmap();
    }

    public void scaleCurrentBitmap(){ // just scaling it
       this.bitmap = Bitmap.createScaledBitmap(this.bitmap, bitmapWidth/4, bitmapHeight/4, false);
    }

    public int getXLocation(){
        return this.xLocation;
    }

    public int getYLocation(){
        return this.yLocation;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, getXLocation(), getYLocation(), null);
    }

}
