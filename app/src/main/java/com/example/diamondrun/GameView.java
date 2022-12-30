package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;

import java.util.LinkedList;
import java.util.Random;

public class GameView extends View implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    private Handler handler;
    private boolean diamondCollectionNotVisible = false;
    private Runnable r;
    private DiamondCollection diamondCollection;
    private PlayerDiamond playerDiamond;
    private int numDiamonds = 5;
    private boolean maxScoresPrinted = false;
    private int screenX, screenY;
    private GameGrid gameGrid;
    private boolean initActionDown = false;
    private int initDownY;
    private int initDownX;
    private int downX;
    private int downY;
    private int upX;
    private boolean launchSystemTimeAlreadyInitialized = false;
    Rect rect;
    Timer playerPullDownTimer;
    private boolean freezePlayerX = false;
    private boolean flickOccured = false;
    private int[] randScoreNumsArr;
    Random randScore;
    Paint paint;
    Paint paintLine;
    private int MAX_SCORE = 20;
    private int launchLocation;
    private long initLaunchSystemTime;
    private int diamondWidth;
    private int diamondHeight;
    private int randomShakeNum;
    private int clickCount = 0;
    private long firstClickTime;
    int PLAYER_SCORE = 0;
    private boolean playerIsMoving = false;
    GestureDetector gestureDetector;

    //colors need to be inserted in a stack order or a queue for the double tap

    public GameView(Context context, int screenWidth, int screenHeight)  {
        super(context);
        playerPullDownTimer = new Timer();
        gestureDetector = new GestureDetector(this.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
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
        randScoreNumsArr = new int[numDiamonds];
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Math.round(screenWidth/14f));
        paintLine = new Paint();
        paint.setColor(Color.BLACK);
        paintLine.setStrokeWidth(10);
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
        canvas.drawLine(0,150, screenX,150, paintLine);
        if(diamondCollectionNotVisible == false) { //shatter fully animated on collision, next spawn we will set it true
            diamondCollection.draw(canvas);
        }
        playerDiamond.draw(canvas);
        for (int i = 0; i < numDiamonds; i++) {
            if(!maxScoresPrinted) { //draws a score to each diamond and then stops, we gonna have to reset the false somewhere
                randScoreNumsArr[i] = randScore.nextInt(MAX_SCORE);
            }
            if(diamondCollectionNotVisible == false) {
                canvas.drawText("" + randScoreNumsArr[i], diamondCollection.getXLocation(i) + (diamondWidth / 2), diamondCollection.getYLocation(i) + (diamondHeight / 2), paint);
            }
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

        if(flickOccured){ //only occurs when diamond launched
            playerDiamond.executeDiamondLaunch(); //launches diamond up until it collides

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
                    PLAYER_SCORE += randScoreNumsArr[i-1]; //updating score with whatever color collision matched
                }
                if(flickOccured){ //if flick occurred and collided with diamond, bounce it back to start
                    playerDiamond.resetYLocation();
                    flickOccured = false; //resets upon collision and launch

                }
            }
        }

        if(diamondCollection.idCurrentBitmap == diamondCollection.maxShatterIndex){ //shatter animation fully completed
            diamondCollectionNotVisible = true;//shatter has been fully animated, set invisible
            diamondCollection.idCurrentBitmap = 0; //reset the current bitmap
            diamondCollection.startShatter = false; //stop shatter effect before next spawn
            diamondCollection.resetYLocation(); //back to top of screen
            diamondCollection.initResetBitmapsColors = false; //animation has completed so reset collection random colors
            diamondCollectionNotVisible = false; //make visible again
            diamondCollection.moveDiamondsDownScreen();
        }

    }

    private boolean playerStayedInLaunchFor1Second(long initLaunchSystemTime, int launchLocation, int playerLocation){
        if(launchLocation == playerLocation && System.currentTimeMillis()-initLaunchSystemTime > 1000 ){ //3 seconds passed since he held down for launch
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

    private boolean playerDiamondAboveBaseline(int playerDiamondY1){
        if(playerDiamondY1 <= playerDiamond.getBaseStartLine()){
            return true;
        }
        else{
            return false;
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameGrid = diamondCollection.getGameGrid();
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Toast.makeText(this.getContext(), "onSingleTapConfirmed!", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        playerDiamond.getChangedColorBitmap(); //changes player diamond color to a random color thats not the same
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this.getContext(), "onDown!", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        onFingerSlidingLeftOrRight(e2); //moves cleanly across x into correct axis
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private boolean playerFlickedFingerUp(MotionEvent e1, MotionEvent e2){
        if(distance((int)e1.getY(), (int)e2.getY()) > 200){ //make universal later
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(playerFlickedFingerUp(e1,e2)) { //ONLY launches when he flicks across y axis
            flickOccured = true; //starts the launch
        }
        return false;
    }

    public void onFingerSlidingLeftOrRight(MotionEvent e2) {
        if (gameGrid.insideAxis(0, (int) e2.getX())) { //if his finger inside axis 1
            movePlayerDiamondIntoCenterAxis(0); //move him to center of this axis
        }
        if (gameGrid.insideAxis(1, (int) e2.getX())) { //if his finger inside axis 2
            movePlayerDiamondIntoCenterAxis(1); //move him to center of this axis
        }
        if (gameGrid.insideAxis(2, (int) e2.getX())) { //if his finger inside axis 3
            movePlayerDiamondIntoCenterAxis(2); //move him to center of this axis
        }
        if (gameGrid.insideAxis(3, (int) e2.getX())) { //if his finger inside axis 4
            movePlayerDiamondIntoCenterAxis(3); //move him to center of this axis
        }
        if (gameGrid.insideAxis(4, (int) e2.getX())) { //if his finger inside axis 5
            movePlayerDiamondIntoCenterAxis(4); //move him to center of this axis
        }
    }

}


