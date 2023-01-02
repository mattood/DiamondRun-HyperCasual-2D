package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class DiamondShard {
    public int yLocation;
    public int xLocation;
    public Bitmap bitmap;
    public int bitmapWidth;
    public int bitmapHeight;
    public boolean visible = true;
    private Rect rect;

    DiamondShard(int xLocation, int yLocation, Bitmap bitmap){
        this.yLocation = yLocation;
        this.xLocation = xLocation;
        this.bitmap = bitmap;
        bitmapWidth = bitmap.getWidth()/4;
        bitmapHeight = bitmap.getHeight()/4;
        scaleCurrentBitmap();
    }

    public void scaleCurrentBitmap(){ // just scaling it
       this.bitmap = Bitmap.createScaledBitmap(this.bitmap, bitmapWidth, bitmapHeight, false);
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, false);
    }

    public Rect getRect(){
        rect = new Rect(this.xLocation, this.yLocation, this.xLocation+this.bitmapWidth, this.yLocation+this.bitmapHeight);
        return rect;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void draw(Canvas canvas){
        if (visible) {
            canvas.drawBitmap(bitmap, xLocation, yLocation, null);
        }

    }


}
