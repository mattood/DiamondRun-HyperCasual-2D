package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.LinkedList;

public class DiamondCollection {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    private int diamondCollectionYSpeed = 5; //arbitrary
    GameGrid gameGrid;
    private Bitmap bitmap;
    private int topOfScreen=0;
    private int bitmapWidth = 0;
    private int bitmapHeight = 50;

    DiamondCollection(Context context, int numDiamonds, int screenWidth){
        bitmapWidth = screenWidth/numDiamonds;
        gameGrid = new GameGrid(numDiamonds, screenWidth);
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        spawnDiamonds(context, numDiamonds);
    }

    public Diamond get_at(int index)
    {
        return diamonds.get(index);
    }

    private void setXLocation(){
        int bitmapWidth = diamonds.getFirst().getBitmapWidth();
        int axisLength = gameGrid.getAxisLength();

        for(int i = 0; i < diamonds.size(); i++){
            diamonds.get(i).setXLocation(gameGrid.getDiamondXposition(bitmapWidth, i)); //setting x in the center of axis1, they all same width so we use first
        }
    }

    public void spawnDiamonds(Context context, int numDiamonds){
        for(int i = 0; i < numDiamonds; i++){ //we seting the size of entire collection here where they are created
            Diamond d = new Diamond(context, bitmapWidth, bitmapHeight);
            d.setYLocation(topOfScreen - d.getBitmapHeight());
            diamonds.add(d);
        }
        setXLocation();
    }

    public void moveDiamondsDownScreen(){
        for(int i = 0; i < diamonds.size(); i++){ //going through entire linked list
            diamonds.get(i).setYLocation(diamonds.get(i).getYLocation() + this.diamondCollectionYSpeed);
        }
    }

    public void draw(Canvas canvas){

        for(int i = 0; i < diamonds.size(); i++) {
            Diamond d = diamonds.get(i);
            bitmap = d.getRandomDiamondColorBitmap();
            canvas.drawBitmap(bitmap, d.getXLocation(), d.getYLocation(), null);
        }
    }




}
