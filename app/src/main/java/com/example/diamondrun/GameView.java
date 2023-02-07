package com.example.diamondrun;

import static com.example.diamondrun.GameActivity.name;
import static com.example.diamondrun.GameActivity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GameView extends View implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    private boolean playerDiamondInactive = false;
    private long currTime;
    private boolean setRedFlashTime = false;
    private boolean initRespawn = false;
    private Bitmap redScreen;
    private int shatterIndex;
    private boolean dropPlayerDiamond = false;
    private Handler handler;
    int diamondScore;
    Background background1, background2;
    int gameTimerX;
    public boolean multiplierIsActive = false;
    int xMiddleOfScoreXAndScreenX;
    private long currentTime = System.currentTimeMillis();
    private boolean feedScores = false;
    private boolean initDiamondCollision = false;
    private boolean diamondCollectionNotVisible = false;
    private Runnable r;
    private DiamondCollection diamondCollection;
    private PlayerDiamond playerDiamond;
    private int numDiamonds = 5;
    private int screenX, screenY;
    private GameGrid gameGrid;
    Rect rect;
    private boolean flickOccured = false;

    private int CURR_MULTIPLIER = 1;
    Random randScore;
    Random randMultiplierScore;
    String text;

    Paint popUpScore;

    Paint paintScore;
    private int diamondWidth;
    private int diamondHeight;
    int PLAYER_SCORE = 0;
    int scorePopUpYLocation;
    GestureDetector gestureDetector;
    int scorePaintX, scorePaintY;
    DiamondShard diamondShard;
    DiamondShard scoreKeeperShard;
    public ArrayList<DiamondShard> arrDiamondShards = new ArrayList<>();
    int initShardIndex = 0;
    Bitmap bitmapShardTransparent;
    Bitmap scoreKeeperShardBitmapColor;
    private boolean initScoreKeeperXFor2Digits = false;
    private boolean initScoreKeeperXFor3Digits = false;
    MyTimer MultiplierTimer;
    MyTimer GameTimer;
    int multiplierTimerSeconds;
    boolean initScorePopUpYLocation = false;
    int multiplierAtShatterIndex;
    int distBetwScoreXScoreShardX;
    int topScreenWidgetWidth;
    int topScreenWidgetY;
    int scorePaintHeight;
    private SoundPlayer sound;
    Paint paintRedScreen;
    private boolean initRedScreenFlash = false;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    User user1;
    User user2;


    //DatabaseReference databaseReference = database.getReference("Username");



    public GameView(Context context, int screenWidth, int screenHeight, User user, DatabaseReference databaseReference)  {
        super(context);

        database = FirebaseDatabase.getInstance();
        this.databaseReference = databaseReference;
        this.user1 = user;
        MultiplierTimer = new MyTimer(10);
        sound = new SoundPlayer(this.getContext());
        GameTimer = new MyTimer(60);
        gestureDetector = new GestureDetector(this.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
        rect = new Rect();
        numDiamonds = 5;
        multiplierTimerSeconds = 0;
        randScore = new Random(System.currentTimeMillis()); //setting the seed
        randMultiplierScore = new Random(System.currentTimeMillis()); //setting seed
        gameGrid = new GameGrid(numDiamonds, screenWidth, context, screenHeight);
        diamondCollection = new DiamondCollection(this.getContext(), numDiamonds, screenWidth, screenHeight, gameGrid);
        gameGrid.dc = this.diamondCollection;
        playerDiamond = new PlayerDiamond
                (context
                ,diamondCollection.get_at(0).getBitmapWidth()
                , diamondCollection.get_at(0).getBitmapHeight(),
                screenWidth,
                screenHeight);
        screenX = screenWidth;
        screenY = screenHeight;
        popUpScore = new Paint();
        Typeface typefaceBadaboom = ResourcesCompat.getFont(context, R.font.badaboom);
        popUpScore.setTypeface(typefaceBadaboom);
        popUpScore.setTextAlign(Paint.Align.CENTER);
        popUpScore.setTextSize(Math.round(screenWidth/7f));
        redScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gridred), screenWidth, screenHeight, true);
        paintRedScreen = new Paint();
        paintRedScreen.setAlpha(50);
        paintScore = new Paint();
        paintScore.setColor(Color.WHITE);
        paintScore.setTextSize(Math.round(screenWidth/10f));
        paintScore.setTypeface(typefaceBadaboom);
        paintScore.setTextAlign(Paint.Align.CENTER);
        diamondWidth = gameGrid.getAxisLength();
        diamondHeight = playerDiamond.getBitmapHeight();
        Rect bounds = new Rect();
        text = " pts";
        paintScore.getTextBounds(text, 0, text.length(), bounds);

        int text_height =  bounds.height();
        int text_width =  bounds.width();
        scorePaintY = screenY/18;
        gameTimerX =  (screenX/2 );
        scorePaintX = (screenX) - text_width;
        bitmapShardTransparent = playerDiamond.transparentdiamond;
        xMiddleOfScoreXAndScreenX = (screenX *3)/4 -bitmapShardTransparent.getWidth()/6 ;
        scorePaintHeight = (int) paintScore.getTextSize();
        scoreKeeperShard = new DiamondShard(xMiddleOfScoreXAndScreenX, scorePaintY/2, bitmapShardTransparent); //bitmap will be changed
        GameTimer.startTimer();
        background1 = new Background(screenX, screenY, context);
        background2 = new Background(screenX, screenY, context);
        background2.y = -screenY; //
        background1.y = 0;
        int scoreKeeperShardX2 = scoreKeeperShard.xLocation + scoreKeeperShard.bitmapWidth;
        topScreenWidgetY = scorePaintY - scoreKeeperShard.yLocation;
        //int timerX = time
        distBetwScoreXScoreShardX = (scoreKeeperShardX2 - scorePaintX);
        topScreenWidgetWidth = distBetwScoreXScoreShardX;

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

        /*for(int i = 0; i < numDiamonds; i++){
            if(diamondCollection.get_at(i).bitmap != null){
                gameGrid.draw(canvas);
            }
        }*/
        if(diamondCollectionNotVisible == false) { //shatter fully animated on collision, next spawn we will set it true
            diamondCollection.draw(canvas);
        }

        if(diamondShard!=null) { //only appears upon shatter
            for(int i = 0; i < arrDiamondShards.size(); i++){
                arrDiamondShards.get(i).draw(canvas);
            }
        }

        manageScorePopUpPaint(canvas);
        //gameGrid.draw(canvas);
        /*canvas.drawLine(0,screenY/14, screenX,screenY/14, paintAcrossYLine);
        canvas.drawLine(paintAcrossXLine1X, screenY/14, paintAcrossXLine1X, 0, paintAcrossXLine1);
        canvas.drawLine(paintAcrossXLine2X, screenY/14, paintAcrossXLine2X, 0, paintAcrossXLine2);*/
        canvas.drawText(PLAYER_SCORE + text, scorePaintX,scorePaintY,paintScore);
        /*canvas.drawText("X " + CURR_MULTIPLIER, paintMultiplierX, scorePaintY, paintMultiplier); //shows multiplier active
        canvas.drawText(": " + multiplierTimerSeconds, multiplierTimeX, scorePaintY, paintMultiplier); //fix values*/
        canvas.drawText("" + GameTimer.formatTime(GameTimer.getTimerSeconds(), GameTimer.getTimerMinutes()), gameTimerX, scorePaintY, paintScore); //fix values


        if(initRedScreenFlash) { //gets init when hits wrong color diamond
            evaluateScreenBlink(canvas);
        }

        playerDiamond.draw(canvas);

        scoreKeeperShard.draw(canvas);




    }

    private int RandGenerator(int lowerBound, int upperBound){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int num = rand.nextInt(upperBound-lowerBound) + lowerBound;
        return num;
    }

    public void updateScoreShardXLocation(){
        if(PLAYER_SCORE >= 10 && !initScoreKeeperXFor2Digits){ //arbitrary fix later
            scoreKeeperShard.xLocation +=40;
            initScoreKeeperXFor2Digits = true;
        }
        if(PLAYER_SCORE >= 100 && !initScoreKeeperXFor3Digits){ //arbitrary fix later
            scoreKeeperShard.xLocation +=40;
            initScoreKeeperXFor3Digits = true;
        }

    }


    public void backgroundLocationHandler(){
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

        handleGameRounds();
        playerDiamond.setPlayerDiamondInactive(playerDiamondInactive);

        backgroundLocationHandler();
        scorePopUpYLocation-=1;
        diamondCollection.moveDiamondsDownScreen();
        //diamondCollection.evaluateMultiplierActive(multiplierIsActive); ////setting all diamond multiplier to one so its inactive until timer up

        if(dropPlayerDiamond){
            playerDiamond.fallDownScreen(); //makes player diamond fall off screen
        }

        if(multiplierIsActive){
            multiplierTimerSeconds = MultiplierTimer.getTimerSeconds();
            if(multiplierTimerSeconds == 0){
                multiplierIsActive = false; //10 seconds passed so turn multiplier off
                MultiplierTimer.resetTimer(10);
                CURR_MULTIPLIER = 1; //resetting after timer over
            }
        }

        if(initRespawn){
            movePlayerDiamondToSpawn();
            //initRespawn = false;
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

        if(evaluateDiamondCollectionPassedPlayer()){
            playerDiamondInactive = false; //make diamond active again since collection passed over
        }

        if(diamondCollection.diamondCollectionMovedBelowScreen()){ //continue respawning diamonds from top of screen everytime they go below screen
            diamondCollection.resetYLocation(); //back to start
            diamondCollection.moveDiamondsDownScreen();
        }
        //checking for collision
        for(int i = 0; i < numDiamonds; i++) {
            if(!initDiamondCollision && !playerDiamondInactive) { // prevents glitching on collision at bottom
                if (playerAndCollectionCollisionOccurred(i)) { //player and collection collide
                    initDiamondCollision = true;
                    if (diamondCollisionColorsMatched(i)) { //we only check for color match upon collision
                        diamondCollection.createDiamondShatterAnimator(i);
                        sound.playGlassBreakingSound();
                        playerDiamond.resetYLocation();
                        if (diamondCollection.getCollectionSpeed() < diamondCollection.diamondCollectionMaxSpeed) {
                            diamondCollection.increaseSpeed(); //speed can increase upon shatter as long as its not greater than max speed
                        }
                        shatterIndex = i;
                        scoreKeeperShardBitmapColor = diamondCollection.getBitmapAtIndex(i); //marking color at shatter
                        if (diamondCollection.get_at(i).hasMultiplier() && !multiplierIsActive) { //collided with diamond that has multiplier
                            CURR_MULTIPLIER = diamondCollection.get_at(i).multiplier; //setting the multiplier to whatever diamond collided with
                            multiplierIsActive = true; //reset after 10 seconds
                            MultiplierTimer.startTimer();
                        } else { //collided with diamond that doesnt have multiplier
                            diamondScore = diamondCollection.get_at(i).diamondScore;
                            createArrayOfThisShard(diamondScore, diamondCollection.getCenterXLocation(i), diamondCollection.getYLocation(i), i);
                            feedScores = true; //starts feeding now
                        }
                    }
                    else { //collision occured but not same color
                        sound.playErrorSound();
                        initRespawn = true; //starting respawn to baseline
                        playerDiamondInactive = true; //wrong color so make diamond inactive

                        initRedScreenFlash = true;

                        if (diamondCollection.getCollectionSpeed() > diamondCollection.diamondCollectionMinSpeed) {
                            diamondCollection.decreaseSpeed(); //speed can decrease when he hits wrong color as long as not less than min speed
                        }
                        if (playerDiamond.getYLocation() == playerDiamond.getBaseStartLineY()) { //collided with wrong color at baseline so knock player off screen
                            dropPlayerDiamond = true; //makes player diamond fall off screen
                        }
                    }
                    if (flickOccured) { //if flick occurred and collided with diamond, bounce it back to start

                        flickOccured = false; //resets upon collision and launch
                        initDiamondCollision = false; //collision occurred without shatter we have to reset it
                    }

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
            //diamondCollection.resetDiamondMultiplier(); //resetting multiplier after shatter
            diamondCollectionNotVisible = false; //make visible again
            diamondCollection.moveDiamondsDownScreen();
            initScorePopUpYLocation = false; //resetting for use again
            initDiamondCollision = false; //shatter ended after collision so reset
        }
    }

    private boolean evaluateDiamondCollectionPassedPlayer(){
        for(int i = 0; i < numDiamonds; i++) {
            if (diamondCollection.getYLocation(i) >= playerDiamond.getRect().bottom) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    private void manageScorePopUpPaint(Canvas canvas){
        boolean drawScorePopUpPaint = diamondCollection.startShatter;

        if(drawScorePopUpPaint){ //occurs only on shatter

            Diamond d = diamondCollection.get_at(shatterIndex); //diamond at shatter index
            Bitmap bitmapColorAtShatter = diamondCollection.getBitmapAtIndex(shatterIndex); //bitmap at shatter index
            int diamondXLocation = diamondCollection.getCenterXLocation(shatterIndex);
            if(!initScorePopUpYLocation) { //to prevent y location from going back to where diamond y is
                multiplierAtShatterIndex = d.multiplier; // store multiplier value at shatter index before it resets
                scorePopUpYLocation = diamondCollection.getYLocation(shatterIndex);
                initScorePopUpYLocation = true;
            }

            if(bitmapColorAtShatter.sameAs(playerDiamond.redDiamondBitmap)){
                popUpScore.setColor(getResources().getColor(R.color.red));
            }
            else if(bitmapColorAtShatter.sameAs(playerDiamond.greenDiamondBitmap)){
                popUpScore.setColor(getResources().getColor(R.color.green));
            }
            else if(bitmapColorAtShatter.sameAs(playerDiamond.purpleDiamondBitmap)){
                popUpScore.setColor(getResources().getColor(R.color.purple));
            }
            else if(bitmapColorAtShatter.sameAs(playerDiamond.blueDiamondBitmap)){
                popUpScore.setColor(getResources().getColor(R.color.blue));
            }
            else if(bitmapColorAtShatter.sameAs(playerDiamond.yellowDiamondBitmap)){
                popUpScore.setColor(getResources().getColor(R.color.yellow));
            }
            if(multiplierAtShatterIndex == 1){ //if diamond at shatter doesnt have multiplier
                int popScore = diamondCollection.get_at(shatterIndex).diamondScore * CURR_MULTIPLIER;
                canvas.drawText("" + popScore, diamondXLocation, scorePopUpYLocation, popUpScore);
            }
            else{ //diamond at shatter has multiplier
                int popScore = CURR_MULTIPLIER;
                canvas.drawText("X " + popScore, diamondXLocation, scorePopUpYLocation, popUpScore);
            }

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

    private void movePlayerDiamondToSpawn(){
        int moveSpeed = 40;

        int playerDiamondYLocation = playerDiamond.getYLocation();
        int playerDiamondXLocation = playerDiamond.getXLocation();
        if(!gameGrid.insideAxis(3, playerDiamond.getXLocation()) && !gameGrid.insideAxis(4, playerDiamond.getXLocation())&& !gameGrid.insideAxis(2, playerDiamond.getXLocation())) { //for index 0, 1
            int deltaX = Math.abs(playerDiamond.getBaseStartLineX() - playerDiamondXLocation); //target dest - start location
            int deltaY = Math.abs(playerDiamond.getBaseStartLineY() - playerDiamondYLocation); //target dest - start location
            double angle = Math.atan2(deltaY, deltaX);
            playerDiamond.yLocation += moveSpeed * Math.sin(angle); //works for all except last diamond index
            playerDiamond.xLocation += moveSpeed * Math.cos(angle);
        }
        else{ //for index 2, 3, and 4
            int deltaX = Math.abs(playerDiamond.getBaseStartLineX() - playerDiamondXLocation); //target dest - start location
            int deltaY = Math.abs(playerDiamond.getBaseStartLineY() - playerDiamondYLocation); //target dest - start location
            double angle = Math.atan2(deltaY, deltaX);
            playerDiamond.yLocation += moveSpeed * Math.sin(angle); //works for all except last diamond index
            playerDiamond.xLocation -= moveSpeed * Math.cos(angle);
        }
        if(playerDiamond.yLocation >= playerDiamond.getBaseStartLineY()){ //if playerdiamond moved below baseline then snapback
            playerDiamond.resetYLocation(); //snapping back into place
            playerDiamond.resetXLocation();
            initRespawn = false; //stops respawn movement
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

    private void movePlayerDiamondIntoRightAxis(){
        int currPlayerAxisIndex = gameGrid.getCurrAxis(playerDiamond.getXLocation());
        if(currPlayerAxisIndex < gameGrid.numAxis) { // will move him as long as not greater than largest axis index
            playerDiamond.setXLocation(diamondCollection.getXLocation(currPlayerAxisIndex + 1)); //set him to same x location as specific diamondCollection index + 1
        }
    }

    private void movePlayerDiamondIntoLeftAxis(){
        int currPlayerAxisIndex = gameGrid.getCurrAxis(playerDiamond.getXLocation());
        if(currPlayerAxisIndex > 0) { // will move him as long as not less than smallest axis index
            playerDiamond.setXLocation(diamondCollection.getXLocation(currPlayerAxisIndex - 1)); //set him to same x location as specific diamondCollection index + 1
        }
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


    private void evaluateScreenBlink(Canvas canvas){

        if(!setRedFlashTime) { //prevents from resetting everytime it goes in
            currTime = System.currentTimeMillis();
            setRedFlashTime = true;
        }

        canvas.drawBitmap(redScreen, 0, 0, paintRedScreen); //drawing blink to canvas


        if(System.currentTimeMillis() - currTime > 250){
            initRedScreenFlash = false; //half a second passed so stop
            setRedFlashTime = false; //resetting timer till next use
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        playerDiamond.getChangedColorBitmap(); //changes player diamond color to a random color thats not the same
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(!playerFlickedFingerUp(e1, e2) && !playerDiamondInactive) {
            onFingerSlidingLeftOrRight(e2); //moves cleanly across x into correct axis
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private boolean playerFlickedFingerUp(MotionEvent e1, MotionEvent e2){
        if(distance((int)e1.getY(), (int)e2.getY()) > 150 ){ //make universal later
            return true;
        }
        else{
            return false;
        }
    }

    private boolean playerSwipedRight(MotionEvent e1, MotionEvent e2){
        if(!playerFlickedFingerUp(e1, e2)) { //prevents from swiping right while flicking up
            if (distance((int) e1.getX(), (int) e2.getX()) > 100 && e1.getX() < e2.getX()) { //make universal later
                return true;
            }
        }
        else{
            return false;
        }
        return false;
    }

    private boolean playerSwipedLeft(MotionEvent e1, MotionEvent e2){
        if(!playerFlickedFingerUp(e1, e2)) {
            if (distance((int) e1.getX(), (int) e2.getX()) > 100 && e1.getX() > e2.getX()) { //make universal later
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(playerFlickedFingerUp(e1,e2) && !playerDiamondInactive) { //ONLY launches when he flicks across y axis
            flickOccured = true; //starts the launch
        }
        /*if(playerSwipedRight(e1, e2)){
            movePlayerDiamondIntoRightAxis();
        }
        if(playerSwipedLeft(e1, e2)){
            movePlayerDiamondIntoLeftAxis();
        }*/

        return false;
    }

    public void onFingerSlidingLeftOrRight(MotionEvent e2) {

        for(int i = 0; i < gameGrid.numAxis; i++){
            if (gameGrid.insideAxis(i, (int) e2.getX())) { //if his finger inside axis 5
                movePlayerDiamondIntoCenterAxis(i); //move him to center of this axis
            }
        }
    }

    public void handleGameRounds(){

        int turns = 0;

        if(GameTimer.getTimerSeconds() == 50){

            //saving user score at end of game
            user1.setScore(PLAYER_SCORE);

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("score", user1.getScore());
            hashMap.put("name", user1.getName());

            //putting hashmap values into database as two children
            databaseReference.child("Users").child(user1.getName()).updateChildren(hashMap);



        }
    }

}


