package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;

import java.util.Random;

public class GameView extends View {

    private Handler handler;
    private Runnable r;
    private DiamondCollection diamondCollection;
    private PlayerDiamond playerDiamond;
    private int numDiamonds = 5;
    private boolean maxScoresPrinted = false;
    private int screenX, screenY;
    private GameGrid gameGrid;
    private boolean initActionDown = false;
    private int initDownY;
    private int downX;
    private int downY;
    private int upX;
    private boolean launchSystemTimeAlreadyInitialized = false;
    Rect rect;
    Timer playerPullDownTimer;
    private boolean freezePlayerX = false;
    private boolean launchOccured = false;
    private int[] scoreNumsArr;
    Random randScore;
    Paint paint;
    private int MAX_SCORE = 20;
    private int launchLocation;
    private long initLaunchSystemTime;
    private int diamondWidth;
    private int diamondHeight;
    private int randomShakeNum;

    public GameView(Context context, int screenWidth, int screenHeight)  {
        super(context);
        playerPullDownTimer = new Timer();
        rect = new Rect();
        numDiamonds = 5;
        randScore = new Random(System.currentTimeMillis()); //setting the seed
        diamondCollection = new DiamondCollection(context, numDiamonds, screenWidth, screenHeight);
         //gamegrid created here
        playerDiamond = new PlayerDiamond
                (context
                ,diamondCollection.get_at(0).getBitmapWidth()
                , diamondCollection.get_at(0).getBitmapHeight(),
                screenWidth,
                screenHeight);

        screenX = screenWidth;
        screenY = screenHeight;
        scoreNumsArr = new int[numDiamonds];
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Math.round(screenWidth/14f));
        diamondWidth = diamondCollection.getGameGrid().getAxisLength();
        diamondHeight = playerDiamond.getBitmapHeight();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
                update();
            }
        };
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        handler.postDelayed(r, 1);

        diamondCollection.draw(canvas);
        playerDiamond.draw(canvas);
        for (int i = 0; i < numDiamonds; i++) {
            if(!maxScoresPrinted) {
                scoreNumsArr[i] = randScore.nextInt(MAX_SCORE);
            }

            canvas.drawText( "" + scoreNumsArr[i] , diamondCollection.getXLocation(i) + (diamondWidth/2), diamondCollection.getYLocation(i) + (diamondHeight/2), paint);

        }
        maxScoresPrinted = true;
    }

    public void vibrateForLaunch(){
        playerDiamond.rotateBitmap(randomShakeNum, playerDiamond.getDiamondBitmap());
    }

    private int RandGenerator(int lowerBound, int upperBound){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int num = rand.nextInt(upperBound-lowerBound) + lowerBound;
        return num;
    }

    public void update(){
        randomShakeNum = RandGenerator(-10,10);
        diamondCollection.moveDiamondsDownScreen();

        if(!launchOccured && playerDiamondReachedScreenBottom(playerDiamond.getRect().bottom)){
            this.vibrateForLaunch(); //vibrates diamond before it launches
        }


        if(launchOccured){ //only occurs when diamond launched
            playerDiamond.executeDiamondLaunch();
            playerDiamond.rotateBitmap(0, playerDiamond.getDiamondBitmap()); //resets diamond matrix angle to 0
        }

        if(diamondCollection.diamondCollectionMovedBelowScreen()){ //continue respawning diamonds from top of screen everytime they go below screen
            diamondCollection.resetYLocation(); //back to start
            diamondCollection.moveDiamondsDownScreen();
        }
        //checking for collision
        for(int i = 0; i < numDiamonds; i++) {
            if(playerAndCollectionCollisionOccurred(i)) {
                Toast.makeText(this.getContext(), "collision occurred!", Toast.LENGTH_SHORT).show();
                if(diamondCollisionColorsMatched(i)){ //we only check for color match upon collision
                    Toast.makeText(this.getContext(), "two same bitmap colors collided!", Toast.LENGTH_SHORT).show();
                    diamondCollection.createDiamondShatterAnimator(i);
                }
                if(launchOccured){ //if launch occurred and collided with diamond, bounce it back to start
                    playerDiamond.resetYLocation();
                    launchOccured = false; //resets upon collision and launch

                }
            }
        }
        handleLaunch();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameGrid = diamondCollection.getGameGrid();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                initDownY = (int) event.getY(); //getting initial tap location to know if he swipes down
                break;

                    case MotionEvent.ACTION_MOVE:
                            downX = (int) event.getX();
                            downY = (int) event.getY();

                        if(!playerDiamond.belowBaseline(downY)) { //he can only move x location while hes not launching
                            if (gameGrid.insideAxis(0, downX)) { //if his finger inside axis 1
                                movePlayerDiamondIntoCenterAxis(0); //move him to center of this axis
                            }
                            if (gameGrid.insideAxis(1, downX)) { //if his finger inside axis 2
                                movePlayerDiamondIntoCenterAxis(1); //move him to center of this axis
                            }
                            if (gameGrid.insideAxis(2, downX)) { //if his finger inside axis 3
                                movePlayerDiamondIntoCenterAxis(2); //move him to center of this axis
                            }
                            if (gameGrid.insideAxis(3, downX)) { //if his finger inside axis 4
                                movePlayerDiamondIntoCenterAxis(3); //move him to center of this axis
                            }
                            if (gameGrid.insideAxis(4, downX)) { //if his finger inside axis 5
                                movePlayerDiamondIntoCenterAxis(4); //move him to center of this axis
                            }
                        }
                        else{ //player diamond moving down for launch
                            playerDiamond.moveDownWithFinger(downY);
                            if(playerDiamondReachedScreenBottom(playerDiamond.getRect().bottom)) {
                                playerDiamond.setYLocation(screenY-playerDiamond.getBitmapHeight());

                            }
                        }
        }
        return true;
    }

    private boolean playerStayedInLaunchFor3Seconds(long initLaunchSystemTime, int launchLocation, int playerLocation){
        if(launchLocation == playerLocation && System.currentTimeMillis()-initLaunchSystemTime > 3000 ){ //3 seconds passed since he held down for launch
            return true;
        }
        else{
            return false;
        }
    }

    private boolean playerDiamondReachedScreenBottom(int playerDiamondY2){
        if(playerDiamondY2 >= screenY){
            return true;
        }
        else{
            return false;
        }
    }

    private void handleLaunch(){
        if(playerDiamondReachedScreenBottom(playerDiamond.getRect().bottom)){ //diamond y2 cant pass screen bottom while getting rdy to launch
            launchLocation = playerDiamond.getYLocation(); // we marking this point where his y is frozen
            if(!launchSystemTimeAlreadyInitialized) {
                initLaunchSystemTime = System.currentTimeMillis();
                launchSystemTimeAlreadyInitialized = true;
            }
            for(int i = 0; i < numDiamonds; i++) {
                if(playerStayedInLaunchFor3Seconds(initLaunchSystemTime, launchLocation, playerDiamond.getYLocation())) {
                    Toast.makeText(this.getContext(), "ready for launch!", Toast.LENGTH_SHORT).show();
                    launchOccured = true;//is true everytime he launches
                    launchSystemTimeAlreadyInitialized = false; //resetting this initlaunch system time bool
                }
            }
        }
    }

    private boolean diamondCollisionColorsMatched(int i){
        Bitmap diamondCollectionBitmapAtIndex = diamondCollection.getBitmapAtIndex(i);
        Bitmap playerDiamondBitmap = playerDiamond.getDiamondBitmap();

        if(diamondCollectionBitmapAtIndex.sameAs(playerDiamondBitmap)){ //we have the index of collision, check if these two bitmaps match
            return true;
        }
        else{
            return false;
        }
    }

    private boolean playerAndCollectionCollisionOccurred(int i) {
        if (Rect.intersects(diamondCollection.getRect(i), playerDiamond.getRect())) {
            return true;
        }
        else {
            return false;
        }
    }

    private void movePlayerDiamondIntoCenterAxis(int i){
        playerDiamond.setXLocation(diamondCollection.getXLocation(i)); //set him to same x location as specific diamondCollection index
    }

    public int distance(int var1, int var2){
        return Math.abs(var2 - var1);
    }

    private boolean fingerPullingDownForLaunch(int eventGetYInit, int eventGetYFinal){
        if(distance(eventGetYInit, eventGetYFinal) > 100 ){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean playerFingerOnDiamond(int eventGetX, int eventGetY){
        Rect playerDiamondRect = playerDiamond.getRect();
        if(playerDiamondRect.left <= eventGetX && playerDiamondRect.right >= eventGetX
        && playerDiamondRect.top <= eventGetY && playerDiamondRect.bottom >= eventGetY ){ //if touchevent coordinates are inside rectangle it returns true
            return true;
        }
        else{
            return false;
        }
    }

    private boolean motionIsSwipe(int x1, int x2) {
        if(distance(x1,x2) > 100){ //moved finger while down greater than 100 distance across screen
            return true;
        }
        else{
            return false;
        }

    }

}
