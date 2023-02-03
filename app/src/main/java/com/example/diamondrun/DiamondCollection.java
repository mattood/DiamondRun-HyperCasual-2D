package com.example.diamondrun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.LinkedList;

public class DiamondCollection<num> {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    Paint paint;
    private int diamondCollectionYSpeed = 3;
    public int diamondCollectionMaxSpeed = 13;
    public int diamondCollectionMinSpeed = 3;
    public boolean initResetBitmapsColors = false;
    GameGrid gameGrid;
    boolean initShatterDiamonds = false;
    public LinkedList<Bitmap> bitmaps;
    private int topOfScreen = 0;
    private int bottomOfScreen;
    private int bitmapWidth = 0;
    private int numDiamonds;
    private int bitmapHeight;
    private int collectionYLocation;
    private boolean initGreenDiamonds, initRedDiamonds, initPurpleDiamonds, initYellowDiamonds, initBlueDiamonds = false;
    private Rect rect;
    private Bitmap bitmap;
    private int redDiamondShatterFramesNum = 28;
    private Bitmap[] shatterRed = new Bitmap[redDiamondShatterFramesNum];
    private Bitmap[] shatterGreen = new Bitmap[redDiamondShatterFramesNum];
    private Bitmap[] shatterPurple = new Bitmap[redDiamondShatterFramesNum];
    private Bitmap[] shatterYellow = new Bitmap[redDiamondShatterFramesNum];
    private Bitmap[] shatterBlue = new Bitmap[redDiamondShatterFramesNum];
    public boolean startShatter = false;
    public ArrayList<Bitmap> arrGreenBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrRedBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrPurpleBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrYellowBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrBlueBitmaps = new ArrayList<>();
    public ArrayList<Integer> redShatterResourceIdsArr, greenShatterResourceIdsArr, blueShatterResourceIdsArr, yellowShatterResourceIdsArr, purpleShatterResourceIdsArr;
    public ArrayList<Integer> diamondScoreArr;
    public int maxShatterIndex = shatterRed.length-1;
    public int count, animationIncrementDelay, idCurrentBitmap;

    DiamondCollection(Context context, int numDiamonds, int screenWidth, int screenHeight, GameGrid gameGrid) {
        this.gameGrid = gameGrid;
        redShatterResourceIdsArr = new ArrayList<>();
        greenShatterResourceIdsArr = new ArrayList<>();
        blueShatterResourceIdsArr = new ArrayList<>();
        purpleShatterResourceIdsArr = new ArrayList<>();
        yellowShatterResourceIdsArr = new ArrayList<>();
        bitmapWidth = screenWidth / numDiamonds;
        bitmapHeight = screenWidth/4;
        this.numDiamonds = numDiamonds;
        bottomOfScreen = screenHeight;
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        bitmaps = new LinkedList<Bitmap>();
        Typeface typefaceBadaboom = ResourcesCompat.getFont(context, R.font.badaboom);
        String s = "test";

        spawnDiamonds(context, numDiamonds);
        diamondScoreArr = new ArrayList<>(numDiamonds);
        initDiamondScoresArr();
        paint = new Paint();
        paint.setTypeface(typefaceBadaboom);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Math.round(screenWidth/14f));
        paint.setColor(Color.BLACK);
        retrieveFolderResourceIds(context);
        initShatterDiamondBitmaps(context);
        this.count = 0;
        this.animationIncrementDelay = 5;
        this.idCurrentBitmap = 0;
    }

    public void initDiamondScoresArr(){
        for(int i = 0; i < numDiamonds;i++){
            diamondScoreArr.add(diamonds.get(i).diamondScore);
        }
    }

    public void resetDiamondScoresArr(){
        diamondScoreArr.clear(); //clearing old array
        for(int i = 0; i < numDiamonds;i++){ //adding new scores
            diamondScoreArr.add(diamonds.get(i).getRandDiamondScore());
        }
    }

    public void retrieveFolderResourceIds(Context context){

        for(int i = 1; i <= redDiamondShatterFramesNum; i++){
            redShatterResourceIdsArr.add(context.getResources().getIdentifier("reddiamondshatteringpt" + i, "drawable", context.getPackageName()));
        }

        for(int i = 1; i <= redDiamondShatterFramesNum; i++){
            greenShatterResourceIdsArr.add(context.getResources().getIdentifier("greendiamondshatteringpt"+i, "drawable", context.getPackageName()));
        }
        for(int i = 1; i <= redDiamondShatterFramesNum; i++){
            yellowShatterResourceIdsArr.add(context.getResources().getIdentifier("yellowdiamondshatteringpt"+i, "drawable", context.getPackageName()));
        }
        for(int i = 1; i <= redDiamondShatterFramesNum; i++){
            blueShatterResourceIdsArr.add(context.getResources().getIdentifier("bluediamondshatteringpt"+i, "drawable", context.getPackageName()));
        }
        for(int i = 1; i <= redDiamondShatterFramesNum; i++){
            purpleShatterResourceIdsArr.add(context.getResources().getIdentifier("purplediamondshatteringpt"+i, "drawable", context.getPackageName()));
        }
    }

    public void initShatterDiamondBitmaps(Context context) {

        Resources res = context.getResources();

        int size = redShatterResourceIdsArr.size();

        for(int i = 0; i < size; i++) {
            //Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, redShatterResourceIdsArr.get(i)), bitmapWidth, bitmapHeight, false);
            Bitmap temp = ((BitmapDrawable) ResourcesCompat.getDrawable(res, redShatterResourceIdsArr.get(i), null)).getBitmap();
            temp = Bitmap.createScaledBitmap(temp, (bitmapWidth), bitmapHeight, false);

            shatterRed[i] = temp;

        }
        for(int i = 0; i < size; i++){
            //Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, redShatterResourceIdsArr.get(i)), bitmapWidth, bitmapHeight, false);
            Bitmap temp = ((BitmapDrawable) ResourcesCompat.getDrawable(res, purpleShatterResourceIdsArr.get(i), null)).getBitmap();
            temp = Bitmap.createScaledBitmap(temp, (bitmapWidth), bitmapHeight, false);

            shatterPurple[i] = temp;
        }
        for(int i = 0; i < size; i++){
            //Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, redShatterResourceIdsArr.get(i)), bitmapWidth, bitmapHeight, false);
            Bitmap temp = ((BitmapDrawable) ResourcesCompat.getDrawable(res, greenShatterResourceIdsArr.get(i), null)).getBitmap();
            temp = Bitmap.createScaledBitmap(temp, (bitmapWidth), bitmapHeight, false);

            shatterGreen[i] = temp;
        }
        for(int i = 0; i < size; i++){
            //Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, redShatterResourceIdsArr.get(i)), bitmapWidth, bitmapHeight, false);
            Bitmap temp = ((BitmapDrawable) ResourcesCompat.getDrawable(res, blueShatterResourceIdsArr.get(i), null)).getBitmap();
            temp = Bitmap.createScaledBitmap(temp, (bitmapWidth), bitmapHeight, false);

            shatterBlue[i] = temp;
        }
        for(int i = 0; i < size; i++){
            //Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, redShatterResourceIdsArr.get(i)), bitmapWidth, bitmapHeight, false);
            Bitmap temp = ((BitmapDrawable) ResourcesCompat.getDrawable(res, yellowShatterResourceIdsArr.get(i), null)).getBitmap();
            temp = Bitmap.createScaledBitmap(temp, (bitmapWidth), bitmapHeight, false);

            shatterYellow[i] = temp;
        }


    }

    public int getCollectionSpeed(){
        return this.diamondCollectionYSpeed;
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

    public int getCenterXLocation(int index){
        int rightX = get_at(index).getXLocation() + this.bitmapWidth;
        return (get_at(index).getXLocation() + rightX) / 2;
    }

    /*public void evaluateMultiplierActive(Boolean multiplierActive){
        if(multiplierActive) {
            for (int i = 0; i < diamonds.size(); i++) {
                diamonds.get(i).multiplier = 1; //setting all diamond multiplier to one so its inactive until timer up
            }
        }
    }*/

    public void spawnDiamonds(Context context, int numDiamonds) {
        for (int i = 0; i < numDiamonds; i++) { //we setting the size of entire collection here where they are created
            Diamond d = new Diamond(context, bitmapWidth, bitmapHeight);
            d.setYLocation(topOfScreen - d.getBitmapHeight());
            diamonds.add(d);
        }
        setXLocation();
    }

    /*public void resetDiamondMultiplier() {
        for (int i = 0; i < diamonds.size(); i++) { //going through entire linked list
            diamonds.get(i).resetMultiplier();
        }
    }*/

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

    public void increaseSpeed(){
        this.diamondCollectionYSpeed+=1;
    }

    public void decreaseSpeed(){
        this.diamondCollectionYSpeed-=1;
    }


    public Bitmap getBitmapAnimated(ArrayList<Bitmap> arrBitmaps) { //animate shatter
        try{
        count++;
        if (this.count == this.animationIncrementDelay) {
            for (int i = 0; i < arrBitmaps.size(); i++) {
                if (i == arrBitmaps.size() - 1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i + 1; //why breaks at 56
                    break;
                }
            }
            count = 0;
        }

        }
        catch(Exception e){
            Log.d("error", e.getMessage());
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
            for(int g = 0; g < shatterGreen.length; g++){
                this.arrGreenBitmaps.add(shatterGreen[g]); //adding all 26 shatter green bitmaps into the arraylist
            }
            this.initGreenDiamonds = true;
        }

        if(!initPurpleDiamonds){//preventing duplicates
            for(int p = 0; p < shatterPurple.length; p++){
                this.arrPurpleBitmaps.add(shatterPurple[p]); //adding all 26 shatter purple bitmaps into the arraylist
            }
            this.initPurpleDiamonds = true;
        }

        if(!initYellowDiamonds){//preventing duplicates
            for(int y = 0; y < shatterYellow.length; y++){
                this.arrYellowBitmaps.add(shatterYellow[y]); //adding all 26 shatter yellow bitmaps into the arraylist
            }
            this.initYellowDiamonds = true;
        }

        if(!initBlueDiamonds){//preventing duplicates
            for(int b = 0; b < shatterBlue.length; b++){
                this.arrBlueBitmaps.add(shatterBlue[b]); //adding all 26 shatter blue bitmaps into the arraylist
            }
            this.initBlueDiamonds = true;
        }
    }

    private boolean shatterAnimationComplete(ArrayList<Bitmap> arrBitmaps){
        if(idCurrentBitmap == arrBitmaps.size()-1){
            return true;
        }
        else{
            return false;
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
                else if(this.bitmaps.get(i).sameAs(d.purpleDiamondBitmap)){//if current index is purple diamond then shatter animate purple
                    canvas.drawBitmap(this.getBitmapAnimated(arrPurpleBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.yellowDiamondBitmap)){//if current index is yellow diamond then shatter animate yellow
                    canvas.drawBitmap(this.getBitmapAnimated(arrYellowBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.blueDiamondBitmap)){//if current index is blue diamond then shatter animate blue
                    canvas.drawBitmap(this.getBitmapAnimated(arrBlueBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
            }
        }
        else { //when not doing shatter animation
            if(!initResetBitmapsColors) { //resetting random bitmap colors every spawn
                bitmaps.clear();
                initResetBitmapsColors = true;
            }
            for (int i = 0; i < diamonds.size(); i++) {
                Diamond d = diamonds.get(i);
                this.setBitmap(d.getRandomDiamondColorBitmap()); //setting bitmap variable to a random color bitmap
                if (bitmaps.size() < numDiamonds) {
                    bitmaps.add(this.bitmap); // adding each new bitmap to linked list
                }
                canvas.drawBitmap(bitmaps.get(i), d.getXLocation(), d.getYLocation(), null);
                /*if(d.hasMultiplier()){ //it has a multiplier
                    canvas.drawText("x" + d.multiplier, d.getXLocation() + (bitmapWidth/2), d.getYLocation() + (bitmapHeight/2), paint);
                }*/
                //if its 1 its not a multiplier
                canvas.drawText("" + diamondScoreArr.get(i), d.getXLocation() + (bitmapWidth/ 2), d.getYLocation() + (bitmapHeight/ 2), paint);

            }
        }
    }
}



