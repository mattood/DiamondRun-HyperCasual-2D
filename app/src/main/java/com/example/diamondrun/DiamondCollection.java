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
    private int bitmapHeight = 150;
    private int collectionYLocation;

    DiamondCollection(Context context, int numDiamonds, int screenWidth){
        bitmapWidth = screenWidth/numDiamonds;
        gameGrid = new GameGrid(numDiamonds, screenWidth);
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection

        spawnDiamonds(context, numDiamonds);
        for(int i = 0; i < diamonds.size(); i++){
            bitmap = diamonds.get(i).getRandomDiamondColorBitmap();
        }
    }


    public Diamond get_at(int index)
    {
        return diamonds.get(index);
    }

    private void setXLocation(){
        int bitmapWidth = diamonds.getFirst().getBitmapWidth();

        for(int i = 0; i < diamonds.size(); i++){
            diamonds.get(i).setXLocation(gameGrid.getDiamondXposition(bitmapWidth, i));
        }
    }

    public GameGrid getGameGrid(){
        return this.gameGrid;
    }

    public int getYLocation(int index){
        for(int i = 0; i < diamonds.size(); i++){ //going through entire linked list
            collectionYLocation = diamonds.get(i).getYLocation();
        }
        return collectionYLocation;
    }

    public void resetYLocation(){
       for(int i = 0; i < diamonds.size(); i++){
           diamonds.get(i).setYLocation(topOfScreen-bitmapHeight);
       }
    }

    public int getXLocation(int index){
        return get_at(index).getXLocation();
    }


    public void spawnDiamonds(Context context, int numDiamonds){
        for(int i = 0; i < numDiamonds; i++){ //we setting the size of entire collection here where they are created
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
            canvas.drawBitmap(bitmap, d.getXLocation(), d.getYLocation(), null);
        }
    }




}
