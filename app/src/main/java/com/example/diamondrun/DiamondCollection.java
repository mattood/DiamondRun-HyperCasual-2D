package com.example.diamondrun;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.LinkedList;

public class DiamondCollection {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    public int maxDiamondsNum = 5;
    private int diamondCollectionYSpeed = 5; //arbitrary
    GameGrid gameGrid;
    private int topOfScreen;
    private int speed = 0;

    DiamondCollection(){

        gameGrid = new GameGrid();
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        setCollectionXLocation();
        topOfScreen = 0;
    }

    private void setCollectionXLocation(){
        for(int i = 0; i < maxDiamondsNum-1; i++){
            diamonds.get(0).setXLocation(gameGrid.getCenterInsideAxis(diamonds.getFirst().getBitmapWidth(), gameGrid.axis1Interval)); //setting x in the center of axis1, they all same width so we use first
            diamonds.get(1).setXLocation(gameGrid.getCenterInsideAxis(diamonds.getFirst().getBitmapWidth(), gameGrid.axis2Interval)); //setting x in the center of axis2
            diamonds.get(2).setXLocation(gameGrid.getCenterInsideAxis(diamonds.getFirst().getBitmapWidth(), gameGrid.axis3Interval)); //setting x in the center of axis3
            diamonds.get(3).setXLocation(gameGrid.getCenterInsideAxis(diamonds.getFirst().getBitmapWidth(), gameGrid.axis4Interval)); //setting x in the center of axis4
            diamonds.get(4).setXLocation(gameGrid.getCenterInsideAxis(diamonds.getFirst().getBitmapWidth(), gameGrid.axis5Interval)); //setting x in the center of axis5
        }
    }

    public void spawnDiamonds(){
        for(int i = 0; i < maxDiamondsNum; i++){
            diamonds.add(new Diamond()); //spawn
            diamonds.get(i).setYLocation(topOfScreen - diamonds.get(i).getBitmapHeight()); //top of screen - diamond height to start above the screen
        }
    }

    public void moveDiamondsDownScreen(){
        for(int i = 0; i < maxDiamondsNum; i++){
            diamonds.get(i).setYLocation(diamonds.get(i).getYLocation() + this.speed);

        }
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < maxDiamondsNum-1; i++) {
            canvas.drawBitmap(diamonds.get(i).getRandomDiamondBitmapColor(), diamonds.get(i).getXLocation(), diamonds.get(i).getYLocation(), null);
        }
    }




}
