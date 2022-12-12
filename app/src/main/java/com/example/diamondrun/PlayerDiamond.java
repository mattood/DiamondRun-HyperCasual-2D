package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PlayerDiamond extends Diamond  {

    private Bitmap bitmap;

    public PlayerDiamond(Context context, int bmWidth, int bmHeight, int screenWidth, int screenHeight)  {
        super (context, bmWidth, bmHeight);

        this.setXLocation(screenWidth/2); //centered in screen
        this.setYLocation((screenHeight*8)/9);
    }

    public void draw(Canvas canvas){
            bitmap = this.getRandomDiamondColorBitmap();
            canvas.drawBitmap(bitmap, getXLocation(), getYLocation(), null);
    }







}
