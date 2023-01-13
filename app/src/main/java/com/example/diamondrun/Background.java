package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Background {

    int screenX;
    int screenY;
    int x,y = 0;
    private Bitmap bitmap;
    Background(int screenX, int screenY, Context context){
        this.screenX = screenX;
        this.screenY = screenY;
        this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background), screenX, screenY, true);
    }

    //spaceFieldImgs.add(context.getResources().getIdentifier("ezgifframe"+i, "drawable", context.getPackageName()));

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
    }

}
