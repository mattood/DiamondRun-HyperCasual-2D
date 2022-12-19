package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private int screenX, screenY;
    private GameGrid gameGrid;
    private boolean initActionDown = false;
    private int initDownY;
    private int downX;
    private int downY;
    private int upX;
    Rect rect;
    Timer playerPullDownTimer;
    private boolean freezePlayerX = false;
    private int[] scoreNumsArr;
    private int bitmapScore;
    Paint paint;
    Random rand;

    public GameView(Context context, int screenWidth, int screenHeight)  {
        super(context);
        playerPullDownTimer = new Timer();
        rect = new Rect();
        numDiamonds = 5;
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

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(Math.round(screenWidth/14f));

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

        Random randTemp = new Random();
        bitmapScore = TestRandGenerator();
        for(int i = 0; i < numDiamonds; i++){

            canvas.drawText("" + bitmapScore, diamondCollection.getXLocation(i), diamondCollection.getYLocation(i), paint);
        }


    }

    public void update(){

        diamondCollection.moveDiamondsDownScreen();
        if(diamondCollection.diamondCollectionMovedBelowScreen()){ //continue respawning diamonds from top of screen everytime they go below screen
            diamondCollection.resetYLocation();
            diamondCollection.moveDiamondsDownScreen();
        }
        //checking for collision
        for(int i = 0; i < numDiamonds; i++) {
            if(playerAndCollectionCollisionOccurred(i)) {
                Toast.makeText(this.getContext(), "collision occurred!", Toast.LENGTH_SHORT).show(); //google play sign in successful
                if(diamondCollisionColorsMatched(i)){ //we only check for color match upon collision
                    Toast.makeText(this.getContext(), "two same bitmap colors collided!", Toast.LENGTH_SHORT).show();
                    //shatter animation goes here-matthew
                }
            }
        }
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
                        else{
                            playerDiamond.moveDownWithFinger(downY);
                        }
        }
        return true;
    }


    public int TestRandGenerator() {
        int lowerBound = 1;
        int upperBound = 20;
        rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        return rand.nextInt(upperBound-lowerBound) + lowerBound;
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
