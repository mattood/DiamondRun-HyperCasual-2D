package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.LinkedList;

public class DiamondCollection<num> {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    private int diamondCollectionYSpeed = 5; //arbitrary
    GameGrid gameGrid;
    boolean initShatterDiamonds = false;
    public LinkedList<Bitmap> bitmaps;
    private int topOfScreen = 0;
    private int bottomOfScreen;
    private int bitmapWidth = 0;
    private int numDiamonds;
    private int bitmapHeight = 150;
    private int collectionYLocation;
    private boolean initDiamondBitmap = true;
    private boolean initGreenDiamonds, initRedDiamonds, initPurpleDiamonds = false;

    private Rect rect;
    private Bitmap bitmap;
    private Bitmap[] shatterRed = new Bitmap[26];
    private Bitmap[] shatterGreen = new Bitmap[26];
    private Bitmap[] shatterPurple = new Bitmap[26];
    private Bitmap shatterGreenPt1;
    private Bitmap shatterGreenPt2;
    private Bitmap shatterGreenPt3;
    private Bitmap shatterGreenPt4;
    private Bitmap shatterGreenPt5;
    private Bitmap shatterGreenPt6;
    private Bitmap shatterGreenPt7;
    private Bitmap shatterGreenPt8;
    private Bitmap shatterGreenPt9;
    private Bitmap shatterGreenPt10;
    private Bitmap shatterGreenPt11;
    private Bitmap shatterGreenPt12;
    private Bitmap shatterGreenPt13;
    private Bitmap shatterGreenPt14;
    private Bitmap shatterGreenPt15;
    private Bitmap shatterGreenPt16;
    private Bitmap shatterGreenPt17;
    private Bitmap shatterGreenPt18;
    private Bitmap shatterGreenPt19;
    private Bitmap shatterGreenPt20;
    private Bitmap shatterGreenPt21;
    private Bitmap shatterGreenPt22;
    private Bitmap shatterGreenPt23;
    private Bitmap shatterGreenPt24;
    private Bitmap shatterGreenPt25;
    private Bitmap shatterGreenPt26;
    private int MAXCOLORSSIZE = 5;
    public boolean startShatter = false;
    private ArrayList<Bitmap> arrGreenBitmaps = new ArrayList<>();
    private ArrayList<Bitmap> arrRedBitmaps = new ArrayList<>();
    private ArrayList<Bitmap> arrPurpleBitmaps = new ArrayList<>();
    private int count, vFlap, idCurrentBitmap;

    DiamondCollection(Context context, int numDiamonds, int screenWidth, int screenHeight) {
        bitmapWidth = screenWidth / numDiamonds;
        this.numDiamonds = numDiamonds;
        bottomOfScreen = screenHeight;
        gameGrid = new GameGrid(numDiamonds, screenWidth);
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        bitmaps = new LinkedList<Bitmap>();
        initShatterDiamondBitmaps(context);
        spawnDiamonds(context, numDiamonds);
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
    }

    public void initShatterDiamondBitmaps(Context context) {
        //red init
        shatterRed[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt1), bitmapWidth, bitmapHeight, true);
        shatterRed[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt2), bitmapWidth, bitmapHeight, true);
        shatterRed[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt3), bitmapWidth, bitmapHeight, true);
        shatterRed[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt4), bitmapWidth, bitmapHeight, true);
        shatterRed[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt5), bitmapWidth, bitmapHeight, true);
        shatterRed[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt6), bitmapWidth, bitmapHeight, true);
        shatterRed[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt7), bitmapWidth, bitmapHeight, true);
        shatterRed[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt8), bitmapWidth, bitmapHeight, true);
        shatterRed[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt9), bitmapWidth, bitmapHeight, true);
        shatterRed[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt10), bitmapWidth, bitmapHeight, true);
        shatterRed[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt11), bitmapWidth, bitmapHeight, true);
        shatterRed[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt12), bitmapWidth, bitmapHeight, true);
        shatterRed[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt13), bitmapWidth, bitmapHeight, true);
        shatterRed[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt14), bitmapWidth, bitmapHeight, true);
        shatterRed[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt15), bitmapWidth, bitmapHeight, true);
        shatterRed[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt16), bitmapWidth, bitmapHeight, true);
        shatterRed[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt17), bitmapWidth, bitmapHeight, true);
        shatterRed[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt18), bitmapWidth, bitmapHeight, true);
        shatterRed[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt19), bitmapWidth, bitmapHeight, true);
        shatterRed[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt20), bitmapWidth, bitmapHeight, true);
        shatterRed[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt21), bitmapWidth, bitmapHeight, true);
        shatterRed[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt22), bitmapWidth, bitmapHeight, true);
        shatterRed[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt23), bitmapWidth, bitmapHeight, true);
        shatterRed[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt24), bitmapWidth, bitmapHeight, true);
        shatterRed[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt25), bitmapWidth, bitmapHeight, true);
        shatterRed[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt26), bitmapWidth, bitmapHeight, true);


        //green init
        shatterGreen[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt1), bitmapWidth, bitmapHeight, true);
        shatterGreen[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt2), bitmapWidth, bitmapHeight, true);
        shatterGreen[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt3), bitmapWidth, bitmapHeight, true);
        shatterGreen[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt4), bitmapWidth, bitmapHeight, true);
        shatterGreen[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt5), bitmapWidth, bitmapHeight, true);
        shatterGreen[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt6), bitmapWidth, bitmapHeight, true);
        shatterGreen[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt7), bitmapWidth, bitmapHeight, true);
        shatterGreen[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt8), bitmapWidth, bitmapHeight, true);
        shatterGreen[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt9), bitmapWidth, bitmapHeight, true);
        shatterGreen[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt10), bitmapWidth, bitmapHeight, true);
        shatterGreen[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt11), bitmapWidth, bitmapHeight, true);
        shatterGreen[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt12), bitmapWidth, bitmapHeight, true);
        shatterGreen[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt13), bitmapWidth, bitmapHeight, true);
        shatterGreen[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt14), bitmapWidth, bitmapHeight, true);
        shatterGreen[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt15), bitmapWidth, bitmapHeight, true);
        shatterGreen[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt16), bitmapWidth, bitmapHeight, true);
        shatterGreen[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt17), bitmapWidth, bitmapHeight, true);
        shatterGreen[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt18), bitmapWidth, bitmapHeight, true);
        shatterGreen[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt19), bitmapWidth, bitmapHeight, true);
        shatterGreen[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt20), bitmapWidth, bitmapHeight, true);
        shatterGreen[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt21), bitmapWidth, bitmapHeight, true);
        shatterGreen[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt22), bitmapWidth, bitmapHeight, true);
        shatterGreen[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt23), bitmapWidth, bitmapHeight, true);
        shatterGreen[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt24), bitmapWidth, bitmapHeight, true);
        shatterGreen[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt25), bitmapWidth, bitmapHeight, true);
        shatterGreen[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt26), bitmapWidth, bitmapHeight, true);

        //purple init
        shatterPurple[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept1), bitmapWidth, bitmapHeight, true);
        shatterPurple[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept2), bitmapWidth, bitmapHeight, true);
        shatterPurple[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept3), bitmapWidth, bitmapHeight, true);
        shatterPurple[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept4), bitmapWidth, bitmapHeight, true);
        shatterPurple[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept5), bitmapWidth, bitmapHeight, true);
        shatterPurple[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept6), bitmapWidth, bitmapHeight, true);
        shatterPurple[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept7), bitmapWidth, bitmapHeight, true);
        shatterPurple[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept8), bitmapWidth, bitmapHeight, true);
        shatterPurple[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept9), bitmapWidth, bitmapHeight, true);
        shatterPurple[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept10), bitmapWidth, bitmapHeight, true);
        shatterPurple[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept11), bitmapWidth, bitmapHeight, true);
        shatterPurple[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept12), bitmapWidth, bitmapHeight, true);
        shatterPurple[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept13), bitmapWidth, bitmapHeight, true);
        shatterPurple[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept14), bitmapWidth, bitmapHeight, true);
        shatterPurple[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept15), bitmapWidth, bitmapHeight, true);
        shatterPurple[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept16), bitmapWidth, bitmapHeight, true);
        shatterPurple[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept17), bitmapWidth, bitmapHeight, true);
        shatterPurple[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept18), bitmapWidth, bitmapHeight, true);
        shatterPurple[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept19), bitmapWidth, bitmapHeight, true);
        shatterPurple[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept20), bitmapWidth, bitmapHeight, true);
        shatterPurple[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept21), bitmapWidth, bitmapHeight, true);
        shatterPurple[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept22), bitmapWidth, bitmapHeight, true);
        shatterPurple[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept23), bitmapWidth, bitmapHeight, true);
        shatterPurple[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept24), bitmapWidth, bitmapHeight, true);
        shatterPurple[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept25), bitmapWidth, bitmapHeight, true);
        shatterPurple[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept26), bitmapWidth, bitmapHeight, true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
        return this.bitmaps.get(i);
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

    public boolean diamondCollectionMovedBelowScreen() {
        for (int i = 0; i < numDiamonds; i++) { //axis length is number of diamonds
            if (this.getYLocation(1) > bottomOfScreen) { //all index have same y location so using 1
                return true;
            } else {
                return false;
            }
        }
        return false;
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

    public void createDiamondShatterAnimator(int i) {
        this.startShatter = true; //gets set true in updated

        if(!initRedDiamonds) { //preventing duplicates
            for(int r = 0; r < shatterRed.length; r++){
                this.arrRedBitmaps.add(shatterRed[r]); //adding all 26 shatter red bitmaps into the arraylist
            }
            this.initRedDiamonds = true;
        }

        if(!initGreenDiamonds) { //preventing duplicates
            for(int g = 0; g < shatterRed.length; g++){
                this.arrGreenBitmaps.add(shatterGreen[g]); //adding all 26 shatter red bitmaps into the arraylist
            }
            this.initGreenDiamonds = true;
        }

        if(!initPurpleDiamonds){//preventing duplicates
            for(int p = 0; p < shatterRed.length; p++){
                this.arrPurpleBitmaps.add(shatterPurple[p]); //adding all 26 shatter red bitmaps into the arraylist
            }
            this.initPurpleDiamonds = true;
        }
    }


    public void draw(Canvas canvas) {
        if (startShatter) { //collided correct color now animate shatter
            for (int i = 0; i < diamonds.size(); i++) {
                Diamond d = diamonds.get(i);
                if(!initShatterDiamonds) {
                    createDiamondShatterAnimator(i); //prevent duplicates
                    initShatterDiamonds = true;
                }
                if(this.bitmaps.get(i).sameAs(d.redDiamondBitmap)) { //if current index is red diamond then shatter animate red
                    canvas.drawBitmap(this.getBitmapAnimated(arrRedBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.greenDiamondBitmap)){//if current index is green diamond then shatter animate green
                    canvas.drawBitmap(this.getBitmapAnimated(arrGreenBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.purpleDiamondBitmap)){//if current index is green diamond then shatter animate green
                    canvas.drawBitmap(this.getBitmapAnimated(arrPurpleBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
            }
        } else {
            for (int i = 0; i < diamonds.size(); i++) {
                Diamond d = diamonds.get(i);
                this.setBitmap(d.getRandomDiamondColorBitmap()); //setting bitmap variable to a random color bitmap
                if (bitmaps.size() < numDiamonds) {
                    bitmaps.add(this.bitmap); // adding each new bitmap to linked list
                }
                canvas.drawBitmap(bitmaps.get(i), d.getXLocation(), d.getYLocation(), null);
            }
        }
    }
}



