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
    private boolean initStartBackgroundLocation1, initStartBackgroundLocation2 = false;
    int diamondScore;
    Background background1, background2;
    int gameTimerX;
    public boolean multiplierIsActive = false;
    int xMiddleOfScoreXAndScreenX;
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
    Rect rect;
    //Timer playerPullDownTimer;
    private boolean freezePlayerX = false;
    private boolean flickOccured = false;
    int paintAcrossXLine1X;
    int paintAcrossXLine2X;
    private int paintMultiplierX;
    private int CURR_MULTIPLIER = 1;
    Random randScore;
    Random randMultiplierScore;
    int multiplierTimeX;
    Paint paintAcrossXLine1;
    Paint paintAcrossXLine2;
    Paint paintAcrossYLine;
    Paint paintScore;
    Paint paintMultiplier;
    Paint paintMultiplierSeconds;
    private int diamondWidth;
    private int diamondHeight;
    int PLAYER_SCORE = 0;
    private boolean playerIsMoving = false;
    GestureDetector gestureDetector;
    int scorePaintX, scorePaintY;
    DiamondShard diamondShard;
    DiamondShard scoreKeeperShard;
    public ArrayList<DiamondShard> arrDiamondShards = new ArrayList<>();
    int initShardIndex = 0;
    Bitmap bitmapShardTransparent;
    Bitmap scoreKeeperShardBitmapColor;
    private boolean initScoreKeeperXFor2Digits = false;
    MyTimer MultiplierTimer;
    MyTimer GameTimer;
    int multiplierTimerSeconds;

    public GameView(Context context, int screenWidth, int screenHeight)  {
        super(context);
        MultiplierTimer = new MyTimer(10);
        GameTimer = new MyTimer(300);
        gestureDetector = new GestureDetector(this.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
        rect = new Rect();
        numDiamonds = 5;
        multiplierTimerSeconds = 0;
        randScore = new Random(System.currentTimeMillis()); //setting the seed
        randMultiplierScore = new Random(System.currentTimeMillis()); //setting seed
        diamondCollection = new DiamondCollection(context, numDiamonds, screenWidth, screenHeight);
        playerDiamond = new PlayerDiamond
                (context
                ,diamondCollection.get_at(0).getBitmapWidth()
                , diamondCollection.get_at(0).getBitmapHeight(),
                screenWidth,
                screenHeight);

        screenX = screenWidth;
        screenY = screenHeight;
        paintAcrossXLine1 = new Paint();
        paintAcrossXLine1.setColor(Color.WHITE);
        paintAcrossXLine1.setStrokeWidth(8);
        paintAcrossXLine2 = new Paint();
        paintAcrossXLine2.setColor(Color.WHITE);
        paintAcrossXLine2.setStrokeWidth(8);
        paintAcrossXLine1X = screenX/6;
        paintAcrossXLine2X = screenX/3;
        paintAcrossYLine = new Paint();
        paintAcrossYLine.setColor(Color.WHITE);
        paintAcrossYLine.setStrokeWidth(8);
        paintScore = new Paint();
        paintScore.setColor(Color.WHITE);
        paintScore.setTextSize(Math.round(screenWidth/14f));
        paintMultiplier = new Paint();
        paintMultiplier.setColor(Color.WHITE);
        paintMultiplier.setTextSize(Math.round(screenWidth/14f));
        paintMultiplier.setTextAlign(Paint.Align.CENTER);
        paintMultiplierX = paintAcrossXLine1X/2;
        multiplierTimeX = (paintAcrossXLine1X+paintAcrossXLine2X)/2 ;
        paintMultiplierSeconds = new Paint(); //multiplier timer paint
        diamondWidth = diamondCollection.getGameGrid().getAxisLength();
        diamondHeight = playerDiamond.getBitmapHeight();
        scorePaintX = (screenX*2)/3;
        xMiddleOfScoreXAndScreenX = (screenX + scorePaintX)/2;
        scorePaintY = screenY/18;
        gameTimerX = (paintAcrossXLine2X + screenX) / 2;
        bitmapShardTransparent = playerDiamond.transparentdiamond;
        scoreKeeperShard = new DiamondShard(xMiddleOfScoreXAndScreenX, scorePaintY/2, bitmapShardTransparent); //bitmap will be changed
        GameTimer.startTimer();
        background1 = new Background(screenX, screenY, context);
        background2 = new Background(screenX, screenY, context);
        background2.y = -screenY; //
        background1.y = 0;
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
        background1.draw(canvas);
        background2.draw(canvas);
        canvas.drawLine(0,screenY/14, screenX,screenY/14, paintAcrossYLine);
        canvas.drawLine(paintAcrossXLine1X, screenY/14, paintAcrossXLine1X, 0, paintAcrossXLine1);
        canvas.drawLine(paintAcrossXLine2X, screenY/14, paintAcrossXLine2X, 0, paintAcrossXLine2);
        canvas.drawText(PLAYER_SCORE + " pts", scorePaintX,scorePaintY,paintScore);
        canvas.drawText("X " + CURR_MULTIPLIER, paintMultiplierX, scorePaintY, paintMultiplier); //shows multiplier active
        canvas.drawText(": " + multiplierTimerSeconds, multiplierTimeX, scorePaintY, paintMultiplier); //fix values
        canvas.drawText("" + GameTimer.formatTime(GameTimer.getTimerSeconds(), GameTimer.getTimerMinutes()), screenX/2, scorePaintY, paintMultiplier); //fix values

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
    }

    private int RandGenerator(int lowerBound, int upperBound){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int num = rand.nextInt(upperBound-lowerBound) + lowerBound;
        return num;
    }

    public void updateScoreShardXLocation(){
        if(PLAYER_SCORE >= 10 && !initScoreKeeperXFor2Digits){
            scoreKeeperShard.xLocation +=40;
            initScoreKeeperXFor2Digits = true;
        }
    }

    public void backgroundLocationHandler(){
        int background2Y2 = background2.y +background2.getBackgroundHeight(); //rect bottom
        int background1Y2 = background1.y +background1.getBackgroundHeight(); //rect bottom

        background1.y+=1;
        background2.y+=1;

        if(background1.y >= screenY) { //top of background1 passes bottom of screen
            background1.y = -screenY;
        }

        if(background2.y >= screenY ){ //top of background1 passes bottom of screen
            background2.y = -screenY;

        }



    }

    public void update(){
        backgroundLocationHandler();

        updateScoreShardXLocation();
        diamondCollection.moveDiamondsDownScreen();
        diamondCollection.evaluateMultiplierActive(multiplierIsActive); ////setting all diamond multiplier to one so its inactive until timer up

        if(multiplierIsActive){
            multiplierTimerSeconds = MultiplierTimer.getTimerSeconds();
            if(multiplierTimerSeconds == 0){
                multiplierIsActive = false; //10 seconds passed so turn multiplier off
                MultiplierTimer.resetTimer();
            }
        }

        if(feedScores){
            feedScores();
            if(System.currentTimeMillis() - currentTime > 63){  //every shard move sequentially
                initShardIndex++; //spawning next shard
                currentTime = System.currentTimeMillis(); //current time gets updated every index
            }
            for(int i = 0; i < arrDiamondShards.size(); i++){
                if(Rect.intersects(arrDiamondShards.get(i).getRect(), scoreKeeperShard.getRect())){
                    scoreKeeperShard.setBitmap(scoreKeeperShardBitmapColor);
                    arrDiamondShards.get(i).setVisible(false); //shards invisible after collide with scoreshard
                    PLAYER_SCORE+=1; //every time shard collides it increments
                    arrDiamondShards.remove(i); //delete each element after collides, gets picked up by garbage collector
                    if(arrDiamondShards.isEmpty()){
                        scoreKeeperShard.setBitmap(bitmapShardTransparent);
                        feedScores = false; //reset the feedscores boolean when arraylist is empty
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
            if(playerAndCollectionCollisionOccurred(i)) { //player and collection collide
                if(diamondCollisionColorsMatched(i)){ //we only check for color match upon collision
                    diamondCollection.createDiamondShatterAnimator(i);
                    scoreKeeperShardBitmapColor = diamondCollection.getBitmapAtIndex(i); //marking color at shatter
                    if(diamondCollection.get_at(i).hasMultiplier() && !multiplierIsActive){ //collided with diamond that has multiplier
                        CURR_MULTIPLIER = diamondCollection.get_at(i).multiplier; //setting the multiplier to whatever diamond collided with
                        multiplierIsActive = true; //reset after 10 seconds
                        MultiplierTimer.startTimer();
                    }
                    else { //collided with diamond that doesnt have multiplier
                        diamondScore = diamondCollection.get_at(i).diamondScore;
                        if(multiplierIsActive){
                            diamondScore*=CURR_MULTIPLIER;
                        }
                        createArrayOfThisShard(diamondScore, diamondCollection.getCenterXLocation(i), diamondCollection.getYLocation(i), i);
                        feedScores = true; //starts feeding now
                    }

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
            diamondCollection.resetDiamondScoresArr(); //resetting scores after shatter
            diamondCollection.resetDiamondMultiplier(); //resetting multiplier after shatter
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

    private void feedScores(){
        int feedSpeed = 30;

        int numIterations = initShardIndex;

        if(numIterations > arrDiamondShards.size()){
            numIterations = arrDiamondShards.size(); //prevents from going over
        }


        for(int i = 0; i < numIterations; i++){
            int diamondShardYLocation = arrDiamondShards.get(i).yLocation;
            int diamondShardXLocation = arrDiamondShards.get(i).xLocation;
            if(!gameGrid.insideAxis(4, playerDiamond.getXLocation())) { //any index except the last one
                int deltaX = Math.abs(scoreKeeperShard.xLocation - diamondShardXLocation); //target dest - start location
                int deltaY = Math.abs(scoreKeeperShard.yLocation - diamondShardYLocation); //target dest - start location
                double angle = Math.atan2(deltaY, deltaX);
                arrDiamondShards.get(i).yLocation -= feedSpeed * Math.sin(angle); //works for all except last diamond index
                arrDiamondShards.get(i).xLocation += feedSpeed * Math.cos(angle);
            }
            else{ //for last index
                int deltaX = Math.abs(scoreKeeperShard.xLocation - diamondShardXLocation); //target dest - start location
                int deltaY = Math.abs(scoreKeeperShard.yLocation + diamondShardYLocation); //target dest - start location
                double angle = Math.atan2(deltaY, deltaX);
                arrDiamondShards.get(i).yLocation -= feedSpeed * Math.sin(angle); //works for all except last diamond index
                arrDiamondShards.get(i).xLocation -= feedSpeed * Math.cos(angle);
            }
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


