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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameView extends View implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    private Handler handler;
    int xMiddleOfScoreXAndScreenX;
    int yMiddleOfScoreYAndScreenY;
    private long currentTime = System.currentTimeMillis();
    private boolean feedScores = false;
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
    Paint paintScore;
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
    int scorePaintX, scorePaintY;
    DiamondShard diamondShard;
    DiamondShard scoreKeeperShard;
    public ArrayList<DiamondShard> arrDiamondShards = new ArrayList<>();
    int initShardIndex = 0;
    Bitmap tempScoreShardTransparent;

    public GameView(Context context, int screenWidth, int screenHeight)  {
        super(context);
        playerPullDownTimer = new Timer();
        gestureDetector = new GestureDetector(this.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
        rect = new Rect();
        numDiamonds = 5;
        randScore = new Random(System.currentTimeMillis()); //setting the seed
        diamondCollection = new DiamondCollection(context, numDiamonds, screenWidth, screenHeight);
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
        paintLine.setStrokeWidth(8);
        paintScore = new Paint();
        paintScore.setColor(Color.BLACK);
        paintScore.setTextSize(Math.round(screenWidth/14f));
        diamondWidth = diamondCollection.getGameGrid().getAxisLength();
        diamondHeight = playerDiamond.getBitmapHeight();
        scorePaintX = (screenX*2)/3;
        xMiddleOfScoreXAndScreenX = (screenX + scorePaintX)/2;

        scorePaintY = screenY/18;
        scoreKeeperShard = new DiamondShard(xMiddleOfScoreXAndScreenX, scorePaintY/2, playerDiamond.blueDiamondBitmap); //bitmap will be changed
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
        canvas.drawLine(0,screenY/14, screenX,screenY/14, paintLine);
        canvas.drawText(PLAYER_SCORE + " pts", scorePaintX,scorePaintY,paintScore);

        if(diamondCollectionNotVisible == false) { //shatter fully animated on collision, next spawn we will set it true
            diamondCollection.draw(canvas);
        }

        playerDiamond.draw(canvas);

        scoreKeeperShard.draw(canvas);

        if(diamondShard!=null) { //only appears upon shatter basically
            for(int i = 0; i < arrDiamondShards.size(); i++){
                arrDiamondShards.get(i).draw(canvas);
            }
        }

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
        diamondCollection.moveDiamondsDownScreen();

        if(feedScores){
            feedScores();
            if(System.currentTimeMillis() - currentTime > 250){  //every shard move sequentially
                initShardIndex++;
                currentTime = System.currentTimeMillis(); //current time gets updated every index
            }
            for(int i = 0; i < arrDiamondShards.size(); i++){
                if(Rect.intersects(arrDiamondShards.get(i).getRect(), scoreKeeperShard.getRect())){
                    arrDiamondShards.get(i).setVisible(false); //shards invisible after collide with scoreshard
                    arrDiamondShards.remove(i);
                    if(arrDiamondShards.isEmpty()){
                        feedScores = false;
                        initShardIndex = 0;
                    }
                }
            }
        }


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
                    scoreKeeperShard.setBitmap(diamondCollection.getBitmapAtIndex(i)); //score shard becomes color of shatter index
                    createArrayOfThisShard(randScoreNumsArr[i], diamondCollection.getXLocation(i), diamondCollection.getYLocation(i), i);
                    feedScores = true; //starts feeding now
                    PLAYER_SCORE += randScoreNumsArr[i]; //updating score with whatever color collision matched
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

    private void createArrayOfThisShard( int numberOfShards, int shatterXLocation, int shatterYLocation, int shatterIndex){
        for(int i = 0; i < numberOfShards; i++) {
            diamondShard = new DiamondShard(shatterXLocation, shatterYLocation, diamondCollection.getBitmapAtIndex(shatterIndex)); //initializing here
            arrDiamondShards.add(diamondShard); //adding same shard into array set amount of times
        }
    }

    private void blinkScoreShard(Bitmap bitmap){
        if(bitmap.sameAs(playerDiamond.redDiamondBitmap)){
            scoreKeeperShard.setBitmap(playerDiamond.redDiamondBlinkBitmap);
        }
        else if(bitmap.sameAs(playerDiamond.blueDiamondBitmap)){
            scoreKeeperShard.setBitmap(playerDiamond.blueDiamondBlinkBitmap);
        }
        else if(bitmap.sameAs(playerDiamond.purpleDiamondBitmap)){
            scoreKeeperShard.setBitmap(playerDiamond.purpleDiamondBlinkBitmap);
        }
        else if(bitmap.sameAs(playerDiamond.greenDiamondBitmap)){
            scoreKeeperShard.setBitmap(playerDiamond.greenDiamondBlinkBitmap);
        }
        else if(bitmap.sameAs(playerDiamond.yellowDiamondBitmap)){
            scoreKeeperShard.setBitmap(playerDiamond.yellowDiamondBlinkBitmap);
        }
    }

    private void feedScores(){

        int feedSpeed = 10;


        int numIterations = initShardIndex;

        if(numIterations > arrDiamondShards.size()){
            numIterations = arrDiamondShards.size(); //prevents from going over
        }

        for(int i = 0; i < numIterations; i++){
            int diamondShardYLocation = arrDiamondShards.get(i).yLocation;
            int diamondShardXLocation = arrDiamondShards.get(i).xLocation;
            int deltaX = Math.abs(scoreKeeperShard.xLocation-diamondShardXLocation); //target dest - start location
            int deltaY = Math.abs(scoreKeeperShard.yLocation-diamondShardYLocation); //target dest - start location
            double angle = Math.atan2(deltaY, deltaX);
            arrDiamondShards.get(i).yLocation -= feedSpeed * Math.sin(angle);
            arrDiamondShards.get(i).xLocation += feedSpeed * Math.cos(angle);
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


