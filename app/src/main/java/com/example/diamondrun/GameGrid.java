package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.res.ResourcesCompat;

public class GameGrid {

    private int axis_interval;
    int numAxis;
    Bitmap gridblue;
    Bitmap gridyellow;
    Bitmap gridpurple;
    Bitmap gridgreen;
    Bitmap gridred;
    DiamondCollection dc;
    Paint paintInnerGrids;
    Paint paintGridLines;
    int screenHeight;
    Rect gridRect;


    GameGrid(int numAxis, int screenWidth, Context context, int screenHeight){
        axis_interval = screenWidth/numAxis;
        this.numAxis = numAxis;
        paintGridLines = new Paint();
        paintGridLines.setColor(Color.BLACK);
        paintGridLines.setStrokeWidth(10);
        //paintInnerGrids = new Paint();
       // paintInnerGrids.setAlpha(7);
        this.screenHeight = screenHeight;
        gridRect = new Rect();
        initGridBitmaps(context);

    }

    public int getAxisLength(){
        return axis_interval;
    }

    public boolean insideAxis(int axisIndex, int eventGetXLocation) {
        int leftX = axis_interval * axisIndex;
        int rightX = (axis_interval * (axisIndex+1)) - 1;

        return (eventGetXLocation >= leftX && eventGetXLocation < rightX);
    }

    public int getCurrAxis(int playerXLocation){
        for(int i = 0; i < numAxis; i++){
            if(insideAxis(i,playerXLocation)){
                return i;
            }
        }
        return 0; //should never get here
    }


    public int getDiamondXposition(int diamondWidth, int axisIndex){
        int leftX = axis_interval * axisIndex;  //this is the left of the interval

        int leftXofDiamond = leftX + ((axis_interval-diamondWidth)/2);   //puts the left x of diamond at the left x of interval so its centered inside
        return leftXofDiamond;
    }

    public void initGridBitmaps(Context context){

        gridblue = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.timesupbackground), getAxisLength(), screenHeight, true);
        gridgreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.timesupbackground), getAxisLength(), screenHeight, true);
        gridpurple = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gridpurple), getAxisLength(), screenHeight, true);
        gridyellow = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.timesupbackground), getAxisLength(), screenHeight, true);
        gridred = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gridred), getAxisLength(), screenHeight, true);
    }

    public void draw(Canvas canvas){



    }




}
