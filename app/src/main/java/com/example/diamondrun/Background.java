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
    private ArrayList<Bitmap> arrBackgroundImages = new ArrayList<>();
    ArrayList<Integer> spaceFieldImgs = new ArrayList<Integer>();
    private int count;
    private int vFlap;
    private int idCurrentBitmap;

    Background(int screenX, int screenY, Context context){
        this.screenX = screenX;
        this.screenY = screenY;
        retrieveFolderResourceIds(context);
        createArrBackgroundBitmaps(context);
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
    }

    public void retrieveFolderResourceIds(Context context){
        for(int i = 1; i < 5; i++){
            spaceFieldImgs.add(context.getResources().getIdentifier("ezgifframe"+i, "drawable", context.getPackageName()));
        }
    }

    public void createArrBackgroundBitmaps(Context context){

        for(int i = 0; i < spaceFieldImgs.size(); i++){
            Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), spaceFieldImgs.get(i)), screenX, screenY, true);
            arrBackgroundImages.add(temp);
        }
    }

    public int getBackgroundWidth(){
        return screenX;
    }

    public int getBackgroundHeight(){
        return screenY;
    }

    public Bitmap getBitmapAnimated(ArrayList<Bitmap> arrBitmaps) { //animate shatter
        count++;
        if (this.count == this.vFlap) {
            for (int i = 0; i < arrBitmaps.size(); i++) {
                if (i == arrBitmaps.size() - 1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i + 1;
                    break;
                }
            }
            count = 0;

        }
        return arrBitmaps.get(idCurrentBitmap);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.getBitmapAnimated(arrBackgroundImages), this.x, this.y, null);
    }

}
