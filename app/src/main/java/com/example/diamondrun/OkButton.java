package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class OkButton {

    int x, y, width, height;
    private Bitmap okButton;

    OkButton(Context context, int screenHeight, int screenWidth) {
        okButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.okbutton), 400, 250, false);

        x = (screenWidth/2) - (okButton.getWidth()/2);
        y = (screenHeight / 2) + (okButton.getHeight()/2);

    }

    public Rect getRect(){
        return new Rect((int)this.x, (int)this.y, (int)this.x+okButton.getWidth(),(int)this.y + okButton.getHeight());
    }

    public boolean fingerTappedOkayButton(float eventGetX, float eventGetY){
        if(getRect().contains((int)eventGetX, (int)eventGetY)){
            return true;
        }
        else{
            return false;
        }
    }


    public void draw(Canvas canvas) {
        //int okButtonX = x - (okButton.getWidth()/2);
        //int okButtonY = y + (okButton.getHeight()/2);

        canvas.drawBitmap(okButton, x, y , null);


    }


}
