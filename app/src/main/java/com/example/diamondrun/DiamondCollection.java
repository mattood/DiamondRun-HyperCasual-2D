package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DiamondCollection<num> {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    private int diamondCollectionYSpeed = 5; //arbitrary
    GameGrid gameGrid;
    public LinkedList<Bitmap> bitmaps;
    private int topOfScreen = 0;
    private int bottomOfScreen;
    private int bitmapWidth = 0;
    private int numDiamonds;
    private int bitmapHeight = 150;
    private int collectionYLocation;
    private boolean initDiamondBitmap = true;
    private Rect rect;


    DiamondCollection(Context context, int numDiamonds, int screenWidth, int screenHeight) {
        bitmapWidth = screenWidth / numDiamonds;
        this.numDiamonds = numDiamonds;
        bottomOfScreen = screenHeight;
        gameGrid = new GameGrid(numDiamonds, screenWidth);
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        bitmaps = new LinkedList<Bitmap>();
        spawnDiamonds(context, numDiamonds);
    }





    public Diamond get_at(int index) {
        return diamonds.get(index);
    }

    public Rect getRect(int i) { //pass in index upon call
        int bitmapHeight = this.get_at(i).getBitmapHeight();

        rect = new Rect((int) this.get_at(i).getXLocation(), (int) this.get_at(i).getYLocation(),
                (int) this.get_at(i).getXLocation() + this.bitmapWidth,
                (int) this.get_at(i).getYLocation() + bitmapHeight);
        return rect;
    }

    private void setXLocation() {
        int bitmapWidth = diamonds.getFirst().getBitmapWidth();

        for (int i = 0; i < diamonds.size(); i++) {
            diamonds.get(i).setXLocation(gameGrid.getDiamondXposition(bitmapWidth, i));
        }
    }

    public GameGrid getGameGrid() {
        return this.gameGrid;
    }

    public int getYLocation(int index) {
        for (int i = 0; i < diamonds.size(); i++) { //going through entire linked list
            collectionYLocation = diamonds.get(i).getYLocation();
        }
        return collectionYLocation;
    }

    public void resetYLocation() {
        for (int i = 0; i < diamonds.size(); i++) {
            diamonds.get(i).setYLocation(topOfScreen - bitmapHeight);
        }
    }

    public Bitmap getBitmapAtIndex(int i) {
        return this.get_at(i).getDiamondBitmap();
    }

    public int getXLocation(int index) {
        return get_at(index).getXLocation();
    }

    public void spawnDiamonds(Context context, int numDiamonds) {
        for (int i = 0; i < numDiamonds; i++) { //we setting the size of entire collection here where they are created
            Diamond d = new Diamond(context, bitmapWidth, bitmapHeight);
            d.setYLocation(topOfScreen - d.getBitmapHeight());
            diamonds.add(d);
        }
        setXLocation();
    }

    public void moveDiamondsDownScreen() {
        for (int i = 0; i < diamonds.size(); i++) { //going through entire linked list
            diamonds.get(i).setYLocation(diamonds.get(i).getYLocation() + this.diamondCollectionYSpeed);
        }
    }

    public boolean diamondCollectionMovedBelowScreen(){
        for(int i=0; i < numDiamonds; i++){ //axis length is number of diamonds
            if(this.getYLocation(1) > bottomOfScreen){ //all index have same y location so using 1
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public void draw(Canvas canvas) {

        if (initDiamondBitmap) {
            bitmaps.clear();
        }

        for (int i = 0; i < diamonds.size(); i++) {
            Diamond d = diamonds.get(i);
            Bitmap bitmap;

            if (initDiamondBitmap) {
                bitmap = d.getRandomDiamondColorBitmap();
                bitmaps.add(bitmap);


            } else {
                bitmap = bitmaps.get(i);

            }
            canvas.drawBitmap(bitmap, d.getXLocation(), d.getYLocation(), null);

        }
        initDiamondBitmap = false;
    }
}



